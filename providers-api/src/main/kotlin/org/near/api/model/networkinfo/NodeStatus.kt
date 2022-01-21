package org.near.api.model.networkinfo

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.ProtocolVersion
import org.near.api.model.validators.ValidatorInfo
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
