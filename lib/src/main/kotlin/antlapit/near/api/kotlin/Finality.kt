package antlapit.near.api.kotlin

/**
 * Query parameter for choosing last block search strategy
 *
 * @link https://docs.near.org/docs/api/rpc#using-finality-param
 */
enum class Finality(val code: String) {

    /**
     * Uses the latest block that has been validated on at least 66% of the nodes in the network (usually takes 2 blocks / approx. 2 second delay)
     */
    FINAL("final"),

    /**
     * Uses the latest block recorded on the node that responded to your query (<1 second delay after the transaction is submitted)
     */
    OPTIMISTIC("optimistic")
}
