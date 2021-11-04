package antlapit.near.api.providers.model.blocks

import antlapit.near.api.providers.model.validators.ValidatorStakeView
import antlapit.near.api.providers.primitives.*

data class ChunkHeaderView(
    val chunkHash: CryptoHash,
    val prevBlockHash: CryptoHash,
    val outcomeRoot: CryptoHash,
    val prevStateRoot: StateRoot,
    val encodedMerkleRoot: CryptoHash,
    val encodedLength: Long, // TODO Rust u64
    val heightCreated: BlockHeight,
    val heightIncluded: BlockHeight,
    val shardId: ShardId,
    val gasUsed: Gas,
    val gasLimit: Gas,
    val validatorReward: Balance,
    val balanceBurnt: Balance,
    val outgoingReceiptsRoot: CryptoHash,
    val txRoot: CryptoHash,
    val validatorProposals: List<ValidatorStakeView> = emptyList(),
    val signature: Signature
)
