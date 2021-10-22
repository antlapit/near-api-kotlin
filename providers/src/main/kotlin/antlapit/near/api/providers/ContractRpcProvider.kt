package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality

/**
 * RPC endpoint for working with Accounts / Contracts
 * @link https://docs.near.org/docs/api/rpc/contracts
 */
class ContractRpcProvider(private val client: BaseJsonRpcProvider) : ContractProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-account
     */
    private suspend fun getAccount(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any = client.query(
        mapOf(
            "request_type" to "view_account",
            "account_id" to accountId
        ),
        blockSearch
    )

    override suspend fun getAccount(accountId: String, finality: Finality) = getAccount(accountId, ofFinality(finality))

    override suspend fun getAccount(accountId: String, blockId: Long) = getAccount(accountId, fromBlockId(blockId))

    override suspend fun getAccount(accountId: String, blockHash: String) = getAccount(accountId, fromBlockHash(blockHash))


    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     */
    private suspend fun getContractCode(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any =
        client.query(
            mapOf(
                "request_type" to "view_code",
                "account_id" to accountId,
            ),
            blockSearch
        )

    override suspend fun getContractCode(accountId: String, finality: Finality) = getContractCode(accountId, ofFinality(finality))

    override suspend fun getContractCode(accountId: String, blockId: Long) = getContractCode(accountId, fromBlockId(blockId))

    override suspend fun getContractCode(accountId: String, blockHash: String) =
        getContractCode(accountId, fromBlockHash(blockHash))

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-state
     */
    private suspend fun getContractState(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any =
        client.query(
            mapOf(
                "request_type" to "view_state",
                "account_id" to accountId,
                "prefix_base64" to ""
            ),
            blockSearch
        )

    override suspend fun getContractState(accountId: String, finality: Finality) = getContractState(accountId, ofFinality(finality))

    override suspend fun getContractState(accountId: String, blockId: Long) = getContractState(accountId, fromBlockId(blockId))

    override suspend fun getContractState(accountId: String, blockHash: String) =
        getContractState(accountId, fromBlockHash(blockHash))

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#call-a-contract-function
     */
    suspend fun callFunction(
        accountId: String,
        methodName: String,
        args: String,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC
    ) : Any = client.query(
        mapOf(
            "request_type" to "call_function",
            "account_id" to accountId,
            "method_name" to methodName,
            "args_base64" to Utils.encodeToBase64(args)
        ),
        blockSearch
    )

    override suspend fun callFunction(
        accountId: String, methodName: String,
        args: String, finality: Finality
    ) = callFunction(accountId, methodName, args, ofFinality(finality))

    override suspend fun callFunction(
        accountId: String, methodName: String,
        args: String, blockId: Long
    ) = callFunction(accountId, methodName, args, fromBlockId(blockId))

    override suspend fun callFunction(
        accountId: String, methodName: String,
        args: String, blockHash: String
    ) = callFunction(accountId, methodName, args, fromBlockHash(blockHash))
}
