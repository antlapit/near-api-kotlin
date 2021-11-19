package antlapit.near.api.providers

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.transaction.FinalExecutionOutcome

interface TransactionProvider {

    /**
     * Sends a transaction and immediately returns transaction hash.
     *
     * @param signedTx Signed Transaction
     * @return Transaction hash
     */
    suspend fun sendTxAsync(signedTx: String, timeout: Long = Constants.DEFAULT_TIMEOUT): CryptoHash

    /**
     * Sends a transaction and waits until transaction is fully complete. (Has a 10 second timeout)
     *
     * @param signedTx Signed Transaction
     * @param timeout
     */
    suspend fun sendTxAndWait(signedTx: String, timeout: Long = Constants.DEFAULT_TIMEOUT): FinalExecutionOutcome

    /**
     * Queries status of a transaction by hash and returns the final transaction result.
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status
     *
     * @param txHash Transaction hash
     * @param txRecipientId Sender account id
     */
    suspend fun getTx(
        txHash: CryptoHash,
        txRecipientId: AccountId,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): FinalExecutionOutcome

    // TODO Transaction Status with Receipts
    // TODO Receipt by ID

}
