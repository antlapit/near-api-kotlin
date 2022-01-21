package org.near.api.provider.config

import io.ktor.client.features.logging.*

data class JsonRpcConfig(
    val network: Network = NetworkEnum.MAINNET,
    val logging: Logging.Config = defaultLoggingConfig(),
    val defaultTimeout: Long = 10_000
) {
    fun getAddress() = network.getUrl()
}

fun defaultLoggingConfig(): Logging.Config {
    val config = Logging.Config()
    config.logger = Logger.DEFAULT
    config.level = LogLevel.BODY
    return config
}
