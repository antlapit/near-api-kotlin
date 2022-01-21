package org.near.api.model.validators

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.Balance
import org.near.api.model.primitives.PublicKey
import org.near.api.model.primitives.ShardId

data class NextEpochValidatorInfo(
    val accountId: AccountId,
    val publicKey: PublicKey?,
    val stake: Balance?,
    val shards: List<ShardId>
)
