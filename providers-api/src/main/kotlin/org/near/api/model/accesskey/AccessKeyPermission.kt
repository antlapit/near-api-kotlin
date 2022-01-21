package org.near.api.model.accesskey

import org.near.api.model.primitives.Balance
import org.near.api.model.rust.RustEnum

@RustEnum
sealed interface AccessKeyPermission {
    object FullAccess : org.near.api.model.accesskey.AccessKeyPermission
    data class FunctionCall(
        val allowance: Balance?,
        val receiverId: String,
        val methodNames: List<String> = emptyList(),
    ) : org.near.api.model.accesskey.AccessKeyPermission
}
