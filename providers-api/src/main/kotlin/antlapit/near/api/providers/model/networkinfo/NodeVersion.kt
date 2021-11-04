package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.validators.ValidatorInfo
import antlapit.near.api.providers.primitives.ProtocolVersion
import java.net.InetSocketAddress

data class NodeVersion(
    val version: String,
    val build: String,
    val protocolVersion: ProtocolVersion,
    val latestProtocolVersion: ProtocolVersion,
    val rpcAddr: InetSocketAddress?,
    val validators: List<ValidatorInfo> = emptyList()
)
