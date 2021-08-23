package antlapit.near.api.kotlin.rpc

import antlapit.near.api.kotlin.rpc.Utils.Companion.encodeToBase64
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
class RPCClient(val address: String) {

    private val client: HttpClient

    constructor(rpcAddr: String, port: Int) : this("http://$rpcAddr:$port")

    init {
        this.client = HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
            install(JsonFeature)
        }
    }

    suspend fun sendRequest(method: String, params: Any? = null, timeout: Long = 2) {
        client.post<Any>(address) {
            contentType(ContentType.Application.Json)
            body = GenericRequest(method, params)

            timeout {
                requestTimeoutMillis = timeout
            }
        }
    }

    suspend fun sendTx(signedTx: String) = sendRequest(
        method = "broadcast_tx_async",
        params = listOf(encodeToBase64(signedTx))
    )

    suspend fun sendTxAndWait(signedTx: String, timeout: Long) = sendRequest(
        method = "broadcast_tx_commit",
        params = listOf(encodeToBase64(signedTx)),
        timeout = timeout
    )

 /*   suspend fun getStatus() {
        client.get("/status") {
            timeout {
                requestTimeoutMillis = 2
            }
        }
    }

    def (self):
    r = requests.get("%s/status" % self.rpc_addr(), timeout=2)
    r.raise_for_status()
    return json.loads(r.content)*/

    suspend fun getValidators() = sendRequest(method = "validators")

    suspend fun query(queryObj: Map<String, Any>, blockSearch: BlockSearch) {
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
        sendRequest(method = "query", params = paramsMap)
    }

    suspend fun getTx(txHash: String, txRecipientId: String) = sendRequest(
        method = "tx",
        params = listOf(txHash, txRecipientId)
    )

    private data class GenericRequest(val method: String, val params: Any?) {
        val jsonrpc = "2.0"
        val id = "dontcare"
    }
}
