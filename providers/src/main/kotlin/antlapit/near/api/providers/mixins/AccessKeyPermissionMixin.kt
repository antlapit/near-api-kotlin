package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.accesskey.FullAccessAccessKeyPermission
import antlapit.near.api.providers.model.accesskey.FunctionCallAccessKeyPermission
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = FunctionCallAccessKeyPermission::class, name = "FunctionCall"),
    JsonSubTypes.Type(value = FullAccessAccessKeyPermission::class, name = "FullAccess")
)
internal abstract class AccessKeyPermissionMixin
