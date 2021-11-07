package antlapit.near.api.providers

import antlapit.near.api.providers.exception.ProviderException
import antlapit.near.api.providers.mixins.AccessKeyPermissionMixin
import antlapit.near.api.providers.mixins.ActionMixin
import antlapit.near.api.providers.mixins.ReceiptEnumMixin
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.ReceiptEnum
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
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
class JsonRpcProvider(
    val address: String
) {

    private val objectMapper: ObjectMapper = jacksonMapperBuilder()
        .addModule(JavaTimeModule())
        .addModule(Jdk8Module())
        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .addMixIn(ReceiptEnum::class.java, ReceiptEnumMixin::class.java)
        .addMixIn(Action::class.java, ActionMixin::class.java)
        .addMixIn(AccessKeyPermission::class.java, AccessKeyPermissionMixin::class.java)
        .build()

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

    constructor(
        rpcAddr: String,
        port: Int,
    ) : this(address = "http://$rpcAddr:$port")

    suspend fun sendRpcDefault(method: String, params: Any?, timeout: Long = Constants.DEFAULT_TIMEOUT) =
        sendRpc<Map<String, Any>>(method, params, timeout)

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
        val response = client.post<GenericRpcResponse<T>>(address) {
            contentType(ContentType.Application.Json)
            body = GenericRpcRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout
            }
        }
        when {
            response.error != null -> {
                throw Utils.constructException(response.error)
            }
            response.result == null -> {
                throw ProviderException("Empty result in response without specifying error")
            }
            else -> {
                return response.result
            }
        }
    }

    suspend inline fun <reified T> query(queryObj: Map<String, Any>, blockSearch: BlockSearch): T =
        sendRpc(method = "query", params = mergeParams(queryObj, blockSearch))

    /**
     * Some undocumented feature of RPC API - querying contract by path and data
     *
     * @param path Smart-contract path
     * @param data Some data to call with
     */
    suspend inline fun <reified T> query(path: String, data: String) =
        sendRpc<T>(method = "query", params = arrayListOf(path, data))

    data class RpcError(val name: String, val cause: RpcErrorCause)

    data class RpcErrorCause(val name: String, val info: Map<String, Any?>?)

    data class GenericRpcRequest(val method: String, val params: Any?) {
        val jsonrpc = "2.0"
        val id = UUID.randomUUID().toString()
    }

    data class GenericRpcResponse<T>(val id: String, val jsonrpc: String, val error: RpcError?, val result: T?)

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
