package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.PublicKey

data class ValidatorStakeView(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?
)
