package org.near.api.endpoints

import org.near.api.model.networkinfo.NetworkInfo
import org.near.api.model.networkinfo.NodeStatus
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.validators.EpochValidatorInfo

interface NetworkEndpoint {

    /**
     * Returns general status of a given node (sync status, nearcore node version, config version, etc), and the current set of validators.
     */
    suspend fun getNodeStatus(timeout: Long? = null): NodeStatus

    /**
     * Returns the current state of node network connections (active peers, transmitted data, etc.)
     */
    suspend fun getNetworkInfo(timeout: Long? = null): NetworkInfo

    /**
     * Returns validation status of latest block.
     */
    suspend fun getValidationStatus(timeout: Long? = null): EpochValidatorInfo

    /**
     * Returns validation status of numeric block id.
     * @param blockId Numeric block number
     */
    suspend fun getValidationStatus(blockId: BlockHeight, timeout: Long? = null): EpochValidatorInfo

    /**
     * Returns validation status of block hash.
     * @param blockHash Block hash
     */
    suspend fun getValidationStatus(blockHash: CryptoHash, timeout: Long? = null): EpochValidatorInfo
}
