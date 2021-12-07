package antlapit.near.api.json

import antlapit.near.api.providers.model.account.*
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class ContractDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Account") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                    {
                        "amount":"99998750632125700200000000",
                        "locked":"0",
                        "code_hash":"11111111111111111111111111111111",
                        "storage_usage":468,
                        "storage_paid_at":0,
                        "block_height":74055091,
                        "block_hash":"5R1zy2VSPnmkgt5AvkTTnyFcNLArcUgYuDqAy3GZG7kU"
                    }
                    """,
                    Account(
                        amount = BigInteger("99998750632125700200000000"),
                        locked = BigInteger.ZERO,
                        codeHash = "11111111111111111111111111111111",
                        storageUsage = 468,
                        storagePaidAt = 0,
                        blockHeight = 74055091,
                        blockHash = "5R1zy2VSPnmkgt5AvkTTnyFcNLArcUgYuDqAy3GZG7kU"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as Account shouldBe b
            }

        }
    }

    context("Contract code") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                    {
                        "code_base64":"AG",
                        "hash":"AFQCuhpYeMVvz6quXtNzSFhg2nPNkzP3yk5G4KtkQYn6",
                        "block_height":74055562,
                        "block_hash":"7ho4hhWACSKyffnxn43qjEsNzFXSkPoCYFpgVReSAUsC"
                    }
                    """,
                    ContractCode(
                        codeBase64 = "AG",
                        hash = "AFQCuhpYeMVvz6quXtNzSFhg2nPNkzP3yk5G4KtkQYn6",
                        blockHeight = 74055562,
                        blockHash = "7ho4hhWACSKyffnxn43qjEsNzFXSkPoCYFpgVReSAUsC"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as ContractCode shouldBe b
            }

        }
    }

    context("Contract state") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                    {
                        "values":[{
                            "key": "key",
                            "value": "value",
                            "proof": ["test"]
                        }],
                        "proof":["test"],
                        "block_height":74055920,
                        "block_hash":"GGZWQE2cXnxbKAMypEJ984B88NpDH93f3VFSNd4i1UFD"
                    }
                    """,
                    ContractState(
                        values = listOf(
                            ContractStateItem(
                                key = "key",
                                value = "value",
                                proof = listOf("test")
                            )
                        ),
                        proof = listOf("test"),
                        blockHeight = 74055920,
                        blockHash = "GGZWQE2cXnxbKAMypEJ984B88NpDH93f3VFSNd4i1UFD"
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as ContractState shouldBe b
            }

        }
    }

    context("Call result") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """
                    {
                        "result":[34,123,92,34,97,99,99,111,117,110,116,73,100,92,34,58,92,34,97,112,105,95,107,111,116,108,105,110,46,116,101,115,116,110,101,116,92,34,125,34],
                        "logs":[],
                        "block_height":74056185,
                        "block_hash":"FXDS3AfDiBenpAnx1oTy5u7k6y5iMKnWKkq8CjXDFn6w"
                    }
                """,
                CallResult(
                    result = "\"{\\\"accountId\\\":\\\"api_kotlin.testnet\\\"}\"".toByteArray().asList(),
                    blockHeight = 74056185,
                    blockHash = "FXDS3AfDiBenpAnx1oTy5u7k6y5iMKnWKkq8CjXDFn6w"
                )
            ),
            TestData(
                """
                    {
                        "result":[],
                        "logs":["something wrong"],
                        "error": "error message",
                        "block_height":74056185,
                        "block_hash":"FXDS3AfDiBenpAnx1oTy5u7k6y5iMKnWKkq8CjXDFn6w"
                    }
                """,
                CallResult(
                    logs = listOf("something wrong"),
                    error = "error message",
                    blockHeight = 74056185,
                    blockHash = "FXDS3AfDiBenpAnx1oTy5u7k6y5iMKnWKkq8CjXDFn6w"
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as CallResult shouldBe b
            }
        }
    }
})
