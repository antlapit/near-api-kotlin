package antlapit.near.api.providers.config

data class JsonRpcConfig(
    val network: Network = Network.MAINNET,
) {
    fun getAddress() = network.url
}
