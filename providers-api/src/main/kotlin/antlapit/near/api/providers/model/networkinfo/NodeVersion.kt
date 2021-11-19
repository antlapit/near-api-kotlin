package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.primitives.ProtocolVersion
import antlapit.near.api.providers.model.validators.ValidatorInfo
import java.net.InetSocketAddress

data class NodeVersion(
    val version: String,
    val build: String,
    val protocolVersion: ProtocolVersion,
    val latestProtocolVersion: ProtocolVersion,
    val rpcAddr: InetSocketAddress?,
    val validators: List<ValidatorInfo> = emptyList()
)
