package antlapit.near.api.providers.model.networkinfo

data class NetworkInfo(
    val activePeers: List<PeerInfo> = emptyList(),
    val numActivePeers: Long = 0,
    val peerMaxCount: Int = 0,
    val sentBytesPerSec: Long = 0,
    val receivedBytesPerSec: Long = 0,
    val knownProducers: List<KnownProducer> = emptyList()
)
