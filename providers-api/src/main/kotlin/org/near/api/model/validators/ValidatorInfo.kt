package org.near.api.model.validators

import org.near.api.model.primitives.AccountId

data class ValidatorInfo(
    val accountId: AccountId,
    val isSlashed: Boolean
)
