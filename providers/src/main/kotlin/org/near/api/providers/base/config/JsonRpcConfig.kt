package org.near.api.providers.base.config

import io.ktor.client.features.logging.*

data class JsonRpcConfig(
    val network: Network = NetworkEnum.MAINNET,
    val logging: Logging.Config = defaultLoggingConfig()
) {
    fun getAddress() = network.getUrl()
}

fun defaultLoggingConfig(): Logging.Config {
    val config = Logging.Config()
    config.logger = Logger.DEFAULT
    config.level = LogLevel.BODY
    return config
}
