package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.model.BlockHeight
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.CryptoHash
import antlapit.near.api.providers.primitives.PublicKey

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
    private suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockSearch: BlockSearch) : Any = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_access_key",
            "account_id" to accountId,
            "public_key" to publicKey
        ),
        blockSearch
    )

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, finality: Finality) = getAccessKey(accountId, publicKey, ofFinality(finality))

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockId: BlockHeight) = getAccessKey(accountId, publicKey, fromBlockId(blockId))

    override suspend fun getAccessKey(accountId: AccountId, publicKey: PublicKey, blockHash: CryptoHash) = getAccessKey(accountId, publicKey, fromBlockHash(blockHash))

    /**
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/access-keys#view-access-key-list
     */
    private suspend fun getAccessKeyList(accountId: AccountId, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_access_key_list",
            "account_id" to accountId
        ),
        blockSearch
    )

    override suspend fun getAccessKeyList(accountId: AccountId, finality: Finality) = getAccessKeyList(accountId, ofFinality(finality))

    override suspend fun getAccessKeyList(accountId: AccountId, blockId: BlockHeight) = getAccessKeyList(accountId, fromBlockId(blockId))

    override suspend fun getAccessKeyList(accountId: AccountId, blockHash: CryptoHash) = getAccessKeyList(accountId, fromBlockHash(blockHash))
}


