package antlapit.near.api.providers.base

import antlapit.near.api.json.ObjectMapperFactory
import antlapit.near.api.providers.Constants
import antlapit.near.api.providers.base.config.JsonRpcConfig
import antlapit.near.api.providers.exception.ErrorCause
import antlapit.near.api.providers.exception.ProviderException
import com.fasterxml.jackson.databind.ObjectMapper
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
 * Should be initialized from config class
 */
class JsonRpcProvider(
    val config: JsonRpcConfig,
    val objectMapper: ObjectMapper = ObjectMapperFactory.newInstance()
) {

    // TODO close client after execution
    val client: HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(JsonFeature) {
            serializer = JacksonSerializer(
                jackson = objectMapper
            )
        }
        install(HttpTimeout)
    }

    suspend inline fun <reified T> sendRpc(
        method: String,
        blockSearch: BlockSearch,
        params: Map<String, Any?>,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): T {
        return sendRpc(method = method, params = mergeParams(params, blockSearch), timeout)
    }

    suspend inline fun <reified T> sendRpc(
        method: String,
        blockSearch: BlockSearch,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): T {
        return sendRpc(method = method, params = mergeParams(emptyMap(), blockSearch), timeout)
    }

    /**
     * Base method for sending RPC request
     *
     * @param method Method code
     * @param params Method params (array, object, ...)
     * @param timeout Request timeout in ms (default - Constants.DEFAULT_TIMEOUT)
     *
     * @see Constants
     */
    suspend inline fun <reified T> sendRpc(
        method: String,
        params: Any? = null,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): T {
        val response = client.post<GenericRpcResponse<T>>(config.getAddress()) {
            contentType(ContentType.Application.Json)
            body = GenericRpcRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout
            }
        }
        when {
            response.error != null -> {
                when (response.error) {
                    is String -> {
                        throw ProviderException(response.error)
                    }
                    is Map<*, *> -> {
                        val causeMap = response.error["cause"] as Map<String, Any?>
                        val rpcError = RpcError(
                            response.error["name"] as String, RpcErrorCause(
                                name = causeMap["name"] as String,
                                info = causeMap["info"] as Map<String, Any?>?
                            )
                        )
                        throw constructException(rpcError)
                    }
                    else -> {
                        throw ProviderException("Undefined response error")
                    }
                }
            }
            response.result == null -> {
                throw ProviderException("Empty result in response without specifying error")
            }
            else -> {
                return response.result
            }
        }
    }

    /**
     * @param queryObj Generic map with query params
     * @param blockSearch Block searching params (id, hash, finality)
     * @param timeout Request timeout in ms (default - Constants.DEFAULT_TIMEOUT)
     *
     * @see Constants
     */
    suspend inline fun <reified T> query(
        queryObj: Map<String, Any>,
        blockSearch: BlockSearch,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): T =
        sendRpc(method = "query", params = mergeParams(queryObj, blockSearch), timeout = timeout)

    /**
     * Some undocumented feature of RPC API - querying contract by path and data
     *
     * @param path Smart-contract path
     * @param data Some data to call with
     * @param timeout Request timeout in ms (default - Constants.DEFAULT_TIMEOUT)
     *
     * @see Constants
     */
    suspend inline fun <reified T> query(path: String, data: String, timeout: Long = Constants.DEFAULT_TIMEOUT) =
        sendRpc<T>(method = "query", params = arrayListOf(path, data), timeout = timeout)

    data class RpcError(val name: String, val cause: RpcErrorCause)

    data class RpcErrorCause(val name: String, val info: Map<String, Any?>?)

    data class GenericRpcRequest(val method: String, val params: Any?) {
        @Suppress("unused")
        val jsonrpc = "2.0"
        @Suppress("unused")
        val id = UUID.randomUUID().toString()
    }

    data class GenericRpcResponse<T>(val id: String, val jsonrpc: String, val error: Any?, val result: T?)

    companion object {
        fun mergeParams(params: Map<String, Any?>, blockSearch: BlockSearch): Map<String, Any?> {
            val paramsMap = LinkedHashMap(params)
            if (blockSearch.finality == null) {
                if (blockSearch.blockId == null) {
                    paramsMap["block_id"] = blockSearch.blockHash!!
                } else {
                    paramsMap["block_id"] = blockSearch.blockId!!
                }
            } else {
                paramsMap["finality"] = blockSearch.finality!!.code
            }
            return paramsMap
        }

        @JvmStatic
        fun constructException(error: RpcError) : ProviderException {
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
