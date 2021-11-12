package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.primitives.Balance

interface AccessKeyPermission

interface ParametrizedAccessKeyPermission : AccessKeyPermission

data class FunctionCall(
    val allowance: Balance?,
    val receiverId: String,
    val methodNames: List<String> = emptyList(),
) : ParametrizedAccessKeyPermission

enum class NotParametrizedAccessKeyPermission : AccessKeyPermission {
    FullAccess
}
