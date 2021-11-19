package antlapit.near.api.providers.model.networkinfo

import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.PeerId
import java.net.InetSocketAddress

data class PeerInfo(
    val id: PeerId,
    val addr: InetSocketAddress?,
    val accountId: AccountId?
)
