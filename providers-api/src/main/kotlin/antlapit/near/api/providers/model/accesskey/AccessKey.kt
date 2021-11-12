package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.Nonce
import antlapit.near.api.providers.primitives.PublicKey

data class AccessKey(
    val nonce: Nonce,
    val permission: AccessKeyPermission
)

data class AccessKeyInfo(
    val publicKey: PublicKey,
    val accessKey: AccessKey
)

data class AccessKeyInBlock(
    val blockHeight: BlockHeight,
    val blockHash: CryptoHash,
    val nonce: Nonce,
    val permission: AccessKeyPermission
)

data class AccessKeysContainer(
    val blockHeight: BlockHeight,
    val blockHash: CryptoHash,
    val keys: List<AccessKeyInfo> = emptyList()
)
