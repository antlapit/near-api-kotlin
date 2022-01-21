package org.near.api.model.networkinfo

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.PeerId
import java.net.InetSocketAddress

data class PeerInfo(
    val id: PeerId,
    val addr: InetSocketAddress?,
    val accountId: AccountId?
)
