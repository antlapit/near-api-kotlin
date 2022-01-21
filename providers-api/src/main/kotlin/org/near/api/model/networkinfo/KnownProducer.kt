package org.near.api.model.networkinfo

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.PeerId
import java.net.InetSocketAddress

data class KnownProducer(
    val accountId: AccountId,
    val addr: InetSocketAddress?,
    val peerId: PeerId
)
