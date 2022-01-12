package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.model.primitives.Balance
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.StorageUsage

open class Account(
    open val amount: Balance,
    open val locked: Balance,
    open val codeHash: CryptoHash,
    open val storageUsage: StorageUsage,
    open val storagePaidAt: BlockHeight
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (amount != other.amount) return false
        if (locked != other.locked) return false
        if (codeHash != other.codeHash) return false
        if (storageUsage != other.storageUsage) return false
        if (storagePaidAt != other.storagePaidAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = amount.hashCode()
        result = 31 * result + locked.hashCode()
        result = 31 * result + codeHash.hashCode()
        result = 31 * result + storageUsage.hashCode()
        result = 31 * result + storagePaidAt.hashCode()
        return result
    }
}

data class AccountInBlock(
    val amount: Balance,
    val locked: Balance,
    val codeHash: CryptoHash,
    val storageUsage: StorageUsage,
    val storagePaidAt: BlockHeight,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)