package antlapit.near.api.providers

import antlapit.near.api.providers.model.gas.GasPrice
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash

interface GasProvider {

    /**
     * Returns gas price for latest block
     */
    suspend fun getLatestGasPrice(timeout: Long = Constants.DEFAULT_TIMEOUT): GasPrice

    /**
     * Returns gas price for a specific block
     * @param blockId Numeric block identifier
     */
    suspend fun getGasPrice(blockId: BlockHeight, timeout: Long = Constants.DEFAULT_TIMEOUT): GasPrice

    /**
     * Returns gas price for a specific block
     * @param blockHash String block hash
     */
    suspend fun getGasPrice(blockHash: CryptoHash, timeout: Long = Constants.DEFAULT_TIMEOUT): GasPrice
}
