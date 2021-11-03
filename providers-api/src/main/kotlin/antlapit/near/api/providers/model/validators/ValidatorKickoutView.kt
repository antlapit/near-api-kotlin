package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.primitives.AccountId

data class ValidatorKickoutView(
    val accountId: AccountId,
    //val reason: ValidatorKickoutReason
)
