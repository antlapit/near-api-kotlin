package antlapit.near.api.providers

interface AccessKeyProvider {

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param finality Finality param for last block
     */
    suspend fun getAccessKey(accountId: String, publicKey: String, finality: Finality = Finality.OPTIMISTIC): Any


    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKey(accountId: String, publicKey: String, blockId: Long): Any

    /**
     * Returns information about a single access key for given account.
     *
     * @param accountId Account Identifier
     * @param publicKey Public key
     * @param blockHash String block hash
     */
    suspend fun getAccessKey(accountId: String, publicKey: String, blockHash: String): Any

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param finality Finality param for last block
     */
    suspend fun getAccessKeyList(accountId: String, finality: Finality): Any

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockId Numeric block identifier
     */
    suspend fun getAccessKeyList(accountId: String, blockId: Long): Any

    /**
     * Returns <b>all</b> access keys for a given account.
     *
     * @param accountId Account Identifier
     * @param blockHash String block hash
     */
    suspend fun getAccessKeyList(accountId: String, blockHash: String): Any


    // TODO single_access_key_changes
    // TODO all_access_key_changes

}
