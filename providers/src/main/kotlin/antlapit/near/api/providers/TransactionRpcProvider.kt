package antlapit.near.api.providers

/**
 * RPC endpoint for transactions
 * @link https://docs.near.org/docs/api/rpc/transactions
 */
class TransactionRpcProvider(private val client: BaseJsonRpcProvider) : TransactionProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-async
     */
    override suspend fun sendTx(signedTx: String) = client.sendJsonRpc(
        method = "broadcast_tx_async",
        params = listOf(Utils.encodeToBase64(signedTx))
    )

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-await
     */
    override suspend fun sendTxAndWait(signedTx: String, timeout: Long) = client.sendJsonRpc(
        method = "broadcast_tx_commit",
        params = listOf(Utils.encodeToBase64(signedTx)),
        timeout = timeout
    )

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     */
    override suspend fun getTx(txHash: String, txRecipientId: String) = client.sendJsonRpc(
        method = "tx",
        params = listOf(txHash, txRecipientId)
    )

}
