package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.block.*
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = DeployContract::class, name = "DeployContract"),
    JsonSubTypes.Type(value = FunctionCall::class, name = "FunctionCall"),
    JsonSubTypes.Type(value = Transfer::class, name = "Transfer"),
    JsonSubTypes.Type(value = Stake::class, name = "Stake"),
    JsonSubTypes.Type(value = AddKey::class, name = "AddKey"),
    JsonSubTypes.Type(value = DeleteKey::class, name = "DeleteKey"),
    JsonSubTypes.Type(value = DeleteAccount::class, name = "DeleteAccount"),
    JsonSubTypes.Type(value = StakeChunkOnly::class, name = "StakeChunkOnly"),
)
internal abstract class ParametrizedActionMixin
