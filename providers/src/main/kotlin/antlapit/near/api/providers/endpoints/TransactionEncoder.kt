package antlapit.near.api.providers.endpoints

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

/**
 * Workaround for enums
 * TODO should be in borshj
 */
data class BorshEnum(
    val index: Byte,
    val data: Any
) : Borsh

/**
 * Workaround for fixed array with 32 elements
 * TODO should be in borshj
 */
data class BorshArray32(
    val b0: Byte,
    val b1: Byte,
    val b2: Byte,
    val b3: Byte,
    val b4: Byte,
    val b5: Byte,
    val b6: Byte,
    val b7: Byte,
    val b8: Byte,
    val b9: Byte,
    val b10: Byte,
    val b11: Byte,
    val b12: Byte,
    val b13: Byte,
    val b14: Byte,
    val b15: Byte,
    val b16: Byte,
    val b17: Byte,
    val b18: Byte,
    val b19: Byte,
    val b20: Byte,
    val b21: Byte,
    val b22: Byte,
    val b23: Byte,
    val b24: Byte,
    val b25: Byte,
    val b26: Byte,
    val b27: Byte,
    val b28: Byte,
    val b29: Byte,
    val b30: Byte,
    val b31: Byte,
) : Borsh {
    constructor(b: ByteArray) : this(
        b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7],
        b[8], b[9], b[10], b[11], b[12], b[13], b[14], b[15],
        b[16], b[17], b[18], b[19], b[20], b[21], b[22], b[23],
        b[24], b[25], b[26], b[27], b[28], b[29], b[30], b[31]
    )
}

/**
 * Workaround for fixed array with 64 elements
 * TODO should be in borshj
 */
data class BorshArray64(
    val b0: Byte,
    val b1: Byte,
    val b2: Byte,
    val b3: Byte,
    val b4: Byte,
    val b5: Byte,
    val b6: Byte,
    val b7: Byte,
    val b8: Byte,
    val b9: Byte,
    val b10: Byte,
    val b11: Byte,
    val b12: Byte,
    val b13: Byte,
    val b14: Byte,
    val b15: Byte,
    val b16: Byte,
    val b17: Byte,
    val b18: Byte,
    val b19: Byte,
    val b20: Byte,
    val b21: Byte,
    val b22: Byte,
    val b23: Byte,
    val b24: Byte,
    val b25: Byte,
    val b26: Byte,
    val b27: Byte,
    val b28: Byte,
    val b29: Byte,
    val b30: Byte,
    val b31: Byte,
    val b32: Byte,
    val b33: Byte,
    val b34: Byte,
    val b35: Byte,
    val b36: Byte,
    val b37: Byte,
    val b38: Byte,
    val b39: Byte,
    val b40: Byte,
    val b41: Byte,
    val b42: Byte,
    val b43: Byte,
    val b44: Byte,
    val b45: Byte,
    val b46: Byte,
    val b47: Byte,
    val b48: Byte,
    val b49: Byte,
    val b50: Byte,
    val b51: Byte,
    val b52: Byte,
    val b53: Byte,
    val b54: Byte,
    val b55: Byte,
    val b56: Byte,
    val b57: Byte,
    val b58: Byte,
    val b59: Byte,
    val b60: Byte,
    val b61: Byte,
    val b62: Byte,
    val b63: Byte,
) : Borsh {
    constructor(b: ByteArray) : this(
        b[0], b[1], b[2], b[3], b[4], b[5], b[6], b[7],
        b[8], b[9], b[10], b[11], b[12], b[13], b[14], b[15],
        b[16], b[17], b[18], b[19], b[20], b[21], b[22], b[23],
        b[24], b[25], b[26], b[27], b[28], b[29], b[30], b[31],
        b[32], b[33], b[34], b[35], b[36], b[37], b[38], b[39],
        b[40], b[41], b[42], b[43], b[44], b[45], b[46], b[47],
        b[48], b[49], b[50], b[51], b[52], b[53], b[54], b[55],
        b[56], b[57], b[58], b[59], b[60], b[61], b[62], b[63],
    )
}
