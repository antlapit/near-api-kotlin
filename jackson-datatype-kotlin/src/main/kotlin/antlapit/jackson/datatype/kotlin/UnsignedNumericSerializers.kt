package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.databind.BeanDescription
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializationConfig
import com.fasterxml.jackson.databind.ser.Serializers
import java.io.Serializable

class UnsignedNumericSerializers : Serializers.Base(), Serializable {

    override fun findSerializer(
        config: SerializationConfig, type: JavaType,
        beanDesc: BeanDescription?
    ): JsonSerializer<*>? {
        val raw = type.rawClass
        if (ULong::class.java.isAssignableFrom(raw)) {
            return ULongSerializer.INSTANCE
        }
        if (UInt::class.java.isAssignableFrom(raw)) {
            return UIntSerializer.INSTANCE
        }
        return null
    }
}
