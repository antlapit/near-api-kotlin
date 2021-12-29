package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.TxExecutionError
import antlapit.near.api.providers.model.rust.RustEnum
import antlapit.near.api.providers.model.rust.RustSinglePropertyEnumItem

@RustEnum
sealed interface ExecutionStatus {
    object Unknown : ExecutionStatus

    /**
     * The execution has failed.
     **/
    @RustSinglePropertyEnumItem
    data class SuccessReceiptId(val receiptId: CryptoHash) : ExecutionStatus

    /**
     * The final action succeeded and returned some value or an empty vec encoded in base64.
     **/
    @RustSinglePropertyEnumItem
    data class SuccessValue(val value: String?) : ExecutionStatus

    /**
     * Execution failure
     */
    @RustSinglePropertyEnumItem
    data class Failure(val txError: TxExecutionError) : ExecutionStatus
}

@RustEnum
sealed interface FinalExecutionStatus {
    // The execution has not yet started.
    object NotStarted : FinalExecutionStatus

    // The execution has started and still going.
    object Started : FinalExecutionStatus

    /**
     * The final action succeeded and returned some value or an empty vec encoded in base64.
     **/
    @RustSinglePropertyEnumItem
    data class SuccessValue(val value: String?) : FinalExecutionStatus

    /**
     * Execution failure
     */
    @RustSinglePropertyEnumItem
    data class Failure(val txError: TxExecutionError) : FinalExecutionStatus
}
