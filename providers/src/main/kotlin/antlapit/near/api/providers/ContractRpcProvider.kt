package antlapit.near.api.providers

import antlapit.near.api.providers.BlockSearch.Companion.fromBlockHash
import antlapit.near.api.providers.BlockSearch.Companion.fromBlockId
import antlapit.near.api.providers.BlockSearch.Companion.ofFinality
import antlapit.near.api.providers.model.account.Account
import antlapit.near.api.providers.model.account.CallResult
import antlapit.near.api.providers.model.account.ContractCode
import antlapit.near.api.providers.model.account.ContractState
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

/**
 * RPC endpoint for working with Accounts / Contracts
 * @link https://docs.near.org/docs/api/rpc/contracts
 */
class ContractRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : ContractProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-account
     */
    private suspend fun getAccount(
        accountId: AccountId,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long
    ): Account = jsonRpcProvider.query(
        mapOf(
            "request_type" to "view_account",
            "account_id" to accountId
        ),
        blockSearch,
        timeout
    )

    override suspend fun getAccount(accountId: AccountId, finality: Finality, timeout: Long) =
        getAccount(accountId, ofFinality(finality), timeout)

    override suspend fun getAccount(accountId: AccountId, blockId: BlockHeight, timeout: Long) =
        getAccount(accountId, fromBlockId(blockId), timeout)

    override suspend fun getAccount(accountId: AccountId, blockHash: CryptoHash, timeout: Long) =
        getAccount(accountId, fromBlockHash(blockHash), timeout)


    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     */
    private suspend fun getContractCode(
        accountId: AccountId,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long
    ): ContractCode =
        jsonRpcProvider.query(
            mapOf(
                "request_type" to "view_code",
                "account_id" to accountId,
            ),
            blockSearch,
            timeout
        )

    override suspend fun getContractCode(accountId: AccountId, finality: Finality, timeout: Long) =
        getContractCode(accountId, ofFinality(finality), timeout)

    override suspend fun getContractCode(accountId: AccountId, blockId: BlockHeight, timeout: Long) =
        getContractCode(accountId, fromBlockId(blockId), timeout)

    override suspend fun getContractCode(accountId: AccountId, blockHash: CryptoHash, timeout: Long) =
        getContractCode(accountId, fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-state
     */
    private suspend fun getContractState(
        accountId: AccountId,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long
    ): ContractState =
        jsonRpcProvider.query(
            mapOf(
                "request_type" to "view_state",
                "account_id" to accountId,
                "prefix_base64" to ""
            ),
            blockSearch,
            timeout
        )

    override suspend fun getContractState(accountId: AccountId, finality: Finality, timeout: Long) =
        getContractState(accountId, ofFinality(finality), timeout)

    override suspend fun getContractState(accountId: AccountId, blockId: BlockHeight, timeout: Long) =
        getContractState(accountId, fromBlockId(blockId), timeout)

    override suspend fun getContractState(accountId: AccountId, blockHash: CryptoHash, timeout: Long) =
        getContractState(accountId, fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#call-a-contract-function
     */
    private suspend fun callFunction(
        accountId: AccountId,
        methodName: String,
        args: String,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long
    ): CallResult = jsonRpcProvider.query(
        mapOf(
            "request_type" to "call_function",
            "account_id" to accountId,
            "method_name" to methodName,
            "args_base64" to Utils.encodeToBase64(args)
        ),
        blockSearch,
        timeout
    )

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, finality: Finality, timeout: Long
    ) = callFunction(accountId, methodName, args, ofFinality(finality), timeout)

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockId: BlockHeight, timeout: Long
    ) = callFunction(accountId, methodName, args, fromBlockId(blockId), timeout)

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockHash: CryptoHash, timeout: Long
    ) = callFunction(accountId, methodName, args, fromBlockHash(blockHash), timeout)

}
