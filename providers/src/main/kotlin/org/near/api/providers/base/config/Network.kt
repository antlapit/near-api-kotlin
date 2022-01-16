package org.near.api.providers.base.config

interface Network {
    fun getUrl() : String
}

enum class NetworkEnum(private val url: String) : Network {
    MAINNET("https://rpc.mainnet.near.org"),
    MAINNET_ARCHIVAL("https://archival-rpc.mainnet.near.org"),
    TESTNET("https://rpc.testnet.near.org"),
    TESTNET_ARCHIVAL("https://archival-rpc.testnet.near.org"),
    BETANET("https://rpc.betanet.near.org"),
    LOCALNET("http://localhost:3000");

    override fun getUrl(): String = url
}
