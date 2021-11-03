package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.primitives.AccountId

data class ValidatorInfo(
    val accountId: AccountId,
    val isSlashed: Boolean
)
