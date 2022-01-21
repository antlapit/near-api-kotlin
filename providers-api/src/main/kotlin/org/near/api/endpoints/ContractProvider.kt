package org.near.api.endpoints

import org.near.api.model.account.AccountInBlock
import org.near.api.model.account.CallResult
import org.near.api.model.account.ContractCode
import org.near.api.model.account.ContractState
import org.near.api.model.changes.StateChanges
import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash

interface ContractProvider {

    /**
     * Returns basic account information by finality param.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getAccount(
        accountId: AccountId, finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): AccountInBlock

    /**
     * Returns basic account information by numeric block id.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getAccount(
        accountId: AccountId, blockId: BlockHeight,
        timeout: Long? = null
    ): AccountInBlock

    /**
     * Returns basic account information by block hash.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getAccount(
        accountId: AccountId, blockHash: CryptoHash,
        timeout: Long? = null
    ): AccountInBlock

    /**
     * Returns the contract code (Wasm binary) deployed to the account by finality param.
     * <br>Please note that the returned code will be encoded in base64
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getContractCode(
        accountId: AccountId, finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): ContractCode

    /**
     * Returns the contract code (Wasm binary) deployed to the account by numeric block id.
     * <br>Please note that the returned code will be encoded in base64.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getContractCode(
        accountId: AccountId, blockId: BlockHeight,
        timeout: Long? = null
    ): ContractCode

    /**
     * Returns the contract code (Wasm binary) deployed to the account by block hash.
     * <br>Please note that the returned code will be encoded in base64.
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getContractCode(
        accountId: AccountId,
        blockHash: CryptoHash,
        timeout: Long? = null
    ): ContractCode

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded) by finality param.
     *
     * @param accountId Account Identifier
     * @param keyPrefix base64 encoded key value
     * @param finality Finality param for last block
     */
    suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String = "",
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): ContractState

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded) by numeric block id.
     *
     * @param accountId Account Identifier
     * @param keyPrefix base64 encoded key value
     * @param blockId Numeric block identifier
     */
    suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String = "",
        blockId: BlockHeight,
        timeout: Long? = null
    ): ContractState

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded) by block hash.
     *
     * @param accountId Account Identifier
     * @param keyPrefix base64 encoded key value
     * @param blockHash String block hash
     */
    suspend fun getContractState(
        accountId: AccountId,
        keyPrefix: String = "",
        blockHash: CryptoHash,
        timeout: Long? = null
    ): ContractState

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a>  by finality param.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param finality Finality param for last block
     */
    suspend fun callFunction(
        accountId: AccountId, methodName: String, args: String, finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): CallResult

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a> by numeric block id.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param blockId Numeric block identifier
     */
    suspend fun callFunction(
        accountId: AccountId, methodName: String, args: String, blockId: BlockHeight,
        timeout: Long? = null
    ): CallResult

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a> by block hash.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param blockHash String block hash
     */
    suspend fun callFunction(
        accountId: AccountId, methodName: String, args: String, blockHash: CryptoHash,
        timeout: Long? = null
    ): CallResult

    /**
     * Returns account changes from transactions in a given account.
     *
     * @param accountIds List of account ids
     * @param finality Finality param for last block
     */
    suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns account changes from transactions in a given account.
     *
     * @param accountIds List of account ids
     * @param blockId Numeric block identifier
     */
    suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns account changes from transactions in a given account.
     *
     * @param accountIds List of account ids
     * @param blockHash String block hash
     */
    suspend fun getAccountsChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long? = null
    ): StateChanges


    /**
     * Returns the state change details of a contract based on the key prefix (encoded to base64).
     * Pass an empty string for this param if you would like to return all state changes.
     *
     * @param accountIds List of account ids
     * @param keyPrefix base64 encoded key value
     * @param finality Finality param for last block
     */
    suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String = "",
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns the state change details of a contract based on the key prefix (encoded to base64).
     * Pass an empty string for this param if you would like to return all state changes.
     *
     * @param accountIds List of account ids
     * @param keyPrefix base64 encoded key value
     * @param blockId Numeric block identifier
     */
    suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String = "",
        blockId: BlockHeight,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns the state change details of a contract based on the key prefix (encoded to base64).
     * Pass an empty string for this param if you would like to return all state changes.
     *
     * @param accountIds List of account ids
     * @param keyPrefix base64 encoded key value
     * @param blockHash String block hash
     */
    suspend fun getContractStateChanges(
        accountIds: List<AccountId>,
        keyPrefix: String = "",
        blockHash: CryptoHash,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns code changes made when deploying a contract. Change is returned is a base64 encoded WASM file.
     *
     * @param accountIds List of account ids
     * @param finality Finality param for last block
     */
    suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        finality: Finality = Finality.OPTIMISTIC,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns code changes made when deploying a contract. Change is returned is a base64 encoded WASM file.
     *
     * @param accountIds List of account ids
     * @param blockId Numeric block identifier
     */
    suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        blockId: BlockHeight,
        timeout: Long? = null
    ): StateChanges

    /**
     * Returns code changes made when deploying a contract. Change is returned is a base64 encoded WASM file.
     *
     * @param accountIds List of account ids
     * @param blockHash String block hash
     */
    suspend fun getContractCodeChanges(
        accountIds: List<AccountId>,
        blockHash: CryptoHash,
        timeout: Long? = null
    ): StateChanges
}
