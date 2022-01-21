package org.near.api.endpoints

import org.near.api.model.gas.GasPrice
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash

interface GasProvider {

    /**
     * Returns gas price for latest block
     */
    suspend fun getLatestGasPrice(timeout: Long? = null): GasPrice

    /**
     * Returns gas price for a specific block
     * @param blockId Numeric block identifier
     */
    suspend fun getGasPrice(blockId: BlockHeight, timeout: Long? = null): GasPrice

    /**
     * Returns gas price for a specific block
     * @param blockHash String block hash
     */
    suspend fun getGasPrice(blockHash: CryptoHash, timeout: Long? = null): GasPrice
}
