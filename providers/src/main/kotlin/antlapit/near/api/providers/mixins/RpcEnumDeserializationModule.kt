package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.accesskey.NotParametrizedAccessKeyPermission
import antlapit.near.api.providers.model.accesskey.ParametrizedAccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.NotParametrizedAction
import antlapit.near.api.providers.model.block.ParametrizedAction
import antlapit.near.api.providers.model.block.ReceiptInfo
import antlapit.near.api.providers.model.transaction.*
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
        ctx.setMixInAnnotations(ReceiptInfo::class.java, ReceiptInfoMixin::class.java)
        ctx.setMixInAnnotations(ParametrizedAction::class.java, ParametrizedActionMixin::class.java)
        ctx.setMixInAnnotations(
            ParametrizedAccessKeyPermission::class.java,
            ParametrizedAccessKeyPermissionMixin::class.java
        )
        ctx.setMixInAnnotations(
            SuccessValue::class.java, SuccessValueMixin::class.java
        )
        ctx.setMixInAnnotations(
            SuccessReceiptId::class.java, SuccessReceiptIdMixin::class.java
        )
        ctx.setMixInAnnotations(
            Failure::class.java, FailureMixin::class.java
        )
        ctx.setMixInAnnotations(
            TxExecutionError::class.java, TxExecutionErrorMixin::class.java
        )
        ctx.setMixInAnnotations(
            ActionErrorKind::class.java, ActionErrorKindMixin::class.java
        )
        ctx.setMixInAnnotations(
            ParamExecutionStatus::class.java, ParamExecutionStatusMixin::class.java
        )
        ctx.setMixInAnnotations(
            ParamFinalExecutionStatus::class.java, ParamFinalExecutionStatusMixin::class.java
        )

        ctx.addDeserializers(object : SimpleDeserializers() {
            init {
                addDeserializer(
                    Action::class.java, RustEnumDeserializer(ParametrizedAction::class, NotParametrizedAction::class)
                )
                addDeserializer(
                    AccessKeyPermission::class.java,
                    RustEnumDeserializer(
                        ParametrizedAccessKeyPermission::class,
                        NotParametrizedAccessKeyPermission::class
                    )
                )
                addDeserializer(
                    ExecutionStatus::class.java,
                    RustEnumDeserializer(ParamExecutionStatus::class, SimpleExecutionStatus::class)
                )
                addDeserializer(
                    FinalExecutionStatus::class.java,
                    RustEnumDeserializer(ParamFinalExecutionStatus::class, SimpleFinalExecutionStatus::class)
                )
            }
        })
    }
}
