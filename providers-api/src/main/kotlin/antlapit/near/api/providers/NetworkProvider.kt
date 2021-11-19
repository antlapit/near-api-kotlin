package antlapit.near.api.providers

import antlapit.near.api.providers.model.networkinfo.NetworkInfo
import antlapit.near.api.providers.model.networkinfo.NodeStatus
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.validators.EpochValidatorInfo

interface NetworkProvider {

    /**
     * Returns general status of a given node (sync status, nearcore node version, protocol version, etc), and the current set of validators.
     */
    suspend fun getNodeStatus(timeout: Long = Constants.DEFAULT_TIMEOUT): NodeStatus

    /**
     * Returns the current state of node network connections (active peers, transmitted data, etc.)
     */
    suspend fun getNetworkInfo(timeout: Long = Constants.DEFAULT_TIMEOUT): NetworkInfo

    /**
     * Returns validation status of latest block.
     */
    suspend fun getValidationStatus(timeout: Long = Constants.DEFAULT_TIMEOUT): EpochValidatorInfo

    /**
     * Returns validation status of numeric block id.
     * @param blockId Numeric block number
     */
    suspend fun getValidationStatus(blockId: BlockHeight, timeout: Long = Constants.DEFAULT_TIMEOUT): EpochValidatorInfo

    /**
     * Returns validation status of block hash.
     * @param blockHash Block hash
     */
    suspend fun getValidationStatus(blockHash: CryptoHash, timeout: Long = Constants.DEFAULT_TIMEOUT): EpochValidatorInfo
}
