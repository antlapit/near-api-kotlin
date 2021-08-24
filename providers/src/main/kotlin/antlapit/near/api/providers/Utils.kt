package antlapit.near.api.providers

import java.nio.charset.Charset
import java.util.*

class Utils {

    companion object {
        @JvmStatic
        internal fun encodeToBase64(str: String): String {
            return Base64.getEncoder().encode(str.toByteArray()).toString(Charset.forName("UTF-8"))
        }
    }
}
