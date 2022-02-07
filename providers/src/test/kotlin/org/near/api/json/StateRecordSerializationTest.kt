package org.near.api.json

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.endpoints.SandboxRpcEndpoint
import org.near.api.model.accesskey.AccessKey
import org.near.api.model.accesskey.AccessKeyPermission
import org.near.api.model.account.Account
import org.near.api.model.changes.StateRecord
import org.near.api.model.primitives.Balance
import org.near.api.model.primitives.PublicKey

/**
 * Access key change container deserialization test
 */
@ExperimentalKotest
class StateRecordSerializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("State Records") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"records":[{"Contract":{"account_id":"abcdef.test.near","code":"47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="}}]}""",
                SandboxRpcEndpoint.StateRecordsWrapper(
                    records = listOf(
                        StateRecord.Contract(
                            accountId = "abcdef.test.near",
                            code = "47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="
                        )
                    )
                )
            ),
            TestData(
                """{"Contract":{"account_id":"abcdef.test.near","code":"47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="}}""",
                StateRecord.Contract(
                    accountId = "abcdef.test.near",
                    code = "47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU="
                )
            ),
            TestData(
                """{"AccessKey":{"account_id":"abcdef.test.near","public_key":"ed25519:CngrirkGDwSS75EKczcsUsciRtMmHd9iicrrYxz4uckD","access_key":{"nonce":0,"permission":"FullAccess"}}}""",
                StateRecord.AccessKey(
                    accountId = "abcdef.test.near",
                    publicKey = PublicKey("ed25519:CngrirkGDwSS75EKczcsUsciRtMmHd9iicrrYxz4uckD"),
                    accessKey = AccessKey(
                        nonce = 0,
                        permission = AccessKeyPermission.FullAccess
                    )
                )
            ),
            TestData(
                """{"Data":{"account_id":"abcdef.test.near","data_key":"U1RBVEU=","value":"AwAAAA8AAABhbGljZS50ZXN0Lm5lYXIFAAAAaGVsbG8NAAAAYm9iLnRlc3QubmVhcgUAAAB3b3JsZAoAAABhbGljZS5uZWFyCwAAAGhlbGxvIHdvcmxk"}}""",
                StateRecord.Data(
                    accountId = "abcdef.test.near",
                    dataKey = "U1RBVEU=",
                    value = "AwAAAA8AAABhbGljZS50ZXN0Lm5lYXIFAAAAaGVsbG8NAAAAYm9iLnRlc3QubmVhcgUAAAB3b3JsZAoAAABhbGljZS5uZWFyCwAAAGhlbGxvIHdvcmxk"
                )
            ),
            TestData(
                """{"Account":{"account_id":"abcdef.test.near","account":{"amount":"100000000000","locked":"0","code_hash":"7KoFshMQkdyo5iTx8P2LbLu9jQpxRn24d27FrKShNVXs","storage_usage":200000,"storage_paid_at":0}}}""",
                StateRecord.Account(
                    accountId = "abcdef.test.near",
                    account = Account(
                        amount = Balance.valueOf(100000000000),
                        locked = Balance.ZERO,
                        codeHash = "7KoFshMQkdyo5iTx8P2LbLu9jQpxRn24d27FrKShNVXs",
                        storageUsage = 200000,
                        storagePaidAt = 0
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.writeValueAsString(b) shouldBe a
            }
        }
    }
})
