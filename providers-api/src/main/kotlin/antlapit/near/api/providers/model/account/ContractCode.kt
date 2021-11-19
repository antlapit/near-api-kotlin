package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash

data class ContractCode(
    val codeBase64: String,
    val hash: CryptoHash,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)
