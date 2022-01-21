package org.near.api.model.account

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.Balance
import org.near.api.model.primitives.PublicKey

data class AccountInfo(
    val accountId: AccountId,
    val publicKey: PublicKey,
    val amount: Balance
)
