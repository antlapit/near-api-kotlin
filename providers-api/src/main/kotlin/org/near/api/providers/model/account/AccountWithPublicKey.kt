package org.near.api.providers.model.account

import org.near.api.providers.model.primitives.AccountId
import org.near.api.providers.model.primitives.PublicKey

data class AccountWithPublicKey(
    val accountId: AccountId,
    val publicKey: PublicKey
)
