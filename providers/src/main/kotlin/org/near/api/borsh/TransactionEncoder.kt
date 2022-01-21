package org.near.api.borsh

import org.near.api.model.block.Action
import org.near.api.model.primitives.KeyType
import org.near.api.model.primitives.PublicKey
import org.near.api.model.primitives.decodeBase58
import org.near.api.model.transaction.SignedTransaction
import org.near.api.model.transaction.Transaction
import org.near.api.model.transaction.TransactionSignature
import org.near.borshj.Borsh
import java.math.BigInteger
import java.util.*

data class TransactionBorsh(
    val signerId: String,
    val publicKey: PublicKeyBorsh,
    val nonce: Long,
    val receiverId: String,
    val blockHash: BorshArray32,
    val actions: List<BorshEnum>,
) : Borsh {

    constructor(transaction: Transaction) : this(
        signerId = transaction.signerId,
        publicKey = PublicKeyBorsh(transaction.publicKey),
        nonce = transaction.nonce,
        receiverId = transaction.receiverId,
        blockHash = BorshArray32(transaction.blockHash.decodeBase58()),
        actions = transaction.actions.map {
            BorshEnum(
                it.stableIndex(),
                it.toBorsh()
            )
        }
    )

    fun encode(): ByteArray {
        return Borsh.serialize(this)
    }
}

fun Transaction.encode() = TransactionBorsh(this).encode()

data class PublicKeyBorsh(val keyType: Byte, val data: BorshArray32) : Borsh {
    constructor(key: PublicKey) : this(
        keyType = key.keyType.stableIndex(),
        data = BorshArray32(key.decodeBase58())
    )
}

fun KeyType.stableIndex(): Byte = when (this) {
    KeyType.ED25519 -> 0
}


data class SignedTransactionBorsh(
    val transaction: TransactionBorsh,
    val signature: SignatureBorsh
) : Borsh {

    constructor(transaction: SignedTransaction) : this(
        transaction = TransactionBorsh(transaction.transaction),
        signature = SignatureBorsh(transaction.signature)
    )

    fun encode(): ByteArray {
        return Borsh.serialize(this)
    }
}

fun SignedTransaction.encode() = SignedTransactionBorsh(this).encode()

data class SignatureBorsh(val keyType: Byte, val data: BorshArray64) : Borsh {
    constructor(signature: TransactionSignature) : this(
        keyType = signature.keyType.stableIndex(),
        data = BorshArray64(signature.decodeBase58())
    )
}

data class AccessKeyBorsh(val nonce: Long, val permission: BorshEnum) : Borsh {
    constructor(accessKey: org.near.api.model.accesskey.AccessKey) : this(
        nonce = accessKey.nonce,
        permission = BorshEnum(
            accessKey.permission.stableIndex(),
            accessKey.permission.toBorsh()
        )
    )
}

fun org.near.api.model.accesskey.AccessKeyPermission.stableIndex(): Byte = when (this) {
    is org.near.api.model.accesskey.AccessKeyPermission.FunctionCall -> 0
    org.near.api.model.accesskey.AccessKeyPermission.FullAccess -> 1
}

private fun org.near.api.model.accesskey.AccessKeyPermission.toBorsh(): Borsh = when (this) {
    is org.near.api.model.accesskey.AccessKeyPermission.FunctionCall -> FunctionCallPermissionBorsh(
        allowance = Optional.ofNullable(allowance),
        receiverId = receiverId,
        methodNames = methodNames
    )
    org.near.api.model.accesskey.AccessKeyPermission.FullAccess -> FullAccessPermissionBorsh()
}

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

private fun Action.toBorsh(): Borsh = when (this) {
    Action.CreateAccount -> CreateAccountBorsh()
    is Action.AddKey -> AddKeyBorsh(
        publicKey = PublicKeyBorsh(publicKey),
        accessKey = AccessKeyBorsh(accessKey)
    )
    is Action.DeleteAccount -> DeleteAccountBorsh(beneficiaryId = beneficiaryId)
    is Action.DeleteKey -> DeleteKeyBorsh(publicKey = PublicKeyBorsh(publicKey))
    is Action.DeployContract -> DeployContractBorsh(code = code.toByteArray().toList())
    is Action.FunctionCall -> FunctionCallBorsh(
        methodName = methodName,
        args = args.toByteArray().toList(),
        gas = gas,
        deposit = deposit
    )

    is Action.Stake -> StakeBorsh(stake = stake, publicKey = PublicKeyBorsh(publicKey))
    is Action.StakeChunkOnly -> StakeChunkOnlyBorsh(stake = stake, publicKey = PublicKeyBorsh(publicKey))
    is Action.Transfer -> TransferBorsh(deposit = deposit)
}

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
