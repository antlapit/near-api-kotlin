package org.near.api.model.primitives

import org.komputing.kbase58.decodeBase58

typealias CryptoHash = String

fun CryptoHash.decodeBase58() = decodeBase58()
