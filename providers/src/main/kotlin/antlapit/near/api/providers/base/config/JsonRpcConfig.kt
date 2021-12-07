package antlapit.near.api.providers.base.config

data class JsonRpcConfig(
    val network: Network = NetworkEnum.MAINNET
) {
    fun getAddress() = network.getUrl()
}
