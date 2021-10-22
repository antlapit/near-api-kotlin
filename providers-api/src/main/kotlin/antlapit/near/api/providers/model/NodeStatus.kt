package antlapit.near.api.providers.model

data class NodeStatus(
    val validatorAccountId: String?,
    val version: NodeVersion,
    val chainId: String?,
    val syncInfo: NodeSyncInfo
)
