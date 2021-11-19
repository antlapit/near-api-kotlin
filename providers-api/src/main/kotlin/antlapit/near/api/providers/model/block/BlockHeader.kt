package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.primitives.*
import antlapit.near.api.providers.model.validators.ValidatorStakeView

data class BlockHeader(
    val height: BlockHeight,
    val prevHeight: BlockHeight?,
    val epochId: CryptoHash,
    val nextEpochId: CryptoHash,
    val hash: CryptoHash,
    val prevHash: CryptoHash,
    val prevStateRoot: CryptoHash,
    val chunkReceiptsRoot: CryptoHash,
    val chunkHeadersRoot: CryptoHash,
    val chunkTxRoot: CryptoHash,
    val outcomeRoot: CryptoHash,
    val chunksIncluded: Long, // TODO Rust u64,
    val challengesRoot: CryptoHash,
    val timestampNanosec: Long, // TODO Rust u64,
    val randomValue: CryptoHash,
    val validatorProposals: List<ValidatorStakeView> = emptyList(),
    val chunkMask: List<Boolean> = emptyList(),
    val gasPrice: Balance,
    val blockOrdinal: NumBlocks?,
    val totalSupply: Balance,
    val challengesResult: ChallengesResult,
    val lastFinalBlock: CryptoHash,
    val lastDsFinalBlock: CryptoHash,
    val nextBpHash: CryptoHash,
    val blockMerkleRoot: CryptoHash,
    val epochSyncDataHash: CryptoHash?,
    val approvals: List<Signature?> = emptyList(),
    val signature: Signature,
    val latestProtocolVersion: ProtocolVersion
)
