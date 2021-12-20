package antlapit.near.api.providers

import java.nio.charset.Charset
import java.util.*

fun ByteArray.base64(): String {
    return Base64.getEncoder().encode(this)
        .toString(Charset.forName("UTF-8"))
}

fun String.decodeBase64(): ByteArray {
    return Base64.getDecoder().decode(this)
}
