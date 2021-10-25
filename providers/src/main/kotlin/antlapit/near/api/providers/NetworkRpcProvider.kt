package antlapit.near.api.providers

import antlapit.near.api.providers.model.NodeStatus

/**
 * RPC endpoint for getting Network state
 * @link https://docs.near.org/docs/api/rpc/network
 */
class NetworkRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : NetworkProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/network#node-status
     */
    override suspend fun getNodeStatus() : NodeStatus = jsonRpcProvider.sendRpc(method = "status", emptyList<Any>())

    /**
     * @link https://docs.near.org/docs/api/rpc/network#network-info
     */
    override suspend fun getNetworkInfo() = jsonRpcProvider.sendRpcDefault(method = "network_info", emptyList<Any>())

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus() = jsonRpcProvider.sendRpcDefault(method = "validators", listOf(null))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockId: Long) = jsonRpcProvider.sendRpcDefault(method = "validators", params = listOf(blockId))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockHash: String) = jsonRpcProvider.sendRpcDefault(method = "validators", params = listOf(blockHash))
}
