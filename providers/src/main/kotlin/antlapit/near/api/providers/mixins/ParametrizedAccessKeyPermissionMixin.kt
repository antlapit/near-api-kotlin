package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.accesskey.FunctionCall
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = FunctionCall::class, name = "FunctionCall")
)
internal abstract class ParametrizedAccessKeyPermissionMixin
