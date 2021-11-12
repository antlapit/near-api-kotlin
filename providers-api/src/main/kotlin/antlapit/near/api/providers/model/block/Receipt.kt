package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.PublicKey

data class Receipt(
    val predecessorId: AccountId,
    val receiverId: AccountId,
    val receiptId: CryptoHash,
    val receipt: ReceiptInfo
)

interface ReceiptInfo

data class ActionReceipt(
    val signerId: AccountId,
    val signerPublicKey: PublicKey,
    val gasPrice: Balance,
    val outputDataReceivers: List<DataReceiver> = emptyList(),
    val inputDataIds: List<CryptoHash> = emptyList(),
    val actions: List<Action> = emptyList(),
) : ReceiptInfo

data class DataReceipt(
    val dataId: CryptoHash,
    val data: String?
) : ReceiptInfo

data class DataReceiver(
    val dataId: CryptoHash,
    val receiverId: AccountId,
)
