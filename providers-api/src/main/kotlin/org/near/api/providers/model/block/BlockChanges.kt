package org.near.api.providers.model.block

import org.near.api.providers.model.changes.StateChangeKind
import org.near.api.providers.model.primitives.CryptoHash

data class BlockChangesContainer(
    val blockHash: CryptoHash,
    val changes: List<StateChangeKind> = emptyList()
)
