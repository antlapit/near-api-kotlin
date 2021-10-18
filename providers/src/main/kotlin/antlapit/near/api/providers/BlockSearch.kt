package antlapit.near.api.providers

/**
 * Block search strategy. Possible cases:
 * <ul>
 *      <li>finality - search for last block</li>
 *      <li>blockId or blockHash - search for concrete block</li>
 * </ul>
 * @link https://docs.near.org/docs/api/rpc#using-finality-param
 * @link https://docs.near.org/docs/api/rpc#using-block_id-param
 */
class BlockSearch(var finality: Finality? = Finality.OPTIMISTIC,
                  var blockId: Long? = null,
                  var blockHash: String? = null) {

    companion object {
        val BLOCK_FINAL = BlockSearch(finality = Finality.FINAL)
        val BLOCK_OPTIMISTIC = BlockSearch(finality = Finality.OPTIMISTIC)

        fun fromBlockId(blockId: Long) : BlockSearch = BlockSearch(blockId =  blockId)

        fun fromBlockHash(blockHash: String) : BlockSearch = BlockSearch(blockHash =  blockHash)

        fun ofFinality(finality: Finality) : BlockSearch {
            return when (finality) {
                Finality.OPTIMISTIC -> BLOCK_OPTIMISTIC
                Finality.FINAL -> BLOCK_FINAL
                else ->  {
                    throw IllegalArgumentException("Unsupported finality param $finality")
                }
            }
        }
    }
}

