package org.near.api.endpoints

import org.near.api.model.block.Block
import org.near.api.model.block.BlockChangesContainer
import org.near.api.model.block.Chunk
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.ShardId

interface BlockEndpoint {

    /**
     * Returns block details.
     * @param finality Finality param for last block
     */
    suspend fun getLatestBlock(finality: Finality = Finality.OPTIMISTIC, timeout: Long? = null): Block

    /**
     * Returns block details.
     * @param blockId Numeric block identifier
     */
    suspend fun getBlock(blockId: BlockHeight, timeout: Long? = null): Block

    /**
     * Returns block details.
     * @param blockHash String block hash
     */
    suspend fun getBlock(blockHash: CryptoHash, timeout: Long? = null): Block

    /**
     * Returns details of a specific chunk.
     * @param chunkHash Valid chunk hash
     */
    suspend fun getChunk(chunkHash: CryptoHash, timeout: Long? = null): Chunk

    /**
     * Returns details of a specific chunk.
     * @param blockId Numeric block identifier
     * @param shardId Numeric shard identifier
     */
    suspend fun getChunkInBlock(blockId: BlockHeight, shardId: ShardId, timeout: Long? = null): Chunk

    /**
     * Returns details of a specific chunk.
     * @param blockHash String block hash
     * @param shardId Numeric shard identifier
     */
    suspend fun getChunkInBlock(blockHash: CryptoHash, shardId: ShardId, timeout: Long? = null): Chunk

    /**
     * Returns changes in block for given block
     * @param finality Finality param for last block
     */
    suspend fun getChangesInLatestBlock(finality: Finality = Finality.OPTIMISTIC, timeout: Long? = null): BlockChangesContainer

    /**
     * Returns changes in block for given block
     * @param blockId Numeric block identifier
     */
    suspend fun getChangesInBlock(blockId: BlockHeight, timeout: Long? = null): BlockChangesContainer

    /**
     * Returns changes in block for given block
     * @param blockHash String block hash
     */
    suspend fun getChangesInBlock(blockHash: CryptoHash, timeout: Long? = null): BlockChangesContainer
}
