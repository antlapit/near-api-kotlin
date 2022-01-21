package org.near.api.model.validators

import org.near.api.model.primitives.AccountId

data class ValidatorKickoutView(
    val accountId: AccountId,
    val reason: ValidatorKickoutReason
)
