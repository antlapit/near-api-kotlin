package antlapit.near.api.providers

import antlapit.near.api.providers.model.NodeStatus

interface NetworkProvider {

    /**
     * Returns general status of a given node (sync status, nearcore node version, protocol version, etc), and the current set of validators.
     */
    suspend fun getNodeStatus(): NodeStatus

    /**
     * Returns the current state of node network connections (active peers, transmitted data, etc.)
     */
    suspend fun getNetworkInfo(): Any

    /**
     * Returns validation status of latest block.
     */
    suspend fun getValidationStatus(): Any

    /**
     * Returns validation status of numeric block id.
     * @param blockId Numeric block number
     */
    suspend fun getValidationStatus(blockId: Long): Any

    /**
     * Returns validation status of block hash.
     * @param blockHash Block hash
     */
    suspend fun getValidationStatus(blockHash: String): Any
}
