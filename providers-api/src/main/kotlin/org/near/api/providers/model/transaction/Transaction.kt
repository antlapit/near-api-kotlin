package org.near.api.providers.model.transaction

import org.near.api.providers.model.block.Action
import org.near.api.providers.model.primitives.AccountId
import org.near.api.providers.model.primitives.CryptoHash
import org.near.api.providers.model.primitives.Nonce
import org.near.api.providers.model.primitives.PublicKey

/**
 * Transaction in RPC requests
 */
data class Transaction(
    val signerId: AccountId,
    val publicKey: PublicKey,
    val nonce: Nonce,
    val receiverId: AccountId,
    val actions: List<Action>,
    val blockHash: CryptoHash,
)
