package org.near.api.endpoints

import org.near.api.model.config.GenesisConfig
import org.near.api.model.config.ProtocolConfig
import org.near.api.model.primitives.BlockHeight
import org.near.api.model.primitives.CryptoHash
import org.near.api.provider.BlockSearch
import org.near.api.provider.JsonRpcProvider

/**
 * RPC endpoint for getting config info
 * @link https://docs.near.org/docs/api/rpc/protocol
 */
class ProtocolRpcProvider(private val jsonRpcProvider: JsonRpcProvider) : ProtocolProvider {

    /**
     * @link https://docs.near.org/docs/api/rpc/protocol#genesis-config
     */
    override suspend fun getGenesisConfig(timeout: Long?): GenesisConfig =
        jsonRpcProvider.sendRpc(
            method = "EXPERIMENTAL_genesis_config",
            timeout
        )

    /**
     * @link https://docs.near.org/docs/api/rpc/config#config-config
     */
    private suspend fun getProtocolConfigGeneral(blockSearch: BlockSearch, timeout: Long?): ProtocolConfig =
        jsonRpcProvider.sendRpc(
            method = "EXPERIMENTAL_protocol_config",
            blockSearch = blockSearch,
            timeout = timeout
        )

    override suspend fun getLatestProtocolConfig(finality: Finality, timeout: Long?) =
        getProtocolConfigGeneral(BlockSearch.ofFinality(finality), timeout)

    override suspend fun getProtocolConfig(blockId: BlockHeight, timeout: Long?) =
        getProtocolConfigGeneral(BlockSearch.fromBlockId(blockId), timeout)

    override suspend fun getProtocolConfig(blockHash: CryptoHash, timeout: Long?) =
        getProtocolConfigGeneral(BlockSearch.fromBlockHash(blockHash), timeout)

}
