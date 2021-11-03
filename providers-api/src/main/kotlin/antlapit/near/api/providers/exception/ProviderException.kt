package antlapit.near.api.providers.exception

enum class ErrorType {
    HANDLER_ERROR, REQUEST_VALIDATION_ERROR, INTERNAL_ERROR
}

enum class ErrorCause(val type: ErrorType) {
    UNKNOWN_BLOCK(ErrorType.HANDLER_ERROR),
    INVALID_ACCOUNT(ErrorType.HANDLER_ERROR),
    UNKNOWN_ACCOUNT(ErrorType.HANDLER_ERROR),
    UNKNOWN_ACCESS_KEY(ErrorType.HANDLER_ERROR),
    UNAVAILABLE_SHARD(ErrorType.HANDLER_ERROR),
    NO_SYNCED_BLOCKS(ErrorType.HANDLER_ERROR),
    REQUEST_VALIDATION_ERROR(ErrorType.REQUEST_VALIDATION_ERROR),
    INTERNAL_ERROR(ErrorType.INTERNAL_ERROR);

    companion object {
        @JvmStatic
        fun findByCode(code: String) = values().firstOrNull { cause -> cause.name == code }
    }
}

open class ProviderException(
    override val message: String?
) : RuntimeException(message) {
    constructor(errorType: String, errorCause: String, info: Map<String, Any?>?) : this("RPC client exception ${errorType}, caused by $errorCause")

    constructor(errorCause: ErrorCause, info: Map<String, Any?>?) : this(errorCause.type.name, errorCause.name, info)

    companion object {
        @JvmStatic
        fun byCause(errorCause: ErrorCause, info: Map<String, Any?>?) : ProviderException {
            return when (errorCause) {
                ErrorCause.UNKNOWN_BLOCK -> UnknownBlockException(info)
                ErrorCause.INVALID_ACCOUNT -> InvalidAccountException(info)
                ErrorCause.UNKNOWN_ACCOUNT -> UnknownAccountException(info)
                ErrorCause.UNKNOWN_ACCESS_KEY -> UnknownAccessKeyException(info)
                ErrorCause.UNAVAILABLE_SHARD -> UnavailableShardException(info)
                ErrorCause.NO_SYNCED_BLOCKS -> NoSyncedBlocksException(info)
                ErrorCause.REQUEST_VALIDATION_ERROR -> RequestValidationErrorException(info)
                ErrorCause.INTERNAL_ERROR -> InternalErrorException(info)
            }
        }
    }
}

/**
 * Reason: The requested block has not been produced yet or it has been garbage-collected (cleaned up to save space on the RPC node)
 * <br />
 * Solution:
 * <ul>
 *    <li>Check that the requested block is legit</li>
 *    <li>If the block had been produced more than 5 epochs ago, try to send your request to an archival node</li>
 * </ul>
 */
class UnknownBlockException(info: Map<String, Any?>?) : ProviderException(ErrorCause.UNKNOWN_BLOCK, info)

/**
 * Reason: The requested <b>account_id</b> is invalid
 * <br />
 * Solution: Provide a valid <b>account_id</b>
 */
class InvalidAccountException(info: Map<String, Any?>?) : ProviderException(ErrorCause.INVALID_ACCOUNT, info)

/**
 * Reason: The requested <b>account_id</b> has not been found while viewing since the account has not been created or has been already deleted
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the <b>account_id</b></li>
 *    <li>Specify a different block or retry if you request the latest state</li>
 * </ul>
 */
class UnknownAccountException(info: Map<String, Any?>?) : ProviderException(ErrorCause.UNKNOWN_ACCOUNT, info)

/**
 * Reason: The requested <b>public_key</b> has not been found while viewing since the public key has not been created or has been already deleted
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the <b>public_key</b></li>
 *    <li>Specify a different block or retry if you request the latest state</li>
 * </ul>
 */
class UnknownAccessKeyException(info: Map<String, Any?>?) : ProviderException(ErrorCause.UNKNOWN_ACCESS_KEY, info)

/**
 * Reason: The node was unable to found the requested data because it does not track the shard where data is present
 * <br />
 * Solution: Send a request to a different node which might track the shard
 */
class UnavailableShardException(info: Map<String, Any?>?) : ProviderException(ErrorCause.UNAVAILABLE_SHARD, info)

/**
 * Reason: The node is still syncing and the requested block is not in the database yet
 * <br />
 * Solution:
 * <ul>
 *    <li>Wait until the node finish syncing</li>
 *    <li>Send a request to a different node which is synced</li>
 * </ul>
 */
class NoSyncedBlocksException(info: Map<String, Any?>?) : ProviderException(ErrorCause.NO_SYNCED_BLOCKS, info)

/**
 * Reason: Passed arguments can't be parsed by JSON RPC server (missing arguments, wrong format, etc.)
 * <br />
 * Solution:
 * <ul>
 *    <li>Check the arguments passed and pass the correct ones</li>
 *    <li>Check info for more details</li>
 * </ul>
 */
class RequestValidationErrorException(info: Map<String, Any?>?) : ProviderException(ErrorCause.REQUEST_VALIDATION_ERROR, info)

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
class InternalErrorException(info: Map<String, Any?>?) : ProviderException(ErrorCause.INTERNAL_ERROR, info)
