package antlapit.near.api.json

import antlapit.near.api.providers.base.RustEnumDeserializer
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.ReceiptInfo
import antlapit.near.api.providers.model.primitives.*
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import antlapit.near.api.providers.model.transaction.FinalExecutionStatus
import antlapit.near.api.providers.model.validators.ValidatorKickoutReason
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleDeserializers


class RustEnumDeserializationModule : Module() {
    override fun version(): Version {
        return Version(0, 0, 1, "NONE", null, null)
    }

    override fun getModuleName(): String {
        return "RustEnumDeserialization"
    }

    override fun setupModule(ctx: SetupContext?) {
        ctx!!
        ctx.addDeserializers(object : SimpleDeserializers() {
            init {
                // access key
                addDeserializer(AccessKeyPermission::class.java, RustEnumDeserializer(AccessKeyPermission::class))

                // block
                addDeserializer(Action::class.java, RustEnumDeserializer(Action::class))
                addDeserializer(ReceiptInfo::class.java, RustEnumDeserializer(ReceiptInfo::class))

                // errors
                addDeserializer(ActionErrorKind::class.java, RustEnumDeserializer(ActionErrorKind::class))
                addDeserializer(ActionsValidationError::class.java, RustEnumDeserializer(ActionsValidationError::class))
                addDeserializer(CompilationErrorType::class.java, RustEnumDeserializer(CompilationErrorType::class))
                addDeserializer(ContractCallError::class.java, RustEnumDeserializer(ContractCallError::class))
                addDeserializer(InvalidAccessKeyErrorType::class.java, RustEnumDeserializer(InvalidAccessKeyErrorType::class))
                addDeserializer(InvalidTxError::class.java, RustEnumDeserializer(InvalidTxError::class))
                addDeserializer(ReceiptValidationError::class.java, RustEnumDeserializer(ReceiptValidationError::class))
                addDeserializer(TxExecutionError::class.java, RustEnumDeserializer(TxExecutionError::class))
                addDeserializer(HostErrorType::class.java, RustEnumDeserializer(HostErrorType::class))

                // transaction
                addDeserializer(
                    ExecutionStatus::class.java,
                    RustEnumDeserializer(ExecutionStatus::class)
                )
                addDeserializer(
                    FinalExecutionStatus::class.java,
                    RustEnumDeserializer(FinalExecutionStatus::class)
                )

                // validators
                addDeserializer(
                    ValidatorKickoutReason::class.java,
                    RustEnumDeserializer(ValidatorKickoutReason::class)
                )
            }
        })
    }
}
