package org.near.api.model.config

import org.near.api.model.primitives.*
import java.time.ZonedDateTime

data class ProtocolConfig(
    /** Current Protocol Version
     */
    val protocolVersion: ProtocolVersion,
    /** Official time of blockchain start.
     */
    val genesisTime: ZonedDateTime,
    /** ID of the blockchain. This must be unique for every blockchain.
    If your testnet blockchains do not have unique chain IDs, you will have a bad time.
     */
    val chainId: String,
    /** Height of genesis block.
     */
    val genesisHeight: BlockHeight,
    /** Number of block producer seats at genesis.
     */
    val numBlockProducerSeats: NumSeats,
    /** Defines number of shards and number of block producer seats per each shard at genesis.
     */
    val numBlockProducerSeatsPerShard: List<NumSeats>,
    /** Expected number of hidden validators per shard.
     */
    val avgHiddenValidatorSeatsPerShard: List<NumSeats>,
    /** Enable dynamic re-sharding.
     */
    val dynamicResharding: Boolean,
    /** Threshold of stake that needs to indicate that they ready for upgrade.
     */
    val protocolUpgradeStakeThreshold: Rational,
    /** Epoch length counted in block heights.
     */
    val epochLength: BlockHeightDelta,
    /** Initial gas limit.
     */
    val gasLimit: Gas,
    /** Minimum gas price. It is also the initial gas price.
     */
    val minGasPrice: Balance,
    /** Maximum gas price.
     */
    val maxGasPrice: Balance,
    /** Criterion for kicking out block producers (this is a number between 0 and 100)
     */
    val blockProducerKickoutThreshold: Byte,
    /** Criterion for kicking out chunk producers (this is a number between 0 and 100)
     */
    val chunkProducerKickoutThreshold: Byte,
    /** Online minimum threshold below which validator doesn't receive reward.
     */
    val onlineMinThreshold: Rational,
    /** Online maximum threshold above which validator gets full reward.
     */
    val onlineMaxThreshold: Rational,
    /** Gas price adjustment rate
     */
    val gasPriceAdjustmentRate: Rational,
    /** Runtime configuration (mostly economics constants).
     */
    val runtimeConfig: RuntimeConfig,
    /** Number of blocks for which a given transaction is valid
     */
    val transactionValidityPeriod: NumBlocks,
    /** Protocol treasury rate
     */
    val protocolRewardRate: Rational,
    /** Maximum inflation on the total supply every epoch.
     */
    val maxInflationRate: Rational,
    /** Expected number of blocks per year
     */
    val numBlocksPerYear: NumBlocks,
    /** Protocol treasury account
     */
    val protocolTreasuryAccount: AccountId,
    /** Fishermen stake threshold.
     */
    val fishermenThreshold: Balance,

    /** The minimum stake required for staking is last seat price divided by this number.
     */
    val minimumStakeDivisor: Long,
)

class Rational(override val start: Int, override val endInclusive: Int) : ClosedRange<Int> {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        if (start != other.start) return false
        if (endInclusive != other.endInclusive) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start
        result = 31 * result + endInclusive
        return result
    }

    override fun toString(): String {
        return "[$start, $endInclusive]"
    }


}
