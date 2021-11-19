package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.primitives.PublicKey
import antlapit.near.api.providers.model.primitives.ShardId

data class NextEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?,
    val shards: List<ShardId>
)
