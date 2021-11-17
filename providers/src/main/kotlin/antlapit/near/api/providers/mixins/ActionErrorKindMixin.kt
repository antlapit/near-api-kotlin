package antlapit.near.api.providers.mixins

import antlapit.near.api.providers.primitives.*
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes(
    JsonSubTypes.Type(value = AccountAlreadyExists::class, name = "AccountAlreadyExists"),
    JsonSubTypes.Type(value = AccountDoesNotExist::class, name = "AccountDoesNotExist"),
    JsonSubTypes.Type(value = CreateAccountOnlyByRegistrar::class, name = "CreateAccountOnlyByRegistrar"),
    JsonSubTypes.Type(value = CreateAccountNotAllowed::class, name = "CreateAccountNotAllowed"),
    JsonSubTypes.Type(value = ActorNoPermission::class, name = "ActorNoPermission"),
    JsonSubTypes.Type(value = DeleteKeyDoesNotExist::class, name = "DeleteKeyDoesNotExist"),
    JsonSubTypes.Type(value = AddKeyAlreadyExists::class, name = "AddKeyAlreadyExists"),
    JsonSubTypes.Type(value = DeleteAccountStaking::class, name = "DeleteAccountStaking"),
    JsonSubTypes.Type(value = LackBalanceForState::class, name = "LackBalanceForState"),
    JsonSubTypes.Type(value = TriesToUnstake::class, name = "TriesToUnstake"),
    JsonSubTypes.Type(value = TriesToStake::class, name = "TriesToStake"),
    JsonSubTypes.Type(value = InsufficientStake::class, name = "InsufficientStake"),
    JsonSubTypes.Type(value = FunctionCallError::class, name = "FunctionCallError"),
    JsonSubTypes.Type(value = NewReceiptValidationError::class, name = "NewReceiptValidationError"),
    JsonSubTypes.Type(value = OnlyImplicitAccountCreationAllowed::class, name = "OnlyImplicitAccountCreationAllowed"),
    JsonSubTypes.Type(value = DeleteAccountWithLargeState::class, name = "DeleteAccountWithLargeState")
)
internal abstract class ActionErrorKindMixin
