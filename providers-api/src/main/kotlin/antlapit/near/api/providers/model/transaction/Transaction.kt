package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.Nonce
import antlapit.near.api.providers.model.primitives.PublicKey

open class Transaction(
    open val signerId: AccountId,
    open val publicKey: PublicKey,
    open val nonce: Nonce,
    open val receiverId: AccountId,
    open val hash: CryptoHash,
    open val actions: List<Action>,
) {

    override fun toString(): String {
        return "Transaction(signerId='$signerId', publicKey=$publicKey, nonce=$nonce, receiverId='$receiverId', hash='$hash', actions=$actions)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transaction

        if (signerId != other.signerId) return false
        if (publicKey != other.publicKey) return false
        if (nonce != other.nonce) return false
        if (receiverId != other.receiverId) return false
        if (hash != other.hash) return false
        if (actions != other.actions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = signerId.hashCode()
        result = 31 * result + publicKey.hashCode()
        result = 31 * result + nonce.hashCode()
        result = 31 * result + receiverId.hashCode()
        result = 31 * result + hash.hashCode()
        result = 31 * result + actions.hashCode()
        return result
    }
}
