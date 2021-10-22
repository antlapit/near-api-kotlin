package antlapit.near.api.providers

import antlapit.near.api.providers.exception.ProviderException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.util.*

/**
 * Main Client for accessing NEAR RPC API
 * <br>
 * Possible initialization cases:
 * <ul>
 *      <li>full RPC API address</li>
 *      <li>HTTP scheme with Address and Port</li>
 * </ul>
 */
class BaseJsonRpcProvider(
    val address: String
) {

    val objectMapper = jacksonMapperBuilder()
        .addModule(JavaTimeModule())
        .addModule(Jdk8Module())
        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()

    // TODO close client after execution
    val client: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(JsonFeature) {
            serializer = JacksonSerializer(
                jackson = objectMapper
            )
        }
        install(HttpTimeout)
    }

    constructor(
        rpcAddr: String,
        port: Int,
    ) : this(address = "http://$rpcAddr:$port")

    suspend fun sendJsonRpcDefault(method: String, params: Any?, timeout: Long = 10_000) =
        sendJsonRpc<Map<String, Any>>(method, params, timeout)

    /**
     * Base method for sending RPC request
     *
     * @param method Method code
     * @param params Method params (often - array)
     * @param timeout Request timeout in ms (default - 10_000)
     */
    suspend inline fun <reified T> sendJsonRpc(method: String, params: Any? = null, timeout: Long = 10_000): T {
        val response = client.post<GenericRpcResponse<T>>(address) {
            contentType(ContentType.Application.Json)
            body = GenericRpcRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout
            }
        }
        //val response = objectMapper.readValue(responseStr, object : TypeReference<GenericRpcResponse<T>>() {})
        when {
            response.error != null -> {
                throw Utils.constructException(response.error)
            }
            response.result == null -> {
                throw ProviderException("Empty result in response without specifying error")
            }
            else -> {
                return response.result as T
            }
        }
    }

    suspend inline fun <reified T> query(queryObj: Map<String, Any>, blockSearch: BlockSearch): T {
        val paramsMap = LinkedHashMap(queryObj)
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

    data class RpcError(val name: String, val cause: RpcErrorCause)

    data class RpcErrorCause(val name: String, val info: Map<String, Any>)

    data class GenericRpcRequest(val method: String, val params: Any?) {
        val jsonrpc = "2.0"
        val id = UUID.randomUUID().toString()
    }

    data class GenericRpcResponse<T>(val id: String, val jsonrpc: String, val error: RpcError?, val result: T?)
}
