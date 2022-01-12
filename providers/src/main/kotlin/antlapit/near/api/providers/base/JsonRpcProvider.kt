package antlapit.near.api.providers.base

import antlapit.near.api.json.ObjectMapperFactory
import antlapit.near.api.providers.Constants
import antlapit.near.api.providers.base.config.JsonRpcConfig
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.io.Closeable
import java.util.*

/**
 * Main Client for accessing NEAR RPC API
 * <br>
 * Should be initialized from config class
 */
class JsonRpcProvider(
    val config: JsonRpcConfig = JsonRpcConfig(),
    private val objectMapper: ObjectMapper = ObjectMapperFactory.newInstance()
) : Closeable {

    val exceptionFactory: ExceptionFactory = ExceptionFactory(objectMapper)

    val client: HttpClient = HttpClient(CIO) {
        install(Logging) {
            level = config.logging.level
            logger = config.logging.logger
        }
        install(JsonFeature) {
            serializer = JacksonSerializer(
                jackson = objectMapper
            )
        }
        install(HttpTimeout)
    }

    /**
     * Note that this is experimental feature
     */
    suspend inline fun <reified T> getChanges(
        blockSearch: BlockSearch,
        params: Map<String, Any?>,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): T {
        return sendRpc(method = "EXPERIMENTAL_changes", params = mergeParams(params, blockSearch), timeout)
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
            response.result != null -> return response.result
            response.error != null && !response.error.isNull -> throw exceptionFactory.fromJsonNode(response.error)
            else -> throw exceptionFactory.emptyResult()
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

    data class GenericRpcRequest(val method: String, val params: Any?) {
        @Suppress("unused")
        val jsonrpc = "2.0"

        @Suppress("unused")
        val id = UUID.randomUUID().toString()
    }

    data class GenericRpcResponse<T>(val id: String, val jsonrpc: String, val error: JsonNode?, val result: T?)

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
    }

    override fun close() = client.close()
}
