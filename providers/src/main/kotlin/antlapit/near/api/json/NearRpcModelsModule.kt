package antlapit.near.api.json

import antlapit.near.api.providers.camelCaseToSnakeCase
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.ReceiptInfo
import antlapit.near.api.providers.model.changes.AccessKeyChange
import antlapit.near.api.providers.model.changes.StateChange
import antlapit.near.api.providers.model.changes.StateChangeCause
import antlapit.near.api.providers.model.primitives.*
import antlapit.near.api.providers.model.transaction.ExecutionStatus
import antlapit.near.api.providers.model.transaction.FinalExecutionStatus
import antlapit.near.api.providers.model.validators.ValidatorKickoutReason
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.jsontype.NamedType
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleSerializers


class NearRpcModelsModule : Module() {
    override fun version(): Version {
        return Version(0, 0, 1, "NONE", null, null)
    }

    override fun getModuleName(): String {
        return "NearRpcModels"
    }

    override fun setupModule(ctx: SetupContext?) {
        ctx!!
        ctx.addSerializers(object : SimpleSerializers() {
            init {
                addSerializer(PublicKey::class.java, PublicKeySerializer())
            }
        })

        ctx.addBeanSerializerModifier(RustEnumSerializerModifier())

        ctx.addDeserializers(object : SimpleDeserializers() {
            init {
                // public key
                addDeserializer(PublicKey::class.java, PublicKeyDeserializer())

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

        ctx.setMixInAnnotations(AccessKeyChange::class.java, AccessKeyChangeMixin::class.java)
        ctx.setMixInAnnotations(StateChangeCause::class.java, StateChangeCauseMixin::class.java)

        val subTypes = ArrayList<NamedType>()
        for (sealedSubclass in StateChange::class.sealedSubclasses) {
            val type = NamedType(sealedSubclass.java, sealedSubclass.simpleName!!.camelCaseToSnakeCase())
            subTypes.add(type)
        }
        for (sealedSubclass in StateChangeCause::class.sealedSubclasses) {
            val type = NamedType(sealedSubclass.java, sealedSubclass.simpleName!!.camelCaseToSnakeCase())
            subTypes.add(type)
        }
        ctx.registerSubtypes(*subTypes.toTypedArray())
    }
}
