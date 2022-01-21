package org.near.api.providers.endpoints

import org.near.api.providers.NetworkProvider
import org.near.api.providers.base.JsonRpcProvider
import org.near.api.providers.model.networkinfo.NetworkInfo
import org.near.api.providers.model.networkinfo.NodeStatus
import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash
import org.near.api.providers.model.validators.EpochValidatorInfo

/**
 * RPC endpoint for getting Network state
 * @link https://docs.near.org/docs/api/rpc/network
 */
class NetworkRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : NetworkProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/network#node-status
     */
    override suspend fun getNodeStatus(timeout: Long): NodeStatus =
        jsonRpcProvider.sendRpc(method = "status", params = emptyList<Any>(), timeout = timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/network#network-info
     */
    override suspend fun getNetworkInfo(timeout: Long): NetworkInfo =
        jsonRpcProvider.sendRpc(method = "network_info", params = emptyList<Any>(), timeout = timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(timeout: Long): EpochValidatorInfo =
        jsonRpcProvider.sendRpc(method = "validators", params = listOf(null), timeout = timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockId: BlockHeight, timeout: Long): EpochValidatorInfo =
        jsonRpcProvider.sendRpc(method = "validators", params = listOf(blockId), timeout = timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/network#validation-status
     */
    override suspend fun getValidationStatus(blockHash: CryptoHash, timeout: Long): EpochValidatorInfo =
        jsonRpcProvider.sendRpc(method = "validators", params = listOf(blockHash), timeout = timeout)
}
