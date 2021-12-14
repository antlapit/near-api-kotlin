package antlapit.near.api.providers

import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

class Utils {

    companion object {
        @JvmStatic
        internal fun encodeToBase64(str: String): String {
            return Base64.getEncoder().encode(str.toByteArray()).toString(Charset.forName("UTF-8"))
        }

    }

}

fun String.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
