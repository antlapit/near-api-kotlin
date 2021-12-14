package antlapit.near.api.providers.model.primitives

import org.komputing.kbase58.decodeBase58

typealias CryptoHash = String

fun CryptoHash.decodeBase58() = decodeBase58()
