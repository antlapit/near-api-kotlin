package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.model.BlockHeight
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.CryptoHash

/**
 * RPC endpoint for working with Accounts / Contracts
 * @link https://docs.near.org/docs/api/rpc/contracts
 */
class ContractRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : ContractProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-account
     */
    private suspend fun getAccount(accountId: AccountId, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_account",
            "account_id" to accountId
        ),
        blockSearch
    )

    override suspend fun getAccount(accountId: AccountId, finality: Finality) = getAccount(accountId, ofFinality(finality))

    override suspend fun getAccount(accountId: AccountId, blockId: BlockHeight) = getAccount(accountId, fromBlockId(blockId))

    override suspend fun getAccount(accountId: AccountId, blockHash: CryptoHash) = getAccount(accountId, fromBlockHash(blockHash))


    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     */
    private suspend fun getContractCode(accountId: AccountId, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any =
        jsonRpcProvider.query(
            mapOf(
                "request_type" to "view_code",
                "account_id" to accountId,
            ),
            blockSearch
        )

    override suspend fun getContractCode(accountId: AccountId, finality: Finality) = getContractCode(accountId, ofFinality(finality))

    override suspend fun getContractCode(accountId: AccountId, blockId: BlockHeight) = getContractCode(accountId, fromBlockId(blockId))

    override suspend fun getContractCode(accountId: AccountId, blockHash: CryptoHash) =
        getContractCode(accountId, fromBlockHash(blockHash))

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-state
     */
    private suspend fun getContractState(accountId: AccountId, blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC) : Any =
        jsonRpcProvider.query(
            mapOf(
                "request_type" to "view_state",
                "account_id" to accountId,
                "prefix_base64" to ""
            ),
            blockSearch
        )

    override suspend fun getContractState(accountId: AccountId, finality: Finality) = getContractState(accountId, ofFinality(finality))

    override suspend fun getContractState(accountId: AccountId, blockId: BlockHeight) = getContractState(accountId, fromBlockId(blockId))

    override suspend fun getContractState(accountId: AccountId, blockHash: CryptoHash) =
        getContractState(accountId, fromBlockHash(blockHash))

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#call-a-contract-function
     */
    private suspend fun callFunction(
        accountId: AccountId,
        methodName: String,
        args: String,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC
    ) : Any = jsonRpcProvider.query(
        mapOf(
            "request_type" to "call_function",
            "account_id" to accountId,
            "method_name" to methodName,
            "args_base64" to Utils.encodeToBase64(args)
        ),
        blockSearch
    )

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, finality: Finality
    ) = callFunction(accountId, methodName, args, ofFinality(finality))

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockId: BlockHeight
    ) = callFunction(accountId, methodName, args, fromBlockId(blockId))

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockHash: CryptoHash
    ) = callFunction(accountId, methodName, args, fromBlockHash(blockHash))

}
