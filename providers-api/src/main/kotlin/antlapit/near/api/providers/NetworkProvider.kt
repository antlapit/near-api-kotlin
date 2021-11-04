package antlapit.near.api.providers

import antlapit.near.api.providers.model.networkinfo.NetworkInfo
import antlapit.near.api.providers.model.networkinfo.NodeStatus
import antlapit.near.api.providers.model.validators.EpochValidatorInfo
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

interface NetworkProvider {

    /**
     * Returns general status of a given node (sync status, nearcore node version, protocol version, etc), and the current set of validators.
     */
    suspend fun getNodeStatus(): NodeStatus

    /**
     * Returns the current state of node network connections (active peers, transmitted data, etc.)
     */
    suspend fun getNetworkInfo(): NetworkInfo

    /**
     * Returns validation status of latest block.
     */
    suspend fun getValidationStatus(): EpochValidatorInfo

    /**
     * Returns validation status of numeric block id.
     * @param blockId Numeric block number
     */
    suspend fun getValidationStatus(blockId: BlockHeight): EpochValidatorInfo

    /**
     * Returns validation status of block hash.
     * @param blockHash Block hash
     */
    suspend fun getValidationStatus(blockHash: CryptoHash): EpochValidatorInfo
}
