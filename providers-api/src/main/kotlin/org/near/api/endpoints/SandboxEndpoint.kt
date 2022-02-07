package org.near.api.endpoints

import org.near.api.model.changes.StateRecord

interface SandboxEndpoint {

    /**
     * Patch account, access keys, contract code, or contract state.
     * <ul>
     *  <li>Only additions and mutations are supported.</li>
     *  <li>No deletions.</li>
     *  <li>Account, access keys, contract code, and contract states have different formats.</li>
     * </ul>
     */
    suspend fun patchState(records: List<StateRecord>, timeout: Long? = null)
}
