package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.model.primitives.ActionErrorKind
import antlapit.near.api.providers.model.primitives.ContractCallError
import antlapit.near.api.providers.model.primitives.TxExecutionError
import antlapit.near.api.providers.model.transaction.FinalExecutionStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
internal class TransactionRpcProviderTest {

    private val archivalClient = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var archivalEndpoint: TransactionRpcProvider

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var endpoint: TransactionRpcProvider

    @BeforeTest
    fun initEndpoint() {
        archivalEndpoint = TransactionRpcProvider(archivalClient)
        endpoint = TransactionRpcProvider(client)
    }

    @Test
    fun getTransactionStatus_whenSuccessValue_thenCorrect() = runBlocking {
        val finalExecutionOutcome =
            archivalEndpoint.getTx("AB6pehcunRvvo2ErEWnWsAUX3xFsDTy6He2SgiwqJZQt", "namlebao19.testnet")
        assertEquals(FinalExecutionStatus.SuccessValue(""), finalExecutionOutcome.status)
        return@runBlocking
    }

    @Test
    fun getTransactionStatus_whenTxError_thenCorrect() = runBlocking {
        val finalExecutionOutcome =
            archivalEndpoint.getTx("yHHM8mGPVLToxwPGcMpNEm5TnjQkYPchQNf8NpoVZ8a", "07.oracle.flux-dev")
        assertEquals(
            FinalExecutionStatus.Failure(
                TxExecutionError.ActionError(
                    index = 0, kind = ActionErrorKind.FunctionCallError(
                        error = ContractCallError.ExecutionError(
                            msg = "Smart contract panicked: panicked at 'Can't stake in finalized DataRequest', oracle/src/data_request.rs:316:9"
                        )
                    )
                )
            ), finalExecutionOutcome.status
        )
        return@runBlocking
    }
}
