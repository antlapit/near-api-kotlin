package antlapit.near.api.providers

import antlapit.near.api.providers.model.block.Block
import antlapit.near.api.providers.model.block.Chunk
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.ShardId

interface BlockProvider {

    /**
     * Returns block details.
     * @param finality Finality param for last block
     */
    suspend fun getBlock(finality: Finality, timeout: Long = Constants.DEFAULT_TIMEOUT): Block

    /**
     * Returns block details.
     * @param blockId Numeric block identifier
     */
    suspend fun getBlock(blockId: BlockHeight, timeout: Long = Constants.DEFAULT_TIMEOUT): Block

    /**
     * Returns block details.
     * @param blockHash String block hash
     */
    suspend fun getBlock(blockHash: CryptoHash, timeout: Long = Constants.DEFAULT_TIMEOUT): Block

    /**
     * Returns details of a specific chunk.
     * @param chunkHash Valid chunk hash
     */
    suspend fun getChunk(chunkHash: CryptoHash, timeout: Long = Constants.DEFAULT_TIMEOUT): Chunk

    /**
     * Returns details of a specific chunk.
     * @param blockId Numeric block identifier
     * @param shardId Numeric shard identifier
     */
    suspend fun getChunk(blockId: BlockHeight, shardId: ShardId, timeout: Long = Constants.DEFAULT_TIMEOUT): Chunk

    /**
     * Returns details of a specific chunk.
     * @param blockHash String block hash
     * @param shardId Numeric shard identifier
     */
    suspend fun getChunk(blockHash: CryptoHash, shardId: ShardId, timeout: Long = Constants.DEFAULT_TIMEOUT): Chunk

    // TODO Changes in Block
}
