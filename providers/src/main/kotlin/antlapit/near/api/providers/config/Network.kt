package antlapit.near.api.providers.config

enum class Network(val url: String) {
    MAINNET("https://rpc.mainnet.near.org"),
    MAINNET_ARCHIVAL("https://archival-rpc.mainnet.near.org"),
    TESTNET("https://rpc.testnet.near.org"),
    TESTNET_ARCHIVAL("https://archival-rpc.testnet.near.org"),
    BETANET("https://rpc.betanet.near.org"),
    LOCALNET("http://localhost:3000")
}
