package org.near.api.providers.base

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.near.api.providers.model.primitives.PublicKey
import org.near.api.providers.model.primitives.TxExecutionError

class ExceptionFactory(
    private val objectMapper: ObjectMapper
) {
    fun emptyResult() =
        org.near.api.providers.exception.ProviderException("Empty result in response without specifying error")

    fun fromJsonNode(error: JsonNode): org.near.api.providers.exception.ProviderException {
        return when {
            error.isTextual -> {
                org.near.api.providers.exception.ProviderException(error.asText())
            }
            error.isObject -> {
                val name = error["name"].asText()
                val cause = error["cause"]
                val causeName = cause["name"].asText()
                val causeInfo = cause["info"]
                // FIXME old field, but currently contains more info than cause
                val oldData = error["data"]

                constructException(name, causeName, if (causeInfo == null || causeInfo.isEmpty) oldData else causeInfo)
            }
            else -> {
                org.near.api.providers.exception.ProviderException("Undefined response error")
            }
        }
    }

    private fun constructException(error: String, cause: String, info: JsonNode): org.near.api.providers.exception.ProviderException {
        return try {
            when (error) {
                "HANDLER_ERROR" -> when (cause) {
                    "UNKNOWN_BLOCK" -> org.near.api.providers.exception.UnknownBlockException(info)
                    "UNKNOWN_CHUNK" -> org.near.api.providers.exception.UnknownChunkException(
                        info.get("chunk_hash").asText()
                    )
                    "UNKNOWN_RECEIPT" -> org.near.api.providers.exception.UnknownReceiptException(
                        info.get("receipt_id").asText()
                    )
                    "INVALID_SHARD_ID" -> org.near.api.providers.exception.InvalidShardIdException(
                        info.get("shard_id").asLong()
                    )
                    "NOT_SYNCED_YET" -> org.near.api.providers.exception.NotSyncedException(info)
                    "INVALID_ACCOUNT" -> org.near.api.providers.exception.InvalidAccountException(
                        info.get("requested_account_id").asText(),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNKNOWN_ACCOUNT" -> org.near.api.providers.exception.UnknownAccountException(
                        info.get("requested_account_id").asText(),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNKNOWN_ACCESS_KEY" -> org.near.api.providers.exception.UnknownAccessKeyException(
                        PublicKey(info.get("public_key").asText()),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNAVAILABLE_SHARD" -> org.near.api.providers.exception.UnavailableShardException(info)
                    "NO_SYNCED_BLOCKS" -> org.near.api.providers.exception.NoSyncedBlocksException(info)
                    "INVALID_TRANSACTION" -> {
                        val txError =
                            objectMapper.treeToValue(info.get("TxExecutionError"), TxExecutionError::class.java)
                        org.near.api.providers.exception.InvalidTransactionException(txExecutionError = txError)
                    }
                    "TIMEOUT_ERROR" -> org.near.api.providers.exception.TimeoutErrorException(info)
                    "UNKNOWN_EPOCH" -> org.near.api.providers.exception.UnknownEpochException(info)
                    else -> org.near.api.providers.exception.ProviderException(error, cause)
                }
                "REQUEST_VALIDATION_ERROR" -> when (cause) {
                    "PARSE_ERROR" -> org.near.api.providers.exception.ParseErrorException(
                        info.get("error_message").asText()
                    )
                    else -> org.near.api.providers.exception.ProviderException(error, cause)
                }
                "INTERNAL_ERROR" -> when (cause) {
                    "INTERNAL_ERROR" -> org.near.api.providers.exception.InternalErrorException(
                        info.get("error_message").asText()
                    )
                    else -> org.near.api.providers.exception.ProviderException(error, cause)
                }
                else -> org.near.api.providers.exception.ProviderException(error, cause)
            }
        } catch (e: Throwable) {
            org.near.api.providers.exception.ProviderException(error, cause, e)
        }
    }
}
