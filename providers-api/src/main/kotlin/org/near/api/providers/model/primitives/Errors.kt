package org.near.api.providers.model.primitives

import org.near.api.providers.model.rust.RustEnum
import org.near.api.providers.model.rust.RustSinglePropertyEnumItem

/**
 * The execution has failed.
 **/
@RustEnum
sealed interface TxExecutionError {

    data class ActionError(
        val index: Long?,
        val kind: ActionErrorKind
    ) : TxExecutionError

}

@RustEnum
sealed interface InvalidTxError : TxExecutionError {
    /// Happens if a wrong AccessKey used or AccessKey has not enough permissions
    @RustSinglePropertyEnumItem
    data class InvalidAccessKeyError(val error: InvalidAccessKeyErrorType) : InvalidTxError

    /// TX signer_id is not a valid [`AccountId`]
    data class InvalidSignerId(val signerId: String) : InvalidTxError

    /// TX signer_id is not found in a storage
    data class SignerDoesNotExist(val signerId: AccountId) : InvalidTxError

    /// Transaction nonce must be `account[access_key].nonce + 1`.
    data class InvalidNonce(val txNonce: Nonce, val akNonce: Nonce) : InvalidTxError

    /// Transaction nonce is larger than the upper bound given by the block height
    data class NonceTooLarge(val txNonce: Nonce, val upperBound: Nonce) : InvalidTxError

    /// TX receiver_id is not a valid AccountId
    data class InvalidReceiverId(val receiverId: String) : InvalidTxError

    /// TX signature is not valid
    object InvalidSignature : InvalidTxError

    /// Account does not have enough balance to cover TX cost
    data class NotEnoughBalance(
        val signerId: AccountId,
        val balance: Balance,
        val cost: Balance
    ) : InvalidTxError

    /// Signer account doesn't have enough balance after transaction.
    data class LackBalanceForState(
        /// An account which doesn't have enough balance to cover storage.
        val signerId: AccountId,
        /// Required balance to cover the state.
        val amount: Balance
    ) : InvalidTxError

    /// An integer overflow occurred during transaction cost estimation.
    object CostOverflow : InvalidTxError

    /// Transaction parent block hash doesn't belong to the current chain
    object InvalidChain : InvalidTxError

    /// Transaction has expired
    object Expired : InvalidTxError

    /// An error occurred while validating actions of a Transaction.
    @RustSinglePropertyEnumItem
    data class ActionsValidation(val error: ActionsValidationError) : InvalidTxError

    /// The size of serialized transaction exceeded the limit.
    data class TransactionSizeExceeded(val size: Long, val limit: Long) : InvalidTxError
}

@RustEnum
sealed interface ActionErrorKind {

    /**
     * Happens when CreateAccount action tries to create an account with accountId which is already exists in the storage
     */
    data class AccountAlreadyExists(val accountId: AccountId) : ActionErrorKind

    /**
     * Happens when TX receiver_id doesn't exist (but action is not Action::CreateAccount)
     */
    data class AccountDoesNotExist(val accountId: AccountId) : ActionErrorKind

    /**
     * A top-level account ID can only be created by registrar.
     */
    data class CreateAccountOnlyByRegistrar(
        val accountId: AccountId,
        val registrarAccountId: AccountId,
        val predecessorId: AccountId,
    ) : ActionErrorKind

    /**
     * A newly created account must be under a namespace of the creator account
     */
    data class CreateAccountNotAllowed(val accountId: AccountId, val predecessorId: AccountId) : ActionErrorKind

    /**
     * Administrative actions like `DeployContract`, `Stake`, `AddKey`, `DeleteKey`. can be proceed only if sender=receiver
     * or the first TX action is a `CreateAccount` action
     */
    data class ActorNoPermission(val accountId: AccountId, val actorId: AccountId) : ActionErrorKind

    /**
     * Account tries to remove an access key that doesn't exist
     */
    data class DeleteKeyDoesNotExist(val accountId: AccountId, val publicKey: PublicKey) : ActionErrorKind

    /**
     * The public key is already used for an existing access key
     */
    data class AddKeyAlreadyExists(val accountId: AccountId, val publicKey: PublicKey) : ActionErrorKind

    /**
     * Account is staking and can not be deleted
     */
    data class DeleteAccountStaking(val accountId: AccountId) : ActionErrorKind

    /**
     * ActionReceipt can't be completed, because the remaining balance will not be enough to cover storage.
     */
    data class LackBalanceForState(
        /**
         * An account which needs balance
         */
        val accountId: AccountId,
        /**
         * Balance required to complete an action.
         */
        val amount: Balance,
    ) : ActionErrorKind

    /**
     * Account is not yet staked, but tries to unstake
     */
    data class TriesToUnstake(val accountId: AccountId) : ActionErrorKind

    /**
     * The account doesn't have enough balance to increase the stake.
     */
    data class TriesToStake(
        val accountId: AccountId,
        val stake: Balance,
        val locked: Balance,
        val balance: Balance,
    ) : ActionErrorKind

    data class InsufficientStake(
        val accountId: AccountId,
        val stake: Balance,
        val minimumStake: Balance,
    ) : ActionErrorKind

    /**
     * An error occurred during a `FunctionCall` Action, parameter is debug message.
     */
    @RustSinglePropertyEnumItem
    data class FunctionCallError(val error: ContractCallError) : ActionErrorKind

    /**
     * Error occurs when a new `ActionReceipt` created by the `FunctionCall` action fails
     * receipt validation.
     */
    @RustSinglePropertyEnumItem
    data class NewReceiptValidationError(val error: ReceiptValidationError) : ActionErrorKind

    /**
     * Error occurs when a `CreateAccount` action is called on hex-characters
     * account of length 64.  See implicit account creation NEP:
     * <https://github.com/nearprotocol/NEPs/pull/71>.
     */
    data class OnlyImplicitAccountCreationAllowed(val accountId: AccountId) : ActionErrorKind

    /**
     * Delete account whose state is large is temporarily banned.
     */
    data class DeleteAccountWithLargeState(val accountId: AccountId) : ActionErrorKind
}

@RustEnum
sealed interface ContractCallError {
    @RustSinglePropertyEnumItem
    data class CompilationError(val type: CompilationErrorType) : ContractCallError

    data class LinkError(val msg: String) : ContractCallError

    @RustSinglePropertyEnumItem
    data class MethodResolveError(val type: MethodResolveErrorType) : ContractCallError

    @RustSinglePropertyEnumItem
    data class WasmTrap(val type: WasmTrapType) : ContractCallError

    object WasmUnknownError : ContractCallError

    @RustSinglePropertyEnumItem
    data class HostError(val type: HostErrorType) : ContractCallError

    @RustSinglePropertyEnumItem
    data class ExecutionError(val msg: String) : ContractCallError
}

enum class WasmTrapType {
    /// An `unreachable` opcode was executed.
    Unreachable,

    /// Call indirect incorrect signature trap.
    IncorrectCallIndirectSignature,

    /// Memory out of bounds trap.
    MemoryOutOfBounds,

    /// Call indirect out of bounds trap.
    CallIndirectOOB,

    /// An arithmetic exception, e.g. divided by zero.
    IllegalArithmetic,

    /// Misaligned atomic access trap.
    MisalignedAtomicAccess,

    /// Indirect call to null.
    IndirectCallToNull,

    /// Stack overflow.
    StackOverflow,

    /// Generic trap.
    GenericTrap
}

enum class MethodResolveErrorType {
    MethodEmptyName,
    MethodNotFound,
    MethodInvalidSignature,
}

@RustEnum
sealed interface CompilationErrorType {
    data class CodeDoesNotExist(val accountId: AccountId) : CompilationErrorType
    data class PrepareError(val type: PrepareErrorType) : CompilationErrorType
    data class WasmerCompileError(val msg: String) : CompilationErrorType
    data class UnsupportedCompiler(val msg: String) : CompilationErrorType
}

/// Error that can occur while preparing or executing Wasm smart-contract.
enum class PrepareErrorType {
    /// Error happened while serializing the module.
    Serialization,

    /// Error happened while deserializing the module.
    Deserialization,

    /// Internal memory declaration has been found in the module.
    InternalMemoryDeclared,

    /// Gas instrumentation failed.
    ///
    /// This most likely indicates the module isn't valid.
    GasInstrumentation,

    /// Stack instrumentation failed.
    ///
    /// This  most likely indicates the module isn't valid.
    StackHeightInstrumentation,

    /// Error happened during instantiation.
    ///
    /// This might indicate that `start` function trapped, or module isn't
    /// instantiable and/or unlinkable.
    Instantiate,

    /// Error creating memory.
    Memory,

    /// Contract contains too many functions.
    TooManyFunctions,
}

@RustEnum
sealed interface InvalidAccessKeyErrorType {
    /// The access key identified by the `publicKey` doesn't exist for the account
    data class AccessKeyNotFound(val accountId: AccountId, val publicKey: PublicKey) : InvalidAccessKeyErrorType

    /// Transaction `receiver_id` doesn't match the access key receiver_id
    data class ReceiverMismatch(val txReceiver: AccountId, val akReceiver: String) : InvalidAccessKeyErrorType

    /// Transaction method name isn't allowed by the access key
    data class MethodNameMismatch(val methodName: String) : InvalidAccessKeyErrorType

    /// Transaction requires a full permission access key.
    object RequiresFullAccess : InvalidAccessKeyErrorType

    /// Access Key does not have enough allowance to cover transaction cost
    data class NotEnoughAllowance(
        val accountId: AccountId,
        val publicKey: PublicKey,
        val allowance: Balance,
        val cost: Balance
    ) : InvalidAccessKeyErrorType

    /// Having a deposit with a function call action is not allowed with a function call access key.
    @Suppress("unused")
    object DepositWithFunctionCall : InvalidAccessKeyErrorType
}

@RustEnum
sealed interface ActionsValidationError {
    /// The delete action must be a final action in transaction
    object DeleteActionMustBeFinal : ActionsValidationError

    /// The total prepaid gas (for all given actions) exceeded the limit.
    data class TotalPrepaidGasExceeded(val totalPrepaidGas: Gas, val limit: Gas) : ActionsValidationError

    /// The number of actions exceeded the given limit.
    data class TotalNumberOfActionsExceeded(val totalNumberOfActions: Long, val limit: Long) :
        ActionsValidationError

    /// The total number of bytes of the method names exceeded the limit in a Add Key action.
    data class AddKeyMethodNamesNumberOfBytesExceeded(val totalNumberOfBytes: Long, val limit: Long) :
        ActionsValidationError

    /// The length of some method name exceeded the limit in a Add Key action.
    data class AddKeyMethodNameLengthExceeded(val length: Long, val limit: Long) : ActionsValidationError

    /// Integer overflow during a compute.
    @Suppress("unused")
    object IntegerOverflow : ActionsValidationError

    /// Invalid account ID.
    data class InvalidAccountId(val accountId: AccountId) : ActionsValidationError

    /// The size of the contract code exceeded the limit in a DeployContract action.
    data class ContractSizeExceeded(val size: Long, val limit: Long) : ActionsValidationError

    /// The length of the method name exceeded the limit in a Function Call action.
    data class FunctionCallMethodNameLengthExceeded(val length: Long, val limit: Long) : ActionsValidationError

    /// The length of the arguments exceeded the limit in a Function Call action.
    data class FunctionCallArgumentsLengthExceeded(val length: Long, val limit: Long) : ActionsValidationError

    /// An attempt to stake with a public key that is not convertible to ristretto.
    data class UnsuitableStakingKey(val publicKey: PublicKey) : ActionsValidationError

    /// The attached amount of gas in a FunctionCall action has to be a positive number.
    object FunctionCallZeroAttachedGas : ActionsValidationError
}

@RustEnum
sealed interface ReceiptValidationError {
    /// The `predecessor_id` of a Receipt is not valid.
    data class InvalidPredecessorId(val accountId: String) : ReceiptValidationError

    /// The `receiver_id` of a Receipt is not valid.
    data class InvalidReceiverId(val accountId: String) : ReceiptValidationError

    /// The `signer_id` of an ActionReceipt is not valid.
    data class InvalidSignerId(val accountId: String) : ReceiptValidationError

    /// The `receiver_id` of a DataReceiver within an ActionReceipt is not valid.
    data class InvalidDataReceiverId(val accountId: String) : ReceiptValidationError

    /// The length of the returned data exceeded the limit in a DataReceipt.
    data class ReturnedValueLengthExceeded(val length: Long, val limit: Long) : ReceiptValidationError

    /// The number of input data dependencies exceeds the limit in an ActionReceipt.
    data class NumberInputDataDependenciesExceeded(val numberOfInputDataDependencies: Long, val limit: Long) :
        ReceiptValidationError

    /// An error occurred while validating actions of an ActionReceipt.
    @RustSinglePropertyEnumItem
    data class ActionsValidation(val error: ActionsValidationError) : ReceiptValidationError
}

@RustEnum
sealed interface HostErrorType {
    /// String encoding is bad UTF-16 sequence
    @Suppress("unused")
    object BadUTF16 : HostErrorType

    /// String encoding is bad UTF-8 sequence
    @Suppress("unused")
    object BadUTF8 : HostErrorType

    /// Exceeded the prepaid gas
    @Suppress("unused")
    object GasExceeded : HostErrorType

    /// Exceeded the maximum amount of gas allowed to burn per contract
    @Suppress("unused")
    object GasLimitExceeded : HostErrorType

    /// Exceeded the account balance
    @Suppress("unused")
    object BalanceExceeded : HostErrorType

    /// Tried to call an empty method name
    @Suppress("unused")
    object EmptyMethodName : HostErrorType

    /// Smart contract panicked
    data class GuestPanic(val panicMsg: String) : HostErrorType

    /// IntegerOverflow happened during a contract execution
    @Suppress("unused")
    object IntegerOverflow : HostErrorType

    /// `promise_idx` does not correspond to existing promises
    data class InvalidPromiseIndex(val promiseIdx: Long) : HostErrorType

    /// Actions can only be appended to non-joint promise.
    @Suppress("unused")
    object CannotAppendActionToJointPromise : HostErrorType

    /// Returning joint promise is currently prohibited
    @Suppress("unused")
    object CannotReturnJointPromise : HostErrorType

    /// Accessed invalid promise result index
    data class InvalidPromiseResultIndex(val resultIdx: Long) : HostErrorType

    /// Accessed invalid register id
    data class InvalidRegisterId(val registerId: Long) : HostErrorType

    /// Iterator `iterator_index` was invalidated after its creation by performing a mutable operation on trie
    data class IteratorWasInvalidated(val iteratorIndex: Long) : HostErrorType

    /// Accessed memory outside the bounds
    @Suppress("unused")
    object MemoryAccessViolation : HostErrorType

    /// VM Logic returned an invalid receipt index
    data class InvalidReceiptIndex(val receiptIndex: Long) : HostErrorType

    /// Iterator index `iterator_index` does not exist
    data class InvalidIteratorIndex(val iteratorIndex: Long) : HostErrorType

    /// VM Logic returned an invalid account id
    @Suppress("unused")
    object InvalidAccountId : HostErrorType

    /// VM Logic returned an invalid method name
    @Suppress("unused")
    object InvalidMethodName : HostErrorType

    /// VM Logic provided an invalid public key
    @Suppress("unused")
    object InvalidPublicKey : HostErrorType

    /// `method_name` is not allowed in view calls
    data class ProhibitedInView(val methodName: String) : HostErrorType

    /// The total number of logs will exceed the limit.
    data class NumberOfLogsExceeded(val limit: Long) : HostErrorType

    /// The storage key length exceeded the limit.
    data class KeyLengthExceeded(val length: Long, val limit: Long) : HostErrorType

    /// The storage value length exceeded the limit.
    data class ValueLengthExceeded(val length: Long, val limit: Long) : HostErrorType

    /// The total log length exceeded the limit.
    data class TotalLogLengthExceeded(val length: Long, val limit: Long) : HostErrorType

    /// The maximum number of promises within a FunctionCall exceeded the limit.
    data class NumberPromisesExceeded(val numberOfPromises: Long, val limit: Long) : HostErrorType

    /// The maximum number of input data dependencies exceeded the limit.
    data class NumberInputDataDependenciesExceeded(val numberOfInputDataDependencies: Long, val limit: Long) :
        HostErrorType

    /// The returned value length exceeded the limit.
    data class ReturnedValueLengthExceeded(val length: Long, val limit: Long) : HostErrorType

    /// The contract size for DeployContract action exceeded the limit.
    data class ContractSizeExceeded(val size: Long, val limit: Long) : HostErrorType

    /// The host function was deprecated.
    data class Deprecated(val methodName: String) : HostErrorType

    /// General errors for ECDSA recover.
    data class ECRecoverError(val msg: String) : HostErrorType

    /// Deserialization error for alt_bn128 functions
    data class AltBn128DeserializationError(val msg: String) : HostErrorType

    /// Serialization error for alt_bn128 functions
    data class AltBn128SerializationError(val msg: String) : HostErrorType
}
