package org.near.api.providers.model.account

import org.near.api.providers.model.block.BlockReference
import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash

data class ContractCode(
    val codeBase64: String,
    val hash: CryptoHash,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)
