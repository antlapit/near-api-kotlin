package org.near.api.model.networkinfo

import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import java.time.ZonedDateTime

data class StatusSyncInfo(
    val earliestBlockHash: CryptoHash?,
    val earliestBlockHeight: BlockHeight?,
    val earliestBlockTime: ZonedDateTime?,
    val latestBlockHash: CryptoHash,
    val latestBlockHeight: BlockHeight,
    val latestStateRoot: CryptoHash,
    val latestBlockTime: ZonedDateTime,
    val syncing: Boolean
)
