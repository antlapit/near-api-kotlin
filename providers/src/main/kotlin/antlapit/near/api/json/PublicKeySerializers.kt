package antlapit.near.api.json

import antlapit.near.api.providers.model.primitives.PublicKey
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.FromStringDeserializer
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase

class PublicKeySerializer : ToStringSerializerBase(PublicKey::class.java) {
    override fun valueToString(value: Any?): String {
        return if (value == null) "" else (value as PublicKey).asString()
    }
}

class PublicKeyDeserializer : FromStringDeserializer<PublicKey>(PublicKey::class.java) {
    override fun _deserialize(value: String?, ctxt: DeserializationContext?): PublicKey? {
        return if (value == null) null else PublicKey(value)
    }
}
