package antlapit.near.api.providers

import antlapit.near.api.providers.model.transaction.FinalExecutionOutcome
import antlapit.near.api.providers.primitives.CryptoHash

/**
 * RPC endpoint for transactions
 * @link https://docs.near.org/docs/api/rpc/transactions
 */
class TransactionRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : TransactionProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-async
     */
    override suspend fun sendTxAsync(signedTx: String, timeout: Long): CryptoHash = jsonRpcProvider.sendRpc(
        method = "broadcast_tx_async",
        params = listOf(Utils.encodeToBase64(signedTx)),
        timeout = timeout
    )

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-await
     */
    override suspend fun sendTxAndWait(signedTx: String, timeout: Long): FinalExecutionOutcome =
        jsonRpcProvider.sendRpc(
            method = "broadcast_tx_commit",
            params = listOf(Utils.encodeToBase64(signedTx)),
            timeout = timeout
        )

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     */
    override suspend fun getTx(txHash: CryptoHash, txRecipientId: String, timeout: Long): FinalExecutionOutcome =
        jsonRpcProvider.sendRpc(
            method = "tx",
            params = listOf(txHash, txRecipientId),
            timeout = timeout
        )

}
