package org.near.api.model.block

import org.near.api.model.changes.StateChangeKind
import org.near.api.model.primitives.CryptoHash

data class BlockChangesContainer(
    val blockHash: CryptoHash,
    val changes: List<StateChangeKind> = emptyList()
)
