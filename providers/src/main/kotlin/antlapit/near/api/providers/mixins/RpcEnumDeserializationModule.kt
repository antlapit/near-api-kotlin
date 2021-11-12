package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.accesskey.NotParametrizedAccessKeyPermission
import antlapit.near.api.providers.model.accesskey.ParametrizedAccessKeyPermission
import antlapit.near.api.providers.model.block.Action
import antlapit.near.api.providers.model.block.NotParametrizedAction
import antlapit.near.api.providers.model.block.ParametrizedAction
import antlapit.near.api.providers.model.block.ReceiptInfo
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

    override fun setupModule(context: SetupContext?) {
        context!!.setMixInAnnotations(ReceiptInfo::class.java, ReceiptInfoMixin::class.java)
        context.setMixInAnnotations(ParametrizedAction::class.java, ParametrizedActionMixin::class.java)
        context.setMixInAnnotations(
            ParametrizedAccessKeyPermission::class.java,
            ParametrizedAccessKeyPermissionMixin::class.java
        )
        context.addDeserializers(object : SimpleDeserializers() {
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
            }
        })
    }
}
