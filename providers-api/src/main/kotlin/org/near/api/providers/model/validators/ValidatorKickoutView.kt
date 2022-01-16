package org.near.api.providers.model.validators

import org.near.api.providers.model.primitives.AccountId

data class ValidatorKickoutView(
    val accountId: AccountId,
    val reason: ValidatorKickoutReason
)
