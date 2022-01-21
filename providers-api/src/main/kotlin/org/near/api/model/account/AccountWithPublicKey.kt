package org.near.api.model.account

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.PublicKey

data class AccountWithPublicKey(
    val accountId: AccountId,
    val publicKey: PublicKey
)
