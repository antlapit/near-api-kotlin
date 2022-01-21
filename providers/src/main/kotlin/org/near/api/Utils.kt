package org.near.api

import java.nio.charset.Charset
import java.util.*

fun ByteArray.base64(): String {
    return Base64.getEncoder().encode(this)
        .toString(Charset.forName("UTF-8"))
}

fun String.decodeBase64(): ByteArray {
    return Base64.getDecoder().decode(this)
}

val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

fun String.camelCaseToSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.lowercase(Locale.getDefault())
}
