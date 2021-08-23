package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId

/**
 * RPC endpoint for working with Blocks / Chunks
 * @link https://docs.near.org/docs/api/providers/block-chunk
 */
class BlockProvider(private val client: BaseJsonRpcProvider) {

    /**
     * Returns basic account information.
     * @link https://docs.near.org/docs/api/providers/block-chunk#block-details
     *
     * @param blockSearch Block search strategy for querying blocks
     */
    suspend fun getBlock(blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = client.sendJsonRpc(
        method = "block",
        blockSearch
    )

    suspend fun getBlock(blockId: Long) = getBlock(fromBlockId(blockId))

    suspend fun getBlock(blockHash: String) = getBlock(fromBlockHash(blockHash))

    // TODO Changes in Block
    /**
     * def get_changes_in_block(self, changes_in_block_request):
    return self.json_rpc('EXPERIMENTAL_changes_in_block', changes_in_block_request)
     */

    /**`
     * Returns details of a specific chunk.
     * @link https://docs.near.org/docs/api/providers/block-chunk#chunk-details
     *
     * @param chunkHash Valid chunk hash
     */
    suspend fun getChunk(chunkHash: String) = client.sendJsonRpc(method = "chunk", params = listOf(chunkHash))

}
