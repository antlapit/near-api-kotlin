package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.block.ActionReceipt
import antlapit.near.api.providers.model.block.DataReceipt
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = ActionReceipt::class, name = "Action"),
    JsonSubTypes.Type(value = DataReceipt::class, name = "Data")
)
internal abstract class ReceiptEnumMixin
