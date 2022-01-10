package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.PublicKey

data class AccountWithPublicKey(
    val accountId: AccountId,
    val publicKey: PublicKey
)
