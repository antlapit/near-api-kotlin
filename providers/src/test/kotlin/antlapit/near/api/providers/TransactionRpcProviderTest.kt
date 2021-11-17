package antlapit.near.api.providers

import antlapit.near.api.providers.model.transaction.FinalExecutionOutcome
import antlapit.near.api.providers.model.transaction.ParamFinalExecutionStatus
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

// TODO attributes checking
@ExperimentalCoroutinesApi
internal class TransactionRpcProviderTest {

    private val client = JsonRpcProvider("https://rpc.testnet.near.org")
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
        //val res = endpoint.getTx("yHHM8mGPVLToxwPGcMpNEm5TnjQkYPchQNf8NpoVZ8a", "07.oracle.flux-dev")
        //println(res)
        val res = client.objectMapper.readValue<ParamFinalExecutionStatus>("{\"Failure\":{\n" +
                "                     \"ActionError\":{\n" +
                "                        \"index\":0,\n" +
                "                        \"kind\":{\n" +
                "                           \"FunctionCallError\":{\n" +
                "                              \"ExecutionError\":\"Smart contract panicked: panicked at 'Can't stake in finalized DataRequest', oracle/src/data_request.rs:316:9\"\n" +
                "                           }\n" +
                "                        }\n" +
                "                     }}}\n")
        println(res)

        val resp = "{\"receipts_outcome\":[{\"block_hash\":\"FWmobESJ2kWuM9tb4K5H2MbXXTnMTrEkLyDn6rDDd8qN\",\"id\":\"9esKrcqaadLFyRzt3tuabinyPahgco2X6Bu7ENJTNrBk\",\"outcome\":{\"executor_id\":\"07.oracle.flux-dev\",\"gas_burnt\":3916493040423,\"logs\":[],\"metadata\":{\"gas_profile\":[{\"cost\":\"BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"3971521665\"},{\"cost\":\"CONTRACT_COMPILE_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"35445963\"},{\"cost\":\"CONTRACT_COMPILE_BYTES\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"98885034750\"},{\"cost\":\"READ_MEMORY_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"10439452800\"},{\"cost\":\"READ_MEMORY_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"433351962\"},{\"cost\":\"WRITE_MEMORY_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"14018974305\"},{\"cost\":\"WRITE_MEMORY_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"1544378724\"},{\"cost\":\"READ_REGISTER_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"10068660744\"},{\"cost\":\"READ_REGISTER_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"54307662\"},{\"cost\":\"WRITE_REGISTER_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"11462089944\"},{\"cost\":\"WRITE_REGISTER_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"2094661764\"},{\"cost\":\"UTF8_DECODING_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"3111779061\"},{\"cost\":\"UTF8_DECODING_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"24492760236\"},{\"cost\":\"STORAGE_READ_BASE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"169070537250\"},{\"cost\":\"STORAGE_READ_KEY_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"928575990\"},{\"cost\":\"STORAGE_READ_VALUE_BYTE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"2968221645\"},{\"cost\":\"TOUCHING_TRIE_NODE\",\"cost_category\":\"WASM_HOST_COST\",\"gas_used\":\"885607575930\"}],\"version\":1},\"receipt_ids\":[\"2Qy9cs8yZpbT3JYH1GjpiCHJDyPRikiPNcV42Qnnk5Th\"],\"status\":{\"Failure\":{\"ActionError\":{\"index\":0,\"kind\":{\"FunctionCallError\":{\"ExecutionError\":\"Smart contract panicked: panicked at 'Can't stake in finalized DataRequest', oracle/src/data_request.rs:316:9\"}}}}},\"tokens_burnt\":\"391649304042300000000\"},\"proof\":[]},{\"block_hash\":\"AQqCdU9kXNcChHrHhCsqTZixdh4S6rUhi5qvwYo77GsH\",\"id\":\"2Qy9cs8yZpbT3JYH1GjpiCHJDyPRikiPNcV42Qnnk5Th\",\"outcome\":{\"executor_id\":\"nonane.testnet\",\"gas_burnt\":223182562500,\"logs\":[],\"metadata\":{\"gas_profile\":[],\"version\":1},\"receipt_ids\":[],\"status\":{\"SuccessValue\":\"\"},\"tokens_burnt\":\"0\"},\"proof\":[]}],\"status\":{\"Failure\":{\"ActionError\":{\"index\":0,\"kind\":{\"FunctionCallError\":{\"ExecutionError\":\"Smart contract panicked: panicked at 'Can't stake in finalized DataRequest', oracle/src/data_request.rs:316:9\"}}}}},\"transaction\":{\"actions\":[{\"FunctionCall\":{\"args\":\"eyJyZXF1ZXN0X2lkIjoiMjg1MTYifQ==\",\"deposit\":\"0\",\"gas\":300000000000000,\"method_name\":\"dr_finalize\"}}],\"hash\":\"yHHM8mGPVLToxwPGcMpNEm5TnjQkYPchQNf8NpoVZ8a\",\"nonce\":69629578009340,\"public_key\":\"ed25519:B5Xj3YAGTu7kJCYpyaGvj1Nmc1VYEU3Tvx6yYGCdFTgT\",\"receiver_id\":\"07.oracle.flux-dev\",\"signature\":\"ed25519:57i6vurX5vdr65UQP8AWawg24dB6ZEojZAdWw2EavHK5LTqxmRmAyG6jMY5rdMjm92cDsCEKfqY2fFRmnHxwW4u7\",\"signer_id\":\"nonane.testnet\"},\"transaction_outcome\":{\"block_hash\":\"9praNAhayNAj8i7YS9CsKdZdXLGgWvTBYmX7n2Dg2G8W\",\"id\":\"yHHM8mGPVLToxwPGcMpNEm5TnjQkYPchQNf8NpoVZ8a\",\"outcome\":{\"executor_id\":\"nonane.testnet\",\"gas_burnt\":2427994785822,\"logs\":[],\"metadata\":{\"gas_profile\":null,\"version\":1},\"receipt_ids\":[\"9esKrcqaadLFyRzt3tuabinyPahgco2X6Bu7ENJTNrBk\"],\"status\":{\"SuccessReceiptId\":\"9esKrcqaadLFyRzt3tuabinyPahgco2X6Bu7ENJTNrBk\"},\"tokens_burnt\":\"242799478582200000000\"},\"proof\":[{\"direction\":\"Right\",\"hash\":\"AcTZh2Zan2HtJmomQ6AtZT2e3kciGJwjDAvaqQxUrHd8\"}]}}"
        client.objectMapper.readValue<FinalExecutionOutcome>(resp)
        return@runBlocking
    }

}
