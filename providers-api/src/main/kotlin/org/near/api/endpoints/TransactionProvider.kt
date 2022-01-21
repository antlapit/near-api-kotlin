package org.near.api.endpoints

import org.near.api.model.block.Receipt
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.transaction.FinalExecutionOutcome
import org.near.api.model.transaction.FinalExecutionOutcomeWithReceipts
import org.near.api.model.transaction.SignedTransaction

interface TransactionProvider {

    /**
     * Sends a transaction and immediately returns transaction hash.
     *
     * @param signedTx Signed Transaction in bytes
     * @return Transaction hash
     */
    suspend fun sendTxAsync(signedTx: ByteArray, timeout: Long? = null): CryptoHash

    suspend fun sendTxAsync(
        signedTransaction: SignedTransaction,
        timeout: Long? = null
    ): CryptoHash

    /**
     * Sends a transaction and waits until transaction is fully complete. (Has a 10 second timeout)
     *
     * @param signedTx Signed Transaction
     * @param timeout
     */
    suspend fun sendTxAndWait(signedTx: ByteArray, timeout: Long? = null): FinalExecutionOutcome

    suspend fun sendTxAndWait(
        signedTransaction: SignedTransaction,
        timeout: Long? = null
    ): FinalExecutionOutcome

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
        timeout: Long? = null
    ): FinalExecutionOutcome

    /**
     * Queries status of a transaction by hash and returns the final transaction result and details of all receipts.
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#transaction-status-with-receipts
     *
     * @param txHash Transaction hash
     * @param txRecipientId Sender account id
     */
    suspend fun getTxWithReceipts(
        txHash: CryptoHash,
        txRecipientId: AccountId,
        timeout: Long? = null
    ): FinalExecutionOutcomeWithReceipts

    /**
     * Fetches a receipt by it's ID (as is, without a status or execution outcome)
     *
     * @link https://docs.near.org/docs/api/rpc/transactions#receipt-by-id
     *
     * @param receiptId Valid receipt id
     */
    suspend fun getReceipt(
        receiptId: CryptoHash,
        timeout: Long? = null
    ) : Receipt
}
