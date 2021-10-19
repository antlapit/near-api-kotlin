package antlapit.near.api.providers

/**
 * RPC endpoint for getting Network state
 * @link https://docs.near.org/docs/api/rpc/network
 */
class NetworkRpcProvider(private val client: BaseJsonRpcProvider) : NetworkProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/network#node-status
     */
    override suspend fun getNodeStatus() = client.sendJsonRpc(method = "status", emptyList<Any>())

    /**
     * @link https://docs.near.org/docs/api/rpc/network#network-info
     */
    override suspend fun getNetworkInfo() = client.sendJsonRpc(method = "network_info", emptyList<Any>())

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus() = client.sendJsonRpc(method = "validators", listOf(null))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockId: Long) = client.sendJsonRpc(method = "validators", params = listOf(blockId))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockHash: String) = client.sendJsonRpc(method = "validators", params = listOf(blockHash))
}
