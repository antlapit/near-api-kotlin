package org.near.api.providers.base

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.near.api.providers.exception.*
import org.near.api.providers.model.primitives.PublicKey
import org.near.api.providers.model.primitives.TxExecutionError

class ExceptionFactory(
    private val objectMapper: ObjectMapper
) {
    fun emptyResult() =
        ProviderException("Empty result in response without specifying error")

    fun fromJsonNode(error: JsonNode): ProviderException {
        return when {
            error.isTextual -> {
                ProviderException(error.asText())
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
                ProviderException("Undefined response error")
            }
        }
    }

    private fun constructException(error: String, cause: String, info: JsonNode): ProviderException {
        return try {
            when (error) {
                "HANDLER_ERROR" -> when (cause) {
                    "UNKNOWN_BLOCK" -> UnknownBlockException(info)
                    "UNKNOWN_CHUNK" -> UnknownChunkException(
                        info.get("chunk_hash").asText()
                    )
                    "UNKNOWN_RECEIPT" -> UnknownReceiptException(
                        info.get("receipt_id").asText()
                    )
                    "INVALID_SHARD_ID" -> InvalidShardIdException(
                        info.get("shard_id").asLong()
                    )
                    "NOT_SYNCED_YET" -> NotSyncedException(info)
                    "INVALID_ACCOUNT" -> InvalidAccountException(
                        info.get("requested_account_id").asText(),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNKNOWN_ACCOUNT" -> UnknownAccountException(
                        info.get("requested_account_id").asText(),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNKNOWN_ACCESS_KEY" -> UnknownAccessKeyException(
                        PublicKey(info.get("public_key").asText()),
                        info.get("block_height").asLong(),
                        info.get("block_hash").asText()
                    )
                    "UNAVAILABLE_SHARD" -> UnavailableShardException(info)
                    "NO_SYNCED_BLOCKS" -> NoSyncedBlocksException(info)
                    "INVALID_TRANSACTION" -> {
                        val txError =
                            objectMapper.treeToValue(info.get("TxExecutionError"), TxExecutionError::class.java)
                        InvalidTransactionException(txExecutionError = txError)
                    }
                    "TIMEOUT_ERROR" -> TimeoutErrorException(info)
                    "UNKNOWN_EPOCH" -> UnknownEpochException(info)
                    else -> ProviderException(error, cause)
                }
                "REQUEST_VALIDATION_ERROR" -> when (cause) {
                    "PARSE_ERROR" -> ParseErrorException(
                        info.get("error_message").asText()
                    )
                    else -> ProviderException(error, cause)
                }
                "INTERNAL_ERROR" -> when (cause) {
                    "INTERNAL_ERROR" -> InternalErrorException(
                        info.get("error_message").asText()
                    )
                    else -> ProviderException(error, cause)
                }
                else -> ProviderException(error, cause)
            }
        } catch (e: Throwable) {
            ProviderException(error, cause, e)
        }
    }
}
