package org.near.api.providers.model.config

import org.near.api.providers.model.primitives.Gas

data class VMConfig(
    /** Costs for runtime externals
     */
    val extCosts: ExtCostsConfig,

    /** Gas cost of a growing memory by single page.
     */
    val growMemCost: Int,
    /** Gas cost of a regular operation.
     */
    val regularOpCost: Int,

    /** Describes limits for VM and Runtime.
     */
    val limitConfig: VMLimitConfig,
)

/**
 * Describes limits for VM and Runtime.
 */
data class VMLimitConfig(
    /** Max amount of gas that can be used, excluding gas attached to promises.
     */
    val maxGasBurnt: Gas,

    /** How tall the stack is allowed to grow?
     * See <https://wiki.parity.io/WebAssembly-StackHeight> to find out
     * how the stack frame cost is calculated.
     */
    val maxStackHeight: Int,

    /** The initial number of memory pages.
     * NOTE: It's not a limiter itself, but it's a value we use for initialMemoryPages.
     */
    val initialMemoryPages: Int,
    /** What is the maximal memory pages amount is allowed to have for
     * a contract.
     */
    val maxMemoryPages: Int,

    /** Limit of memory used by registers.
     */
    val registersMemoryLimit: Long,
    /** Maximum number of bytes that can be stored in a single register.
     */
    val maxRegisterSize: Long,
    /** Maximum number of registers that can be used simultaneously.
     */
    val maxNumberRegisters: Long,

    /** Maximum number of log entries.
     */
    val maxNumberLogs: Long,
    /** Maximum total length in bytes of all log messages.
     */
    val maxTotalLogLength: Long,

    /** Max total prepaid gas for all function call actions per receipt.
     */
    val maxTotalPrepaidGas: Gas,

    /** Max number of actions per receipt.
     */
    val maxActionsPerReceipt: Long,
    /** Max total length of all method names (including terminating character) for a function call
     * permission access key.
     */
    val maxNumberBytesMethodNames: Long,
    /** Max length of any method name (without terminating character).
     */
    val maxLengthMethodName: Long,
    /** Max length of arguments in a function call action.
     */
    val maxArgumentsLength: Long,
    /** Max length of returned data
     */
    val maxLengthReturnedData: Long,
    /** Max contract size
     */
    val maxContractSize: Long,
    /** Max transaction size
     */
    val maxTransactionSize: Long,
    /** Max storage key size
     */
    val maxLengthStorageKey: Long,
    /** Max storage value size
     */
    val maxLengthStorageValue: Long,
    /** Max number of promises that a function call can create
     */
    val maxPromisesPerFunctionCallAction: Long,
    /** Max number of input data dependencies
     */
    val maxNumberInputDataDependencies: Long,
    /** If present, stores max number of functions in one contract
     */
    val maxFunctionsNumberPerContract: Long?,
)

data class ExtCostsConfig(
    /** Base cost for calling a host function.
     */
    val base: Gas,

    /** Base cost of loading and compiling contract
     */
    val contractCompileBase: Gas,
    /** Cost of the execution to load and compile contract
     */
    val contractCompileBytes: Gas,

    /** Base cost for guest memory read
     */
    val readMemoryBase: Gas,
    /** Cost for guest memory read
     */
    val readMemoryByte: Gas,

    /** Base cost for guest memory write
     */
    val writeMemoryBase: Gas,
    /** Cost for guest memory write per byte
     */
    val writeMemoryByte: Gas,

    /** Base cost for reading from register
     */
    val readRegisterBase: Gas,
    /** Cost for reading byte from register
     */
    val readRegisterByte: Gas,

    /** Base cost for writing into register
     */
    val writeRegisterBase: Gas,
    /** Cost for writing byte into register
     */
    val writeRegisterByte: Gas,

    /** Base cost of decoding utf8. It's used for `log_utf8` and `panic_utf8`.
     */
    val utf8DecodingBase: Gas,
    /** Cost per byte of decoding utf8. It's used for `log_utf8` and `panic_utf8`.
     */
    val utf8DecodingByte: Gas,

    /** Base cost of decoding utf16. It's used for `log_utf16`.
     */
    val utf16DecodingBase: Gas,
    /** Cost per byte of decoding utf16. It's used for `log_utf16`.
     */
    val utf16DecodingByte: Gas,

    /** Cost of getting sha256 base
     */
    val sha256Base: Gas,
    /** Cost of getting sha256 per byte
     */
    val sha256Byte: Gas,

    /** Cost of getting sha256 base
     */
    val keccak256Base: Gas,
    /** Cost of getting sha256 per byte
     */
    val keccak256Byte: Gas,

    /** Cost of getting sha256 base
     */
    val keccak512Base: Gas,
    /** Cost of getting sha256 per byte
     */
    val keccak512Byte: Gas,

    /** Cost of getting ripemd160 base
     */
    val ripemd160Base: Gas,
    /** Cost of getting ripemd160 per message block
     */
    val ripemd160Block: Gas,

    /** Cost of calling ecrecover
     */
    val ecrecoverBase: Gas,

    /** Cost for calling logging.
     */
    val logBase: Gas,
    /** Cost for logging per byte
     */
    val logByte: Gas,

    /** Storage trie write key base cost
     */
    val storageWriteBase: Gas,
    /** Storage trie write key per byte cost
     */
    val storageWriteKeyByte: Gas,
    /** Storage trie write value per byte cost
     */
    val storageWriteValueByte: Gas,
    /** Storage trie write cost per byte of evicted value.
     */
    val storageWriteEvictedByte: Gas,

    /** Storage trie read key base cost
     */
    val storageReadBase: Gas,
    /** Storage trie read key per byte cost
     */
    val storageReadKeyByte: Gas,
    /** Storage trie read value cost per byte cost
     */
    val storageReadValueByte: Gas,

    /** Remove key from trie base cost
     */
    val storageRemoveBase: Gas,
    /** Remove key from trie per byte cost
     */
    val storageRemoveKeyByte: Gas,
    /** Remove key from trie ret value byte cost
     */
    val storageRemoveRetValueByte: Gas,

    /** Storage trie check for key existence cost base
     */
    val storageHasKeyBase: Gas,
    /** Storage trie check for key existence per key byte
     */
    val storageHasKeyByte: Gas,

    /** Create trie prefix iterator cost base
     */
    val storageIterCreatePrefixBase: Gas,
    /** Create trie prefix iterator cost per byte.
     */
    val storageIterCreatePrefixByte: Gas,

    /** Create trie range iterator cost base
     */
    val storageIterCreateRangeBase: Gas,
    /** Create trie range iterator cost per byte of from key.
     */
    val storageIterCreateFromByte: Gas,
    /** Create trie range iterator cost per byte of to key.
     */
    val storageIterCreateToByte: Gas,

    /** Trie iterator per key base cost
     */
    val storageIterNextBase: Gas,
    /** Trie iterator next key byte cost
     */
    val storageIterNextKeyByte: Gas,
    /** Trie iterator next key byte cost
     */
    val storageIterNextValueByte: Gas,

    /** Cost per touched trie node
     */
    val touchingTrieNode: Gas,

    /** Cost for calling `promiseAnd`
     */
    val promiseAndBase: Gas,
    /** Cost for calling `promiseAnd` for each promise
     */
    val promiseAndPerPromise: Gas,
    /** Cost for calling `promiseReturn`
     */
    val promiseReturn: Gas,

    /** Cost of calling `validatorStake`.
     */
    val validatorStakeBase: Gas,
    /** Cost of calling `validatorTotalStake`.
     */
    val validatorTotalStakeBase: Gas,
)
