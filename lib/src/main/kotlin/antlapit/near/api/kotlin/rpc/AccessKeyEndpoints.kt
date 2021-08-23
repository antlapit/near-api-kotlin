package antlapit.near.api.kotlin.rpc

import antlapit.near.api.kotlin.rpc.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.kotlin.rpc.BlockSearch.Companion.fromBlockId

/**
 * RPC endpoint for accessing Access Keys
 * @link https://docs.near.org/docs/api/rpc/access-keys
 */
class AccessKeyEndpoints(private val client: RPCClient) {

    /**
     * Returns information about a single access key for given account.
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC
     */
    suspend fun getAccessKey(accountId: String, publicKey: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = client.query(
        mapOf(
            "request_type" to "view_access_key",
            "account_id" to accountId,
            "public_key" to publicKey
        ),
        blockSearch
    )

    suspend fun getAccessKey(accountId: String, publicKey: String, blockId: Long) = getAccessKey(accountId, publicKey, fromBlockId(blockId))

    suspend fun getAccessKey(accountId: String, publicKey: String, blockHash: String) = getAccessKey(accountId, publicKey, fromBlockHash(blockHash))

    /**
     * Returns <b>all</b> access keys for a given account.
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-list
     *
     * @param accountId Account Identifier
     * @param blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC
     */
    suspend fun getAccessKeyList(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = client.query(
        mapOf(
            "request_type" to "view_access_key_list",
            "account_id" to accountId
        ),
        blockSearch
    )

    suspend fun getAccessKeyList(accountId: String, blockId: Long) = getAccessKeyList(accountId, fromBlockId(blockId))

    suspend fun getAccessKeyList(accountId: String, blockHash: String) = getAccessKeyList(accountId, fromBlockHash(blockHash))

    // TODO single_access_key_changes
    // TODO all_access_key_changes

}


