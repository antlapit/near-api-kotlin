package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.DeserializationConfig
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.Deserializers
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer
import com.fasterxml.jackson.databind.type.ReferenceType
import java.io.Serializable

class UnsignedNumericDeserializers : Deserializers.Base(), Serializable {

    private val serialVersionUID = 1L

    // since 2.7
    override fun findReferenceDeserializer(
        refType: ReferenceType,
        config: DeserializationConfig?, beanDesc: BeanDescription?,
        contentTypeDeserializer: TypeDeserializer?, contentDeserializer: JsonDeserializer<*>?
    ): JsonDeserializer<*>? {
        return when {
            refType.hasRawClass(ULong::class.java) -> {
                ULongDeserializer.INSTANCE
            }
            refType.hasRawClass(UInt::class.java) -> {
                UIntDeserializer.INSTANCE
            }
            else -> null
        }
    }
}
