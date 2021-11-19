package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.primitives.AccountId

data class NodeStatus(
    val validatorAccountId: AccountId?,
    val version: NodeVersion,
    val chainId: String?,
    val syncInfo: StatusSyncInfo
)
