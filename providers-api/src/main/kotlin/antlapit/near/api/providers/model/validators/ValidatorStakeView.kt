package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.primitives.PublicKey

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
