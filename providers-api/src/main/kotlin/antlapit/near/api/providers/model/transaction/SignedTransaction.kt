package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.*

data class SignedTransaction(
    override val signerId: AccountId,
    override val publicKey: PublicKey,
    override val nonce: Nonce,
    override val receiverId: AccountId,
    override val hash: CryptoHash,
    override val actions: List<Action> = emptyList(),
    val signature: Signature,
) : Transaction(
    signerId, publicKey, nonce, receiverId, hash, actions
) {
    constructor(transaction: Transaction, signature: Signature) : this(
        signerId = transaction.signerId,
        publicKey = transaction.publicKey,
        nonce = transaction.nonce,
        receiverId = transaction.receiverId,
        hash = transaction.hash,
        actions = transaction.actions,
        signature = signature
    )
}
