package antlapit.near.api.json

import antlapit.near.api.providers.model.primitives.*
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class ErrorsDeserializeTest : FunSpec({

    val mapper = ObjectMapperFactory.newInstance()

    context("Tx execution error") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """
                {
                    "ActionError": {
                        "index": 1,
                        "kind": {
                            "AccountAlreadyExists": {
                                "account_id": "test"
                            }
                        }
                    }
                }
                """,
                TxExecutionError.ActionError(
                    kind = ActionErrorKind.AccountAlreadyExists(
                        accountId = "test"
                    ),
                    index = 1
                )
            ),
            TestData(
                """{"InvalidTxError": {"InvalidAccessKeyError": "RequiresFullAccess"}}""",
                InvalidTxError.InvalidAccessKeyError(error = InvalidAccessKeyErrorType.RequiresFullAccess)
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as TxExecutionError shouldBe obj
            }
        }
    }

    context("Invalid tx error") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"InvalidAccessKeyError": "RequiresFullAccess"}""",
                InvalidTxError.InvalidAccessKeyError(InvalidAccessKeyErrorType.RequiresFullAccess)
            ),
            TestData(
                """{"InvalidSignerId": {"signer_id": "signer"}}""",
                InvalidTxError.InvalidSignerId(signerId = "signer")
            ),
            TestData(
                """{"SignerDoesNotExist": {"signer_id": "signer"}}""",
                InvalidTxError.SignerDoesNotExist(signerId = "signer")
            ),
            TestData(
                """{"InvalidNonce": {"tx_nonce": 1,"ak_nonce": 2}}""",
                InvalidTxError.InvalidNonce(txNonce = 1, akNonce = 2)
            ),
            TestData(
                """{"NonceTooLarge": {"tx_nonce": 1,"upper_bound": 2}}""",
                InvalidTxError.NonceTooLarge(txNonce = 1, upperBound = 2)
            ),
            TestData(
                """{"InvalidReceiverId": {"receiver_id": "signer"}}""",
                InvalidTxError.InvalidReceiverId(receiverId = "signer")
            ),
            TestData("\"InvalidSignature\"", InvalidTxError.InvalidSignature),
            TestData(
                """{"NotEnoughBalance": {"signer_id": "signer","balance": "10","cost": "10"}}""",
                InvalidTxError.NotEnoughBalance(
                    signerId = "signer",
                    balance = BigInteger.TEN,
                    cost = BigInteger.TEN
                )
            ),
            TestData(
                """{"LackBalanceForState": {"signer_id": "signer","amount": "10"}}""",
                InvalidTxError.LackBalanceForState(
                    signerId = "signer",
                    amount = BigInteger.TEN
                )
            ),
            TestData("\"CostOverflow\"", InvalidTxError.CostOverflow),
            TestData("\"InvalidChain\"", InvalidTxError.InvalidChain),
            TestData("\"Expired\"", InvalidTxError.Expired),
            TestData(
                """{"ActionsValidation": "DeleteActionMustBeFinal"}""", InvalidTxError.ActionsValidation(
                    error = ActionsValidationError.DeleteActionMustBeFinal
                )
            ),
            TestData(
                """{"TransactionSizeExceeded": {"size": 1,"limit": 2}}""",
                InvalidTxError.TransactionSizeExceeded(
                    size = 1,
                    limit = 2
                )
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as InvalidTxError shouldBe obj
            }
        }
    }

    context("Action error kind") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"AccountAlreadyExists": {"account_id": "account"}}""",
                ActionErrorKind.AccountAlreadyExists(accountId = "account")
            ),
            TestData(
                """{"AccountDoesNotExist": {"account_id": "account"}}""",
                ActionErrorKind.AccountDoesNotExist(accountId = "account")
            ),
            TestData(
                """{"CreateAccountOnlyByRegistrar": {
                        "account_id": "account",
                        "registrar_account_id": "registar",
                        "predecessor_id": "predecessor"
                    }}""",
                ActionErrorKind.CreateAccountOnlyByRegistrar(
                    accountId = "account",
                    registrarAccountId = "registar",
                    predecessorId = "predecessor"
                )
            ),
            TestData(
                """{"CreateAccountNotAllowed": {"account_id": "account","predecessor_id": "predecessor"}}""",
                ActionErrorKind.CreateAccountNotAllowed(
                    accountId = "account",
                    predecessorId = "predecessor"
                )
            ),
            TestData(
                """{"ActorNoPermission": {"account_id": "account","actor_id": "actor"}}""",
                ActionErrorKind.ActorNoPermission(
                    accountId = "account",
                    actorId = "actor"
                )
            ),
            TestData(
                """{"DeleteKeyDoesNotExist": {
                        "account_id": "account",
                        "public_key": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                    }}""",
                ActionErrorKind.DeleteKeyDoesNotExist(
                    accountId = "account",
                    publicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                )
            ),
            TestData(
                """{"AddKeyAlreadyExists": {
                        "account_id": "account",
                        "public_key": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                    }}""",
                ActionErrorKind.AddKeyAlreadyExists(
                    accountId = "account",
                    publicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                )
            ),
            TestData(
                """{"DeleteAccountStaking": {"account_id": "account"}}""",
                ActionErrorKind.DeleteAccountStaking(accountId = "account")
            ),
            TestData(
                """{"LackBalanceForState": {"account_id": "account", "amount": "1"}}""",
                ActionErrorKind.LackBalanceForState(accountId = "account", amount = BigInteger.ONE)
            ),
            TestData(
                """{"TriesToUnstake": {"account_id": "account"}}""",
                ActionErrorKind.TriesToUnstake(accountId = "account")
            ),
            TestData(
                """{
                    "TriesToStake": {
                        "account_id": "account",
                        "stake": "1",
                        "locked": "2",
                        "balance": "3"
                    }
                }""",
                ActionErrorKind.TriesToStake(
                    accountId = "account",
                    stake = BigInteger.ONE,
                    locked = BigInteger.valueOf(2),
                    balance = BigInteger.valueOf(3)
                )
            ),
            TestData(
                """{
                    "InsufficientStake": {
                        "account_id": "account",
                        "stake": "2",
                        "minimum_stake": "3"
                    }
                }""",
                ActionErrorKind.InsufficientStake(
                    accountId = "account",
                    stake = BigInteger.valueOf(2),
                    minimumStake = BigInteger.valueOf(3)
                )
            ),
            TestData(
                """{"FunctionCallError": "WasmUnknownError"}""",
                ActionErrorKind.FunctionCallError(error = ContractCallError.WasmUnknownError)
            ),
            TestData(
                """{
                        "NewReceiptValidationError": {
                            "InvalidPredecessorId": {
                                "account_id": "account"
                            }
                        }
                    }""",
                ActionErrorKind.NewReceiptValidationError(
                    error = ReceiptValidationError.InvalidPredecessorId(accountId = "account")
                )
            ),
            TestData(
                """{"OnlyImplicitAccountCreationAllowed": {"account_id": "account"}}""",
                ActionErrorKind.OnlyImplicitAccountCreationAllowed(accountId = "account")
            ),
            TestData(
                """{"DeleteAccountWithLargeState": {"account_id": "account"}}""",
                ActionErrorKind.DeleteAccountWithLargeState(accountId = "account")
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ActionErrorKind shouldBe obj
            }
        }
    }

    context("Contract call error") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{
                        "CompilationError": {
                            "CodeDoesNotExist": {"account_id": "account"}
                        }
                }""",
                ContractCallError.CompilationError(
                    type = CompilationErrorType.CodeDoesNotExist(accountId = "account")
                )
            ),
            TestData(
                """{"LinkError": {"msg": "message"}}""",
                ContractCallError.LinkError(msg = "message")
            ),
            TestData(
                """{"MethodResolveError": "MethodEmptyName"}""",
                ContractCallError.MethodResolveError(type = MethodResolveErrorType.MethodEmptyName)
            ),
            TestData(
                """{"WasmTrap": "GenericTrap"}""",
                ContractCallError.WasmTrap(type = WasmTrapType.GenericTrap)
            ),
            TestData(
                """"WasmUnknownError"""",
                ContractCallError.WasmUnknownError
            ),
            TestData(
                """{"HostError": "BadUTF16"}""",
                ContractCallError.HostError(type = HostErrorType.BadUTF16)
            ),
            TestData(
                """{"ExecutionError": "error"}""",
                ContractCallError.ExecutionError(msg = "error")
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ContractCallError shouldBe obj
            }
        }
    }

    context("Wasm trap type") {
        withData(
            nameFn = { "${it.typed}" },
            WasmTrapType.values().map { TestData("\"$it\"", it) }
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as WasmTrapType shouldBe obj
            }
        }
    }

    context("Method resolve error type") {
        withData(
            nameFn = { "${it.typed}" },
            MethodResolveErrorType.values().map { TestData("\"$it\"", it) }
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as MethodResolveErrorType shouldBe obj
            }
        }
    }

    context("Prepare error type") {
        withData(
            nameFn = { "${it.typed}" },
            PrepareErrorType.values().map { TestData("\"$it\"", it) }
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as PrepareErrorType shouldBe obj
            }
        }
    }

    context("Compilation error type") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"CodeDoesNotExist": {"account_id": "account"}}""",
                CompilationErrorType.CodeDoesNotExist(accountId = "account")
            ),
            TestData(
                """{
                        "PrepareError": {
                            "type": "Deserialization"
                        }
                    }""".trimMargin(),
                CompilationErrorType.PrepareError(type = PrepareErrorType.Deserialization)
            ),
            TestData(
                """{"WasmerCompileError": {"msg": "error"}}""",
                CompilationErrorType.WasmerCompileError("error")
            ),
            TestData(
                """{"UnsupportedCompiler": {"msg":"error"}}""",
                CompilationErrorType.UnsupportedCompiler("error")
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as CompilationErrorType shouldBe obj
            }
        }
    }

    context("Invalid access key error type : objects") {
        withData(
            nameFn = { "${it.typed}" },
            InvalidAccessKeyErrorType::class.sealedSubclasses
                .filter { it.objectInstance != null }
                .map { TestData("\"${it.simpleName}\"", it.objectInstance) },
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as InvalidAccessKeyErrorType shouldBe obj
            }
        }
    }

    context("Invalid access key error type : data classes") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{
                    "AccessKeyNotFound": {
                       "account_id": "account",
                       "public_key": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                    }
                }""".trimMargin(),
                InvalidAccessKeyErrorType.AccessKeyNotFound(
                    accountId = "account",
                    publicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"
                )
            ),
            TestData(
                """{"ReceiverMismatch": {"tx_receiver": "account","ak_receiver": "receiver"}}""".trimMargin(),
                InvalidAccessKeyErrorType.ReceiverMismatch(
                    txReceiver = "account",
                    akReceiver = "receiver"
                )
            ),
            TestData(
                """{"MethodNameMismatch": {"method_name": "get"}}""".trimMargin(),
                InvalidAccessKeyErrorType.MethodNameMismatch(
                    methodName = "get"
                )
            ),
            TestData(
                """{
                    "NotEnoughAllowance": {
                       "account_id": "account",
                       "public_key": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                       "allowance": "1",
                       "cost": "10"
                    }
                }""".trimMargin(),
                InvalidAccessKeyErrorType.NotEnoughAllowance(
                    accountId = "account",
                    publicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                    allowance = BigInteger.ONE,
                    cost = BigInteger.TEN
                )
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as InvalidAccessKeyErrorType shouldBe obj
            }
        }
    }

    context("Actions validation error : objects") {
        withData(
            nameFn = { "${it.typed}" },
            ActionsValidationError::class.sealedSubclasses
                .filter { it.objectInstance != null }
                .map { TestData("\"${it.simpleName}\"", it.objectInstance) },
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ActionsValidationError shouldBe obj
            }
        }
    }

    context("Actions validation error : data classes") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{
                    "TotalPrepaidGasExceeded": {
                        "total_prepaid_gas": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.TotalPrepaidGasExceeded(
                    totalPrepaidGas = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "TotalNumberOfActionsExceeded": {
                        "total_number_of_actions": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.TotalNumberOfActionsExceeded(
                    totalNumberOfActions = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "AddKeyMethodNamesNumberOfBytesExceeded": {
                        "total_number_of_bytes": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.AddKeyMethodNamesNumberOfBytesExceeded(
                    totalNumberOfBytes = 1,
                    limit = 2
                )
            ),
            TestData(
                """{"InvalidAccountId": {"account_id": "account"}}""",
                ActionsValidationError.InvalidAccountId(accountId = "account")
            ),
            TestData(
                """{
                    "ContractSizeExceeded": {
                        "size": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.ContractSizeExceeded(
                    size = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "FunctionCallMethodNameLengthExceeded": {
                        "length": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.FunctionCallMethodNameLengthExceeded(
                    length = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "FunctionCallArgumentsLengthExceeded": {
                        "length": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.FunctionCallArgumentsLengthExceeded(
                    length = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "UnsuitableStakingKey": {
                        "public_key": "key"
                    }
                }""",
                ActionsValidationError.UnsuitableStakingKey(
                    publicKey = "key"
                )
            ),
            TestData(
                """{
                    "AddKeyMethodNameLengthExceeded": {
                        "length": 1,
                        "limit": 2
                    }
                }""",
                ActionsValidationError.AddKeyMethodNameLengthExceeded(
                    length = 1,
                    limit = 2
                )
            )
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ActionsValidationError shouldBe obj
            }
        }
    }

    context("Receipt validation error : data classes") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """{"InvalidPredecessorId": {"account_id": "account"}}""",
                ReceiptValidationError.InvalidPredecessorId(accountId = "account")
            ),
            TestData(
                """{"InvalidReceiverId": {"account_id": "account"}}""",
                ReceiptValidationError.InvalidReceiverId(accountId = "account")
            ),
            TestData(
                """{"InvalidSignerId": {"account_id": "account"}}""",
                ReceiptValidationError.InvalidSignerId(accountId = "account")
            ),
            TestData(
                """{"InvalidDataReceiverId": {"account_id": "account"}}""",
                ReceiptValidationError.InvalidDataReceiverId(accountId = "account")
            ),
            TestData(
                """{
                    "ReturnedValueLengthExceeded": {
                        "length": 1,
                        "limit": 2
                    }
                }""",
                ReceiptValidationError.ReturnedValueLengthExceeded(
                    length = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "NumberInputDataDependenciesExceeded": {
                        "number_of_input_data_dependencies": 1,
                        "limit": 2
                    }
                }""",
                ReceiptValidationError.NumberInputDataDependenciesExceeded(
                    numberOfInputDataDependencies = 1,
                    limit = 2
                )
            ),
            TestData(
                """{
                    "ActionsValidation": "FunctionCallZeroAttachedGas"
                }""",
                ReceiptValidationError.ActionsValidation(error = ActionsValidationError.FunctionCallZeroAttachedGas)
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as ReceiptValidationError shouldBe obj
            }
        }
    }

    context("Host error type : objects") {
        withData(
            nameFn = { "${it.typed}" },
            HostErrorType::class.sealedSubclasses
                .filter { it.objectInstance != null }
                .map { TestData("\"${it.simpleName}\"", it.objectInstance) },
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as HostErrorType shouldBe obj
            }
        }
    }

    context("Host error type : data classes") {
        withData(
            nameFn = { "${it.typed}" },
            TestData("""{"GuestPanic":{"panic_msg": "error"}}""", HostErrorType.GuestPanic("error")),
            TestData(
                """{"InvalidPromiseIndex":{"promise_idx": 1}}""",
                HostErrorType.InvalidPromiseIndex(promiseIdx = 1)
            ),
            TestData(
                """{"InvalidPromiseResultIndex":{"result_idx": 1}}""",
                HostErrorType.InvalidPromiseResultIndex(resultIdx = 1)
            ),
            TestData(
                """{"InvalidRegisterId":{"register_id": 1}}""", HostErrorType.InvalidRegisterId(registerId = 1)
            ),
            TestData(
                """{"IteratorWasInvalidated":{"iterator_index": 1}}""",
                HostErrorType.IteratorWasInvalidated(iteratorIndex = 1)
            ),
            TestData(
                """{"InvalidReceiptIndex":{"receipt_index": 1}}""", HostErrorType.InvalidReceiptIndex(receiptIndex = 1)
            ),
            TestData(
                """{"InvalidIteratorIndex":{"iterator_index": 1}}""",
                HostErrorType.InvalidIteratorIndex(iteratorIndex = 1)
            ),
            TestData(
                """{"ProhibitedInView":{"method_name": "method"}}""",
                HostErrorType.ProhibitedInView(methodName = "method")
            ),
            TestData(
                """{"NumberOfLogsExceeded":{"limit": 1}}""",
                HostErrorType.NumberOfLogsExceeded(limit = 1)
            ),
            TestData(
                """{"KeyLengthExceeded":{"length": 2, "limit": 1}}""",
                HostErrorType.KeyLengthExceeded(length = 2, limit = 1)
            ),
            TestData(
                """{"ValueLengthExceeded":{"length": 2, "limit": 1}}""",
                HostErrorType.ValueLengthExceeded(length = 2, limit = 1)
            ),
            TestData(
                """{"TotalLogLengthExceeded":{"length": 2, "limit": 1}}""",
                HostErrorType.TotalLogLengthExceeded(length = 2, limit = 1)
            ),
            TestData(
                """{"NumberPromisesExceeded":{"number_of_promises": 2, "limit": 1}}""",
                HostErrorType.NumberPromisesExceeded(numberOfPromises = 2, limit = 1)
            ),
            TestData(
                """{"NumberInputDataDependenciesExceeded":{"number_of_input_data_dependencies": 2, "limit": 1}}""",
                HostErrorType.NumberInputDataDependenciesExceeded(numberOfInputDataDependencies = 2, limit = 1)
            ),
            TestData(
                """{"ReturnedValueLengthExceeded":{"length": 2, "limit": 1}}""",
                HostErrorType.ReturnedValueLengthExceeded(length = 2, limit = 1)
            ),
            TestData(
                """{"ContractSizeExceeded":{"size": 2, "limit": 1}}""",
                HostErrorType.ContractSizeExceeded(size = 2, limit = 1)
            ),
            TestData(
                """{"Deprecated":{"method_name": "method"}}""",
                HostErrorType.Deprecated(methodName = "method")
            ),
            TestData("""{"ECRecoverError":{"msg": "error"}}""", HostErrorType.ECRecoverError("error")),
            TestData(
                """{"AltBn128DeserializationError":{"msg": "error"}}""",
                HostErrorType.AltBn128DeserializationError("error")
            ),
            TestData(
                """{"AltBn128SerializationError":{"msg": "error"}}""",
                HostErrorType.AltBn128SerializationError("error")
            ),
        ) { (str, obj) ->
            shouldNotThrow<Throwable> {
                mapper.readValue(str) as HostErrorType shouldBe obj
            }
        }
    }
})
