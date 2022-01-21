package org.near.api.model.config

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.NumShards
import org.near.api.model.primitives.ShardId
import org.near.api.model.rust.RustEnum

@RustEnum
sealed interface ShardLayout {
    data class V0(val numShards: NumShards, val version: ShardVersion) : ShardLayout

    data class V1(
        /**
         * Each account and all sub-accounts map to the shard of position in this array.
         */
        val fixedShards: List<AccountId> = listOf(),

        /**
         * The rest are divided by boundary_accounts to ranges, each range is mapped to a shard
         */
        val boundaryAccounts: List<AccountId> = listOf(),

        /**
         * Maps shards from the last shard layout to shards that it splits to in this shard layout,
         * Useful for constructing states for the shards.
         * None for the genesis shard layout
         */
        val shardsSplitMap: ShardSplitMap?,

        /**
         * Maps shard in this shard layout to their parent shard
         *  Since shard_ids always range from 0 to num_shards - 1, we use vec instead of a hashmap
         */
        val toParentShardMap: List<ShardId>?,

        /**
         * Version of the shard layout, this is useful for uniquely identify the shard layout
         */
        val version: ShardVersion
    ) : ShardLayout
}

typealias ShardVersion = Int

typealias ShardSplitMap = List<List<ShardId>>
