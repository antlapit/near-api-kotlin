package org.near.api.provider

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.near.api.json.ObjectMapperFactory
import org.near.api.provider.config.JsonRpcConfig
import java.io.Closeable
import java.util.*

/**
 * Main Client for accessing NEAR RPC API
 * <br>
 * Should be initialized from config class
 */
class JsonRpcProvider(
    val config: JsonRpcConfig = JsonRpcConfig(),
) : Closeable {

    private val objectMapper: ObjectMapper = ObjectMapperFactory.newInstance()

    val exceptionFactory: ApiExceptionFactory = ApiExceptionFactory(objectMapper)

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
     * Method for sending RPC request with block reference
     *
     * @param method Method code
     * @param blockSearch block reference
     * @param params Method params as Map
     * @param timeout Request timeout in ms (default from JsonRpcConfig)
     */
    suspend inline fun <reified T> sendRpc(
        method: String,
        blockSearch: BlockSearch,
        params: Map<String, Any?> = emptyMap(),
        timeout: Long? = null
    ): T {
        return sendRpc(
            method = method,
            params = mergeParams(params, blockSearch),
            timeout
        )
    }

    /**
     * Base method for sending RPC request
     *
     * @param method Method code
     * @param params Method params (array, object, ...)
     * @param timeout Request timeout in ms (default from JsonRpcConfig)
     */
    suspend inline fun <reified T> sendRpc(
        method: String,
        params: Any? = null,
        timeout: Long? = null
    ): T {
        val response = client.post<GenericRpcResponse<T>>(config.getAddress()) {
            contentType(ContentType.Application.Json)
            body = GenericRpcRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout ?: config.defaultTimeout
            }
        }
        when {
            response.result != null -> return response.result
            response.error != null && !response.error.isNull -> throw exceptionFactory.fromJsonNode(response.error)
            else -> throw exceptionFactory.emptyResult()
        }
    }

    override fun close() = client.close()

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
}
