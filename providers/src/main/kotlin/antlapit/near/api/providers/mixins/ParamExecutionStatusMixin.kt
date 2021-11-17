package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.transaction.Failure
import antlapit.near.api.providers.model.transaction.SuccessReceiptId
import antlapit.near.api.providers.model.transaction.SuccessValue
import antlapit.near.api.providers.primitives.ActionError
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.InvalidTxError
import antlapit.near.api.providers.primitives.TxExecutionError
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = SuccessValue::class, name = "SuccessValue"),
    JsonSubTypes.Type(value = Failure::class, name = "Failure"),
    JsonSubTypes.Type(value = SuccessReceiptId::class, name = "SuccessReceiptId"),
)
internal abstract class ParamExecutionStatusMixin

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = SuccessValue::class, name = "SuccessValue"),
    JsonSubTypes.Type(value = Failure::class, name = "Failure"),
)
internal abstract class ParamFinalExecutionStatusMixin

internal abstract class SuccessReceiptIdMixin @JsonCreator constructor(
    val receiptId: CryptoHash
)

internal abstract class SuccessValueMixin @JsonCreator constructor(
    val value: String
)

internal abstract class FailureMixin @JsonCreator(mode = JsonCreator.Mode.DELEGATING) constructor(
    val txError: TxExecutionError
)


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = ActionError::class, name = "ActionError"),
    JsonSubTypes.Type(value = InvalidTxError::class, name = "InvalidTxError"),
)
internal abstract class TxExecutionErrorMixin
