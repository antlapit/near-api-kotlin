package antlapit.near.api.providers

import antlapit.near.api.providers.model.accesskey.AccessKeyInBlock
import antlapit.near.api.providers.model.accesskey.AccessKeysContainer
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.PublicKey

interface AccessKeyProvider {

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param finality Finality param for last block
     */
    suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, finality: Finality = Finality.OPTIMISTIC): AccessKeyInBlock


    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockId: BlockHeight): AccessKeyInBlock

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockHash String block hash
     */
    suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockHash: CryptoHash): AccessKeyInBlock

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getAccessKeyList(accountId: AccountId, finality: Finality): AccessKeysContainer

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKeyList(accountId: AccountId, blockId: BlockHeight): AccessKeysContainer

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getAccessKeyList(accountId: AccountId, blockHash: CryptoHash): AccessKeysContainer


    // TODO single_access_key_changes
    // TODO all_access_key_changes

}
