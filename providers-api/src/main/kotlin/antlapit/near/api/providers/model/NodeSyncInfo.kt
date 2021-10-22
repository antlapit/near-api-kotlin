package antlapit.near.api.providers.model

import java.time.ZonedDateTime

class NodeSyncInfo(
    val earliestBlockHash: String,
    val earliestBlockHeight: Long,
    val earliestBlockTime: ZonedDateTime,
    val latestBlockHash: String,
    val latestBlockHeight: Long,
    val latestStateRoot: String,
    val latestBlockTime: ZonedDateTime,
    val syncing: Boolean
)
