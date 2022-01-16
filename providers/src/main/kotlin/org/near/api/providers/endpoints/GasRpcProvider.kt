package org.near.api.providers.endpoints

import org.near.api.providers.base.JsonRpcProvider
import org.near.api.providers.model.gas.GasPrice
import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash

/**
 * RPC endpoint for getting gas price
 * @link https://docs.near.org/docs/api/rpc/gas
 */
class GasRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : org.near.api.providers.GasProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getGasPriceGeneral(blockId: Any?, timeout: Long): GasPrice =
        jsonRpcProvider.sendRpc(
            method = "gas_price",
            arrayListOf(blockId),
            timeout
        )

    override suspend fun getLatestGasPrice(timeout: Long) = getGasPriceGeneral(null, timeout)

    override suspend fun getGasPrice(blockId: BlockHeight, timeout: Long) = getGasPriceGeneral(blockId, timeout)

    override suspend fun getGasPrice(blockHash: CryptoHash, timeout: Long) = getGasPriceGeneral(blockHash, timeout)

}
