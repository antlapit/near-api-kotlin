package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.Nonce

data class AccessKey(
    val nonce: Nonce,
    val permission: AccessKeyPermission
)

open class AccessKeyPermission

data class FunctionCallAccessKeyPermission(
    val allowance: Balance?,
    val receiverId: String,
    val methodNames: List<String> = emptyList(),
) : AccessKeyPermission()

class FullAccessAccessKeyPermission : AccessKeyPermission()
