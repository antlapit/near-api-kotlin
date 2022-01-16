package org.near.api.providers.model.accesskey

import org.near.api.providers.model.block.BlockReference
import org.near.api.providers.model.primitives.*

data class AccessKey(
    val nonce: Nonce,
    val permission: AccessKeyPermission
) {
    companion object {
        fun functionCallAccessKey(receiverId: String, methodNames: List<String>, allowance: Balance?): AccessKey =
            AccessKey(
                nonce = 0,
                permission = AccessKeyPermission.FunctionCall(
                    receiverId = receiverId,
                    methodNames = methodNames,
                    allowance = allowance
                )
            )

        fun fullAccessKey(): AccessKey = AccessKey(
            nonce = 0,
            permission = AccessKeyPermission.FullAccess
        )
    }
}

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
