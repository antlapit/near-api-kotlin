package antlapit.jackson.datatype.kotlin

import com.fasterxml.jackson.core.util.VersionUtil
import com.fasterxml.jackson.databind.Module

class UnsignedNumericModule : Module() {
    override fun version() = VersionUtil.parseVersion(
        "1.0.0", "antlapit.jackson.datatype", "jackson-datatype-kotlin"
    )

    override fun getModuleName() = "UnsignedNumericModule"

    override fun setupModule(context: SetupContext?) {
        context!!.addSerializers(UnsignedNumericSerializers())
        context.addDeserializers(UnsignedNumericDeserializers())
    }
}
