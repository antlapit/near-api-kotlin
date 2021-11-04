package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.PublicKey
import antlapit.near.api.providers.primitives.ShardId

data class NextEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?,
    val shards: List<ShardId>
)
