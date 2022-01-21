package org.near.api.model.config

import org.near.api.model.primitives.AccountId
import org.near.api.model.primitives.Balance

data class RuntimeConfig(
    /**
     * Amount of yN per byte required to have on the account.  See
     */
    val storageAmountPerByte: Balance,
    /**
     * Costs of different actions that need to be performed when sending and processing transaction
     * and receipts.
     */
    val transactionCosts: RuntimeFeesConfig,
    /**
     * Config of wasm operations.
     */
    val wasmConfig: VMConfig,
    /**
     * Config that defines rules for account creation.
     */
    val accountCreationConfig: AccountCreationConfig,
)


data class AccountCreationConfig(
    /**
     * The minimum length of the top-level account ID that is allowed to be created by any account.
     */
    val minAllowedTopLevelAccountLength: Byte,
    /**
     * The account ID of the account registrar. This account ID allowed to create top-level
     * accounts of any valid length.
     */
    val registrarAccountId: AccountId,
)
