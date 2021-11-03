package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

/**
 * [UInt] serializer
 */
class UIntSerializer
/**
 * Constructor
 */
private constructor() : StdSerializer<UInt>(UInt::class.java) {
    @Throws(IOException::class)
    override fun serialize(uInt: UInt, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeRaw(uInt.toString())
    }

    companion object {
        private const val serialVersionUID = 1L

        /**
         * Singleton instance
         */
        val INSTANCE = UIntSerializer()
    }
}
