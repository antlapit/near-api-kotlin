package org.near.api.endpoints

import io.kotest.common.ExperimentalKotest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.near.api.model.account.AccountWithPublicKey
import org.near.api.model.primitives.PublicKey
import org.near.api.provider.JsonRpcProvider
import org.near.api.provider.config.JsonRpcConfig
import org.near.api.provider.config.NetworkEnum
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * End-to-end test of access keys endpoints
 */
@ExperimentalKotest
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccessKeysRpcEndpointTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private val archivalClient = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var endpoint: AccessKeysRpcEndpoint
    private lateinit var archivalEndpoint: AccessKeysRpcEndpoint
    private lateinit var archivalBlockEndpoint: BlockRpcEndpoint

    @BeforeAll
    fun initEndpoint() {
        endpoint = AccessKeysRpcEndpoint(client)
        archivalEndpoint = AccessKeysRpcEndpoint(archivalClient)
        archivalBlockEndpoint = BlockRpcEndpoint(archivalClient)
    }

    @AfterAll
    fun close() {
        client.close()
    }

    @Test
    fun getAccessKeys_whenLatest_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val finalKeys = endpoint.getAccessKeyList(accountId, Finality.FINAL)
        assert(finalKeys.keys.isNotEmpty()) { "access keys should not be empty" }

        // access keys loaded by block should equal latest keys
        val keysByHash = endpoint.getAccessKeyList(accountId, finalKeys.blockHash)
        assertEquals(finalKeys, keysByHash, "access keys by block hash should equals latest access keys")

        val keysByBlockId = endpoint.getAccessKeyList(accountId, finalKeys.blockHeight)
        assertEquals(finalKeys, keysByBlockId, "access keys by block id should equals latest access keys")
    }

    @Test
    fun getSingleAccessKey_whenLatest_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val publicKey = PublicKey("ed25519:7jytAYzviGM2a3dcXYAD5YTtCcYJ3XiYSwLcHYdEx1uk")
        val finalKey = endpoint.getAccessKey(accountId, publicKey, Finality.FINAL)
        assertEquals(org.near.api.model.accesskey.AccessKeyPermission.FullAccess, finalKey.permission, "access key should be FullAccess key")

        val keysByHash = endpoint.getAccessKey(accountId, publicKey, finalKey.blockHash)
        assertEquals(finalKey, keysByHash, "access key by block hash should equals latest access key")

        val keysByBlockId = endpoint.getAccessKey(accountId, publicKey, finalKey.blockHeight)
        assertEquals(finalKey, keysByBlockId, "access key by block id should equals latest access key")
    }

    @Test
    fun getSingleAccessKeyChanges_whenConcreteBlock_thenCorrect() = runBlocking {
        val accountId = "tx1.api_kotlin.testnet"
        val publicKey = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF")
        val keysRequest = listOf(AccountWithPublicKey(accountId, publicKey))

        val block = archivalBlockEndpoint.getBlock("AWXoLJtyQaYkogEjR96NExSDXpwspw5UAfLaHUP8QyB2")

        val changesByHash = archivalEndpoint.getAccessKeyChanges(keysRequest, block.header.hash)

        val changesByBlockId = archivalEndpoint.getAccessKeyChanges(keysRequest, block.header.height)
        assertEquals(changesByBlockId, changesByHash, "changes in block by id and hash should be equal")
    }

    @Test
    fun getSingleAccessKeyChanges_whenFinal_thenCorrect() = runBlocking {
        val accountId = "tx1.api_kotlin.testnet"
        val publicKey = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF")
        val keysRequest = listOf(AccountWithPublicKey(accountId, publicKey))

        val finalChanges = archivalEndpoint.getAccessKeyChanges(keysRequest)
        assertNotNull(finalChanges.blockHash, "block hash should not be null")
    }

    @Test
    fun getAllAccessKeyChanges_whenConcreteBlock_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val accountIds = listOf(accountId)

        val block = archivalBlockEndpoint.getBlock("6r9Kc66jNnkoDW2PyvqT9CLPvtaYnrcrU5Uq5htguB4a")

        val changesByBlockHash = archivalEndpoint.getAllAccessKeysChanges(accountIds, block.header.hash)

        val changesByBlockId = archivalEndpoint.getAllAccessKeysChanges(accountIds, block.header.height)
        assertEquals(changesByBlockId, changesByBlockHash, "changes in block by id and hash should be equal")
    }

    @Test
    fun getAllAccessKeyChanges_whenFinal_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val accountIds = listOf(accountId)

        val finalChanges = archivalEndpoint.getAllAccessKeysChanges(accountIds)
        assertNotNull(finalChanges.blockHash, "block hash should not be null")
    }
}
