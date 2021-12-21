package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.Constants
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.*

/**
 * End-to-end test of network endpoints
 */
@ExperimentalCoroutinesApi
internal class NetworkRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.MAINNET_ARCHIVAL))
    private lateinit var endpoint: NetworkRpcProvider
    private lateinit var blockProvider: BlockRpcProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = NetworkRpcProvider(client)
        blockProvider = BlockRpcProvider(client)
    }

    @Test
    fun getNodeStatus_thenCorrect() = runBlocking {
        val nodeStatus = endpoint.getNodeStatus()
        assertNotNull(nodeStatus.version, "version should be not null")
        assertNotNull(nodeStatus.syncInfo, "syncInfo should be not null")
        return@runBlocking
    }

    @Test
    fun getNetworkInfo_thenCorrect() = runBlocking {
        val resp = endpoint.getNetworkInfo()
        assertTrue(resp.numActivePeers > 0, "active peers should not be 0")
        return@runBlocking
    }

    @Test
    fun getValidationStatus_thenCorrect() = runBlocking {
        val latestStatus = endpoint.getValidationStatus(6 * Constants.DEFAULT_TIMEOUT)
        assertTrue(latestStatus.epochStartHeight > 0, "epoch start height should not be 0")
        assertTrue(latestStatus.epochHeight > 0, "epoch start height should not be 0")
        return@runBlocking
    }

    @Test
    @Ignore
    fun getValidationStatusOfBlock_thenCorrect() = runBlocking {
        // FIXME validation status by block is not working!
        // Always getting VALIDATOR_INFO_UNAVAILABLE
        val statusByBlockId = endpoint.getValidationStatus(blockId = 54483512)
        return@runBlocking
    }
}
