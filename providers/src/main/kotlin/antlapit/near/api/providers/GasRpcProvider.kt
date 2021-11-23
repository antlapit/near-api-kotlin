package antlapit.near.api.providers

import antlapit.near.api.providers.model.gas.GasPrice
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash

/**
 * RPC endpoint for getting gas price
 * @link https://docs.near.org/docs/api/rpc/gas
 */
class GasRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : GasProvider {

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
