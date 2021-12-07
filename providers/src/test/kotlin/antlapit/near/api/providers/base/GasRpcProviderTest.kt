package antlapit.near.api.providers.base

import antlapit.near.api.providers.BlockProvider
import antlapit.near.api.providers.GasProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.endpoints.BlockRpcProvider
import antlapit.near.api.providers.endpoints.GasRpcProvider
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
        assertEquals(priceByHash, priceById, "gas price should equals by block id and hash")
        return@runBlocking
    }

}
