package antlapit.near.api.providers

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

/**
 * Main Client for accessing NEAR RPC API
 * <br>
 * Possible initialization cases:
 * <ul>
 *      <li>full RPC API address</li>
 *      <li>HTTP scheme with Address and Port</li>
 * </ul>
 */
class BaseJsonRpcProvider(val address: String) {

    private val client: HttpClient

    constructor(rpcAddr: String, port: Int) : this("http://$rpcAddr:$port")

    init {
        this.client = HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
            install(JsonFeature)
            install(HttpTimeout)
        }
    }

    suspend fun sendJsonRpc(method: String, params: Any? = null, timeout: Long = 2_000): Any {
        val response = client.post<Map<String, Any>>(address) {
            contentType(ContentType.Application.Json)
            body = GenericRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout
            }
        }
        when {
            response["error"] != null -> {
                throw RPCClientException(response["error"].toString())
            }
            response["result"] == null -> {
                throw RPCClientException("Empty result")
            }
            else -> {
                return response["result"]!!
            }
        }
    }

    suspend fun query(queryObj: Map<String, Any>, blockSearch: BlockSearch): Any {
        val paramsMap = LinkedHashMap<String, Any>(queryObj)
        if (blockSearch.finality == null) {
            if (blockSearch.blockId == null) {
                paramsMap["blockId"] = blockSearch.blockHash!!
            } else {
                paramsMap["blockId"] = blockSearch.blockId!!
            }
        } else {
            paramsMap["finality"] = blockSearch.finality!!.code
        }
        return sendJsonRpc(method = "query", params = paramsMap)
    }

    private data class GenericRequest(val method: String, val params: Any?) {
        val jsonrpc = "2.0"
        val id = "dontcare"
    }

    class RPCClientException(error: String) : RuntimeException(error)
}
