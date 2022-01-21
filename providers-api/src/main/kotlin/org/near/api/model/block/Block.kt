package org.near.api.model.block

data class Block(
    val author: String,
    val header: BlockHeader,
    val chunks: List<ChunkHeader> = emptyList()
)
