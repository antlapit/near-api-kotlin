package antlapit.near.api.providers

interface BlockProvider {

    /**
     * Returns block details.
     * @param finality Finality param for last block
     */
    suspend fun getBlock(finality: Finality): Any

    /**
     * Returns block details.
     * @param blockId Numeric block identifier
     */
    suspend fun getBlock(blockId: Long): Any

    /**
     * Returns block details.
     * @param blockHash String block hash
     */
    suspend fun getBlock(blockHash: String): Any

    /**
     * Returns details of a specific chunk.
     * @param chunkHash Valid chunk hash
     */
    suspend fun getChunk(chunkHash: String): Any

    // TODO Changes in Block
}
