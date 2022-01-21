package org.near.api.model.primitives

import java.math.BigInteger


/**
Hash used by a struct implementing the Merkle tree.
 */
typealias MerkleHash = CryptoHash
/**
Validator identifier in current group.
 */
typealias ValidatorId = Long
/**
Mask which validators participated in multi sign.
 */
typealias ValidatorMask = List<Boolean>
/**
StorageUsage is used to count the amount of storage used by a contract.
 */
typealias StorageUsage = Long
/**
StorageUsageChange is used to count the storage usage within a single contract call.
 */
typealias StorageUsageChange = Long
/**
Nonce for transactions.
 */
typealias Nonce = Long
/**
Height of the block.
 */
typealias BlockHeight = Long
/**
Height of the epoch.
 */
typealias EpochHeight = Long
/**
Shard index, from 0 to NUM_SHARDS - 1.
 */
typealias ShardId = Long
/**
Balance is type for storing amounts of tokens.
 */
typealias Balance = BigInteger
/**
Gas is a type for storing amount of gas.
 */
typealias Gas = Long

/**
Number of blocks in current group.
 */
typealias NumBlocks = Long
/**
Number of shards in current group.
 */
typealias NumShards = Long
/**
Number of seats of validators (block producer or hidden ones) in current group (settlement).
 */
typealias NumSeats = Long
/**
Block height delta that measures the difference between `BlockHeight`s.
 */
typealias BlockHeightDelta = Long

typealias GCCount = Long

typealias ReceiptIndex = Long
typealias PromiseId = List<ReceiptIndex>
typealias ProtocolVersion = Int

/// Hash used by to store state root.
typealias StateRoot = CryptoHash


/// Set of serialized TrieNodes that are encoded in base64. Represent proof of inclusion of some TrieNode in the MerkleTrie.
typealias TrieProofPath = List<String>
