package org.near.api.providers.model.block

import org.near.api.providers.model.accesskey.AccessKey
import org.near.api.providers.model.primitives.AccountId
import org.near.api.providers.model.primitives.Balance
import org.near.api.providers.model.primitives.Gas
import org.near.api.providers.model.primitives.PublicKey
import org.near.api.providers.model.rust.RustEnum

@RustEnum
sealed interface Action {
    object CreateAccount : Action

    data class DeployContract(
        val code: String
    ) : Action

    data class FunctionCall(
        val methodName: String,
        val args: String,
        val gas: Gas,
        val deposit: Balance,
    ) : Action

    data class Transfer(
        val deposit: Balance
    ) : Action

    data class Stake(
        val stake: Balance,
        val publicKey: PublicKey,
    ) : Action

    data class AddKey(
        val publicKey: PublicKey,
        val accessKey: AccessKey,
    ) : Action

    data class DeleteKey(
        val publicKey: PublicKey,
    ) : Action

    data class DeleteAccount(
        val beneficiaryId: AccountId,
    ) : Action

    data class StakeChunkOnly(
        val stake: Balance,
        val publicKey: PublicKey,
    ) : Action
}
