package antlapit.near.api.borsh

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.KeyType
import antlapit.near.api.providers.model.primitives.PublicKey
import antlapit.near.api.providers.model.primitives.Signature
import antlapit.near.api.providers.model.primitives.decodeBase58
import antlapit.near.api.providers.model.transaction.SignedTransaction
import antlapit.near.api.providers.model.transaction.Transaction
import org.near.borshj.Borsh
import java.math.BigInteger
import java.util.*

data class TransactionBorsh(
    val signerId: String,
    val publicKey: PublicKeyBorsh,
    val nonce: Long,
    val receiverId: String,
    val hash: BorshArray32,
    val actions: List<BorshEnum>,
) : Borsh

fun Transaction.toBorsh() = TransactionBorsh(
    signerId = signerId,
    publicKey = publicKey.toBorsh(),
    nonce = nonce,
    receiverId = receiverId,
    hash = BorshArray32(hash.decodeBase58()),
    actions = actions.map {
        BorshEnum(
            it.stableIndex(),
            it.toBorsh()
        )
    }
)

data class PublicKeyBorsh(val keyType: Byte, val data: BorshArray32) : Borsh

fun PublicKey.toBorsh() = PublicKeyBorsh(keyType = keyType.stableIndex(), data = BorshArray32(decodeBase58()))

fun KeyType.stableIndex(): Byte = when (this) {
    KeyType.ed25519 -> 0
}


data class SignedTransactionBorsh(val transaction: TransactionBorsh/*, val signature: SignatureBorsh*/) : Borsh

fun SignedTransaction.toBorsh() =
    SignedTransactionBorsh(transaction = (this as Transaction).toBorsh()/*, signature = signature.toBorsh()*/)


data class SignatureBorsh(val keyType: Byte, val data: BorshArray64) : Borsh

fun Signature.toBorsh() = SignatureBorsh(
    keyType = 0, // TODO KeyType ??
    data = BorshArray64(decodeBase58())
)

data class AccessKeyBorsh(val nonce: Long, val permission: BorshEnum) : Borsh

fun AccessKey.toBorsh(): AccessKeyBorsh = AccessKeyBorsh(
    nonce = nonce,
    permission = BorshEnum(
        permission.stableIndex(),
        permission.toBorsh()
    )
)

fun AccessKeyPermission.stableIndex(): Byte = when (this) {
    is AccessKeyPermission.FunctionCall -> 0
    AccessKeyPermission.FullAccess -> 1
}

fun AccessKeyPermission.toBorsh(): Borsh = when (this) {
    is AccessKeyPermission.FunctionCall -> toBorsh()
    AccessKeyPermission.FullAccess -> FullAccessPermissionBorsh()
}

fun AccessKeyPermission.FunctionCall.toBorsh() = FunctionCallPermissionBorsh(
    allowance = Optional.ofNullable(allowance),
    receiverId = receiverId,
    methodNames = methodNames
)

data class FunctionCallPermissionBorsh(
    val allowance: Optional<BigInteger>,
    val receiverId: String,
    val methodNames: List<String>
) : Borsh

class FullAccessPermissionBorsh : Borsh

fun Action.stableIndex(): Byte = when (this) {
    Action.CreateAccount -> 0
    is Action.AddKey -> 5
    is Action.DeleteAccount -> 7
    is Action.DeleteKey -> 6
    is Action.DeployContract -> 1
    is Action.FunctionCall -> 2
    is Action.Stake -> 4
    is Action.StakeChunkOnly -> 8
    is Action.Transfer -> 3
}

fun Action.toBorsh(): Borsh = when (this) {
    Action.CreateAccount -> CreateAccountBorsh()
    is Action.AddKey -> toBorsh()
    is Action.DeleteAccount -> toBorsh()
    is Action.DeleteKey -> toBorsh()
    is Action.DeployContract -> toBorsh()
    is Action.FunctionCall -> toBorsh()
    is Action.Stake -> toBorsh()
    is Action.StakeChunkOnly -> toBorsh()
    is Action.Transfer -> toBorsh()
}

fun Action.AddKey.toBorsh() = AddKeyBorsh(publicKey = publicKey.toBorsh(), accessKey = accessKey.toBorsh())
fun Action.DeleteAccount.toBorsh() = DeleteAccountBorsh(beneficiaryId = beneficiaryId)
fun Action.DeleteKey.toBorsh() = DeleteKeyBorsh(publicKey = publicKey.toBorsh())
fun Action.DeployContract.toBorsh() = DeployContractBorsh(code = code.toByteArray().toList())
fun Action.FunctionCall.toBorsh() = FunctionCallBorsh(
    methodName = methodName,
    args = args.toByteArray().toList(),
    gas = gas,
    deposit = deposit
)

fun Action.Stake.toBorsh() = StakeBorsh(stake = stake, publicKey = publicKey.toBorsh())
fun Action.StakeChunkOnly.toBorsh() = StakeChunkOnlyBorsh(stake = stake, publicKey = publicKey.toBorsh())
fun Action.Transfer.toBorsh() = TransferBorsh(deposit = deposit)

class CreateAccountBorsh : Borsh

data class DeployContractBorsh(val code: List<Byte>) : Borsh

data class DeleteAccountBorsh(val beneficiaryId: String) : Borsh

data class FunctionCallBorsh(
    val methodName: String,
    val args: List<Byte>,
    val gas: Long,
    val deposit: BigInteger,
) : Borsh

data class TransferBorsh(
    val deposit: BigInteger
) : Borsh

data class StakeBorsh(
    val stake: BigInteger,
    val publicKey: PublicKeyBorsh,
) : Borsh

data class AddKeyBorsh(
    val publicKey: PublicKeyBorsh,
    val accessKey: AccessKeyBorsh,
) : Borsh

data class DeleteKeyBorsh(
    val publicKey: PublicKeyBorsh,
) : Borsh

data class StakeChunkOnlyBorsh(
    val stake: BigInteger,
    val publicKey: PublicKeyBorsh,
) : Borsh
