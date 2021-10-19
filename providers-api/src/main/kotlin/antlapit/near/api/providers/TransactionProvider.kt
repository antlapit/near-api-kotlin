package antlapit.near.api.providers

interface TransactionProvider {

    /**
     * Sends a transaction and immediately returns transaction hash.
     *
     * @param signedTx Signed Transaction
     */
    suspend fun sendTx(signedTx: String): Any

    /**
     * Sends a transaction and waits until transaction is fully complete. (Has a 10 second timeout)
     *
     * @param signedTx Signed Transaction
     * @param timeout
     */
    suspend fun sendTxAndWait(signedTx: String, timeout: Long): Any

    /**
     * Queries status of a transaction by hash and returns the final transaction result.
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     *
     * @param txHash Transaction hash
     * @param txRecipientId Sender account id
     */
    suspend fun getTx(txHash: String, txRecipientId: String): Any

    // TODO Transaction Status with Receipts
    // TODO Receipt by ID

}
