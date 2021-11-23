package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.Network
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

// TODO attributes checking
@ExperimentalCoroutinesApi
internal class NetworkRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(Network.TESTNET))
    private lateinit var endpoint: NetworkRpcProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = NetworkRpcProvider(client)
    }

    @Test
    fun getNodeStatus_thenCorrect() = runBlocking {
        val resp = endpoint.getNodeStatus()
        println(resp)
        assertNotNull(resp.chainId)
        return@runBlocking
    }

    @Test
    fun getNetworkInfo_thenCorrect() = runBlocking {
        val resp = endpoint.getNetworkInfo()
        println(resp)
        assertNotNull(resp.activePeers)
        return@runBlocking
    }

    @Test
    fun getValidationStatus_thenCorrect() = runBlocking {
        val latestStatus = endpoint.getValidationStatus()

        val statusByBlockId = endpoint.getValidationStatus(latestStatus.epochHeight)
        assertEquals(latestStatus, statusByBlockId)
        return@runBlocking
    }
}
