package org.near.api.model.block

import org.near.api.model.primitives.AccountId
import org.near.api.model.transaction.SignedTransactionView

data class Chunk(
    val author: AccountId,
    val header: ChunkHeader,
    val transactions: List<SignedTransactionView> = emptyList(),
    val receipts: List<Receipt> = emptyList(),
)
