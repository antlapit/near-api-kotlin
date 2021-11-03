package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonTokenId
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.cfg.CoercionAction
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.type.LogicalType
import java.io.IOException

class UIntDeserializer : StdScalarDeserializer<UInt?>(UInt::class.java) {
    override fun logicalType(): LogicalType {
        return LogicalType.Integer
    }

    @Throws(IOException::class)
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): UInt? {
        if (p.isExpectedNumberIntToken) {
            return p.bigIntegerValue.toString().toUInt()
        }
        var text: String = when (p.currentTokenId()) {
            JsonTokenId.ID_STRING -> p.text
            JsonTokenId.ID_NUMBER_FLOAT -> {
                val act = _checkFloatToIntCoercion(p, ctxt, _valueClass)
                if (act == CoercionAction.AsNull) {
                    return getNullValue(ctxt)
                }
                return if (act == CoercionAction.AsEmpty) {
                    getEmptyValue(ctxt) as UInt
                } else p.decimalValue.toBigInteger().toString().toUInt()
            }
            JsonTokenId.ID_START_OBJECT -> ctxt.extractScalarFromObject(p, this, _valueClass)
            JsonTokenId.ID_START_ARRAY -> return _deserializeFromArray(p, ctxt)
            else ->                 // String is ok too, can easily convert; otherwise, no can do:
                return ctxt.handleUnexpectedToken(getValueType(ctxt), p) as UInt
        }
        val act = _checkFromStringCoercion(ctxt, text)
        if (act == CoercionAction.AsNull) {
            return getNullValue(ctxt)
        }
        if (act == CoercionAction.AsEmpty) {
            return getEmptyValue(ctxt) as UInt
        }
        text = text.trim { it <= ' ' }
        if (_hasTextualNull(text)) {
            // note: no need to call `coerce` as this is never primitive
            return getNullValue(ctxt)
        }
        try {
            return text.toUInt()
        } catch (iae: IllegalArgumentException) {
        }
        return ctxt.handleWeirdStringValue(
            _valueClass, text,
            "not a valid representation"
        ) as UInt
    }

    companion object {
        private const val serialVersionUID = 1L
        val INSTANCE = UIntDeserializer()
    }
}
