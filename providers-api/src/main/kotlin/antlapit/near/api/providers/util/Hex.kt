package antlapit.near.api.providers.util

fun ByteArray.toHexString(): String = joinToString("", transform = { "%02x".format(it) })

fun String.fromHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}
