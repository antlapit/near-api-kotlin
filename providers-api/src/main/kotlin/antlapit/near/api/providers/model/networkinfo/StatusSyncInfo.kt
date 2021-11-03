package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
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
