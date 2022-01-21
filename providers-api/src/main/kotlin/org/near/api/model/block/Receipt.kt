package org.near.api.model.block

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.Balance
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.PublicKey
import org.near.api.model.rust.RustEnum

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
        val actions: List<org.near.api.model.block.Action> = emptyList(),
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
