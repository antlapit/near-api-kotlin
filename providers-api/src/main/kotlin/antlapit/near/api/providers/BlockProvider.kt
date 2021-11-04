package antlapit.near.api.providers

import antlapit.near.api.providers.model.blocks.BlockView
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

interface BlockProvider {

    /**
     * Returns block details.
     * @param finality Finality param for last block
     */
    suspend fun getBlock(finality: Finality, timeout: Long = Constants.DEFAULT_TIMEOUT): BlockView

    /**
     * Returns block details.
     * @param blockId Numeric block identifier
     */
    suspend fun getBlock(blockId: BlockHeight, timeout: Long = Constants.DEFAULT_TIMEOUT): BlockView

    /**
     * Returns block details.
     * @param blockHash String block hash
     */
    suspend fun getBlock(blockHash: CryptoHash, timeout: Long = Constants.DEFAULT_TIMEOUT): BlockView

    /**
     * Returns details of a specific chunk.
     * @param chunkHash Valid chunk hash
     */
    suspend fun getChunk(chunkHash: String, timeout: Long = Constants.DEFAULT_TIMEOUT): Any

    // TODO Changes in Block
}
