package antlapit.near.api.deser

import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.ReceiptInfo
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import antlapit.near.api.providers.model.transaction.FinalExecutionStatus
import antlapit.near.api.providers.primitives.ActionErrorKind
import antlapit.near.api.providers.primitives.TxExecutionError
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleDeserializers


class RpcEnumDeserializationModule : Module() {
    override fun version(): Version {
        return Version(0, 0, 1, "NONE", null, null)
    }

    override fun getModuleName(): String {
        return "RpcEnumDeserialization"
    }

    override fun setupModule(ctx: SetupContext?) {
        ctx!!
        ctx.addDeserializers(object : SimpleDeserializers() {
            init {

                addDeserializer(Action::class.java, RustEnumDeserializer(Action::class))
                addDeserializer(ActionErrorKind::class.java, RustEnumDeserializer(ActionErrorKind::class))
                addDeserializer(ReceiptInfo::class.java, RustEnumDeserializer(ReceiptInfo::class))
                addDeserializer(AccessKeyPermission::class.java, RustEnumDeserializer(AccessKeyPermission::class))
                addDeserializer(TxExecutionError::class.java, RustEnumDeserializer(TxExecutionError::class))
                addDeserializer(
                    ExecutionStatus::class.java,
                    RustEnumDeserializer(ExecutionStatus::class)
                )
                addDeserializer(
                    FinalExecutionStatus::class.java,
                    RustEnumDeserializer(FinalExecutionStatus::class)
                )
            }
        })
    }
}
