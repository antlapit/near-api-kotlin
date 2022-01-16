package org.near.api.providers.model.block

import org.near.api.providers.model.primitives.*
import org.near.api.providers.model.validators.ValidatorStakeView

data class ChunkHeader(
    val chunkHash: CryptoHash,
    val prevBlockHash: CryptoHash,
    val outcomeRoot: CryptoHash,
    val prevStateRoot: StateRoot,
    val encodedMerkleRoot: CryptoHash,
    val encodedLength: Long,
    val heightCreated: BlockHeight,
    val heightIncluded: BlockHeight,
    val shardId: ShardId,
    val gasUsed: Gas,
    val gasLimit: Gas,
    val balanceBurnt: Balance,
    val outgoingReceiptsRoot: CryptoHash,
    val txRoot: CryptoHash,
    val validatorProposals: List<ValidatorStakeView> = emptyList(),
    val signature: CryptoHash
)
