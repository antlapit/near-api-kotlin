package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality

/**
 * RPC endpoint for working with Blocks / Chunks
 * @link https://docs.near.org/docs/api/rpc/block-chunk
 */
class BlockRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : BlockProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getBlock(blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = jsonRpcProvider.sendRpcDefault(
        method = "block",
        blockSearch
    )

    override suspend fun getBlock(finality: Finality) = getBlock(ofFinality(finality))

    override suspend fun getBlock(blockId: Long) = getBlock(fromBlockId(blockId))

    override suspend fun getBlock(blockHash: String) = getBlock(fromBlockHash(blockHash))

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    override suspend fun getChunk(chunkHash: String) = jsonRpcProvider.sendRpcDefault(method = "chunk", params = listOf(chunkHash))

}
