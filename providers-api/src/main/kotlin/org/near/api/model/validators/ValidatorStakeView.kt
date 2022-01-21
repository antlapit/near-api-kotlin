package org.near.api.model.validators

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.Balance
import org.near.api.model.primitives.PublicKey

data class ValidatorStakeView(
    val validatorStakeStructVersion: ValidatorStakeStructVersion = ValidatorStakeStructVersion.V1,
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?,
    val isChunkOnly: Boolean? = false
)

enum class ValidatorStakeStructVersion {
    V1, V2
}
