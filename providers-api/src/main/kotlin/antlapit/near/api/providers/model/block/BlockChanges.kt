package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.changes.StateChangeKind
import antlapit.near.api.providers.model.primitives.CryptoHash

data class BlockChangesContainer(
    val blockHash: CryptoHash,
    val changes: List<StateChangeKind> = emptyList()
)
