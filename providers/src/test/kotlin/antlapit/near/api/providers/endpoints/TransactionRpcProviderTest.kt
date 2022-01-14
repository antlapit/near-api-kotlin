package antlapit.near.api.providers.endpoints

import antlapit.near.api.borsh.encode
import antlapit.near.api.providers.Constants.Companion.DEFAULT_TIMEOUT
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.base.config.NetworkEnum
import antlapit.near.api.providers.exception.InvalidTransactionException
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.*
import antlapit.near.api.providers.model.transaction.*
import com.iwebpp.crypto.TweetNacl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.komputing.khash.sha256.extensions.sha256
import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TransactionRpcProviderTest {

    private val archivalClient = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET_ARCHIVAL))
    private lateinit var archivalEndpoint: TransactionRpcProvider

    private val client = JsonRpcProvider(JsonRpcConfig(NetworkEnum.TESTNET))
    private lateinit var endpoint: TransactionRpcProvider
    private lateinit var blockEndpoint: BlockRpcProvider
    private lateinit var accessKeyEndpoint: AccessKeyRpcProvider
    private lateinit var contractEndpoint: ContractRpcProvider

    private val tx1Account = "tx1.api_kotlin.testnet"
    private val tx1Public = PublicKey("ed25519:5zpBhMxTtD4ozFsBRV9v5hPKTDDFquHqj8gXGERGh6YF")
    private val tx1Private = "4Qtz6nhCkHFQGHB7Q2XPDLRNN37oc4fsUe4ar8cNZmRC5LHZBHR1XTG9ZEUS5wZ4uvVPVxRUeiyMhZAnAthyqdZh"

    private val tx2Account = "tx2.api_kotlin.testnet"
    private val tx2Public = PublicKey("ed25519:319YAVp3QD4R7b3dUGq4hiU9HvmGiEMjmUeY6riRpLFu")
    private val tx2Private = "5d8TxKjUVVLg1B12LUbSVQe82bfE96tKEVmFDSDuXjL3HyAqktFzM6C15bDFf6vfdvYSXWxaUtFQ6Z5Vtbjwiydu"

    private val transferAmount = BigInteger("1000000000000000000000000")

    @BeforeAll
    fun initEndpoint() {
        archivalEndpoint = TransactionRpcProvider(archivalClient)
        endpoint = TransactionRpcProvider(client)
        blockEndpoint = BlockRpcProvider(client)
        accessKeyEndpoint = AccessKeyRpcProvider(client)
        contractEndpoint = ContractRpcProvider(client)
    }

    @AfterAll
    fun close() {
        client.close()
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

    /**
     * This test performs 2 synchronous token transfers:
     * <ul>
     *     <li>from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet with amount 1N</li>
     *     <li>from tx2.api_kotlin.testnet to tx1.api_kotlin.testnet with amount 1N</li>
     * </ul>
     *
     * This test sometimes produce INVALID_TRANSACTION error, so retry added for this test
     */
    @Test
    fun sendTxAndWait_whenSuccess_thenCorrect() = runBlocking {
        val transferAmount = BigInteger("1000000000000000000000000")

        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        var result: FinalExecutionOutcome =
            transferTestBody(tx1Account, tx1Public, tx1Private, tx2Account, transferAmount)

        val resultTransaction = result.transaction
        assertEquals(tx1Public, resultTransaction.publicKey)
        assertEquals(tx1Account, resultTransaction.signerId)
        assertEquals(tx2Account, resultTransaction.receiverId)
        assertEquals(Action.Transfer(transferAmount), resultTransaction.actions[0])

        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        result = transferTestBody(tx2Account, tx2Public, tx2Private, tx1Account, transferAmount)
    }

    /**
     * This test checks InvalidTransactionException while transferring with wrong Nonce
     */
    @Test
    fun sendTxAndWait_whenInvalidTx_thenException() = runBlocking {
        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        val toTx2 = createTransferTx(tx1Account, tx1Public, tx2Account, transferAmount, 0)
        val signedToTx2 = sign(toTx2, tx1Public, tx1Private)
        val e = assertFails(
            message = "InvalidTransactionException expected"
        ) {
            endpoint.sendTxAndWait(signedToTx2)
        }
        assertTrue(e is InvalidTransactionException)
        assertTrue(e.txExecutionError is InvalidTxError.InvalidNonce)
    }

    private suspend fun transferTestBody(
        sender: String,
        senderPublicKey: PublicKey,
        senderPrivateKey: String,
        receiver: String,
        transferAmount: BigInteger
    ): FinalExecutionOutcome {
        var counter = 3
        while (true) {
            val toTx2 = createTransferTx(sender, senderPublicKey, receiver, transferAmount)
            val signedToTx2 = sign(toTx2, senderPublicKey, senderPrivateKey)
            try {
                val result = endpoint.sendTxAndWait(signedToTx2, DEFAULT_TIMEOUT * 6)
                println("Processed transaction in explorer https://explorer.testnet.near.org/transactions/${result.transaction.hash}")
                return result
            } catch (e: InvalidTransactionException) {
                if (e.txExecutionError != null && (e.txExecutionError is InvalidTxError.InvalidNonce || e.txExecutionError is InvalidTxError.Expired)) {
                    --counter
                    println("Invalid transaction exception - retries left $counter")
                    if (counter == 0) {
                        throw e
                    }
                } else {
                    throw e
                }
            }
        }
    }

    /**
     * This test performs 2 asynchronous token transfers:
     * <ul>
     *     <li>from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet with amount 1N</li>
     *     <li>from tx2.api_kotlin.testnet to tx1.api_kotlin.testnet with amount 1N</li>
     * </ul>
     */
    @Test
    fun sendTxAsync_whenSuccess_thenCorrect() = runBlocking {
        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        val toTx2 = createTransferTx(tx1Account, tx1Public, tx2Account, transferAmount)
        val signedToTx2 = sign(toTx2, tx1Public, tx1Private)

        val result1 = endpoint.sendTxAsync(signedToTx2)
        println("Processed transaction in explorer https://explorer.testnet.near.org/transactions/${result1}")

        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        val toTx1 = createTransferTx(tx2Account, tx2Public, tx1Account, transferAmount)
        val signedToTx1 = sign(toTx1, tx2Public, tx2Private)

        val result2 = endpoint.sendTxAsync(signedToTx1)
        println("Processed transaction in explorer https://explorer.testnet.near.org/transactions/${result2}")
    }

    /**
     * This test performs 2 synchronous token transfers and <b>checks receipts</b>:
     * <ul>
     *     <li>from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet with amount 1N</li>
     *     <li>from tx2.api_kotlin.testnet to tx1.api_kotlin.testnet with amount 1N</li>
     * </ul>
     *
     * This test sometimes produce INVALID_TRANSACTION error, so retry added for this test
     */
    @Test
    fun sendTxAndWait_whenCheckingReceipts_thenCorrect() = runBlocking {
        val transferAmount = BigInteger("1000000000000000000000000")

        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        var result: FinalExecutionOutcome =
            transferTestBody(tx1Account, tx1Public, tx1Private, tx2Account, transferAmount)

        val resultTransaction = result.transaction
        assertEquals(tx1Public, resultTransaction.publicKey)
        assertEquals(tx1Account, resultTransaction.signerId)
        assertEquals(tx2Account, resultTransaction.receiverId)
        assertEquals(Action.Transfer(transferAmount), resultTransaction.actions[0])

        // transfer token from tx1.api_kotlin.testnet to tx2.api_kotlin.testnet
        result = transferTestBody(tx2Account, tx2Public, tx2Private, tx1Account, transferAmount)

        // checking receipts
        val txWithReceipts = endpoint.getTxWithReceipts(result.transaction.hash, tx2Account)
        assertEquals(2, txWithReceipts.receipts.size)

        val receipt = endpoint.getReceipt(txWithReceipts.receipts[0].receiptId)
        assertEquals(tx1Account, receipt.receiverId)
        assertEquals(tx2Account, receipt.predecessorId)
    }

    private suspend fun createTransferTx(
        senderId: String,
        senderPublicKey: PublicKey,
        receiverId: String,
        transferAmount: BigInteger
    ): Transaction {
        // load access key for sender account for getting Nonce
        val accessKey = accessKeyEndpoint.getAccessKey(senderId, senderPublicKey)
        return createTransferTx(senderId, senderPublicKey, receiverId, transferAmount, accessKey.nonce)
    }

    private suspend fun createTransferTx(
        senderId: String,
        senderPublicKey: PublicKey,
        receiverId: String,
        transferAmount: BigInteger,
        accessKeyNonce: Nonce
    ): Transaction {
        // load latest block for transaction
        val latestBlock = blockEndpoint.getLatestBlock()

        return Transaction(
            signerId = senderId,
            publicKey = senderPublicKey,
            nonce = accessKeyNonce + 1,
            receiverId = receiverId,
            actions = listOf(
                Action.Transfer(transferAmount)
            ),
            blockHash = latestBlock.header.hash
        )
    }

    private fun sign(
        transaction: Transaction,
        publicKey: PublicKey,
        secretKey: String
    ): SignedTransaction {
        val message = transaction.encode()
        val hash = message.sha256()
        val keys = TweetNacl.Signature(publicKey.decodeBase58(), secretKey.decodeBase58())
        val signature = keys.detached(hash)
        return SignedTransaction(
            transaction = transaction,
            signature = TransactionSignature(signature = signature)
        )
    }
}
