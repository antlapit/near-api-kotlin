package org.near.api.endpoints

import org.near.api.model.changes.StateRecord
import org.near.api.provider.JsonRpcProvider

/**
 * RPC endpoint for sandbox requests
 * @link https://docs.near.org/docs/api/rpc/sandbox
 */
class SandboxRpcEndpoint(private val jsonRpcProvider: JsonRpcProvider) : SandboxEndpoint {
    override suspend fun patchState(records: List<StateRecord>, timeout: Long?) {
        return jsonRpcProvider.sendRpc(
            method = "sandbox_patch_state",
            params = StateRecordsWrapper(records),
            timeout = timeout
        )
    }

    data class StateRecordsWrapper(
        val records: List<StateRecord>
    )
}
