package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.transaction.SignedTransactionView
import antlapit.near.api.providers.primitives.AccountId

data class Chunk(
    val author: AccountId,
    val header: ChunkHeader,
    val transactions: List<SignedTransactionView> = emptyList(),
    val receipts: List<Receipt> = emptyList(),
)
