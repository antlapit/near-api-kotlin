package org.near.api.model.primitives

import org.komputing.kbase58.decodeBase58

data class PublicKey(
    val keyType: KeyType,
    val data: String
) {
    constructor(args: List<String>) : this(
        if (args.size == 1) KeyType.ED25519 else KeyType.valueOf(args[0].uppercase()),
        if (args.size == 1) args[0] else args[1]
    )

    /**
     * @param encodedString - <curve>:<encoded key> or just <encoded key> with default ED25519
     */
    constructor(encodedString: String) : this(encodedString.split(":"))

    fun asString() = "${keyType.name.lowercase()}:$data"

    fun decodeBase58() = data.decodeBase58()
}

enum class KeyType {
    ED25519
}
