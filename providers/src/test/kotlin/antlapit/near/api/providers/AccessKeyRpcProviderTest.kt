package antlapit.near.api.providers

import antlapit.near.api.providers.config.JsonRpcConfig
import antlapit.near.api.providers.config.NetworkEnum
import io.kotest.common.ExperimentalKotest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalKotest
@ExperimentalCoroutinesApi
class AccessKeyRpcProviderTest  {

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

        val keysByHash = endpoint.getAccessKeyList(accountId, finalKeys.blockHash)
        assertEquals(finalKeys, keysByHash)

        val keysByBlockId = endpoint.getAccessKeyList(accountId, finalKeys.blockHeight)
        assertEquals(finalKeys, keysByBlockId)
    }

    @Test
    fun getSingleAccessKey_whenLatest_thenCorrect() = runBlocking {
        val accountId = "api_kotlin.testnet"
        val publicKey = "ed25519:7jytAYzviGM2a3dcXYAD5YTtCcYJ3XiYSwLcHYdEx1uk"
        val finalKeys = endpoint.getAccessKey(accountId, publicKey, Finality.FINAL)

        val keysByHash = endpoint.getAccessKey(accountId, publicKey, finalKeys.blockHash)
        assertEquals(finalKeys, keysByHash)

        val keysByBlockId = endpoint.getAccessKey(accountId, publicKey, finalKeys.blockHeight)
        assertEquals(finalKeys, keysByBlockId)
    }
}
