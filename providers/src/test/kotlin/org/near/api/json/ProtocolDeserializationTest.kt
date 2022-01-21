package org.near.api.json

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.providers.model.account.AccountInfo
import org.near.api.providers.model.config.*
import org.near.api.providers.model.primitives.Balance
import org.near.api.providers.model.primitives.PublicKey
import java.time.ZoneId
import java.time.ZonedDateTime

@ExperimentalKotest
class ProtocolDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("GenesicConfig") {
        withData(
            nameFn = { "${it.typed.protocolVersion}" },
            listOf(
                TestData(
                    """
                    {
                       "protocol_version":29,
                       "genesis_time":"2020-07-31T03:39:42.911378Z",
                       "chain_id":"testnet",
                       "genesis_height":42376888,
                       "num_block_producer_seats":200,
                       "num_block_producer_seats_per_shard":[200],
                       "avg_hidden_validator_seats_per_shard":[0],
                       "dynamic_resharding":false,
                       "protocol_upgrade_stake_threshold":[4, 5],
                       "protocol_upgrade_num_epochs":2,
                       "epoch_length":43200,
                       "gas_limit":1000000000000000,
                       "min_gas_price":"5000",
                       "max_gas_price":"10000000000000000000000",
                       "block_producer_kickout_threshold":80,
                       "chunk_producer_kickout_threshold":90,
                       "online_min_threshold":[90, 100],
                       "online_max_threshold":[99, 100],
                       "gas_price_adjustment_rate":[1, 100 ],
                       "validators":[
                          {
                             "account_id":"masternode24.pool.f863973.m0",
                             "public_key":"ed25519:9E3JvrQN6VGDGg1WJ3TjBsNyfmrU6kncBcDvvJLj6qHr",
                             "amount":"2096547887468158804726149840014"
                          }
                       ],
                       "transaction_validity_period":86400,
                       "protocol_reward_rate":[1, 10],
                       "max_inflation_rate":[1, 20],
                       "total_supply":"2089646653180081825096998107194444",
                       "num_blocks_per_year":31536000,
                       "protocol_treasury_account":"near",
                       "fishermen_threshold":"340282366920938463463374607431768211455",
                       "minimum_stake_divisor":10,
                       "shard_layout":{
                          "V0":{
                             "num_shards":1,
                             "version":0
                          }
                       },
                       "simple_nightshade_shard_layout":{
                          "V1":{
                             "fixed_shards":[],
                             "boundary_accounts":[
                                "aurora"
                             ],
                             "shards_split_map":[
                                [0, 1, 2, 3]
                             ],
                             "to_parent_shard_map":[0, 0, 0, 0],
                             "version":1
                          }
                       },
                       "minimum_stake_ratio":[1, 6250]
                    }
                """,
                    GenesisConfig(
                        protocolVersion = 29,
                        genesisTime = ZonedDateTime.parse("2020-07-31T03:39:42.911378Z").withZoneSameInstant(
                            ZoneId.of("UTC")
                        ),
                        chainId = "testnet",
                        genesisHeight = 42376888,
                        numBlockProducerSeats = 200,
                        numBlockProducerSeatsPerShard = listOf(200),
                        avgHiddenValidatorSeatsPerShard = listOf(0),
                        dynamicResharding = false,
                        protocolUpgradeStakeThreshold = Rational(4, 5),
                        protocolUpgradeNumEpochs = 2,
                        epochLength = 43200,
                        gasLimit = 1000000000000000,
                        minGasPrice = Balance("5000"),
                        maxGasPrice = Balance("10000000000000000000000"),
                        blockProducerKickoutThreshold = 80,
                        chunkProducerKickoutThreshold = 90,
                        onlineMinThreshold = Rational(90, 100),
                        onlineMaxThreshold = Rational(99, 100),
                        gasPriceAdjustmentRate = Rational(1, 100),
                        validators = listOf(
                            AccountInfo(
                                accountId = "masternode24.pool.f863973.m0",
                                publicKey = PublicKey("ed25519:9E3JvrQN6VGDGg1WJ3TjBsNyfmrU6kncBcDvvJLj6qHr"),
                                amount = Balance("2096547887468158804726149840014")
                            )
                        ),
                        transactionValidityPeriod = 86400,
                        protocolRewardRate = Rational(1, 10),
                        maxInflationRate = Rational(1, 20),
                        numBlocksPerYear = 31536000,
                        protocolTreasuryAccount = "near",
                        fishermenThreshold = Balance("340282366920938463463374607431768211455"),
                        minimumStakeDivisor = 10,
                        shardLayout = ShardLayout.V0(
                            numShards = 1,
                            version = 0
                        ),
                        simpleNightshadeShardLayout = ShardLayout.V1(
                            fixedShards = listOf(),
                            boundaryAccounts = listOf("aurora"),
                            shardsSplitMap = listOf(
                                listOf(0, 1, 2, 3)
                            ),
                            toParentShardMap = listOf(0, 0, 0, 0),
                            version = 1
                        ),
                        minimumStakeRatio = Rational(1, 6250)
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as GenesisConfig shouldBe b
            }
        }
    }

    context("ProtocolConfig") {
        withData(
            nameFn = { "${it.typed.protocolVersion}" },
            listOf(
                TestData(
                    """
                    {
                       "protocol_version":50,
                       "genesis_time":"2020-07-31T03:39:42.911378Z",
                       "chain_id":"testnet",
                       "genesis_height":42376888,
                       "num_block_producer_seats":200,
                       "num_block_producer_seats_per_shard":[200, 200, 200, 200],
                       "avg_hidden_validator_seats_per_shard":[0, 0, 0, 0],
                       "dynamic_resharding":false,
                       "protocol_upgrade_stake_threshold":[4, 5],
                       "epoch_length":43200,
                       "gas_limit":1000000000000000,
                       "min_gas_price":"5000",
                       "max_gas_price":"10000000000000000000000",
                       "block_producer_kickout_threshold":80,
                       "chunk_producer_kickout_threshold":90,
                       "online_min_threshold":[
                          90,
                          100
                       ],
                       "online_max_threshold":[
                          99,
                          100
                       ],
                       "gas_price_adjustment_rate":[
                          1,
                          100
                       ],
                       "runtime_config":{
                          "storage_amount_per_byte":"10000000000000000000",
                          "transaction_costs":{
                             "action_receipt_creation_config":{
                                "send_sir":108059500000,
                                "send_not_sir":108059500000,
                                "execution":108059500000
                             },
                             "data_receipt_creation_config":{
                                "base_cost":{
                                   "send_sir":36486732312,
                                   "send_not_sir":36486732312,
                                   "execution":36486732312
                                },
                                "cost_per_byte":{
                                   "send_sir":17212011,
                                   "send_not_sir":17212011,
                                   "execution":17212011
                                }
                             },
                             "action_creation_config":{
                                "create_account_cost":{
                                   "send_sir":99607375000,
                                   "send_not_sir":99607375000,
                                   "execution":99607375000
                                },
                                "deploy_contract_cost":{
                                   "send_sir":184765750000,
                                   "send_not_sir":184765750000,
                                   "execution":184765750000
                                },
                                "deploy_contract_cost_per_byte":{
                                   "send_sir":6812999,
                                   "send_not_sir":6812999,
                                   "execution":6812999
                                },
                                "function_call_cost":{
                                   "send_sir":2319861500000,
                                   "send_not_sir":2319861500000,
                                   "execution":2319861500000
                                },
                                "function_call_cost_per_byte":{
                                   "send_sir":2235934,
                                   "send_not_sir":2235934,
                                   "execution":2235934
                                },
                                "transfer_cost":{
                                   "send_sir":115123062500,
                                   "send_not_sir":115123062500,
                                   "execution":115123062500
                                },
                                "stake_cost":{
                                   "send_sir":141715687500,
                                   "send_not_sir":141715687500,
                                   "execution":102217625000
                                },
                                "add_key_cost":{
                                   "full_access_cost":{
                                      "send_sir":101765125000,
                                      "send_not_sir":101765125000,
                                      "execution":101765125000
                                   },
                                   "function_call_cost":{
                                      "send_sir":102217625000,
                                      "send_not_sir":102217625000,
                                      "execution":102217625000
                                   },
                                   "function_call_cost_per_byte":{
                                      "send_sir":1925331,
                                      "send_not_sir":1925331,
                                      "execution":1925331
                                   }
                                },
                                "delete_key_cost":{
                                   "send_sir":94946625000,
                                   "send_not_sir":94946625000,
                                   "execution":94946625000
                                },
                                "delete_account_cost":{
                                   "send_sir":147489000000,
                                   "send_not_sir":147489000000,
                                   "execution":147489000000
                                }
                             },
                             "storage_usage_config":{
                                "num_bytes_account":100,
                                "num_extra_bytes_record":40
                             },
                             "burnt_gas_reward":[
                                3,
                                10
                             ],
                             "pessimistic_gas_price_inflation_ratio":[
                                103,
                                100
                             ]
                          },
                          "wasm_config":{
                             "ext_costs":{
                                "base":264768111,
                                "contract_compile_base":35445963,
                                "contract_compile_bytes":216750,
                                "read_memory_base":2609863200,
                                "read_memory_byte":3801333,
                                "write_memory_base":2803794861,
                                "write_memory_byte":2723772,
                                "read_register_base":2517165186,
                                "read_register_byte":98562,
                                "write_register_base":2865522486,
                                "write_register_byte":3801564,
                                "utf8_decoding_base":3111779061,
                                "utf8_decoding_byte":291580479,
                                "utf16_decoding_base":3543313050,
                                "utf16_decoding_byte":163577493,
                                "sha256_base":4540970250,
                                "sha256_byte":24117351,
                                "keccak256_base":5879491275,
                                "keccak256_byte":21471105,
                                "keccak512_base":5811388236,
                                "keccak512_byte":36649701,
                                "ripemd160_base":853675086,
                                "ripemd160_block":680107584,
                                "ecrecover_base":278821988457,
                                "log_base":3543313050,
                                "log_byte":13198791,
                                "storage_write_base":64196736000,
                                "storage_write_key_byte":70482867,
                                "storage_write_value_byte":31018539,
                                "storage_write_evicted_byte":32117307,
                                "storage_read_base":56356845750,
                                "storage_read_key_byte":30952533,
                                "storage_read_value_byte":5611005,
                                "storage_remove_base":53473030500,
                                "storage_remove_key_byte":38220384,
                                "storage_remove_ret_value_byte":11531556,
                                "storage_has_key_base":54039896625,
                                "storage_has_key_byte":30790845,
                                "storage_iter_create_prefix_base":0,
                                "storage_iter_create_prefix_byte":0,
                                "storage_iter_create_range_base":0,
                                "storage_iter_create_from_byte":0,
                                "storage_iter_create_to_byte":0,
                                "storage_iter_next_base":0,
                                "storage_iter_next_key_byte":0,
                                "storage_iter_next_value_byte":0,
                                "touching_trie_node":16101955926,
                                "promise_and_base":1465013400,
                                "promise_and_per_promise":5452176,
                                "promise_return":560152386,
                                "validator_stake_base":911834726400,
                                "validator_total_stake_base":911834726400
                             },
                             "grow_mem_cost":1,
                             "regular_op_cost":822756,
                             "limit_config":{
                                "max_gas_burnt":200000000000000,
                                "max_stack_height":16384,
                                "stack_limiter_version":1,
                                "initial_memory_pages":1024,
                                "max_memory_pages":2048,
                                "registers_memory_limit":1073741824,
                                "max_register_size":104857600,
                                "max_number_registers":100,
                                "max_number_logs":100,
                                "max_total_log_length":16384,
                                "max_total_prepaid_gas":300000000000000,
                                "max_actions_per_receipt":100,
                                "max_number_bytes_method_names":2000,
                                "max_length_method_name":256,
                                "max_arguments_length":4194304,
                                "max_length_returned_data":4194304,
                                "max_contract_size":4194304,
                                "max_transaction_size":4194304,
                                "max_length_storage_key":4194304,
                                "max_length_storage_value":4194304,
                                "max_promises_per_function_call_action":1024,
                                "max_number_input_data_dependencies":128,
                                "max_functions_number_per_contract":10000
                             }
                          },
                          "account_creation_config":{
                             "min_allowed_top_level_account_length":32,
                             "registrar_account_id":"registrar"
                          }
                       },
                       "transaction_validity_period":86400,
                       "protocol_reward_rate":[
                          1,
                          10
                       ],
                       "max_inflation_rate":[
                          1,
                          20
                       ],
                       "num_blocks_per_year":31536000,
                       "protocol_treasury_account":"near",
                       "fishermen_threshold":"340282366920938463463374607431768211455",
                       "minimum_stake_divisor":10
                    }
                """,
                    ProtocolConfig(
                        protocolVersion = 50,
                        genesisTime = ZonedDateTime.parse("2020-07-31T03:39:42.911378Z").withZoneSameInstant(
                            ZoneId.of("UTC")
                        ),
                        chainId = "testnet",
                        genesisHeight = 42376888,
                        numBlockProducerSeats = 200,
                        numBlockProducerSeatsPerShard = listOf(200, 200, 200, 200),
                        avgHiddenValidatorSeatsPerShard = listOf(0, 0, 0, 0),
                        dynamicResharding = false,
                        protocolUpgradeStakeThreshold = Rational(4, 5),
                        epochLength = 43200,
                        gasLimit = 1000000000000000,
                        minGasPrice = Balance("5000"),
                        maxGasPrice = Balance("10000000000000000000000"),
                        blockProducerKickoutThreshold = 80,
                        chunkProducerKickoutThreshold = 90,
                        onlineMinThreshold = Rational(90, 100),
                        onlineMaxThreshold = Rational(99, 100),
                        gasPriceAdjustmentRate = Rational(1, 100),
                        runtimeConfig = RuntimeConfig(
                            storageAmountPerByte = Balance("10000000000000000000"),
                            transactionCosts = RuntimeFeesConfig(
                                actionReceiptCreationConfig = Fee(108059500000, 108059500000, 108059500000),
                                dataReceiptCreationConfig = DataReceiptCreationConfig(
                                    baseCost = Fee(36486732312, 36486732312, 36486732312),
                                    costPerByte = Fee(17212011, 17212011, 17212011),
                                ),
                                actionCreationConfig = ActionCreationConfig(
                                    createAccountCost = Fee(99607375000, 99607375000, 99607375000),
                                    deployContractCost = Fee(184765750000, 184765750000, 184765750000),
                                    deployContractCostPerByte = Fee(6812999, 6812999, 6812999),
                                    functionCallCost = Fee(2319861500000, 2319861500000, 2319861500000),
                                    functionCallCostPerByte = Fee(2235934, 2235934, 2235934),
                                    transferCost = Fee(115123062500, 115123062500, 115123062500),
                                    stakeCost = Fee(141715687500, 141715687500, 102217625000),
                                    addKeyCost = AccessKeyCreationConfig(
                                        fullAccessCost = Fee(101765125000, 101765125000, 101765125000),
                                        functionCallCost = Fee(102217625000, 102217625000, 102217625000),
                                        functionCallCostPerByte = Fee(1925331, 1925331, 1925331),
                                    ),
                                    deleteKeyCost = Fee(94946625000, 94946625000, 94946625000),
                                    deleteAccountCost = Fee(147489000000, 147489000000, 147489000000),
                                ),
                                storageUsageConfig = StorageUsageConfig(
                                    numBytesAccount = 100,
                                    numExtraBytesRecord = 40
                                ),
                                burntGasReward = Rational(3, 10),
                                pessimisticGasPriceInflationRatio = Rational(103, 100)
                            ),
                            wasmConfig = VMConfig(
                                extCosts = ExtCostsConfig(
                                    base = 264768111,
                                    contractCompileBase = 35445963,
                                    contractCompileBytes = 216750,
                                    readMemoryBase = 2609863200,
                                    readMemoryByte = 3801333,
                                    writeMemoryBase = 2803794861,
                                    writeMemoryByte = 2723772,
                                    readRegisterBase = 2517165186,
                                    readRegisterByte = 98562,
                                    writeRegisterBase = 2865522486,
                                    writeRegisterByte = 3801564,
                                    utf8DecodingBase = 3111779061,
                                    utf8DecodingByte = 291580479,
                                    utf16DecodingBase = 3543313050,
                                    utf16DecodingByte = 163577493,
                                    sha256Base = 4540970250,
                                    sha256Byte = 24117351,
                                    keccak256Base = 5879491275,
                                    keccak256Byte = 21471105,
                                    keccak512Base = 5811388236,
                                    keccak512Byte = 36649701,
                                    ripemd160Base = 853675086,
                                    ripemd160Block = 680107584,
                                    ecrecoverBase = 278821988457,
                                    logBase = 3543313050,
                                    logByte = 13198791,
                                    storageWriteBase = 64196736000,
                                    storageWriteKeyByte = 70482867,
                                    storageWriteValueByte = 31018539,
                                    storageWriteEvictedByte = 32117307,
                                    storageReadBase = 56356845750,
                                    storageReadKeyByte = 30952533,
                                    storageReadValueByte = 5611005,
                                    storageRemoveBase = 53473030500,
                                    storageRemoveKeyByte = 38220384,
                                    storageRemoveRetValueByte = 11531556,
                                    storageHasKeyBase = 54039896625,
                                    storageHasKeyByte = 30790845,
                                    storageIterCreatePrefixBase = 0,
                                    storageIterCreatePrefixByte = 0,
                                    storageIterCreateRangeBase = 0,
                                    storageIterCreateFromByte = 0,
                                    storageIterCreateToByte = 0,
                                    storageIterNextBase = 0,
                                    storageIterNextKeyByte = 0,
                                    storageIterNextValueByte = 0,
                                    touchingTrieNode = 16101955926,
                                    promiseAndBase = 1465013400,
                                    promiseAndPerPromise = 5452176,
                                    promiseReturn = 560152386,
                                    validatorStakeBase = 911834726400,
                                    validatorTotalStakeBase = 911834726400
                                ),
                                growMemCost = 1,
                                regularOpCost = 822756,
                                limitConfig = VMLimitConfig(
                                    maxGasBurnt = 200000000000000,
                                    maxStackHeight = 16384,
                                    initialMemoryPages = 1024,
                                    maxMemoryPages = 2048,
                                    registersMemoryLimit = 1073741824,
                                    maxRegisterSize = 104857600,
                                    maxNumberRegisters = 100,
                                    maxNumberLogs = 100,
                                    maxTotalLogLength = 16384,
                                    maxTotalPrepaidGas = 300000000000000,
                                    maxActionsPerReceipt = 100,
                                    maxNumberBytesMethodNames = 2000,
                                    maxLengthMethodName = 256,
                                    maxArgumentsLength = 4194304,
                                    maxLengthReturnedData = 4194304,
                                    maxContractSize = 4194304,
                                    maxTransactionSize = 4194304,
                                    maxLengthStorageKey = 4194304,
                                    maxLengthStorageValue = 4194304,
                                    maxPromisesPerFunctionCallAction = 1024,
                                    maxNumberInputDataDependencies = 128,
                                    maxFunctionsNumberPerContract = 10000
                                )
                            ),
                            accountCreationConfig = AccountCreationConfig(
                                minAllowedTopLevelAccountLength = 32,
                                registrarAccountId = "registrar"
                            )
                        ),
                        transactionValidityPeriod = 86400,
                        protocolRewardRate = Rational(1, 10),
                        maxInflationRate = Rational(1, 20),
                        numBlocksPerYear = 31536000,
                        protocolTreasuryAccount = "near",
                        fishermenThreshold = Balance("340282366920938463463374607431768211455"),
                        minimumStakeDivisor = 10
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as ProtocolConfig shouldBe b
            }
        }
    }
})
