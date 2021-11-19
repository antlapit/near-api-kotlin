package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.PeerId
import java.net.InetSocketAddress

data class KnownProducer(
    val accountId: AccountId,
    val addr: InetSocketAddress?,
    val peerId: PeerId
)
