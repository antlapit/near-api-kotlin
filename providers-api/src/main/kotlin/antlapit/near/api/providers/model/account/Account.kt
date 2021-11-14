package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.StorageUsage

data class Account(
    val amount: Balance,
    val locked: Balance,
    val codeHash: CryptoHash,
    val storageUsage: StorageUsage,
    val storagePaidAt: BlockHeight,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)
