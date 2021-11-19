package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.primitives.PublicKey

data class ValidatorStakeView(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?
)
