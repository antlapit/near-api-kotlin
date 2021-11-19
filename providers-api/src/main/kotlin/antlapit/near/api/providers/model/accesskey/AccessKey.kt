package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.model.block.BlockReference
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.Nonce
import antlapit.near.api.providers.model.primitives.PublicKey

data class AccessKey(
    val nonce: Nonce,
    val permission: AccessKeyPermission
)

data class AccessKeyInfo(
    val publicKey: PublicKey,
    val accessKey: AccessKey
)

data class AccessKeyInBlock(
    val nonce: Nonce,
    val permission: AccessKeyPermission,
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash
) : BlockReference(blockHeight, blockHash)

data class AccessKeysContainer(
    override val blockHeight: BlockHeight,
    override val blockHash: CryptoHash,
    val keys: List<AccessKeyInfo> = emptyList()
) : BlockReference(blockHeight, blockHash)
