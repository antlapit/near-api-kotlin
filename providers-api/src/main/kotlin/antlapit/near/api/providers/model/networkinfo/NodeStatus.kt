package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.ProtocolVersion
import antlapit.near.api.providers.model.validators.ValidatorInfo
import java.net.InetSocketAddress

data class NodeStatus(
    val version: NodeVersion,
    val chainId: String,
    val protocolVersion: ProtocolVersion,
    val latestProtocolVersion: ProtocolVersion,
    val rpcAddr: InetSocketAddress?,
    val validators: List<ValidatorInfo> = emptyList(),
    val syncInfo: StatusSyncInfo,
    val validatorAccountId: AccountId?
)
