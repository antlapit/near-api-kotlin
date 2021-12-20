package antlapit.near.api.providers.model.transaction

import antlapit.near.api.providers.model.primitives.KeyType
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String

data class SignedTransaction(
    val transaction: Transaction,
    val signature: TransactionSignature
)

data class TransactionSignature(
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

    constructor(signature: ByteArray) : this(signature.encodeToBase58String())

    fun asString() = "${keyType.name.lowercase()}:$data"

    fun decodeBase58() = data.decodeBase58()
}

