package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

// TODO attributes checking
@ExperimentalCoroutinesApi
internal class TransactionRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var endpoint: TransactionRpcProvider

    @BeforeTest
    fun initEndpoint() {
        endpoint = TransactionRpcProvider(client)
    }

    @Test
    fun getTransactionStatus_whenSuccessValue_thenCorrect() = runBlocking {
        val res = endpoint.getTx("AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt", "namlebao19.testnet")
        println(res)
        return@runBlocking
    }

    @Test
    fun getTransactionStatus_whenTxError_thenCorrect() = runBlocking {
        val res = endpoint.getTx("yHHM8mGPVLToxwPGcMpNEm5TnjQkYPchQNf8NpoVZ8a", "07.oracle.flux-dev")
        println(res)
        return@runBlocking
    }

}
