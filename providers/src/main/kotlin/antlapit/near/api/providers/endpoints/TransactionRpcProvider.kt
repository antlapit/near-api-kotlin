package antlapit.near.api.providers.endpoints

import antlapit.near.api.borsh.encode
import antlapit.near.api.providers.TransactionProvider
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.base64
import antlapit.near.api.providers.model.block.Receipt
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.transaction.FinalExecutionOutcome
import antlapit.near.api.providers.model.transaction.FinalExecutionOutcomeWithReceipts
import antlapit.near.api.providers.model.transaction.SignedTransaction

/**
 * RPC endpoint for transactions
 * @link https://docs.near.org/docs/api/rpc/transactions
 */
class TransactionRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : TransactionProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-async
     */
    override suspend fun sendTxAsync(signedTx: ByteArray, timeout: Long): CryptoHash = jsonRpcProvider.sendRpc(
        method = "broadcast_tx_async",
        params = listOf(signedTx.base64()),
        timeout = timeout
    )

    override suspend fun sendTxAsync(
        signedTransaction: SignedTransaction,
        timeout: Long
    ): CryptoHash {
        val bytes = signedTransaction.encode()
        return sendTxAsync(bytes, timeout)
    }

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-await
     */
    override suspend fun sendTxAndWait(signedTx: ByteArray, timeout: Long): FinalExecutionOutcome =
        jsonRpcProvider.sendRpc(
            method = "broadcast_tx_commit",
            params = listOf(signedTx.base64()),
            timeout = timeout
        )

    override suspend fun sendTxAndWait(
        signedTransaction: SignedTransaction,
        timeout: Long
    ): FinalExecutionOutcome {
        val bytes = signedTransaction.encode()
        return sendTxAndWait(bytes, timeout)
    }

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     */
    override suspend fun getTx(txHash: CryptoHash, txRecipientId: String, timeout: Long): FinalExecutionOutcome =
        jsonRpcProvider.sendRpc(
            method = "tx",
            params = listOf(txHash, txRecipientId),
            timeout = timeout
        )

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     */
    override suspend fun getTxWithReceipts(txHash: CryptoHash, txRecipientId: String, timeout: Long): FinalExecutionOutcomeWithReceipts =
        jsonRpcProvider.sendRpc(
            method = "EXPERIMENTAL_tx_status",
            params = listOf(txHash, txRecipientId),
            timeout = timeout
        )

    override suspend fun getReceipt(receiptId: CryptoHash, timeout: Long): Receipt = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_receipt",
        params = mapOf(
            "receipt_id" to receiptId
        ),
        timeout
    )
}
