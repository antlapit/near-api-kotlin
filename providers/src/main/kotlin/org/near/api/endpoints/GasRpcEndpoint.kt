package org.near.api.endpoints

import org.near.api.model.gas.GasPrice
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.provider.JsonRpcProvider

/**
 * RPC endpoint for getting gas price
 * @link https://docs.near.org/docs/api/rpc/gas
 */
class GasRpcEndpoint(private val jsonRpcProvider: JsonRpcProvider) : GasEndpoint {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getGasPriceGeneral(blockId: Any?, timeout: Long?): GasPrice =
        jsonRpcProvider.sendRpc(
            method = "gas_price",
            arrayListOf(blockId),
            timeout
        )

    override suspend fun getLatestGasPrice(timeout: Long?) = getGasPriceGeneral(null, timeout)

    override suspend fun getGasPrice(blockId: BlockHeight, timeout: Long?) = getGasPriceGeneral(blockId, timeout)

    override suspend fun getGasPrice(blockHash: CryptoHash, timeout: Long?) = getGasPriceGeneral(blockHash, timeout)

}
