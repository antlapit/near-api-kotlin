package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality

/**
 * RPC endpoint for accessing Access Keys
 * @link https://docs.near.org/docs/api/rpc/access-keys
 */
class AccessKeyRpcProvider(private val client: BaseJsonRpcProvider) : AccessKeyProvider {

    /**
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key
     */
    private suspend fun getAccessKey(accountId: String, publicKey: String, blockSearch: BlockSearch) = client.query(
        mapOf(
            "request_type" to "view_access_key",
            "account_id" to accountId,
            "public_key" to publicKey
        ),
        blockSearch
    )

    override suspend fun getAccessKey(accountId: String, publicKey: String, finality: Finality) = getAccessKey(accountId, publicKey, ofFinality(finality))

    override suspend fun getAccessKey(accountId: String, publicKey: String, blockId: Long) = getAccessKey(accountId, publicKey, fromBlockId(blockId))

    override suspend fun getAccessKey(accountId: String, publicKey: String, blockHash: String) = getAccessKey(accountId, publicKey, fromBlockHash(blockHash))

    /**
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-list
     */
    private suspend fun getAccessKeyList(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = client.query(
        mapOf(
            "request_type" to "view_access_key_list",
            "account_id" to accountId
        ),
        blockSearch
    )

    override suspend fun getAccessKeyList(accountId: String, finality: Finality) = getAccessKeyList(accountId, ofFinality(finality))

    override suspend fun getAccessKeyList(accountId: String, blockId: Long) = getAccessKeyList(accountId, fromBlockId(blockId))

    override suspend fun getAccessKeyList(accountId: String, blockHash: String) = getAccessKeyList(accountId, fromBlockHash(blockHash))
}


