package org.near.api.model.transaction

interface Signer {
    fun sign(message: ByteArray)
}
