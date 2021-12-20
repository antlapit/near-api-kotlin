package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.Finality
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.primitives.PublicKey
import io.kotest.common.ExperimentalKotest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * End-to-end test of access keys endpoints
 */
@ExperimentalKotest
@ExperimentalCoroutinesApi
class AccessKeyRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: AccessKeyRpcProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = AccessKeyRpcProvider(client)
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
        assertEquals(AccessKeyPermission.FullAccess, finalKey.permission, "access key should be FullAccess key")

        val keysByHash = endpoint.getAccessKey(accountId, publicKey, finalKey.blockHash)
        assertEquals(finalKey, keysByHash, "access key by block hash should equals latest access key")

        val keysByBlockId = endpoint.getAccessKey(accountId, publicKey, finalKey.blockHeight)
        assertEquals(finalKey, keysByBlockId, "access key by block id should equals latest access key")
    }
}
