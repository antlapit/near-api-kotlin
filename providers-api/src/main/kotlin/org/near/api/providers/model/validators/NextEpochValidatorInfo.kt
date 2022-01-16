package org.near.api.providers.model.validators

import org.near.api.providers.model.primitives.AccountId
import org.near.api.providers.model.primitives.Balance
import org.near.api.providers.model.primitives.PublicKey
import org.near.api.providers.model.primitives.ShardId

data class NextEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?,
    val shards: List<ShardId>
)
