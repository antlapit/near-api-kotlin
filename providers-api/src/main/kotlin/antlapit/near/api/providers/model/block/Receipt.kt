package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.PublicKey
import antlapit.near.api.providers.util.RustEnum

data class Receipt(
    val predecessorId: AccountId,
    val receiverId: AccountId,
    val receiptId: CryptoHash,
    val receipt: ReceiptInfo
)

@RustEnum
sealed interface ReceiptInfo {

    data class Action(
        val signerId: AccountId,
        val signerPublicKey: PublicKey,
        val gasPrice: Balance,
        val outputDataReceivers: List<DataReceiver> = emptyList(),
        val inputDataIds: List<CryptoHash> = emptyList(),
        val actions: List<antlapit.near.api.providers.model.block.Action> = emptyList(),
    ) : ReceiptInfo

    data class Data(
        val dataId: CryptoHash,
        val data: String?
    ) : ReceiptInfo
}

data class DataReceiver(
    val dataId: CryptoHash,
    val receiverId: AccountId,
)
