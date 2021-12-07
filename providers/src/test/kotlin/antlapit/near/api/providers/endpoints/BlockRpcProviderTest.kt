package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.BlockProvider
import antlapit.near.api.providers.Finality
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class BlockRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: BlockProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = BlockRpcProvider(client)
    }

    @Test
    fun getBlock_whenLatest_thenCorrect() = runBlocking {
        // latest block
        val finalBlock = endpoint.getLatestBlock(Finality.FINAL)

        // latest queried block by hash
        val blockByHash = endpoint.getBlock(finalBlock.header.hash)
        assertEquals(finalBlock, blockByHash)

        // latest queried block by id
        val blockById = endpoint.getBlock(finalBlock.header.height)
        assertEquals(finalBlock, blockById)
        return@runBlocking
    }

    @Test
    fun getChunk_whenLatest_thenCorrect() = runBlocking {
        // latest block
        val finalBlock = endpoint.getLatestBlock(Finality.FINAL)
        // latest chunk by block id
        val chunkByBlockId = endpoint.getChunkInBlock(finalBlock.header.height, 0)
        // latest chunk by block hash
        val chunkByBlockHash = endpoint.getChunkInBlock(finalBlock.header.hash, 0)
        assertEquals(chunkByBlockId, chunkByBlockHash)

        // chunk by hash
        val chunkByHash = endpoint.getChunk(chunkByBlockId.header.chunkHash)
        assertEquals(chunkByHash, chunkByBlockHash)
    }
}
