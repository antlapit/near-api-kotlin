package org.near.api.json

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.jsontype.NamedType
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleSerializers
import org.near.api.camelCaseToSnakeCase
import org.near.api.model.accesskey.AccessKeyPermission
import org.near.api.model.block.Action
import org.near.api.model.block.ReceiptInfo
import org.near.api.model.changes.StateChange
import org.near.api.model.changes.StateChangeCause
import org.near.api.model.changes.StateChangeKind
import org.near.api.model.changes.StateChangeType
import org.near.api.model.config.Rational
import org.near.api.model.config.ShardLayout
import org.near.api.model.primitives.*
import org.near.api.model.transaction.ExecutionStatus
import org.near.api.model.transaction.FinalExecutionStatus
import org.near.api.model.validators.ValidatorKickoutReason
import java.math.BigInteger


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
                addDeserializer(
                    InvalidAccessKeyErrorType::class.java,
                    RustEnumDeserializer(InvalidAccessKeyErrorType::class)
                )
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
                addDeserializer(
                    ShardLayout::class.java,
                    RustEnumDeserializer(ShardLayout::class)
                )
            }
        })

        ctx.setMixInAnnotations(StateChange::class.java, SingleStateChangeMixin::class.java)
        ctx.setMixInAnnotations(StateChangeCause::class.java, StateChangeCauseMixin::class.java)
        ctx.setMixInAnnotations(StateChangeKind::class.java, StateChangeKindMixin::class.java)
        ctx.setMixInAnnotations(Rational::class.java, RationalMixin::class.java)

        val subTypes = ArrayList<NamedType>()
        for (sealedSubclass in StateChangeType::class.sealedSubclasses) {
            val type = NamedType(sealedSubclass.java, sealedSubclass.simpleName!!.camelCaseToSnakeCase())
            subTypes.add(type)
        }
        for (sealedSubclass in StateChangeCause::class.sealedSubclasses) {
            val type = NamedType(sealedSubclass.java, sealedSubclass.simpleName!!.camelCaseToSnakeCase())
            subTypes.add(type)
        }
        for (sealedSubclass in StateChangeKind::class.sealedSubclasses) {
            val type = NamedType(sealedSubclass.java, sealedSubclass.simpleName!!.camelCaseToSnakeCase())
            subTypes.add(type)
        }
        ctx.registerSubtypes(*subTypes.toTypedArray())

        ctx.configOverride(BigInteger::class.java).format = JsonFormat.Value.forShape(JsonFormat.Shape.STRING)
    }
}
