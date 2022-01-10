package antlapit.near.api.providers.model.changes

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.account.Account
import antlapit.near.api.providers.model.primitives.AccountId
import antlapit.near.api.providers.model.primitives.CryptoHash
import antlapit.near.api.providers.model.primitives.PublicKey

data class AccessKeysChangesContainer(
    val blockHash: CryptoHash,
    val changes: List<AccessKeyChange> = emptyList()
)

data class AccessKeyChange(
    val change: StateChange,
    val cause: StateChangeCause
)

sealed interface StateChange {
    data class AccountUpdate(val accountId: AccountId, val account: Account) : StateChange

    data class AccountDeletion(val accountId: AccountId) : StateChange

    data class AccessKeyUpdate(
        val accountId: AccountId,
        val publicKey: PublicKey,
        val accessKey: AccessKey
    ) : StateChange

    data class AccessKeyDeletion(val accountId: AccountId, val publicKey: PublicKey) : StateChange

    data class DataUpdate(val accountId: AccountId, val key: String, val value: String) : StateChange

    data class DataDeletion(val accountId: AccountId, val key: String) : StateChange

    data class ContractCodeUpdate(val accountId: AccountId, val code: String) : StateChange

    data class ContractCodeDeletion(val accountId: AccountId) : StateChange
}

sealed interface StateChangeCause {
    /**
     * A type of update that does not get finalized. Used for verification and execution of
     * immutable smart contract methods. Attempt fo finalize a `TrieUpdate` containing such
     * change will lead to panic.
     */
    object NotWritableToDisk : StateChangeCause

    /**
     * A type of update that is used to mark the initial storage update, e.g. during genesis
     * or in tests setup.
     */
    object InitialState : StateChangeCause

    /**
     * Processing of a transaction.
     */
    data class TransactionProcessing(val txHash: CryptoHash) : StateChangeCause

    /**
     * Before the receipt is going to be processed, inputs get drained from the state, which
     * causes state modification.
     */
    data class ActionReceiptProcessingStarted(val receiptHash: CryptoHash) : StateChangeCause

    /**
     * Computation of gas reward.
     */
    data class ActionReceiptGasReward(val receiptHash: CryptoHash) : StateChangeCause

    /**
     * Processing of a receipt.
     */
    data class ReceiptProcessing(val receiptHash: CryptoHash) : StateChangeCause

    /**
     * The given receipt was postponed. This is either a data receipt or an action receipt.
     * A `DataReceipt` can be postponed if the corresponding `ActionReceipt` is not received yet,
     * or other data dependencies are not satisfied.
     * An `ActionReceipt` can be postponed if not all data dependencies are received.
     */
    data class PostponedReceipt(val receiptHash: CryptoHash) : StateChangeCause

    /**
     * Updated delayed receipts queue in the state.
     * We either processed previously delayed receipts or added more receipts to the delayed queue.
     */
    object UpdatedDelayedReceipts : StateChangeCause

    /**
     * State change that happens when we update validator accounts. Not associated with with any
     * specific transaction or receipt.
     */
    object ValidatorAccountsUpdate : StateChangeCause

    /**
     * State change that is happens due to migration that happens in first block of an epoch
     * after protocol upgrade
     */
    object Migration : StateChangeCause

    /**
     * State changes for building states for re-sharding
     */
    object Resharding : StateChangeCause
}