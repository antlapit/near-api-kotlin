package antlapit.near.api.providers.model.primitives

import java.math.BigInteger


/**
Hash used by a struct implementing the Merkle tree.
 */
typealias MerkleHash = CryptoHash
/**
Validator identifier in current group.
 */
typealias ValidatorId = Long // TODO Rust u64
/**
Mask which validators participated in multi sign.
 */
typealias ValidatorMask = List<Boolean>
/**
StorageUsage is used to count the amount of storage used by a contract.
 */
typealias StorageUsage = Long // TODO Rust u64
/**
StorageUsageChange is used to count the storage usage within a single contract call.
 */
typealias StorageUsageChange = Long
/**
Nonce for transactions.
 */
typealias Nonce = Long // TODO Rust u64
/**
Height of the block.
 */
typealias BlockHeight = Long // TODO Rust u64
/**
Height of the epoch.
 */
typealias EpochHeight = Long // TODO Rust u64
/**
Shard index, from 0 to NUM_SHARDS - 1.
 */
typealias ShardId = Long // TODO Rust u64
/**
Balance is type for storing amounts of tokens.
 */
typealias Balance = BigInteger
/**
Gas is a type for storing amount of gas.
 */
typealias Gas = Long // TODO Rust u64

/**
Number of blocks in current group.
 */
typealias NumBlocks = Long // TODO Rust u64
/**
Number of shards in current group.
 */
typealias NumShards = Long // TODO Rust u64
/**
Number of seats of validators (block producer or hidden ones) in current group (settlement).
 */
typealias NumSeats = Long // TODO Rust u64
/**
Block height delta that measures the difference between `BlockHeight`s.
 */
typealias BlockHeightDelta = Long // TODO Rust u64

typealias GCCount = Long // TODO Rust u64

typealias ReceiptIndex = Long // TODO Rust u64
typealias PromiseId = List<ReceiptIndex>
typealias ProtocolVersion = Int // TODO UInt

/// Hash used by to store state root.
typealias StateRoot = CryptoHash


/// Set of serialized TrieNodes that are encoded in base64. Represent proof of inclusion of some TrieNode in the MerkleTrie.
typealias TrieProofPath = List<String>
