package antlapit.near.api.providers.model

import antlapit.near.api.providers.primitives.CryptoHash
import java.math.BigInteger


/**
Hash used by a struct implementing the Merkle tree.
 */
typealias MerkleHash = CryptoHash
/**
Validator identifier in current group.
 */
typealias ValidatorId = ULong
/**
Mask which validators participated in multi sign.
 */
typealias ValidatorMask = List<Boolean>
/**
StorageUsage is used to count the amount of storage used by a contract.
 */
typealias StorageUsage = ULong
/**
StorageUsageChange is used to count the storage usage within a single contract call.
 */
typealias StorageUsageChange = Long
/**
Nonce for transactions.
 */
typealias Nonce = ULong
/**
Height of the block.
 */
typealias BlockHeight = ULong
/**
Height of the epoch.
 */
typealias EpochHeight = ULong
/**
Shard index, from 0 to NUM_SHARDS - 1.
 */
typealias ShardId = ULong
/**
Balance is type for storing amounts of tokens.
 */
typealias Balance = BigInteger
/**
Gas is a type for storing amount of gas.
 */
typealias Gas = ULong

/**
Number of blocks in current group.
 */
typealias NumBlocks = ULong
/**
Number of shards in current group.
 */
typealias NumShards = ULong
/**
Number of seats of validators (block producer or hidden ones) in current group (settlement).
 */
typealias NumSeats = ULong
/**
Block height delta that measures the difference between `BlockHeight`s.
 */
typealias BlockHeightDelta = ULong

typealias GCCount = ULong

typealias ReceiptIndex = ULong
typealias PromiseId = List<ReceiptIndex>
typealias ProtocolVersion = UInt

// TODO signature
typealias Signature = String
