package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.primitives.*

data class SignedTransactionView(
    val signerId: AccountId,
    val publicKey: PublicKey,
    val nonce: Nonce,
    val receiverId: AccountId,
    val actions: List<Action> = emptyList(),
    val signature: Signature,
    val hash: CryptoHash,
)
