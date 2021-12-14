package antlapit.near.api.providers.model.primitives

import org.komputing.kbase58.decodeBase58

data class PublicKey(
    val keyType: KeyType,
    val data: String
) {
    constructor(args: List<String>) : this(
        if (args.size == 1) KeyType.ed25519 else KeyType.valueOf(args[0]),
        if (args.size == 1) args[0] else args[1]
    )

    /**
     * @param encodedString - <curve>:<encoded key>
     */
    constructor(encodedString: String) : this(encodedString.split(":"))

    fun asString() = "$keyType:$data"

    fun decodeBase58() = data.decodeBase58()
}

enum class KeyType {
    ed25519
}
// TODO signature
typealias Signature = String
