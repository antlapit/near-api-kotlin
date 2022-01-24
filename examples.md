# Examples

Examples of using [`NEAR API Kotlin`]

## Configuration
To use Near Kotlin API you should configure:
* **JsonRpcProvider** for required network 
* **RPC endpoints** for required methods 

### JsonRpcProvider
```kotlin
// configure JsonRpcProvider
val jsonRpcProvider = JsonRpcProvider(
    config = JsonRpcConfig(
        network = NetworkEnum.TESTNET
    )
)
```

### RPC endpoints
```kotlin
// configure endpoint for executing transactions RPC methods
val transactionEndpoint = TransactionsRpcEndpoint(jsonRpcProvider)

// configure endpoint for executing blocks RPC methods
val blockEndpoint = BlockRpcEndpoint(jsonRpcProvider)

// configure endpoint for executing access keys RPC methods
val accessKeyEndpoint = AccessKeysRpcEndpoint(jsonRpcProvider)
```

See other available endpoints in [`NEAR API Kotlin`]. 

## Signer
All examples that required transaction signing use reference Signer implementation with [`TweetNacl Java`]. 

### Sign transaction object
```kotlin
fun sign(
    transaction: Transaction,
    publicKey: PublicKey,
    secretKey: String
): SignedTransaction {
    val message = transaction.encode() // encode transaction object in Borsch format
    val hash = message.sha256() // calculate hash for signing object
    
    // init TweetNacl key pair
    val keys = TweetNacl.Signature(publicKey.decodeBase58(), secretKey.decodeBase58())
    
    // sign hash
    val signature = keys.detached(hash)
    return SignedTransaction(
        transaction = transaction,
        signature = TransactionSignature(signature = signature)
    )
}
```

### Generate new key pair
```kotlin
fun newKeyPair(): Pair<PublicKey, String> {
    val keyPair = TweetNacl.Signature.keyPair()
    val publicKey = PublicKey(keyPair.publicKey.encodeToBase58String())
    val secretKey = keyPair.secretKey.encodeToBase58String()
    return Pair(publicKey, secretKey)
}
```

## Account
**Note:** all examples use Account object with AccountId and key pair.
```kotlin
data class Account(
    val accountId: AccountId,
    val publicKey: PublicKey,
    val secretKey: String
)

val account = Account(
    accountId = "example-account.testnet",
    publicKey = PublicKey("ed25519:EJW25JSD65qbxt19nMUBV2zTgTeofJjcquSCAC7YjEUp"),
    secretKey = "3uzpik5hBUx82UfQqsDNWivRCd4PMnLrmTKB59v1Khb9yubWdhHkgWtiwJV4JrgndPb5PxmZQeJyiRiMEffY7gVn"
)
```

### Load Account
[`RPC configuration`](#Configuration)

```kotlin
// load account by id
suspend fun loadAccount(accountId: AccountId): AccountInBlock {
    return contractEndpoint.getAccount(accountId)
}

val result = runBlocking {
    loadAccount(
        accountId = "example-account.testnet"
    )
}
println(result)
```

### Create Account
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// creates a new account using funds from the account used to create it
suspend fun createAccount(parentAccount: Account, newAccountId: AccountId, initialBalance: Balance): FinalExecutionOutcome {
    val pair = signer.newKeyPair()
    val newPublicKey = pair.first
    return signAndSendTransaction(
        signer = parentAccount,
        receiverId = newAccountId,
        actions = listOf(
            Action.CreateAccount,
            Action.Transfer(deposit = initialBalance),
            Action.AddKey(publicKey = newPublicKey, accessKey = AccessKey.fullAccessKey())
        )
    )
}

val result = runBlocking {
    createAccount(
        parentAccount = account,
        newAccountId = "new.example-account.testnet",
        initialBalance = BigInteger("100000000000000000")
    )
}
println(result)
```

### Delete Account
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// deletes account found in the `account` object
// transfers remaining account balance to the accountId passed as an argument
suspend fun deleteAccount(account: Account, beneficiaryId: AccountId): FinalExecutionOutcome {
    return signAndSendTransaction(
        signer = account,
        receiverId = account.accountId,
        actions = listOf(
            Action.DeleteAccount(beneficiaryId)
        )
    )
}

val result = runBlocking {
    deleteAccount(
        account = account,
        beneficiaryId = "beneficiary-account.testnet"
    )
}
println(result)
```

### Send Tokens
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// sends NEAR tokens
suspend fun sendMoney(sender: Account, receiverId: AccountId, amount: Balance): FinalExecutionOutcome {
    return signAndSendTransaction(
        signer = sender,
        receiverId = receiverId,
        actions = listOf(
            Action.Transfer(deposit = amount)
        )
    )
}

val result = runBlocking {
    sendMoney(
        sender = account,
        receiverId = "receiver-account.testnet",
        amount = BigInteger("100000000000000000")
    )
}
println(result)
```
### Stake tokens
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// stake NEAR tokens from signer account
suspend fun stake(signer: Account, amount: Balance, publicKey: PublicKey): FinalExecutionOutcome {
    return signAndSendTransaction(
        signerAccount = signer,
        receiverId = signer.accountId,
        actions = listOf(
            Action.Stake(
                stake = amount,
                publicKey = publicKey
            )
        )
    )
}

val result = runBlocking {
    stake(
        signer = account,
        amount = BigInteger("100000000000000000"),
        publicKey = PublicKey("ed25519:8hSHprDq2StXwMtNd43wDTXQYsjXcD4MJTXQYsjXcc"),
    )
}
println(result)
```

### Deploy Contract
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// deploys contract into account
suspend fun deployContract(
    account: Account,
    contractCode: String
): FinalExecutionOutcome {
    return signAndSendTransaction(
        signerAccount = account,
        receiverId = account.accountId,
        actions = listOf(
            Action.DeployContract(
                code = contractCode
            )
        )
    )
}

val contractCodeRaw =
    Base64.getEncoder().encode(File("path_to_contract/contract.wasm").readBytes())

val result = runBlocking {
    deployContract(
        account = account,
        contractCode = String(contractCodeRaw)
    )
}
println(result)
```

## Contract

### Call Contract

Change Method:
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// call contract function that changes state
suspend fun callChangeFunction(account: Account, contractId: AccountId, methodName: String,
                               args: String, gas: Gas, deposit: Balance
): FinalExecutionOutcome {
    return signAndSendTransaction(
        signerAccount = account,
        receiverId = contractId,
        actions = listOf(
            Action.FunctionCall(
                methodName = methodName,
                args = args,
                gas = gas,
                deposit = deposit
            )
        )
    )
}

val result = runBlocking {
    callChangeFunction(
        account = account,
        contractId = "example-contract.testnet",
        methodName = "example_method",
        args = """
                    {
                       arg_name: "value", // argument name and value - pass empty object if no args required
                    }
                """.trimIndent(),
        gas = 300000000000000, // attached GAS (optional)
        deposit = BigInteger("1000000000000000000000000") // attached deposit in yoctoNEAR (optional)
    )
}
println(result)
```

View Method:
[`RPC configuration`](#Configuration)

```kotlin
// call view function without state change
suspend fun callViewFunction(contractId: AccountId, methodName: String, args: String): CallResult {
    return contractEndpoint.callFunction(
        accountId = contractId,
        methodName = methodName,
        args = args
    )
}

val result = runBlocking {
    callViewFunction(
        contractId = "example-contract.testnet",
        methodName = "example_method",
        args = """
            {
               arg_name: "value", // argument name and value - pass empty object if no args required
            }
        """.trimIndent()
    )
}
println(result)
```

## Access Keys

### Add Full Access Key
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// add full access key with the public key
suspend fun addFullAccessKey(account: Account, publicKey: PublicKey): FinalExecutionOutcome {
    return signAndSendTransaction(
        signer = account,
        receiverId = account.accountId,
        actions = listOf(
            Action.AddKey(
                publicKey = publicKey,
                accessKey = AccessKey.fullAccessKey()
            )
        )
    )
}

val result = runBlocking {
    addFullAccessKey(
        account = account,
        PublicKey("ed25519:8hSHprDq2StXwMtNd43wDTXQYsjXcD4MJTXQYsjXcc"),
    )
}
println(result)
```

### Add Function Access Key
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// adds function access key
suspend fun addFunctionalCallAccessKey(
    account: Account,
    publicKey: PublicKey,
    receiverId: AccountId,
    methodNames: List<String>,
    allowance: Balance?
): FinalExecutionOutcome {
    return signAndSendTransaction(
        signer = account,
        receiverId = account.accountId,
        actions = listOf(
            Action.AddKey(
                publicKey = publicKey,
                accessKey = AccessKey.functionCallAccessKey(
                    receiverId = receiverId,
                    methodNames = methodNames,
                    allowance = allowance
                )
            )
        )
    )
}

val result = runBlocking {
    addFunctionalCallAccessKey(
        account = account,
        publicKey = PublicKey("ed25519:8hSHprDq2StXwMtNd43wDTXQYsjXcD4MJTXQYsjXcc"), // public key for new account
        receiverId = "key-request-account.testnet", // contract this key is allowed to call
        methodNames = listOf("example_method"), // methods this key is allowed to call (can be empty)
        allowance = BigInteger("2500000000000") // allowance key can use to call methods (optional)
    )
}
println(result)
```

### Get All Access Keys
[`RPC configuration`](#Configuration)
[`Account setup`](#Account)

```kotlin
// returns all access keys associated with an account
suspend fun getAllAccessKeys(accountId: AccountId): AccessKeysContainer {
    return accessKeyEndpoint.getAccessKeyList(accountId)
}

val result = runBlocking {
    getAllAccessKeys(
        accountId = "example-account.testnet",
    )
}
println(result)
```

### Delete Access Key
[`RPC configuration`](#Configuration)
[`Signer implementationt`](#Signer)
[`Account setup`](#Account)

```kotlin
// deletes access key by public key
suspend fun deleteKey(account: Account, publicKey: PublicKey): FinalExecutionOutcome {
    return signAndSendTransaction(
        signer = account,
        receiverId = account.accountId,
        actions = listOf(
            Action.DeleteKey(publicKey = publicKey)
        )
    )
}

val result = runBlocking {
    deleteKey(
        account = account,
        PublicKey("ed25519:8hSHprDq2StXwMtNd43wDTXQYsjXcD4MJTXQYsjXcc"),
    )
}
println(result)
```

[`NEAR API Kotlin`]: https://github.com/antlapit/near-api-kotlin
[`TweetNacl Java`]: https://github.com/InstantWebP2P/tweetnacl-java
