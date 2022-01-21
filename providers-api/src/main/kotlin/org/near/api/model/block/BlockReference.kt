package org.near.api.model.block

import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash

open class BlockReference(
    open val blockHeight: BlockHeight,
    open val blockHash: CryptoHash
)
