package org.near.api.json

import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import org.near.api.common.TestData
import org.near.api.model.networkinfo.*
import org.near.api.model.primitives.PublicKey
import org.near.api.model.validators.*
import java.math.BigInteger
import java.net.InetAddress
import java.net.InetSocketAddress
import java.time.ZoneId
import java.time.ZonedDateTime

@ExperimentalKotest
class NetworkDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Node status") {
        withData(
            nameFn = { "${it.typed.version} ${it.typed.syncInfo}" },
            listOf(
                TestData(
                    """
                    {
                       "version":{
                          "version":"1.23.0-rc.1",
                          "build":"crates-0.10.0-70-g93e8521c9"
                       },
                       "chain_id":"testnet",
                       "protocol_version":49,
                       "latest_protocol_version":49,
                       "rpc_addr":"0.0.0.0:4040",
                       "validators":[
                          {
                             "account_id":"node0",
                             "is_slashed":true
                          }
                       ],
                       "sync_info":{
                          "latest_block_hash":"36egxBBS4vMRrJKQodJpmHKS9EkBPV3Z5CcDEqRqhh8R",
                          "latest_block_height":74020853,
                          "latest_state_root":"wYgU6pH2kZXUBgaZCm7mT1cAqxjnqbjvctorHT4YUS6",
                          "latest_block_time":"2021-12-06T12:05:11.274067480Z",
                          "syncing":false,
                          "earliest_block_hash":"6j3e5kJD44ZeZDLssDcSs5LVr4LhywvFFaRsBDBNZTgT",
                          "earliest_block_height":73814012,
                          "earliest_block_time":"2021-12-04T14:47:01.855209095Z"
                       },
                       "validator_account_id": "validator"
                    }
                """,
                    NodeStatus(
                        version = NodeVersion(
                            version = "1.23.0-rc.1",
                            build = "crates-0.10.0-70-g93e8521c9"
                        ),
                        chainId = "testnet",
                        protocolVersion = 49,
                        latestProtocolVersion = 49,
                        rpcAddr = InetSocketAddress(
                            InetAddress.getByName("0.0.0.0"), 4040
                        ),
                        validators = listOf(
                            ValidatorInfo(
                                accountId = "node0",
                                isSlashed = true
                            )
                        ),
                        syncInfo = StatusSyncInfo(
                            latestBlockHash = "36egxBBS4vMRrJKQodJpmHKS9EkBPV3Z5CcDEqRqhh8R",
                            latestBlockHeight = 74020853,
                            latestStateRoot = "wYgU6pH2kZXUBgaZCm7mT1cAqxjnqbjvctorHT4YUS6",
                            latestBlockTime = ZonedDateTime.parse("2021-12-06T12:05:11.274067480Z").withZoneSameInstant(
                                ZoneId.of("UTC")
                            ),
                            syncing = false,
                            earliestBlockHash = "6j3e5kJD44ZeZDLssDcSs5LVr4LhywvFFaRsBDBNZTgT",
                            earliestBlockHeight = 73814012,
                            earliestBlockTime = ZonedDateTime.parse("2021-12-04T14:47:01.855209095Z")
                                .withZoneSameInstant(
                                    ZoneId.of("UTC")
                                )
                        ),
                        validatorAccountId = "validator",
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as NodeStatus shouldBe b
            }

        }
    }

    context("Network Info") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                        {
                           "active_peers":[
                              {
                                 "id":"ed25519:B1MM6LsP2CxqK2HYk6bThpqhnjNotTjNTgsEuXUoFhCT",
                                 "addr":"135.181.162.15:24567",
                                 "account_id":"test"
                              }
                           ],
                           "num_active_peers":30,
                           "peer_max_count":40,
                           "sent_bytes_per_sec":799784,
                           "received_bytes_per_sec":1038176,
                           "known_producers":[
                              {
                                 "account_id":"account-1.pool.f863973.m0",
                                 "addr":"135.181.162.15:24567",
                                 "peer_id":"ed25519:49m4CpgA6A871aPzXiXxQEXfD8TsDgx3eJka3HSBtPWT"
                              }
                           ]
                        }
                    """,
                    NetworkInfo(
                        activePeers = listOf(
                            PeerInfo(
                                id = "ed25519:B1MM6LsP2CxqK2HYk6bThpqhnjNotTjNTgsEuXUoFhCT",
                                addr = InetSocketAddress(
                                    InetAddress.getByName("135.181.162.15"), 24567
                                ),
                                accountId = "test"
                            )
                        ),
                        numActivePeers = 30,
                        peerMaxCount = 40,
                        sentBytesPerSec = 799784,
                        receivedBytesPerSec = 1038176,
                        knownProducers = listOf(
                            KnownProducer(
                                accountId = "account-1.pool.f863973.m0",
                                addr = InetSocketAddress(
                                    InetAddress.getByName("135.181.162.15"), 24567
                                ),
                                peerId = "ed25519:49m4CpgA6A871aPzXiXxQEXfD8TsDgx3eJka3HSBtPWT"
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as NetworkInfo shouldBe b
            }

        }
    }

    context("Validator Info") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                        {
                           "current_validators":[
                              {
                                 "account_id":"node0",
                                 "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                 "is_slashed":false,
                                 "stake":"12862979641809099040925847359251",
                                 "shards":[0, 1],
                                 "num_produced_blocks":4442,
                                 "num_expected_blocks":4442
                              }
                           ],
                           "next_validators":[
                                {
                                    "account_id":"node0",
                                    "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    "stake":"12878109767515481052269040578386",
                                    "shards":[0,1,2,3]
                                }
                           ], 
                           "current_fishermen":[
                              {
                                    "validator_stake_struct_version":"V1",
                                    "account_id":"node0",
                                    "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    "stake":"1"
                              }
                           ],
                           "next_fishermen":[
                              {
                                    "validator_stake_struct_version":"V1",
                                    "account_id":"node0",
                                    "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    "stake":"2"
                              }
                           ],
                           "current_proposals":[
                              {
                                 "validator_stake_struct_version":"V1",
                                 "account_id":"node0.pool.f863973.m0",
                                 "public_key":"ed25519:7qhA31M3SaT4ntXwFSdBHewYzMTw3GccsBp2D3NeCb1Y",
                                 "stake":"491390068923117066486017091877"
                              }
                           ],
                           "prev_epoch_kickout":[
                              {
                                 "account_id":"node.pool.f863973.m0",
                                 "reason": "Slashed"
                              }
                           ],
                           "epoch_start_height":73999652,
                           "epoch_height":732
                        }
                    """,
                    EpochValidatorInfo(
                        epochStartHeight = 73999652,
                        epochHeight = 732,
                        currentValidators = listOf(
                            CurrentEpochValidatorInfo(
                                accountId = "node0",
                                publicKey = PublicKey("ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su"),
                                isSlashed = false,
                                stake = BigInteger("12862979641809099040925847359251"),
                                shards = listOf(0, 1),
                                numProducedBlocks = 4442,
                                numExpectedBlocks = 4442
                            )
                        ),
                        nextValidators = listOf(
                            NextEpochValidatorInfo(
                                accountId = "node0",
                                publicKey = PublicKey("ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su"),
                                stake = BigInteger("12878109767515481052269040578386"),
                                shards = listOf(0, 1, 2, 3),
                            )
                        ),
                        currentFishermen = listOf(
                            ValidatorStakeView(
                                validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                accountId = "node0",
                                publicKey = PublicKey("ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su"),
                                stake = BigInteger("1")
                            )
                        ),
                        nextFishermen = listOf(
                            ValidatorStakeView(
                                validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                accountId = "node0",
                                publicKey = PublicKey("ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su"),
                                stake = BigInteger("2")
                            )
                        ),
                        currentProposals = listOf(
                            ValidatorStakeView(
                                validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                accountId = "node0.pool.f863973.m0",
                                publicKey = PublicKey("ed25519:7qhA31M3SaT4ntXwFSdBHewYzMTw3GccsBp2D3NeCb1Y"),
                                stake = BigInteger("491390068923117066486017091877")
                            )
                        ),
                        prevEpochKickout = listOf(
                            ValidatorKickoutView("node.pool.f863973.m0", ValidatorKickoutReason.Slashed)
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as EpochValidatorInfo shouldBe b
            }

        }
    }

    context("Validator kickout reason") {
        withData(
            nameFn = { "${it.typed}" },
            TestData("\"Slashed\"", ValidatorKickoutReason.Slashed),
            TestData(
                """
                    {
                        "NotEnoughBlocks": {
                            "produced": 100,
                            "expected": 200
                        }
                    }
                """,
                ValidatorKickoutReason.NotEnoughBlocks(
                    produced = 100,
                    expected = 200
                )
            ),
            TestData(
                """
                    {
                        "NotEnoughChunks": {
                            "produced": 100,
                            "expected": 200
                        }
                    }
                """,
                ValidatorKickoutReason.NotEnoughChunks(
                    produced = 100,
                    expected = 200
                )
            ),
            TestData("\"Unstaked\"", ValidatorKickoutReason.Unstaked),
            TestData(
                """
                    {
                        "NotEnoughStake": {
                            "stake_u128": 100,
                            "threshold_u128": 200
                        }
                    }
                """,
                ValidatorKickoutReason.NotEnoughStake(
                    stakeU128 = BigInteger.valueOf(100),
                    thresholdU128 = BigInteger.valueOf(200)
                )
            ),
            TestData("\"DidNotGetASeat\"", ValidatorKickoutReason.DidNotGetASeat)
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as ValidatorKickoutReason shouldBe b
            }
        }
    }
})
