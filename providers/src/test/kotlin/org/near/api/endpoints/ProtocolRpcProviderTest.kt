package org.near.api.endpoints

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.near.api.provider.JsonRpcProvider
import org.near.api.provider.config.JsonRpcConfig
import org.near.api.provider.config.NetworkEnum
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProtocolRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: ProtocolProvider
    private lateinit var blockProvider: BlockProvider

    @BeforeAll
    fun initEndpoint() {
        endpoint = ProtocolRpcProvider(client)
        blockProvider = BlockRpcProvider(client)
    }

    @AfterAll
    fun close() {
        client.close()
    }

    @Test
    fun getGenesisConfig_thenCorrect() = runBlocking {
        val genesisConfig = endpoint.getGenesisConfig()
        assertNotNull(genesisConfig.protocolVersion)
        return@runBlocking
    }

    @Test
    fun getLatestProtocolConfig_thenCorrect() = runBlocking {
        // latest block
        val protocolConfig = endpoint.getLatestProtocolConfig()
        assertNotNull(protocolConfig.protocolVersion)
        return@runBlocking
    }

    @Test
    fun getProtocolConfig_byBlock_thenCorrect() = runBlocking {
        val block = blockProvider.getLatestBlock()

        // latest queried block by hash
        val protocolConfigByHash = endpoint.getProtocolConfig(block.header.hash)

        // latest queried block by id
        val protocolConfigById = endpoint.getProtocolConfig(block.header.height)
        assertEquals(protocolConfigByHash, protocolConfigById, "config config should equals by block id and hash")
        return@runBlocking
    }
}
