package org.near.api.providers.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.near.api.providers.base.config.JsonRpcConfig
import org.near.api.providers.base.config.NetworkEnum
import org.near.api.providers.model.account.AccountInBlock
import kotlin.test.Test

@ExperimentalCoroutinesApi
class JsonRpcProviderTest {

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))

    @Test
    fun getAccount_whenPathParam_thenCorrect() = runBlocking {
        val resp = client.query<AccountInBlock>("account/api_kotlin.testnet", "")
        println(resp)
        return@runBlocking
    }
}
