package org.near.api.model.config

import org.near.api.model.account.AccountInfo
import org.near.api.model.primitives.*
import java.time.ZonedDateTime

data class GenesisConfig(
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
    /**
     * Number of epochs after stake threshold was achieved to start next prtocol version.
     */
    val protocolUpgradeNumEpochs: EpochHeight,
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

    /**
     * List of initial validators
     */
    val validators: List<AccountInfo> = listOf(),

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

    /**
     * Layout information regarding how to split accounts to shards
     */
    val shardLayout: ShardLayout?,
    val simpleNightshadeShardLayout: ShardLayout?,
    /**
     * The lowest ratio s/s_total any block producer can have.
     */
    val minimumStakeRatio: Rational?

)
