package org.near.api.endpoints

import org.near.api.model.accesskey.AccessKeyInBlock
import org.near.api.model.accesskey.AccessKeysContainer
import org.near.api.model.account.AccountWithPublicKey
import org.near.api.model.changes.StateChanges
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.model.primitives.PublicKey
import org.near.api.provider.BlockSearch
import org.near.api.provider.BlockSearch.Companion.fromBlockHash
import org.near.api.provider.BlockSearch.Companion.fromBlockId
import org.near.api.provider.BlockSearch.Companion.ofFinality
import org.near.api.provider.JsonRpcProvider

/**
 * RPC endpoint for accessing Access Keys
 * @link https://docs.near.org/docs/api/rpc/access-keys
 */
class AccessKeysRpcEndpoint(private val jsonRpcProvider: JsonRpcProvider) : AccessKeysEndpoint {

    /**
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key
     */
    private suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        blockSearch: BlockSearch,
        timeout: Long?
    ): AccessKeyInBlock = jsonRpcProvider.sendRpc(
        method = "query",
        blockSearch = blockSearch,
        params = mapOf(
            "request_type" to "view_access_key",
            "account_id" to accountId,
            "public_key" to publicKey
        ),
        timeout = timeout
    )

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, finality: Finality, timeout: Long?) =
        getAccessKey(accountId, publicKey, ofFinality(finality), timeout)

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockId: BlockHeight, timeout: Long?) =
        getAccessKey(accountId, publicKey, fromBlockId(blockId), timeout)

    override suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        blockHash: CryptoHash,
        timeout: Long?
    ) =
        getAccessKey(accountId, publicKey, fromBlockHash(blockHash), timeout)

    /**
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-list
     */
    private suspend fun getAccessKeyList(
        accountId: AccountId,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): AccessKeysContainer = jsonRpcProvider.sendRpc(
        method = "query",
        blockSearch = blockSearch,
        params = mapOf(
            "request_type" to "view_access_key_list",
            "account_id" to accountId
        ),
        timeout
    )

    override suspend fun getAccessKeyList(accountId: AccountId, finality: Finality, timeout: Long?) =
        getAccessKeyList(accountId, ofFinality(finality), timeout)

    override suspend fun getAccessKeyList(accountId: AccountId, blockId: BlockHeight, timeout: Long?) =
        getAccessKeyList(accountId, fromBlockId(blockId), timeout)

    override suspend fun getAccessKeyList(accountId: AccountId, blockHash: CryptoHash, timeout: Long?) =
        getAccessKeyList(accountId, fromBlockHash(blockHash), timeout)

    /**
     * @param keys List of accounts with keys
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-changes-single
     */
    private suspend fun getAccessKeyChanges(
        keys: List<AccountWithPublicKey>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): StateChanges = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes",
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "single_access_key_changes",
            "keys" to keys
        ),
        timeout = timeout
    )

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, finality: Finality, timeout: Long?) =
        getAccessKeyChanges(keys, ofFinality(finality), timeout)

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, blockId: BlockHeight, timeout: Long?) =
        getAccessKeyChanges(keys, fromBlockId(blockId), timeout)

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, blockHash: CryptoHash, timeout: Long?) =
        getAccessKeyChanges(keys, fromBlockHash(blockHash), timeout)

    /**
     * @param accountIds List of account ids
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-changes-single
     */
    private suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): StateChanges = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes",
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "all_access_key_changes",
            "account_ids" to accountIds
        ),
        timeout = timeout
    )

    override suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        finality: Finality,
        timeout: Long?
    ) = getAllAccessKeysChanges(accountIds, ofFinality(finality), timeout)

    override suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long?
    ) = getAllAccessKeysChanges(accountIds, fromBlockId(blockId), timeout)

    override suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long?
    ) = getAllAccessKeysChanges(accountIds, fromBlockHash(blockHash), timeout)

}


