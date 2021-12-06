package antlapit.near.api.providers.model.account

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash

data class CallResult(
    val result: List<Byte> = emptyList(),
    val logs: List<String> = emptyList(),
    val error: String? = null,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash) {
    fun failed() = error != null
}
