package org.near.api.endpoints

import org.near.api.base64
import org.near.api.model.account.AccountInBlock
import org.near.api.model.account.CallResult
import org.near.api.model.account.ContractCode
import org.near.api.model.account.ContractState
import org.near.api.model.changes.StateChanges
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.provider.BlockSearch
import org.near.api.provider.BlockSearch.Companion.fromBlockHash
import org.near.api.provider.BlockSearch.Companion.fromBlockId
import org.near.api.provider.BlockSearch.Companion.ofFinality
import org.near.api.provider.JsonRpcProvider

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
        timeout: Long?
    ): AccountInBlock = jsonRpcProvider.sendRpc(
        method = "query",
        blockSearch = blockSearch,
        params = mapOf(
            "request_type" to "view_account",
            "account_id" to accountId
        ),
        timeout
    )

    override suspend fun getAccount(accountId: AccountId, finality: Finality, timeout: Long?) =
        getAccount(accountId, ofFinality(finality), timeout)

    override suspend fun getAccount(accountId: AccountId, blockId: BlockHeight, timeout: Long?) =
        getAccount(accountId, fromBlockId(blockId), timeout)

    override suspend fun getAccount(accountId: AccountId, blockHash: CryptoHash, timeout: Long?) =
        getAccount(accountId, fromBlockHash(blockHash), timeout)


    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     */
    private suspend fun getContractCode(
        accountId: AccountId,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long?
    ): ContractCode =
        jsonRpcProvider.sendRpc(
            method = "query",
            blockSearch = blockSearch,
            params = mapOf(
                "request_type" to "view_code",
                "account_id" to accountId,
            ),
            timeout
        )

    override suspend fun getContractCode(accountId: AccountId, finality: Finality, timeout: Long?) =
        getContractCode(accountId, ofFinality(finality), timeout)

    override suspend fun getContractCode(accountId: AccountId, blockId: BlockHeight, timeout: Long?) =
        getContractCode(accountId, fromBlockId(blockId), timeout)

    override suspend fun getContractCode(accountId: AccountId, blockHash: CryptoHash, timeout: Long?) =
        getContractCode(accountId, fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-state
     */
    private suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String = "",
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): ContractState =
        jsonRpcProvider.sendRpc(
            method = "query",
            blockSearch = blockSearch,
            params = mapOf(
                "request_type" to "view_state",
                "account_id" to accountId,
                "prefix_base64" to keyPrefix
            ),
            timeout
        )

    override suspend fun getContractState(accountId: AccountId, keyPrefix: String, finality: Finality, timeout: Long?) =
        getContractState(accountId, keyPrefix, ofFinality(finality), timeout)

    override suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String,
        blockId: BlockHeight,
        timeout: Long?
    ) =
        getContractState(accountId, keyPrefix, fromBlockId(blockId), timeout)

    override suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String,
        blockHash: CryptoHash,
        timeout: Long?
    ) =
        getContractState(accountId, keyPrefix, fromBlockHash(blockHash), timeout)

    /**
     * @link https://docs.near.org/docs/api/rpc/contracts#call-a-contract-function
     */
    private suspend fun callFunction(
        accountId: AccountId,
        methodName: String,
        args: String,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC, timeout: Long?
    ): CallResult = jsonRpcProvider.sendRpc(
        method = "query",
        blockSearch = blockSearch,
        params = mapOf(
            "request_type" to "call_function",
            "account_id" to accountId,
            "method_name" to methodName,
            "args_base64" to args.toByteArray().base64()
        ),
        timeout
    )

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, finality: Finality, timeout: Long?
    ) = callFunction(accountId, methodName, args, ofFinality(finality), timeout)

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockId: BlockHeight, timeout: Long?
    ) = callFunction(accountId, methodName, args, fromBlockId(blockId), timeout)

    override suspend fun callFunction(
        accountId: AccountId, methodName: String,
        args: String, blockHash: CryptoHash, timeout: Long?
    ) = callFunction(accountId, methodName, args, fromBlockHash(blockHash), timeout)

    /**
     * @param accountIds List of account ids
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/contracts#view-account-changes
     */
    private suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): StateChanges = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes",
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "account_changes",
            "account_ids" to accountIds
        ),
        timeout = timeout
    )

    override suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        finality: Finality,
        timeout: Long?
    ) = getAccountsChanges(accountIds, ofFinality(finality), timeout)

    override suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long?
    ) = getAccountsChanges(accountIds, fromBlockId(blockId), timeout)

    override suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long?
    ) = getAccountsChanges(accountIds, fromBlockHash(blockHash), timeout)

    /**
     * @param accountIds List of account ids
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-state-changes
     */
    private suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String = "",
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): StateChanges = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes",
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "data_changes",
            "account_ids" to accountIds,
            "key_prefix_base64" to keyPrefix
        ),
        timeout = timeout
    )

    override suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String,
        finality: Finality,
        timeout: Long?
    ) = getContractStateChanges(accountIds, keyPrefix, ofFinality(finality), timeout)

    override suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String,
        blockId: BlockHeight,
        timeout: Long?
    ) = getContractStateChanges(accountIds, keyPrefix, fromBlockId(blockId), timeout)

    override suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String,
        blockHash: CryptoHash,
        timeout: Long?
    ) = getContractStateChanges(accountIds, keyPrefix, fromBlockHash(blockHash), timeout)

    /**
     * @param accountIds List of account ids
     * @param blockSearch Block search strategy for querying blocks
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code-changes
     */
    private suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        blockSearch: BlockSearch = BlockSearch.BLOCK_OPTIMISTIC,
        timeout: Long?
    ): StateChanges = jsonRpcProvider.sendRpc(
        method = "EXPERIMENTAL_changes",
        blockSearch = blockSearch,
        params = mapOf(
            "changes_type" to "contract_code_changes",
            "account_ids" to accountIds
        ),
        timeout = timeout
    )

    override suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        finality: Finality,
        timeout: Long?
    ) = getContractCodeChanges(accountIds, ofFinality(finality), timeout)

    override suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long?
    ) = getContractCodeChanges(accountIds, fromBlockId(blockId), timeout)

    override suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long?
    ) = getContractCodeChanges(accountIds, fromBlockHash(blockHash), timeout)
}
