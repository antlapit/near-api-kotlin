package antlapit.near.api.providers

import antlapit.near.api.providers.model.accesskey.AccessKeyInBlock
import antlapit.near.api.providers.model.accesskey.AccessKeysContainer
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.PublicKey

interface AccessKeyProvider {

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param finality Finality param for last block
     */
    suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeyInBlock


    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        blockId: BlockHeight,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeyInBlock

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockHash String block hash
     */
    suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        blockHash: CryptoHash,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeyInBlock

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getAccessKeyList(
        accountId: AccountId,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeysContainer

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKeyList(
        accountId: AccountId,
        blockId: BlockHeight,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeysContainer

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getAccessKeyList(
        accountId: AccountId,
        blockHash: CryptoHash,
        timeout: Long = Constants.DEFAULT_TIMEOUT
    ): AccessKeysContainer


    // TODO single_access_key_changes
    // TODO all_access_key_changes

}
