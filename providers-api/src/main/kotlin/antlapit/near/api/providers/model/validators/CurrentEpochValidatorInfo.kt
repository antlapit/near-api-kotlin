package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.Balance
import antlapit.near.api.providers.model.NumBlocks
import antlapit.near.api.providers.model.ShardId
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.PublicKey

data class CurrentEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey,
    val stake: Balance,
    val shards: List<ShardId> = emptyList(),
    val isSlashed: Boolean,
    val numProducedBlocks: NumBlocks = 0u,
    val numExpectedBlocks: NumBlocks = 0u
)
