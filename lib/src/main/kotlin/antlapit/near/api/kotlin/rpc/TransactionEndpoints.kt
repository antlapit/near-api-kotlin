package antlapit.near.api.kotlin.rpc

/**
 * RPC endpoint for transactions
 * @link https://docs.near.org/docs/api/rpc/transactions
 */
class TransactionEndpoints(private val client: RPCClient) {

    /**
     * Sends a transaction and immediately returns transaction hash.
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-async
     *
     * @param signedTx Signed Transaction
     */
    suspend fun sendTx(signedTx: String) = client.sendRequest(
        method = "broadcast_tx_async",
        params = listOf(Utils.encodeToBase64(signedTx))
    )

    /**
     * Sends a transaction and waits until transaction is fully complete. (Has a 10 second timeout)
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-await
     *
     * @param signedTx Signed Transaction
     */
    suspend fun sendTxAndWait(signedTx: String, timeout: Long) = client.sendRequest(
        method = "broadcast_tx_commit",
        params = listOf(Utils.encodeToBase64(signedTx)),
        timeout = timeout
    )

    /**
     * Queries status of a transaction by hash and returns the final transaction result.
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     *
     * @param txHash Transaction hash
     * @param txRecipientId Sender account id
     */
    suspend fun getTx(txHash: String, txRecipientId: String) = client.sendRequest(
        method = "tx",
        params = listOf(txHash, txRecipientId)
    )

    // TODO Transaction Status with Receipts
    // TODO Receipt by ID
}
