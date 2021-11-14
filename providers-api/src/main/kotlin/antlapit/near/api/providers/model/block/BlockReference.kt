package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

open class BlockReference(
    open val blockHeight: BlockHeight,
    open val blockHash: CryptoHash
)
