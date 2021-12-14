package antlapit.near.api.providers.util

fun ByteArray.toHexString(): String = joinToString("", transform = { "%02x".format(it) })
