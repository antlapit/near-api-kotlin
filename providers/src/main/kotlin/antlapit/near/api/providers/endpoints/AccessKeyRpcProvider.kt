package antlapit.near.api.providers.endpoints

import antlapit.near.api.providers.AccessKeyProvider
import antlapit.near.api.providers.Finality
import antlapit.near.api.providers.base.BlockSearch
import antlapit.near.api.providers.base.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.base.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.base.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.base.JsonRpcProvider
import antlapit.near.api.providers.model.accesskey.AccessKeyInBlock
import antlapit.near.api.providers.model.accesskey.AccessKeysContainer
import antlapit.near.api.providers.model.account.AccountWithPublicKey
import antlapit.near.api.providers.model.changes.AccessKeysChangesContainer
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.BlockHeight
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.PublicKey

/**
 * RPC endpoint for accessing Access Keys
 * @link https://docs.near.org/docs/api/rpc/access-keys
 */
class AccessKeyRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : AccessKeyProvider {

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
        timeout: Long
    ): AccessKeyInBlock = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_access_key",
            "account_id" to accountId,
            "public_key" to publicKey
        ),
        blockSearch,
        timeout
    )

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, finality: Finality, timeout: Long) =
        getAccessKey(accountId, publicKey, ofFinality(finality), timeout)

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockId: BlockHeight, timeout: Long) =
        getAccessKey(accountId, publicKey, fromBlockId(blockId), timeout)

    override suspend fun getAccessKey(
        accountId: AccountId,
        publicKey: PublicKey,
        blockHash: CryptoHash,
        timeout: Long
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
        timeout: Long
    ): AccessKeysContainer = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_access_key_list",
            "account_id" to accountId
        ),
        blockSearch,
        timeout
    )

    override suspend fun getAccessKeyList(accountId: AccountId, finality: Finality, timeout: Long) =
        getAccessKeyList(accountId, ofFinality(finality), timeout)

    override suspend fun getAccessKeyList(accountId: AccountId, blockId: BlockHeight, timeout: Long) =
        getAccessKeyList(accountId, fromBlockId(blockId), timeout)

    override suspend fun getAccessKeyList(accountId: AccountId, blockHash: CryptoHash, timeout: Long) =
        getAccessKeyList(accountId, fromBlockHash(blockHash), timeout)

    /**
     * @param keys List of accounts with keys
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-changes-single
     */
    private suspend fun getAccessKeyChanges(
        keys: List<AccountWithPublicKey>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long
    ): AccessKeysChangesContainer = jsonRpcProvider.getChanges(
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "single_access_key_changes",
            "keys" to keys
        ),
        timeout = timeout
    )

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, finality: Finality, timeout: Long) =
        getAccessKeyChanges(keys, ofFinality(finality), timeout)

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, blockId: BlockHeight, timeout: Long) =
        getAccessKeyChanges(keys, fromBlockId(blockId), timeout)

    override suspend fun getAccessKeyChanges(keys: List<AccountWithPublicKey>, blockHash: CryptoHash, timeout: Long) =
        getAccessKeyChanges(keys, fromBlockHash(blockHash), timeout)

    /**
     * @param keys List of account ids
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-changes-single
     */
    private suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long
    ): AccessKeysChangesContainer = jsonRpcProvider.getChanges(
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
        timeout: Long
    ) = getAllAccessKeysChanges(accountIds, ofFinality(finality), timeout)

    override suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long
    ) = getAllAccessKeysChanges(accountIds, fromBlockId(blockId), timeout)

    override suspend fun getAllAccessKeysChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long
    ) = getAllAccessKeysChanges(accountIds, fromBlockHash(blockHash), timeout)
}


