package antlapit.near.api.providers.exception

import antlapit.near.api.providers.model.primitives.*

open class ProviderException(
    message: String?,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    constructor(
        errorType: String,
        errorCause: String,
        cause: Throwable? = null
    ) : this("Generic RPC client exception ${errorType}, caused by $errorCause", cause)
}

sealed class HandlerError(message: String?) : ProviderException(message)


/**
 * Reason: The requested block has not been produced yet or it has been garbage-collected (cleaned up to save space on the RPC node)
 * <br />
 * Solution:
 * <ul>
 *    <li>Check that the requested block is legit</li>
 *    <li>If the block had been produced more than 5 epochs ago, try to send your request to an archival node</li>
 * </ul>
 */
class UnknownBlockException(info: Any?) : HandlerError("Unknown block: $info")

/**
 * Reason: The requested chunk can't be found in a database
 * <br />
 * Solution:
 * <ul>
 *    <li>Check that the requested chunk is legit</li>
 *    <li>If the chunk had been produced more than 5 epochs ago, try to send your request to an archival node</li>
 * </ul>
 */
class UnknownChunkException(val chunkHash: CryptoHash) : HandlerError("Unknown chunk: $chunkHash")

/**
 * Reason: Provided shard_id does not exist
 * <br />
 * Solution:
 * <ul>
 *    <li>Provide shard_id for an existing shard</li>
 * </ul>
 */
class InvalidShardIdException(val shardId: ShardId) : HandlerError("Invalid shard id: $shardId")

/**
 * Reason: The node is still syncing and the requested chunk is not in the database yet
 * <br />
 * Solution:
 * <ul>
 *    <li>Wait until the node finish syncing</li>
 *    <li>Send a request to a different node which is synced</li>
 * </ul>
 */
class NotSyncedException(info: Any?) : HandlerError("Not synced: $info")

/**
 * Reason: The requested <b>account_id</b> is invalid
 * <br />
 * Solution: Provide a valid <b>account_id</b>
 */
class InvalidAccountException(
    val requestedAccountId: AccountId,
    val blockHeight: BlockHeight,
    val blockHash: CryptoHash
) : HandlerError("Invalid account: $requestedAccountId in block(height=$blockHeight, hash=$blockHash)")

/**
 * Reason: The requested <b>account_id</b> has not been found while viewing since the account has not been created or has been already deleted
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the <b>account_id</b></li>
 *    <li>Specify a different block or retry if you request the latest state</li>
 * </ul>
 */
class UnknownAccountException(
    val requestedAccountId: AccountId,
    val blockHeight: BlockHeight,
    val blockHash: CryptoHash
) : HandlerError("Unknown account: $requestedAccountId in block(height=$blockHeight, hash=$blockHash)")

/**
 * Reason: The requested <b>public_key</b> has not been found while viewing since the public key has not been created or has been already deleted
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the <b>public_key</b></li>
 *    <li>Specify a different block or retry if you request the latest state</li>
 * </ul>
 */
class UnknownAccessKeyException(
    val publicKey: PublicKey, val blockHeight: BlockHeight,
    val blockHash: CryptoHash
) : HandlerError("Unknown access key: $publicKey in block(height=$blockHeight, hash=$blockHash)")

/**
 * Reason: The node was unable to found the requested data because it does not track the shard where data is present
 * <br />
 * Solution: Send a request to a different node which might track the shard
 */
class UnavailableShardException(info: Any?) : HandlerError("Unavailable shard: $info")

/**
 * Reason: The node is still syncing and the requested block is not in the database yet
 * <br />
 * Solution:
 * <ul>
 *    <li>Wait until the node finish syncing</li>
 *    <li>Send a request to a different node which is synced</li>
 * </ul>
 */
class NoSyncedBlocksException(info: Any?) : HandlerError("No synced blocks: $info")

/**
 * Reason: An error happened during transaction execution
 * <br />
 * Solution:
 * <ul>
 *    <li>See error.cause.info for details</li>
 * </ul>
 */
class InvalidTransactionException(val txExecutionError: TxExecutionError?) : HandlerError(
    "Invalid transaction $txExecutionError"
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InvalidTransactionException

        if (txExecutionError != other.txExecutionError) return false

        return true
    }

    override fun hashCode(): Int {
        return txExecutionError?.hashCode() ?: 0
    }
}


/**
 * Reason: Transaction was routed, but has not been recorded on chain in 10 seconds.
 * <br />
 * Solution:
 * <ul>
 *    <li>Re-submit the request with the identical transaction (in NEAR Protocol unique transactions apply exactly once, so if the previously sent transaction gets applied, this request will just return the known result, otherwise, it will route the transaction to the chain once again)</li>
 *    <li>Check that your transaction is valid</li>
 *    <li>Check that the signer account id has enough tokens to cover the transaction fees (keep in mind that some tokens on each account are locked to cover the storage cost)</li>
 * </ul>
 */
class TimeoutErrorException(info: Any?) : HandlerError("Timeout: $info")

/**
 * Reason: An epoch for the provided block can't be found in a database
 * <br />
 * Solution:
 * <ul>
 *    <li>Check that the requested block is legit</li>
 *    <li>If the block had been produced more than 5 epochs ago, try to send your request to an archival node</li>
 * </ul>
 */
class UnknownEpochException(info: Any?) : HandlerError("Unknown epoch: $info")


sealed class RequestValidationError(message: String?) : ProviderException(message)

/**
 * Reason: Passed arguments can't be parsed by JSON RPC server (missing arguments, wrong format, etc.)
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the arguments passed and pass the correct ones</li>
 *    <li>Check info for more details</li>
 * </ul>
 */
class ParseErrorException(val errorMessage: String?) : RequestValidationError("Parse error: $errorMessage")

sealed class InternalError(message: String?) : ProviderException(message)

/**
 * Reason: Something went wrong with the node itself or overloaded
 * <br />
 * Solution:
 * <ul>
 *    <li>Try again later</li>
 *    <li>Send a request to a different node</li>
 *    <li>Check info for more details</li>
 * </ul>
 */
class InternalErrorException(val errorMessage: String?) : InternalError("Internal error: $errorMessage")
