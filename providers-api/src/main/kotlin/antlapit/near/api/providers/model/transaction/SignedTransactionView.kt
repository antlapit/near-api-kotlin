package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.Nonce
import antlapit.near.api.providers.model.primitives.PublicKey

data class SignedTransactionView(
    val signerId: AccountId,
    val publicKey: PublicKey,
    val nonce: Nonce,
    val receiverId: AccountId,
    val actions: List<Action>,
    val signature: CryptoHash,
    val hash: CryptoHash,
)
