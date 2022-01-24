package org.near.api.endpoints

import org.near.api.model.accesskey.AccessKeyInBlock
import org.near.api.model.accesskey.AccessKeysContainer
import org.near.api.model.account.AccountWithPublicKey
import org.near.api.model.changes.StateChanges
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.PublicKey

interface AccessKeysEndpoint {

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
        timeout: Long? = null
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
        timeout: Long? = null
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
        timeout: Long? = null
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
        timeout: Long? = null
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
        timeout: Long? = null
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
        timeout: Long? = null
    ): AccessKeysContainer


    /**
     * Returns access keys changes by account and public key.
     *
     * @param keys List of accounts with public key
     * @param finality Finality param for last block
     */
    suspend fun getAccessKeyChanges(
        keys: List<AccountWithPublicKey>,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns access keys changes by account and public key.
     *
     * @param keys List of accounts with public key
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKeyChanges(
        keys: List<AccountWithPublicKey>,
        blockId: BlockHeight,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns access keys changes by account and public key.
     *
     * @param keys List of accounts with public key
     * @param blockHash String block hash
     */
    suspend fun getAccessKeyChanges(
        keys: List<AccountWithPublicKey>,
        blockHash: CryptoHash,
        timeout: Long? = null
    ): StateChanges


    /**
     * Returns changes to <b>all</b> access keys of a specific block.
     * Multiple accounts can be quereied by passing an array of <b>accountIds</b>
     *
     * @param accountIds List of account ids
     * @param finality Finality param for last block
     */
    suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns changes to <b>all</b> access keys of a specific block.
     * Multiple accounts can be quereied by passing an array of <b>accountIds</b>
     *
     * @param accountIds List of account ids
     * @param blockId Numeric block identifier
     */
    suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns changes to <b>all</b> access keys of a specific block.
     * Multiple accounts can be quereied by passing an array of <b>accountIds</b>
     *
     * @param accountIds List of account ids
     * @param blockHash String block hash
     */
    suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long? = null
    ): StateChanges
}
