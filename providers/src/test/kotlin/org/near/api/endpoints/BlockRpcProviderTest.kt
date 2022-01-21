package org.near.api.endpoints

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.near.api.provider.JsonRpcProvider
import org.near.api.provider.config.JsonRpcConfig
import org.near.api.provider.config.NetworkEnum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlockRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: BlockProvider

    @BeforeAll
    fun initEndpoint() {
        endpoint = BlockRpcProvider(client)
    }

    @AfterAll
    fun close() {
        client.close()
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

    /**
     * This test checks UnknownBlockException while accessing by blockId
     */
    @Test
    fun getBlock_whenUnknownBlock_thenException() = runBlocking {
        val e = assertFails(
            message = "UnknownBlockException expected"
        ) {
            endpoint.getBlock("jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c")
        }
        assertTrue(e is org.near.api.exception.UnknownBlockException)
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

    @Test
    fun getChunk_whenUnknownChunk_thenException() = runBlocking {
        val e = assertFails(
            message = "UnknownChunkException expected"
        ) {
            endpoint.getChunk("jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c")
        }
        assertTrue(e is org.near.api.exception.UnknownChunkException)
        assertEquals("jav58J75jTCkAouUyT8fEzoRTqoNaZpX1hGQZNKbU7c", e.chunkHash)
    }

    @Test
    fun getChunk_whenInvalidShard_thenException() = runBlocking {
        // latest block
        val finalBlock = endpoint.getLatestBlock(Finality.FINAL)

        val e = assertFails(
            message = "InvalidShardIdException expected"
        ) {
            endpoint.getChunkInBlock(finalBlock.header.height, 1000)
        }
        assertTrue(e is org.near.api.exception.InvalidShardIdException)
        assertEquals(1000, e.shardId)
    }

    @Test
    fun getChangesInBlock_whenLatest_thenCorrect() = runBlocking {
        val finalChanges = endpoint.getChangesInLatestBlock()

        val changesByBlockHash = endpoint.getChangesInBlock(finalChanges.blockHash)
        assertEquals(finalChanges, changesByBlockHash, "changes in block by hash should equal changes in latest block")

        val blockId = endpoint.getBlock(finalChanges.blockHash).header.height

        val changesByBlockId = endpoint.getChangesInBlock(blockId)
        assertEquals(changesByBlockId, changesByBlockHash, "changes in block by id and hash should be equal")
    }
}
