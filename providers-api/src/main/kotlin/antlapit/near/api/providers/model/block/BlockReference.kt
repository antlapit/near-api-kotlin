package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash

open class BlockReference(
    open val blockHeight: BlockHeight,
    open val blockHash: CryptoHash
)
