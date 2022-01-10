package antlapit.near.api.json

import antlapit.near.api.providers.model.rust.RustEnum
import antlapit.near.api.providers.model.rust.RustSinglePropertyEnumItem
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure


class RustEnumDeserializer<T : Any>(private val kClass: KClass<out T>) : JsonDeserializer<T>() {

    private val subclasses: HashMap<String, KClass<out T>> = HashMap()
    private val deserializers: HashMap<KClass<*>, JsonDeserializer<Any>> = HashMap()

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
                val res = if (ann == null) {
                    val deser = findDeserializer(ctxt, subclass)
                    deser.deserialize(p, ctxt) as T
                } else {
                    val primaryConstructorParams = subclass.primaryConstructor!!.parameters
                    if (primaryConstructorParams.size != 1) {
                        throw RuntimeException("Class with RustSinglePropertyEnumItem annotation should have primary constructor with single parameter")
                    }
                    val deser = findDeserializer(ctxt, primaryConstructorParams[0].type.jvmErasure)
                    val value = deser.deserialize(p, ctxt)
                    subclass.primaryConstructor!!.call(value)
                }
                // And then need the closing END_OBJECT
                if (p.nextToken() != JsonToken.END_OBJECT && p.nextToken() != JsonToken.END_OBJECT) {
                    ctxt.reportWrongTokenException(
                        baseType(), JsonToken.END_OBJECT,
                        "expected closing END_OBJECT after type information and deserialized value"
                    )
                }
                res
            }
            else -> {
                throw JsonParseException(p, "Unexpected JSON token \"$t\" on deserializing class ${baseTypeName()}")
            }
        }
    }

    private fun baseTypeName() = kClass.simpleName

    private fun baseType() = kClass::class.java

    private fun findDeserializer(ctxt: DeserializationContext, subclass: KClass<*>): JsonDeserializer<Any> {
        return deserializers.getOrPut(subclass) {
            val javaType = ctxt.constructType(subclass.java)
            return ctxt.findRootValueDeserializer(javaType)
        }
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

class RustEnumSerializerModifier : BeanSerializerModifier() {
    override fun modifySerializer(
        config: SerializationConfig?,
        beanDesc: BeanDescription?,
        serializer: JsonSerializer<*>?
    ): JsonSerializer<*>? {
        serializer!!
        val kClass = beanDesc!!.beanClass.kotlin
        val isRustEnum = kClass.allSuperclasses
            .flatMap { it.annotations }
            .find { it is RustEnum } != null
        return if (isRustEnum) {
            RustEnumSerializer(serializer as JsonSerializer<Any>)
        } else {
            super.modifySerializer(config, beanDesc, serializer)
        }
    }
}

class RustEnumSerializer(private val beanSerializer: JsonSerializer<Any>) : JsonSerializer<Any>() {
    override fun serialize(value: Any, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen!!
        serializers!!
        val kClass = value::class
        when {
            kClass.objectInstance != null -> gen.writeString(kClass.simpleName!!)
            else -> {
                val isSinglePropertyEnumItem = kClass.annotations.find { it is RustSinglePropertyEnumItem } != null
                gen.writeStartObject()
                gen.writeFieldName(kClass.simpleName!!)
                if (isSinglePropertyEnumItem && beanSerializer.properties().hasNext()) {
                    val writer = beanSerializer.properties().next()
                    writer.serializeAsElement(value, gen, serializers)
                } else {
                    beanSerializer.serialize(value, gen, serializers)
                }
                gen.writeEndObject()
            }
        }
    }

}
