package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class BlockRpcProviderTest : BaseProviderTest() {

    private lateinit var endpoint: BlockProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = BlockRpcProvider(client)
    }

    @Test
    fun getBlock_whenLatest_thenCorrect() = runBlocking {
        // latest block
        val finalBlock = endpoint.getBlock(Finality.FINAL)

        // latest queried block by hash
        val blockByHash = endpoint.getBlock(finalBlock.header.hash)
        assertEquals(finalBlock, blockByHash)

        // latest queried block by id
        val blockById = endpoint.getBlock(finalBlock.header.height)
        assertEquals(finalBlock, blockById)
        println(blockByHash)
        return@runBlocking
    }

    @Test
    fun getChunk_whenLatest_thenCorrect() = runBlocking {
        // latest block
        val finalBlock = endpoint.getBlock(Finality.FINAL)
        // latest chunk by block id
        val chunkByBlockId = endpoint.getChunk(finalBlock.header.height, 0)
        // latest chunk by block hash
        val chunkByBlockHash = endpoint.getChunk(finalBlock.header.hash, 0)
        assertEquals(chunkByBlockId, chunkByBlockHash)

        // chunk by hash
        val chunkByHash = endpoint.getChunk(chunkByBlockId.header.chunkHash)
        assertEquals(chunkByHash, chunkByBlockHash)
    }

    @Test
    fun temp() = runBlocking {
        //val block = endpoint.getBlock("D1RxWJKsLHP614QPQGpg1nCHExx3tDWC3SuAga3eJsu6")
        val chunk = endpoint.getChunk("37QjY12QKbYgmApkQP6usVmsXEhatuYq2NRkSr63Piuj")
        println(chunk)
    }
}
