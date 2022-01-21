package org.near.api.endpoints

import org.near.api.model.config.GenesisConfig
import org.near.api.model.config.ProtocolConfig
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash

interface ProtocolProvider {

    /**
     * Returns current genesis configuration.
     */
    suspend fun getGenesisConfig(
        timeout: Long? = null
    ): GenesisConfig

    /**
     * Returns most recent config configuration for the latest block. Useful for finding current storage and transaction costs.
     * @param finality Finality param for last block
     */
    suspend fun getLatestProtocolConfig(
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): ProtocolConfig

    /**
     * Returns most recent config configuration for a specific queried block. Useful for finding current storage and transaction costs.
     * @param blockId Numeric block identifier
     */
    suspend fun getProtocolConfig(blockId: BlockHeight, timeout: Long? = null): ProtocolConfig

    /**
     * Returns most recent config configuration for a specific queried block. Useful for finding current storage and transaction costs.
     * @param blockHash String block hash
     */
    suspend fun getProtocolConfig(blockHash: CryptoHash, timeout: Long? = null): ProtocolConfig
}
