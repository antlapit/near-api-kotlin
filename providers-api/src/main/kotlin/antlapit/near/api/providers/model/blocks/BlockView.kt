package antlapit.near.api.providers.model.blocks

data class BlockView(
    val author: String,
    val header: BlockHeaderView,
    val chunks: List<ChunkHeaderView> = emptyList()
)
