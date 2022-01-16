package org.near.api.providers.model.account

import org.near.api.providers.model.block.BlockReference
import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash

data class CallResult(
    val result: List<Byte> = emptyList(),
    val logs: List<String> = emptyList(),
    val error: String? = null,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash) {
    @Suppress("unused")
    fun failed() = error != null
}
