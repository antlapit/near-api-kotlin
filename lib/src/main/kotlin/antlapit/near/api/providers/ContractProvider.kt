package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId

/**
 * RPC endpoint for working with Accounts / Contracts
 * @link https://docs.near.org/docs/api/providers/contracts
 */
class ContractProvider(private val client: BaseJsonRpcProvider) {

    /**
     * Returns basic account information.
     * @link https://docs.near.org/docs/api/providers/contracts#view-account
     *
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     */
    suspend fun getAccount(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) = client.query(
        mapOf(
            "request_type" to "view_account",
            "account_id" to accountId
        ),
        blockSearch
    )

    suspend fun getAccount(accountId: String, blockId: Long) = getAccount(accountId, fromBlockId(blockId))

    suspend fun getAccount(accountId: String, blockHash: String) = getAccount(accountId, fromBlockHash(blockHash))


    // TODO View account changes

    /**
     * Returns the contract code (Wasm binary) deployed to the account for last block by finality param.
     * <br>Please note that the returned code will be encoded in base64.
     * @link https://docs.near.org/docs/api/providers/contracts#view-contract-code
     *
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     */
    suspend fun getContractCode(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) =
        client.query(
            mapOf(
                "request_type" to "view_code",
                "account_id" to accountId,
            ),
            blockSearch
        )

    suspend fun getContractCode(accountId: String, blockId: Long) = getContractCode(accountId, fromBlockId(blockId))

    suspend fun getContractCode(accountId: String, blockHash: String) =
        getContractCode(accountId, fromBlockHash(blockHash))

    /**
     * Returns the contract code (Wasm binary) deployed to the account. Please note that the returned code will be encoded in base64.
     * @link https://docs.near.org/docs/api/providers/contracts#view-contract-state
     *
     * @param accountId Account Identifier
     * @param blockSearch Block search strategy for querying blocks
     */
    suspend fun getContractState(accountId: String, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) =
        client.query(
            mapOf(
                "request_type" to "view_state",
                "account_id" to accountId,
                "prefix_base64" to ""
            ),
            blockSearch
        )

    suspend fun getContractState(accountId: String, blockId: Long) = getContractState(accountId, fromBlockId(blockId))

    suspend fun getContractState(accountId: String, blockHash: String) =
        getContractState(accountId, fromBlockHash(blockHash))

    // TODO View contract state changes
    // TODO View contract code changes

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a>.
     *
     * @link https://docs.near.org/docs/api/providers/contracts#call-a-contract-function
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param blockSearch Block search strategy for querying blocks
     */
    suspend fun callFunction(
        accountId: String,
        methodName: String,
        args: String,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC
    ) = client.query(
        mapOf(
            "request_type" to "call_function",
            "account_id" to accountId,
            "method_name" to methodName,
            "args_base64" to Utils.encodeToBase64(args)
        ),
        blockSearch
    )

    suspend fun callFunction(
        accountId: String, methodName: String,
        args: String, blockId: Long
    ) = callFunction(accountId, methodName, args, fromBlockId(blockId))

    suspend fun callFunction(
        accountId: String, methodName: String,
        args: String, blockHash: String
    ) = callFunction(accountId, methodName, args, fromBlockHash(blockHash))
}
