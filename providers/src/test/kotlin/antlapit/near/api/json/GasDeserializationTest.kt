package antlapit.near.api.json

import antlapit.near.api.providers.model.gas.GasPrice
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class GasDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Gas") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                    {
                        "gas_price": "99998750632125700200000000"
                    }
                    """,
                    GasPrice(
                        gasPrice =  BigInteger("99998750632125700200000000"),
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as GasPrice shouldBe b
            }

        }
    }
})
