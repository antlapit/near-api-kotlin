package org.near.api.providers.model.config

import org.near.api.providers.model.primitives.Gas

/**
 * Costs associated with an object that can only be sent over the network (and executed by the receiver).
 * NOTE: `send_sir` or `send_not_sir` fees are usually burned when the item is being created.
 * And `execution` fee is burned when the item is being executed.
 */
data class Fee(
    /**
     * Fee for sending an object from the sender to itself, guaranteeing that it does not leave the shard.
     */
    val sendSir: Gas,
    /**
     * Fee for sending an object potentially across the shards.
     */
    val sendNotSir: Gas,
    /**
     * Fee for executing the object.
     */
    val execution: Gas,
)

data class RuntimeFeesConfig(
    /**
     * Describes the cost of creating an action receipt, `ActionReceipt`, excluding the actual cost of actions.
     * - `send` cost is burned when a receipt is created using `promise_create` or
     *     `promise_batch_create`
     * - `exec` cost is burned when the receipt is being executed.
     */
    val actionReceiptCreationConfig: Fee,
    /**
     * Describes the cost of creating a data receipt, `DataReceipt`.
     */
    val dataReceiptCreationConfig: DataReceiptCreationConfig,
    /**
     * Describes the cost of creating a certain action, `Action`. Includes all variants.
     */
    val actionCreationConfig: ActionCreationConfig,
    /**
     * Describes fees for storage.
     */
    val storageUsageConfig: StorageUsageConfig,

    /**
     * Fraction of the burnt gas to reward to the contract account for execution.
     */
    val burntGasReward: Rational,

    /**
     * Pessimistic gas price inflation ratio.
     */
    val pessimisticGasPriceInflationRatio: Rational,
)

/**
 * Describes the cost of creating a data receipt, `DataReceipt`.
 */
data class DataReceiptCreationConfig(
    /**
     * Base cost of creating a data receipt.
     * Both `send` and `exec` costs are burned when a new receipt has input dependencies. The gas
     * is charged for each input dependency. The dependencies are specified when a receipt is
     * created using `promise_then` and `promise_batch_then`.
     * NOTE: Any receipt with output dependencies will produce data receipts. Even if it fails.
     * Even if the last action is not a function call (in case of success it will return empty
     * value).
     */
    val baseCost: Fee,

    /**
     * Additional cost per byte sent.
     * Both `send` and `exec` costs are burned when a function call finishes execution and returns
     * `N` bytes of data to every output dependency. For each output dependency the cost is
     * `(send(sir) + exec()) * N`.
     */
    val costPerByte: Fee,
)

/**
 * Describes the cost of creating a specific action, `Action`. Includes all variants.
 */
data class ActionCreationConfig(
    /**
     * Base cost of creating an account.
     */
    val createAccountCost: Fee,

    /**
     * Base cost of deploying a contract.
     */
    val deployContractCost: Fee,

    /**
     * Cost per byte of deploying a contract.
     */
    val deployContractCostPerByte: Fee,

    /**
     * Base cost of calling a function.
     */
    val functionCallCost: Fee,

    /**
     * Cost per byte of method name and arguments of calling a function.
     */
    val functionCallCostPerByte: Fee,

    /**
     * Base cost of making a transfer.
     */
    val transferCost: Fee,

    /**
     * Base cost of staking.
     */
    val stakeCost: Fee,

    /**
     * Base cost of adding a key.
     */
    val addKeyCost: AccessKeyCreationConfig,

    /**
     * Base cost of deleting a key.
     */
    val deleteKeyCost: Fee,

    /**
     * Base cost of deleting an account.
     */
    val deleteAccountCost: Fee,
)

/**
 * Describes the cost of creating an access key.
 */
data class AccessKeyCreationConfig(
    /**
     * Base cost of creating a full access access-key.
     */
    val fullAccessCost: Fee,

    /**
     * Base cost of creating an access-key restricted to specific functions.
     */
    val functionCallCost: Fee,

    /**
     * Cost per byte of method_names of creating a restricted access-key.
     */
    val functionCallCostPerByte: Fee,
)

/**
 * Describes cost of storage per block
 */
data class StorageUsageConfig(
    /**
     * Number of bytes for an account record, including rounding up for account id.
     */
    val numBytesAccount: Long,

    /**
     * Additional number of bytes for a k/v record
     */
    val numExtraBytesRecord: Long,
)
