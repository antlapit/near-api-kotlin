package org.near.api.providers.base

import org.near.api.providers.model.primitives.BlockHeight
import org.near.api.providers.model.primitives.CryptoHash

/**
 * Block search strategy. Possible cases:
 * <ul>
 *      <li>finality - search for last block</li>
 *      <li>blockId or blockHash - search for concrete block</li>
 * </ul>
 * @link https://docs.near.org/docs/api/rpc#using-finality-param
 * @link https://docs.near.org/docs/api/rpc#using-block_id-param
 */
class BlockSearch(var finality: org.near.api.providers.Finality? = null,
                  var blockId: BlockHeight? = null,
                  var blockHash: CryptoHash? = null) {

    companion object {
        val BLOCK_FINAL = BlockSearch(finality = org.near.api.providers.Finality.FINAL)
        val BLOCK_OPTIMISTIC = BlockSearch(finality = org.near.api.providers.Finality.OPTIMISTIC)

        fun fromBlockId(blockId: BlockHeight) : BlockSearch = BlockSearch(blockId =  blockId)

        fun fromBlockHash(blockHash: CryptoHash) : BlockSearch = BlockSearch(blockHash =  blockHash)

        fun ofFinality(finality: org.near.api.providers.Finality) : BlockSearch {
            return when (finality) {
                org.near.api.providers.Finality.OPTIMISTIC -> BLOCK_OPTIMISTIC
                org.near.api.providers.Finality.FINAL -> BLOCK_FINAL
                else ->  {
                    throw IllegalArgumentException("Unsupported finality param $finality")
                }
            }
        }
    }
}

