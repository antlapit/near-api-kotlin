package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.io.IOException

/**
 * [ULong] serializer
 */
class ULongSerializer
/**
 * Constructor
 */
private constructor() : StdSerializer<ULong>(ULong::class.java) {
    @Throws(IOException::class)
    override fun serialize(uLong: ULong, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeRaw(uLong.toString())
    }

    companion object {
        private const val serialVersionUID = 1L

        /**
         * Singleton instance
         */
        val INSTANCE = ULongSerializer()
    }
}
