package antlapit.near.api.providers

import antlapit.near.api.providers.primitives.BlockHeight
import antlapit.near.api.providers.primitives.CryptoHash

/**
 * Block search strategy. Possible cases:
 * <ul>
 *      <li>finality - search for last block</li>
 *      <li>blockId or blockHash - search for concrete block</li>
 * </ul>
 * @link https://docs.near.org/docs/api/rpc#using-finality-param
 * @link https://docs.near.org/docs/api/rpc#using-block_id-param
 */
class BlockSearch(var finality: Finality? = null,
                  var blockId: BlockHeight? = null,
                  var blockHash: CryptoHash? = null) {

    companion object {
        val BLOCK_FINAL = BlockSearch(finality = Finality.FINAL)
        val BLOCK_OPTIMISTIC = BlockSearch(finality = Finality.OPTIMISTIC)

        fun fromBlockId(blockId: BlockHeight) : BlockSearch = BlockSearch(blockId =  blockId)

        fun fromBlockHash(blockHash: CryptoHash) : BlockSearch = BlockSearch(blockHash =  blockHash)

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

