package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.TrieProofPath

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
