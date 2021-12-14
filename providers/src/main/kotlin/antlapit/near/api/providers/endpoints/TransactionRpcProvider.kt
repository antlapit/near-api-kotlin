package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.TransactionProvider
import antlapit.near.api.providers.Utils
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.transaction.FinalExecutionOutcome
import antlapit.near.api.providers.model.transaction.Signer
import antlapit.near.api.providers.model.transaction.Transaction
import antlapit.near.api.providers.sha256
import org.near.borshj.Borsh

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

    override suspend fun sendTxAsync(transaction: Transaction, signer: Signer, timeout: Long): CryptoHash {
        val message = Borsh.serialize(transaction) // TODO use the borshj
        val hash = String(message).sha256()
        TODO("Not yet implemented")
        // const message = serialize(SCHEMA, transaction);
        // const hash = new Uint8Array(sha256.sha256.array(message));
        // const signature = await signer.signMessage(message, accountId, networkId);
        // const signedTx = new SignedTransaction({
        //     transaction,
        //     signature: new Signature({ keyType: transaction.publicKey.keyType, data: signature.signature })
        // });
        // return [hash, signedTx]
    }

    /**
     * @link https://docs.near.org/docs/api/rpc/transactions#send-transaction-await
     */
    override suspend fun sendTxAndWait(signedTx: String, timeout: Long): FinalExecutionOutcome =
        jsonRpcProvider.sendRpc(
            method = "broadcast_tx_commit",
            params = listOf(Utils.encodeToBase64(signedTx)),
            timeout = timeout
        )

    override suspend fun sendTxAndWait(transaction: Transaction, signer: Signer, timeout: Long): FinalExecutionOutcome {
        TODO("Not yet implemented")
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

}
