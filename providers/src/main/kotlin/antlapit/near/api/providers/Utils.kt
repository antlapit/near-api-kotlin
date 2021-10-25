package antlapit.near.api.providers

import antlapit.near.api.providers.exception.ErrorCause
import antlapit.near.api.providers.exception.ProviderException
import java.nio.charset.Charset
import java.util.*

class Utils {

    companion object {
        @JvmStatic
        internal fun encodeToBase64(str: String): String {
            return Base64.getEncoder().encode(str.toByteArray()).toString(Charset.forName("UTF-8"))
        }

        @JvmStatic
        fun constructException(error: JsonRpcProvider.RpcError) : ProviderException {
            val errorCause = ErrorCause.findByCode(error.cause.name)
            val info = error.cause.info
            return if (errorCause == null) {
                ProviderException(error.name, error.cause.name, info)
            } else {
                ProviderException.byCause(errorCause, info)
            }
        }
    }
}
