package org.near.api.model.validators

import org.near.api.model.primitives.*

data class CurrentEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey,
    val stake: Balance,
    val shards: List<ShardId> = emptyList(),
    val isSlashed: Boolean,
    val numProducedBlocks: NumBlocks = 0,
    val numExpectedBlocks: NumBlocks = 0
)
