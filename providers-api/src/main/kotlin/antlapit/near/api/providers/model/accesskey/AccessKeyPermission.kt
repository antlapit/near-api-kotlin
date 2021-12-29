package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.rust.RustEnum

@RustEnum
sealed interface AccessKeyPermission {
    object FullAccess : AccessKeyPermission
    data class FunctionCall(
        val allowance: Balance?,
        val receiverId: String,
        val methodNames: List<String> = emptyList(),
    ) : AccessKeyPermission
}
