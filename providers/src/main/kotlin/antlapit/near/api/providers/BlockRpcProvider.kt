package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.model.blocks.BlockView
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

/**
 * RPC endpoint for working with Blocks / Chunks
 * @link https://docs.near.org/docs/api/rpc/block-chunk
 */
class BlockRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : BlockProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#block-details
     */
    private suspend fun getBlock(blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long) : BlockView = jsonRpcProvider.sendRpc(
        method = "block",
        blockSearch,
        timeout
    )

    override suspend fun getBlock(finality: Finality, timeout: Long) = getBlock(ofFinality(finality), timeout)

    override suspend fun getBlock(blockId: BlockHeight, timeout: Long) = getBlock(fromBlockId(blockId), timeout)

    override suspend fun getBlock(blockHash: CryptoHash, timeout: Long) = getBlock(fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/block-chunk#chunk-details
     */
    override suspend fun getChunk(chunkHash: String, timeout: Long): Any = jsonRpcProvider.sendRpcDefault(method = "chunk", params = listOf(chunkHash))

}
