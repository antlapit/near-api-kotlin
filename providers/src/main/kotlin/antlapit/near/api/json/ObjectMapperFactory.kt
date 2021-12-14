package antlapit.near.api.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder

/**
 * Factory for instantiating new jackson ObjectMapper with all properties required for deserializing NEAR JSON RPC structures
 */
class ObjectMapperFactory {

    companion object {
        fun newInstance(): ObjectMapper {
            return jacksonMapperBuilder()
                .addModule(JavaTimeModule())
                .addModule(Jdk8Module())
                .addModule(KotlinModule())
                .addModule(NearRpcModelsModule())
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build()
        }
    }
}
