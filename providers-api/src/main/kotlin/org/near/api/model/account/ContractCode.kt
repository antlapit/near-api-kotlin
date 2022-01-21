package org.near.api.model.account

import org.near.api.model.block.BlockReference
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash

data class ContractCode(
    val codeBase64: String,
    val hash: CryptoHash,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)
