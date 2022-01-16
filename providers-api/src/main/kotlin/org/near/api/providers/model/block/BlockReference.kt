package org.near.api.providers.model.block

import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash

open class BlockReference(
    open val blockHeight: BlockHeight,
    open val blockHash: CryptoHash
)
