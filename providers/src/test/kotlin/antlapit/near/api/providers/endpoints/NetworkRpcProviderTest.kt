package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.Constants
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * End-to-end test of network endpoints
 */
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class NetworkRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: NetworkRpcProvider
    private lateinit var blockProvider: BlockRpcProvider

    @BeforeAll
    fun initEndpoint() {
        endpoint = NetworkRpcProvider(client)
        blockProvider = BlockRpcProvider(client)
    }

    @AfterAll
    fun close() {
        client.close()
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
    fun getValidationStatusOfBlock_thenCorrect() = runBlocking {
        val latestStatus = endpoint.getValidationStatus(6 * Constants.DEFAULT_TIMEOUT)

        // getting last block of previous epoch, which is guaranteed to exist
        val block = blockProvider.getBlock(latestStatus.epochStartHeight - 1)

        val statusByBlockId = endpoint.getValidationStatus(blockId = block.header.height)
        val statusByBlockHash = endpoint.getValidationStatus(blockHash = block.header.hash)
        assertEquals(
            statusByBlockId,
            statusByBlockHash,
            "status by block id should equals status by block hash"
        )
        return@runBlocking
    }
}
