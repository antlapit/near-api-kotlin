package org.near.api.providers.model.transaction

interface Signer {
    fun sign(message: ByteArray)
}