package org.near.api.providers.model.account

import org.near.api.providers.model.block.BlockReference
import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash
import org.near.api.providers.model.primitives.TrieProofPath

data class ContractState(
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash,
    val values: List<ContractStateItem> = emptyList(),
    val proof: TrieProofPath
) : BlockReference(blockHeight = blockHeight, blockHash = blockHash)

data class ContractStateItem(
    val key: String,
    val value: String,
    val proof: TrieProofPath
)
