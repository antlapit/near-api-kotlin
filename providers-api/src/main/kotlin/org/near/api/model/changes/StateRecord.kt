package org.near.api.model.changes

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.PublicKey
import org.near.api.model.rust.RustEnum

@RustEnum
sealed interface StateRecord {
    data class Account(
        val accountId: AccountId,
        val account: org.near.api.model.account.Account
    ) : StateRecord

    data class AccessKey(
        val accountId: AccountId,
        val publicKey: PublicKey,
        val accessKey: org.near.api.model.accesskey.AccessKey
    ) : StateRecord

    data class Data(val accountId: AccountId, val dataKey: String, val value: String) : StateRecord

    data class Contract(val accountId: AccountId, val code: String) : StateRecord

}
