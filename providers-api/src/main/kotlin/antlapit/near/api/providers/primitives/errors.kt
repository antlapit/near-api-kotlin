package antlapit.near.api.providers.primitives

/**
 * The execution has failed.
 **/
interface TxExecutionError

data class ActionError(
    val index: Long?,
    val kind: ActionErrorKind
) : TxExecutionError

data class InvalidTxError(
    val test: Any // TODO implement
) : TxExecutionError


interface ActionErrorKind

/**
 * Happens when CreateAccount action tries to create an account with account_id which is already exists in the storage
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
data class FunctionCallError(val error: Any/* TODO FunctionCallErrorSer*/) : ActionErrorKind

/**
 * Error occurs when a new `ActionReceipt` created by the `FunctionCall` action fails
 * receipt validation.
 */
data class NewReceiptValidationError(val error : ReceiptValidationError) : ActionErrorKind

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


typealias ReceiptValidationError = Any // TODO

typealias ActionsValidationError = Any // TODO
