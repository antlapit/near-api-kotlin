package antlapit.near.api.kotlin.rpc

/**
 *
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
    }
}

