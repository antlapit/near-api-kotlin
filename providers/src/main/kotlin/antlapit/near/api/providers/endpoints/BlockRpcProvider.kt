package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.BlockProvider
import antlapit.near.api.providers.Constants
import antlapit.near.api.providers.Finality
import antlapit.near.api.providers.base.BlockSearch
import antlapit.near.api.providers.base.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.base.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.base.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.model.block.Block
import antlapit.near.api.providers.model.block.BlockChangesContainer
import antlapit.near.api.providers.model.block.Chunk
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.ShardId

/**
 * RPC endpoint for working with Blocks / Chunks
 * @link https://docs.near.org/docs/api/rpc/block-chunk
 */
class BlockRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : BlockProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getBlock(blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long): Block =
        jsonRpcProvider.sendRpc(
            method = "block",
            blockSearch,
            timeout
        )

    override suspend fun getLatestBlock(finality: Finality, timeout: Long) = getBlock(ofFinality(finality), timeout)

    override suspend fun getBlock(blockId: BlockHeight, timeout: Long) = getBlock(fromBlockId(blockId), timeout)

    override suspend fun getBlock(blockHash: CryptoHash, timeout: Long) = getBlock(fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    override suspend fun getChunk(chunkHash: CryptoHash, timeout: Long): Chunk = jsonRpcProvider.sendRpc(
        method = "chunk",
        params = mapOf("chunk_id" to chunkHash)
    )

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    private suspend fun getChunk(
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        shardId: ShardId,
        timeout: Long
    ): Chunk = jsonRpcProvider.sendRpc(
        method = "chunk",
        blockSearch,
        params = mapOf("shard_id" to shardId),
        timeout
    )

    override suspend fun getChunkInBlock(blockId: BlockHeight, shardId: ShardId, timeout: Long) =
        getChunk(fromBlockId(blockId), shardId, timeout)

    override suspend fun getChunkInBlock(blockHash: CryptoHash, shardId: ShardId, timeout: Long) =
        getChunk(fromBlockHash(blockHash), shardId, timeout)


    override suspend fun getChangesInLatestBlock(finality: Finality, timeout: Long) =
        getChangesInBlock(ofFinality(finality), timeout)

    override suspend fun getChangesInBlock(blockId: BlockHeight, timeout: Long) =
        getChangesInBlock(fromBlockId(blockId), timeout)

    override suspend fun getChangesInBlock(blockHash: CryptoHash, timeout: Long) =
        getChangesInBlock(fromBlockHash(blockHash), timeout)

    /**
     * Note that this is experimental feature
     */
    private suspend fun getChangesInBlock(
        blockSearch: BlockSearch,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): BlockChangesContainer = jsonRpcProvider.sendRpc("EXPERIMENTAL_changes_in_block", blockSearch, timeout)
}
