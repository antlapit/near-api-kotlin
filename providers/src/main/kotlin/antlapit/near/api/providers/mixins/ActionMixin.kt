package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.model.block.*
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = CreateAccountAction::class, name = "CreateAccount"),
    JsonSubTypes.Type(value = DeployContractAction::class, name = "DeployContract"),
    JsonSubTypes.Type(value = FunctionCallAction::class, name = "FunctionCall"),
    JsonSubTypes.Type(value = TransferAction::class, name = "Transfer"),
    JsonSubTypes.Type(value = StakeAction::class, name = "Stake"),
    JsonSubTypes.Type(value = AddKeyAction::class, name = "AddKey"),
    JsonSubTypes.Type(value = DeleteKeyAction::class, name = "DeleteKey"),
    JsonSubTypes.Type(value = DeleteAccountAction::class, name = "DeleteAccount"),
    JsonSubTypes.Type(value = StakeChunkOnlyAction::class, name = "StakeChunkOnly"),
)
internal abstract class ActionMixin
