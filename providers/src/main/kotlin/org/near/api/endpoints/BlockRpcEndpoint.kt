package org.near.api.endpoints

import org.near.api.model.block.Block
import org.near.api.model.block.BlockChangesContainer
import org.near.api.model.block.Chunk
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.ShardId
import org.near.api.provider.BlockSearch
import org.near.api.provider.BlockSearch.Companion.fromBlockHash
import org.near.api.provider.BlockSearch.Companion.fromBlockId
import org.near.api.provider.BlockSearch.Companion.ofFinality
import org.near.api.provider.JsonRpcProvider

/**
 * RPC endpoint for working with Blocks / Chunks
 * @link https://docs.near.org/docs/api/rpc/block-chunk
 */
class BlockRpcEndpoint(private val jsonRpcProvider: JsonRpcProvider) : BlockEndpoint {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getBlock(blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long?): Block =
        jsonRpcProvider.sendRpc(
            method = "block",
            blockSearch = blockSearch,
            timeout = timeout
        )

    override suspend fun getLatestBlock(finality: Finality, timeout: Long?) = getBlock(ofFinality(finality), timeout)

    override suspend fun getBlock(blockId: BlockHeight, timeout: Long?) = getBlock(fromBlockId(blockId), timeout)

    override suspend fun getBlock(blockHash: CryptoHash, timeout: Long?) = getBlock(fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    override suspend fun getChunk(chunkHash: CryptoHash, timeout: Long?): Chunk = jsonRpcProvider.sendRpc(
        method = "chunk",
        params = mapOf("chunk_id" to chunkHash)
    )

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    private suspend fun getChunk(
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        shardId: ShardId,
        timeout: Long?
    ): Chunk = jsonRpcProvider.sendRpc(
        method = "chunk",
        blockSearch,
        params = mapOf("shard_id" to shardId),
        timeout
    )

    override suspend fun getChunkInBlock(blockId: BlockHeight, shardId: ShardId, timeout: Long?) =
        getChunk(fromBlockId(blockId), shardId, timeout)

    override suspend fun getChunkInBlock(blockHash: CryptoHash, shardId: ShardId, timeout: Long?) =
        getChunk(fromBlockHash(blockHash), shardId, timeout)


    override suspend fun getChangesInLatestBlock(finality: Finality, timeout: Long?) =
        getChangesInBlock(ofFinality(finality), timeout)

    override suspend fun getChangesInBlock(blockId: BlockHeight, timeout: Long?) =
        getChangesInBlock(fromBlockId(blockId), timeout)

    override suspend fun getChangesInBlock(blockHash: CryptoHash, timeout: Long?) =
        getChangesInBlock(fromBlockHash(blockHash), timeout)

    /**
     * Note that this is experimental feature
     */
    private suspend fun getChangesInBlock(
        blockSearch: BlockSearch,
        timeout: Long? = null
    ): BlockChangesContainer = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes_in_block",
        blockSearch = blockSearch,
        timeout = timeout
    )
}
