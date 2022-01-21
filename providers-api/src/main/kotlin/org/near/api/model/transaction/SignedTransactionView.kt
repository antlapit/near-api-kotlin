package org.near.api.model.transaction

import org.near.api.model.block.Action
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.Nonce
import org.near.api.model.primitives.PublicKey

data class SignedTransactionView(
    val signerId: AccountId,
    val publicKey: PublicKey,
    val nonce: Nonce,
    val receiverId: AccountId,
    val actions: List<Action>,
    val signature: CryptoHash,
    val hash: CryptoHash,
)
