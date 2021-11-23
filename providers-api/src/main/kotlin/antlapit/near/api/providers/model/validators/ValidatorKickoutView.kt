package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.primitives.AccountId

data class ValidatorKickoutView(
    val accountId: AccountId,
    val reason: ValidatorKickoutReason
)
