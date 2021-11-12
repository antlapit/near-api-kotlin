package antlapit.near.api.providers.mixins

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import kotlin.reflect.KClass


class RustEnumDeserializer<T : Any>(
    private val parametrizedClass: KClass<out T>,
    private val enumClass: KClass<out T>
) : JsonDeserializer<T>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): T {
        val jsonToken = p!!.currentToken
        val javaType = if (jsonToken == JsonToken.VALUE_STRING) {
            ctxt!!.constructType(enumClass.java)
        } else {
            ctxt!!.constructType(parametrizedClass.java)
        }
        val deser = ctxt.findRootValueDeserializer(javaType)
        return deser.deserialize(p, ctxt) as T
    }
}
