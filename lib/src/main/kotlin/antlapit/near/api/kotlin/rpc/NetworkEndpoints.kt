package antlapit.near.api.kotlin.rpc

/**
 * RPC endpoint for getting Network state
 * @link https://docs.near.org/docs/api/rpc/network
 */
class NetworkEndpoints(private val client: RPCClient) {

    /**
     * Returns general status of a given node (sync status, nearcore node version, protocol version, etc), and the current set of validators.
     * @link https://docs.near.org/docs/api/rpc/network#node-status
     */
    suspend fun getNodeStatus() = client.sendRequest(method = "status", emptyList<Any>())

    /**
     * Returns the current state of node network connections (active peers, transmitted data, etc.)
     * @link https://docs.near.org/docs/api/rpc/network#network-info
     */
    suspend fun getNetworkInfo() = client.sendRequest(method = "network_info", emptyList<Any>())

    /**
     * Queries active validators on the network returning details and the state of validation on the blockchain.
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    suspend fun getValidationStatus() = client.sendRequest(method = "validators", listOf(null))

    /**
     * @param blockId Numeric block number
     */
    suspend fun getValidationStatus(blockId: Long) = client.sendRequest(method = "validators", params = listOf(blockId))

    /**
     * @param blockHash Block hash
     */
    suspend fun getValidationStatus(blockHash: String) = client.sendRequest(method = "validators", params = listOf(blockHash))
}
