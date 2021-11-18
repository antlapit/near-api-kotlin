package antlapit.near.api.deser

import antlapit.near.api.providers.util.RustSinglePropertyEnumItem
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure


class RustEnumDeserializer<T : Any>(private val kClass: KClass<out T>) : JsonDeserializer<T>() {

    private val subclasses: HashMap<String, KClass<out T>> = HashMap()

    init {
        kClass.sealedSubclasses
            .forEach { subclasses[it.simpleName!!] = it }
    }

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): T {
        p!!
        ctxt!!
        return when (val t = p.currentToken) {
            JsonToken.VALUE_STRING -> getEnumItemClass(p, p.valueAsString).objectInstance!!
            JsonToken.START_OBJECT -> {
                // should always get field name, but just in case...
                if (p.nextToken() != JsonToken.FIELD_NAME) {
                    ctxt.reportWrongTokenException(
                        baseType(), JsonToken.FIELD_NAME,
                        "need JSON String that contains type id (for subtype of ${baseTypeName()})"
                    )
                }

                val typeId = p.text!!
                val subclass = getEnumItemClass(p, typeId)
                p.nextToken()


                val ann = subclass.annotations.find { it is RustSinglePropertyEnumItem }
                val deser = if (ann == null) {
                    findDeserializer(ctxt, subclass)
                } else {
                    val primaryConstructorParams = subclass.primaryConstructor!!.parameters
                    if (primaryConstructorParams.size != 1) {
                        throw RuntimeException("Class with RustSinglePropertyEnumItem annotation should have primary constructor with single parameter")
                    }
                    findDeserializer(ctxt, primaryConstructorParams[0].type.jvmErasure)
                }
                val value: T = deser.deserialize(p, ctxt) as T
                // And then need the closing END_OBJECT
                if (p.nextToken() != JsonToken.END_OBJECT && p.nextToken() != JsonToken.END_OBJECT) {
                    ctxt.reportWrongTokenException(
                        baseType(), JsonToken.END_OBJECT,
                        "expected closing END_OBJECT after type information and deserialized value"
                    )
                }
                if (ann == null) {
                    value
                } else {
                    subclass.primaryConstructor!!.call(value)
                }
            }
            else -> {
                throw JsonParseException(p, "Unexpected JSON token \"$t\" on deserializing class ${baseTypeName()}")
            }
        }
    }

    private fun baseTypeName() = kClass.simpleName

    private fun baseType() = kClass::class.java

    private fun findDeserializer(ctxt: DeserializationContext, subclass: KClass<out Any>): JsonDeserializer<Any> {
        val javaType = ctxt.constructType(subclass.java)
        return ctxt.findRootValueDeserializer(javaType)
    }

    private fun getEnumItemClass(p: JsonParser, typeId: String): KClass<out T> {
        if (subclasses[typeId] == null) {
            throw JsonParseException(
                p,
                "Unexpected enum value \"$typeId\" for class ${kClass.simpleName}"
            )
        }
        return subclasses[typeId]!!
    }
}
