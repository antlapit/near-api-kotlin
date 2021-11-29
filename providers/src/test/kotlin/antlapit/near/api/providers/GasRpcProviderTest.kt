package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.NetworkEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class GasRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: GasProvider
    private lateinit var blockProvider: BlockProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = GasRpcProvider(client)
        blockProvider = BlockRpcProvider(client)
    }

    @Test
    fun getLatestGasPrice_thenCorrect() = runBlocking {
        // latest block
        val finalGasPrice = endpoint.getLatestGasPrice()
        assertNotNull(finalGasPrice.gasPrice)
        return@runBlocking
    }

    @Test
    fun getGasPrice_byBlock_thenCorrect() = runBlocking {
        val block = blockProvider.getLatestBlock()


        // latest queried block by hash
        val priceByHash = endpoint.getGasPrice(block.header.hash)

        // latest queried block by id
        val priceById = endpoint.getGasPrice(block.header.height)
        assertEquals(priceByHash, priceById)
        return@runBlocking
    }

    // TODO gas price attributes checking of concrete block

}