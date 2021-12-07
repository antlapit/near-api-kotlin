package antlapit.near.api.json

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.model.accesskey.AccessKeyPermission
import antlapit.near.api.providers.model.block.*
import antlapit.near.api.providers.model.primitives.SlashedValidator
import antlapit.near.api.providers.model.transaction.SignedTransactionView
import antlapit.near.api.providers.model.validators.ValidatorStakeStructVersion
import antlapit.near.api.providers.model.validators.ValidatorStakeView
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.math.BigInteger

@ExperimentalKotest
class BlockDeserializationTest : FunSpec({

    val objectMapper = ObjectMapperFactory.newInstance()

    context("Block") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                    {
                       "author":"node0",
                       "header":{
                          "height":74058151,
                          "prev_height":74058150,
                          "epoch_id":"GXoQHNswXm4qYTxyAjw2eKR49QUQP5VmqTuG3BobmB8A",
                          "next_epoch_id":"7c5dkWsMtdZFhwsHXQh5gYfNeB5qatmJiHrk7VSxpKC5",
                          "hash":"3K8peS3TCF26rtaFipasWfwoDoUkZrqVToeuqCtcQQXy",
                          "prev_hash":"Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                          "prev_state_root":"4MAywgJoSQMEZr7LbwDm6r4zMq2NqWj1AroFCwuYvqgR",
                          "chunk_receipts_root":"6kXCKYz8gLTH8c3YGnfKwGmGwkLHmvdypeAmKyEdXbJy",
                          "chunk_headers_root":"A8nzrZSnY9UP4nFMJXL7uQApyufn9AaAZX78YRhdGr2t",
                          "chunk_tx_root":"2DoUX6XsDr5BxRN821ZxTLYYcQBzSSxPMTqMU4TLfu35",
                          "outcome_root":"7vCSQYkR4JuKsikg9hGFitr4iZwq6ENw5UPpFkakqSbD",
                          "chunks_included":4,
                          "challenges_root":"11111111111111111111111111111111",
                          "timestamp":1638819731754221213,
                          "timestamp_nanosec":"1638819731754221213",
                          "random_value":"2o77fd3wUJ214DmQP8q3vcN6nzFjuTNVYNQs4VP2eWt8",
                          "validator_proposals":[
                             {
                                "account_id":"node0",
                                "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                "stake":"1"
                            }
                          ],
                          "chunk_mask":[
                             true,
                             false
                          ],
                          "gas_price":"100000000",
                          "block_ordinal":30362168,
                          "rent_paid":"0",
                          "validator_reward":"0",
                          "total_supply":"2162117020300076801612056411015747",
                          "challenges_result":[
                             {
                                "account_id": "account",
                                "is_double_sign": true
                             }
                          ],
                          "last_final_block":"3QJeAVecA6THMN7G5zKXYqg3ozKwLa7A1Z9Lk5NznvHP",
                          "last_ds_final_block":"Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                          "next_bp_hash":"Shdj2X9WLDDnFWqg5oiWKMYVTPVigVp2FPjYu3L3LCk",
                          "block_merkle_root":"ao6qRuA4LCB4pKKxgjf224zyYZqmrAF5hsQyt73xEHh",
                          "epoch_sync_data_hash":"test",
                          "approvals":[
                             "ed25519:2fxFRun5bNpxYG8AGUvquYNtCsv8hRcpSp45Fxh7XY6Zo5nQvvKZgwGPt5pkvPi5K9Ret1SdS1hoEXV7EK6fibUw",
                             null
                          ],
                          "signature":"ed25519:5diBFvg5qK49fJVXxPTGVDxteFWk91Nc7RG7KbZ4oNVrqLv37gL6vUCNE8Jrx1JXcL2Z7i7C9Vwmt5r8QXQRvPNC",
                          "latest_protocol_version":49
                       },
                       "chunks":[
                          {
                             "chunk_hash":"2fbKc7XuEed1qBqA39ZrNZYUCAmNPDZhxamJ8jnTU2jB",
                             "prev_block_hash":"Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                             "outcome_root":"Hfu9kpUbncjqX88bCmMgsNMWVEQNTW8kUXfTnEzYnZLq",
                             "prev_state_root":"CEXNaU7NvNVbigRYyiMX4gL5hX8Tvu9f22y9VCZ4X1jo",
                             "encoded_merkle_root":"5TxYudsfZd2FZoMyJEZAP19ASov2ZD43N8ZWv8mKzWgx",
                             "encoded_length":8,
                             "height_created":74058151,
                             "height_included":74058151,
                             "shard_id":0,
                             "gas_used":446365125000,
                             "gas_limit":1000000000000000,
                             "rent_paid":"0",
                             "validator_reward":"0",
                             "balance_burnt":"0",
                             "outgoing_receipts_root":"8s41rye686T2ronWmFE38ji19vgeb6uPxjYMPt8y8pSV",
                             "tx_root":"11111111111111111111111111111111",
                             "validator_proposals":[
                                {
                                    "account_id":"node0",
                                    "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    "stake":"1"
                                }
                             ],
                             "signature":"ed25519:2qeQo7GumYdpv6xCPSnqt356fhWmQNrJkGUhM36AkRGpsshq9R4jgd4QZPyxYNRiRTozit2SAqGUgiXfV6Ug58St"
                          }
                       ]
                    }
                    """,
                    Block(
                        author = "node0",
                        header = BlockHeader(
                            height = 74058151,
                            prevHeight = 74058150,
                            epochId = "GXoQHNswXm4qYTxyAjw2eKR49QUQP5VmqTuG3BobmB8A",
                            nextEpochId = "7c5dkWsMtdZFhwsHXQh5gYfNeB5qatmJiHrk7VSxpKC5",
                            hash = "3K8peS3TCF26rtaFipasWfwoDoUkZrqVToeuqCtcQQXy",
                            prevHash = "Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                            prevStateRoot = "4MAywgJoSQMEZr7LbwDm6r4zMq2NqWj1AroFCwuYvqgR",
                            chunkReceiptsRoot = "6kXCKYz8gLTH8c3YGnfKwGmGwkLHmvdypeAmKyEdXbJy",
                            chunkHeadersRoot = "A8nzrZSnY9UP4nFMJXL7uQApyufn9AaAZX78YRhdGr2t",
                            chunkTxRoot = "2DoUX6XsDr5BxRN821ZxTLYYcQBzSSxPMTqMU4TLfu35",
                            outcomeRoot = "7vCSQYkR4JuKsikg9hGFitr4iZwq6ENw5UPpFkakqSbD",
                            chunksIncluded = 4,
                            challengesRoot = "11111111111111111111111111111111",
                            timestampNanosec = 1638819731754221213,
                            randomValue = "2o77fd3wUJ214DmQP8q3vcN6nzFjuTNVYNQs4VP2eWt8",
                            validatorProposals = listOf(
                                ValidatorStakeView(
                                    validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                    accountId = "node0",
                                    publicKey = "ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    stake = BigInteger("1")
                                )
                            ),
                            chunkMask = listOf(true, false),
                            gasPrice = BigInteger("100000000"),
                            blockOrdinal = 30362168,
                            totalSupply = BigInteger("2162117020300076801612056411015747"),
                            challengesResult = listOf(
                                SlashedValidator(
                                    accountId = "account",
                                    isDoubleSign = true
                                )
                            ),
                            lastFinalBlock = "3QJeAVecA6THMN7G5zKXYqg3ozKwLa7A1Z9Lk5NznvHP",
                            lastDsFinalBlock = "Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                            nextBpHash = "Shdj2X9WLDDnFWqg5oiWKMYVTPVigVp2FPjYu3L3LCk",
                            blockMerkleRoot = "ao6qRuA4LCB4pKKxgjf224zyYZqmrAF5hsQyt73xEHh",
                            epochSyncDataHash = "test",
                            approvals = listOf(
                                "ed25519:2fxFRun5bNpxYG8AGUvquYNtCsv8hRcpSp45Fxh7XY6Zo5nQvvKZgwGPt5pkvPi5K9Ret1SdS1hoEXV7EK6fibUw",
                                null
                            ),
                            signature = "ed25519:5diBFvg5qK49fJVXxPTGVDxteFWk91Nc7RG7KbZ4oNVrqLv37gL6vUCNE8Jrx1JXcL2Z7i7C9Vwmt5r8QXQRvPNC",
                            latestProtocolVersion = 49
                        ),
                        chunks = listOf(
                            ChunkHeader(
                                chunkHash = "2fbKc7XuEed1qBqA39ZrNZYUCAmNPDZhxamJ8jnTU2jB",
                                prevBlockHash = "Gkbn6FFnCFhLSQSfBEBE6XJUg4iLivMhMMfAGvxQR623",
                                outcomeRoot = "Hfu9kpUbncjqX88bCmMgsNMWVEQNTW8kUXfTnEzYnZLq",
                                prevStateRoot = "CEXNaU7NvNVbigRYyiMX4gL5hX8Tvu9f22y9VCZ4X1jo",
                                encodedMerkleRoot = "5TxYudsfZd2FZoMyJEZAP19ASov2ZD43N8ZWv8mKzWgx",
                                encodedLength = 8,
                                heightCreated = 74058151,
                                heightIncluded = 74058151,
                                shardId = 0,
                                gasUsed = 446365125000,
                                gasLimit = 1000000000000000,
                                balanceBurnt = BigInteger.ZERO,
                                outgoingReceiptsRoot = "8s41rye686T2ronWmFE38ji19vgeb6uPxjYMPt8y8pSV",
                                txRoot = "11111111111111111111111111111111",
                                validatorProposals = listOf(
                                    ValidatorStakeView(
                                        validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                        accountId = "node0",
                                        publicKey = "ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                        stake = BigInteger("1")
                                    )
                                ),
                                signature = "ed25519:2qeQo7GumYdpv6xCPSnqt356fhWmQNrJkGUhM36AkRGpsshq9R4jgd4QZPyxYNRiRTozit2SAqGUgiXfV6Ug58St"
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as Block shouldBe b
            }

        }
    }

    context("Chunk") {
        withData(
            nameFn = { "${it.typed}" },
            listOf(
                TestData(
                    """
                {
                   "author":"shardlabs.pool.f863973.m0",
                   "header":{
                      "chunk_hash":"5u7LRrVvN8mczkjwryqNKtGW8ZnR5EF5UxXXbn2nbb4Z",
                      "prev_block_hash":"34g44Rvnq3dqictWNJKctdNB3hEF2HFwdpST3Gn25pw6",
                      "outcome_root":"11111111111111111111111111111111",
                      "prev_state_root":"3igzPCpjceAwS5jFhfZytbFmt5CD5iHxFiz5RwKMeW7w",
                      "encoded_merkle_root":"7tJSLDF5NwipvDxLPdgXSqUdnHvrUdBuE5DarZLg9GQP",
                      "encoded_length":284,
                      "height_created":74113803,
                      "height_included":74113803,
                      "shard_id":0,
                      "gas_used":0,
                      "gas_limit":1000000000000000,
                      "rent_paid":"0",
                      "validator_reward":"0",
                      "balance_burnt":"0",
                      "outgoing_receipts_root":"8s41rye686T2ronWmFE38ji19vgeb6uPxjYMPt8y8pSV",
                      "tx_root":"8gg9hRerEF9D3NiR9yCDNY81sPK7ann79K1D7T3iDAuW",
                      "validator_proposals":[
                         {
                            "account_id":"node0",
                            "public_key":"ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                            "stake":"1"
                        }
                      ],
                      "signature":"ed25519:2SybLgXLFcFVpDqtKn3WX7ySkLpWwfgecAFeBZByyJvbB4ZfgHirSGxBWAmdCetJtQsM31NASDcQvkG9MPySfdzF"
                   },
                   "transactions":[
                      {
                         "signer_id":"art.artcoin.testnet",
                         "public_key":"ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                         "nonce":428216,
                         "receiver_id":"art.artcoin.testnet",
                         "actions":["CreateAccount"],
                         "signature":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                         "hash":"EBns1TFfY2MiM4TjFUhcNSV5h21UiGem94vQNPPEXpAz"
                      }
                   ],
                   "receipts":[
                      {
                          "predecessor_id":"account1",
                          "receiver_id":"account2",
                          "receipt_id":"ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                          "receipt": {
                              "Data": {
                                  "data_id":"ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                                  "data":"data"
                              }
                          }
                      }
                   ]
                }    
                """,
                    Chunk(
                        author = "shardlabs.pool.f863973.m0",
                        header = ChunkHeader(
                            chunkHash = "5u7LRrVvN8mczkjwryqNKtGW8ZnR5EF5UxXXbn2nbb4Z",
                            prevBlockHash = "34g44Rvnq3dqictWNJKctdNB3hEF2HFwdpST3Gn25pw6",
                            outcomeRoot = "11111111111111111111111111111111",
                            prevStateRoot = "3igzPCpjceAwS5jFhfZytbFmt5CD5iHxFiz5RwKMeW7w",
                            encodedMerkleRoot = "7tJSLDF5NwipvDxLPdgXSqUdnHvrUdBuE5DarZLg9GQP",
                            encodedLength = 284,
                            heightCreated = 74113803,
                            heightIncluded = 74113803,
                            shardId = 0,
                            gasUsed = 0,
                            gasLimit = 1000000000000000,
                            balanceBurnt = BigInteger("0"),
                            outgoingReceiptsRoot = "8s41rye686T2ronWmFE38ji19vgeb6uPxjYMPt8y8pSV",
                            txRoot = "8gg9hRerEF9D3NiR9yCDNY81sPK7ann79K1D7T3iDAuW",
                            validatorProposals = listOf(
                                ValidatorStakeView(
                                    validatorStakeStructVersion = ValidatorStakeStructVersion.V1,
                                    accountId = "node0",
                                    publicKey = "ed25519:ydgzeXHJ5Xyt7M1gXLxqLBW1Ejx6scNV5Nx2pxFM8su",
                                    stake = BigInteger("1")
                                )
                            ),
                            signature = "ed25519:2SybLgXLFcFVpDqtKn3WX7ySkLpWwfgecAFeBZByyJvbB4ZfgHirSGxBWAmdCetJtQsM31NASDcQvkG9MPySfdzF"
                        ),
                        transactions = listOf(
                            SignedTransactionView(
                                signerId = "art.artcoin.testnet",
                                publicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                                nonce = 428216,
                                receiverId = "art.artcoin.testnet",
                                actions = listOf(Action.CreateAccount),
                                signature = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                                hash = "EBns1TFfY2MiM4TjFUhcNSV5h21UiGem94vQNPPEXpAz"
                            )
                        ),
                        receipts = listOf(
                            Receipt(
                                predecessorId = "account1",
                                receiverId = "account2",
                                receiptId = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                                receipt = ReceiptInfo.Data(
                                    dataId = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                                    data = "data"
                                )
                            )
                        )
                    )
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as Chunk shouldBe b
            }
        }
    }

    context("Receipt info") {
        withData(
            nameFn = { "${it.typed}" },
            TestData(
                """
                {
                    "Data": {
                        "data_id":"ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                        "data":"data"
                    }
                }
            """, ReceiptInfo.Data(
                    dataId = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                    data = "data"
                )
            ),
            TestData(
                """
                {
                    "Action": {
                        "signer_id": "signer",
                        "signer_public_key": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                        "gas_price": 1,
                        "output_data_receivers": [{
                            "data_id": "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                            "receiver_id": "receiver"
                        }],
                        "input_data_ids": ["ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"],
                        "actions": ["CreateAccount"]
                    }
                }
            """, ReceiptInfo.Action(
                    signerId = "signer",
                    signerPublicKey = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                    gasPrice = BigInteger.ONE,
                    outputDataReceivers = listOf(
                        DataReceiver(
                            dataId = "ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw",
                            receiverId = "receiver"
                        )
                    ),
                    inputDataIds = listOf("ed25519:4o6mz55p1mNmfwg5EeTDXdtYFxQev672eU5wy5RjRCbw"),
                    actions = listOf(Action.CreateAccount),
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as ReceiptInfo shouldBe b
            }
        }
    }

    context("Action") {
        withData(
            nameFn = { "${it.typed}" },
            TestData("\"CreateAccount\"", Action.CreateAccount),
            TestData(
                """
                {
                    "DeployContract": {
                        "code": "contractcode"
                    }
                }
            """, Action.DeployContract(code = "contractcode")
            ),
            TestData(
                """
                {
                    "FunctionCall": {
                        "method_name": "test",
                        "args": "args",
                        "gas": 1,
                        "deposit": 10
                    }
                }
            """, Action.FunctionCall(
                    methodName = "test",
                    args = "args",
                    gas = 1,
                    deposit = BigInteger.TEN
                )
            ),
            TestData(
                """
                {
                    "Transfer": {
                        "deposit": 100
                    }
                }
            """, Action.Transfer(
                    deposit = BigInteger.valueOf(100)
                )
            ),
            TestData(
                """
                {
                    "Stake": {
                        "stake": 200,
                        "public_key": "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"
                    }
                }
            """, Action.Stake(
                    stake = BigInteger.valueOf(200),
                    publicKey = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"
                )
            ),
            TestData(
                """
                {
                    "AddKey": {
                        "public_key": "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                        "access_key": {
                            "nonce": 69877007000001,
                            "permission": "FullAccess"
                        }
                    }
                }
            """, Action.AddKey(
                    publicKey = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                    accessKey = AccessKey(
                        nonce = 69877007000001,
                        permission = AccessKeyPermission.FullAccess
                    )
                )
            ),
            TestData(
                """
                {
                    "DeleteKey": {
                        "public_key": "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"
                    }
                }
            """, Action.DeleteKey(
                    publicKey = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                )
            ),
            TestData(
                """
                {
                    "DeleteAccount": {
                        "beneficiary_id": "benefeciary"
                    }
                }
            """, Action.DeleteAccount(
                    beneficiaryId = "benefeciary"
                )
            ),
            TestData(
                """
                {
                    "StakeChunkOnly": {
                        "stake": 100,
                        "public_key": "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog"
                    }
                }
            """, Action.StakeChunkOnly(
                    stake = BigInteger.valueOf(100),
                    publicKey = "ed25519:SkvGRgDPF2vPM8uiusmYNAoXtv5421yptvwS82cXQZvtkPb5ynyqXhyPaPoaLw9LE86bHahjgkC4VrSgr6aXEog",
                )
            )
        ) { (a, b) ->
            shouldNotThrow<Throwable> {
                objectMapper.readValue(a) as Action shouldBe b
            }
        }
    }
})
