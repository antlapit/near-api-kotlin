package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.TxExecutionError

interface ExecutionStatus

interface ParamExecutionStatus : ExecutionStatus

/**
 * The execution has failed.
 **/
data class SuccessReceiptId(
    val receiptId: CryptoHash
) : ParamExecutionStatus

enum class SimpleExecutionStatus : ExecutionStatus {
    // The execution is pending or unknown.
    Unknown
}

interface FinalExecutionStatus

interface ParamFinalExecutionStatus : FinalExecutionStatus

enum class SimpleFinalExecutionStatus : FinalExecutionStatus {
    // The execution has not yet started.
    NotStarted,

    // The execution has started and still going.
    Started,
}

/**
 * The final action succeeded and returned some value or an empty vec encoded in base64.
 **/
data class SuccessValue(
    val value: String
) : ParamExecutionStatus, ParamFinalExecutionStatus

/**
 * Execution failure
 */
data class Failure(
    val txError: TxExecutionError
) : ParamExecutionStatus, ParamFinalExecutionStatus
