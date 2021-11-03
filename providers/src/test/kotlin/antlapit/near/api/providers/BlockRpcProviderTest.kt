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
}
