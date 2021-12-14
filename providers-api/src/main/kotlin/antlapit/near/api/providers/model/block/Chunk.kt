package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.transaction.SignedTransaction

data class Chunk(
    val author: AccountId,
    val header: ChunkHeader,
    val transactions: List<SignedTransaction> = emptyList(),
    val receipts: List<Receipt> = emptyList(),
)
