package antlapit.near.api.providers

import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

interface ContractProvider {

    /**
     * Returns basic account information by finality param.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getAccount(accountId: AccountId, finality: Finality = Finality.OPTIMISTIC): Any

    /**
     * Returns basic account information by numeric block id.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getAccount(accountId: AccountId, blockId: BlockHeight) : Any

    /**
     * Returns basic account information by block hash.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getAccount(accountId: AccountId, blockHash: CryptoHash) : Any

    /**
     * Returns the contract code (Wasm binary) deployed to the account by finality param.
     * <br>Please note that the returned code will be encoded in base64
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getContractCode(accountId: AccountId, finality: Finality = Finality.OPTIMISTIC): Any

    /**
     * Returns the contract code (Wasm binary) deployed to the account by numeric block id.
     * <br>Please note that the returned code will be encoded in base64.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getContractCode(accountId: AccountId, blockId: BlockHeight): Any

    /**
     * Returns the contract code (Wasm binary) deployed to the account by block hash.
     * <br>Please note that the returned code will be encoded in base64.
     * @link https://docs.near.org/docs/api/rpc/contracts#view-contract-code
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getContractCode(accountId: AccountId, blockHash: CryptoHash): Any

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded)  by finality param.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getContractState(accountId: AccountId, finality: Finality = Finality.OPTIMISTIC): Any

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded) by numeric block id.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getContractState(accountId: AccountId, blockId: BlockHeight): Any

    /**
     * Returns the state (key value pairs) of a contract based on the key prefix (base64 encoded) by block hash.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getContractState(accountId: AccountId, blockHash: CryptoHash): Any

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a>  by finality param.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param finality Finality param for last block
     */
    suspend fun callFunction(accountId: AccountId, methodName: String, args: String, finality: Finality): Any

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a> by numeric block id.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param blockId Numeric block identifier
     */
    suspend fun callFunction(accountId: AccountId, methodName: String, args: String, blockId: BlockHeight): Any

    /**
     * Allows you to call a contract method as a <a href="https://docs.near.org/docs/develop/contracts/as/intro#view-and-change-functions">view function</a> by block hash.
     *
     * @param accountId Account Identifier
     * @param methodName Contract method to call
     * @param args Serialized JSON method arguments
     * @param blockHash String block hash
     */
    suspend fun callFunction(accountId: AccountId, methodName: String, args: String, blockHash: CryptoHash): Any

    // TODO View account changes
    // TODO View contract state changes
    // TODO View contract code changes
}
