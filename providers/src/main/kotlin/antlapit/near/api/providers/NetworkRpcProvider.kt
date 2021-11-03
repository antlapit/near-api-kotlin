package antlapit.near.api.providers

import antlapit.near.api.providers.model.BlockHeight
import antlapit.near.api.providers.model.networkinfo.NetworkInfo
import antlapit.near.api.providers.model.networkinfo.NodeStatus
import antlapit.near.api.providers.model.validators.EpochValidatorInfo
import antlapit.near.api.providers.primitives.CryptoHash

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
    override suspend fun getNetworkInfo() : NetworkInfo = jsonRpcProvider.sendRpc(method = "network_info", emptyList<Any>())

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus() : EpochValidatorInfo = jsonRpcProvider.sendRpc(method = "validators", listOf(null))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockId: BlockHeight) : EpochValidatorInfo = jsonRpcProvider.sendRpc(method = "validators", params = listOf(blockId))

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockHash: CryptoHash) : EpochValidatorInfo = jsonRpcProvider.sendRpc(method = "validators", params = listOf(blockHash))
}
