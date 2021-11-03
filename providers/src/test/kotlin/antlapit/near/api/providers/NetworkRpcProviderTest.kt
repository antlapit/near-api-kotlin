package antlapit.near.api.providers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
internal class NetworkRpcProviderTest : BaseProviderTest() {

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
        val resp = endpoint.getValidationStatus()
        println(resp)
        assertNotNull(resp.currentProposals)
        return@runBlocking
    }

    // TODO Validation by block id ????
}
