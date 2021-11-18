package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.primitives.Balance

sealed class AccessKeyPermission {
    object FullAccess : AccessKeyPermission()
    data class FunctionCall(
        val allowance: Balance?,
        val receiverId: String,
        val methodNames: List<String> = emptyList(),
    ) : AccessKeyPermission()
}
