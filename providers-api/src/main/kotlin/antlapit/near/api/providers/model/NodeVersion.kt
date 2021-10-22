package antlapit.near.api.providers.model

data class NodeVersion(
    val version: String,
    val build: String,
    val protocolVersion: Int,
    val latestProtocolVersion: Int,
    val rpcAddr: String?,
    val validators: List<NodeValidator> = emptyList()
)
