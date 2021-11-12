package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.Gas
import antlapit.near.api.providers.primitives.PublicKey

interface Action

enum class NotParametrizedAction : Action {
    CreateAccount
}

interface ParametrizedAction : Action

data class DeployContract(
    val code: String
) : ParametrizedAction

data class FunctionCall(
    val methodName: String,
    val args: String,
    val gas: Gas,
    val deposit: Balance,
) : ParametrizedAction

data class Transfer(
    val deposit: Balance
) : ParametrizedAction

data class Stake(
    val stake: Balance,
    val publicKey: PublicKey,
) : ParametrizedAction

data class AddKey(
    val publicKey: PublicKey,
    val accessKey: AccessKey,
) : ParametrizedAction

data class DeleteKey(
    val publicKey: PublicKey,
) : ParametrizedAction

data class DeleteAccount(
    val beneficiaryId: AccountId,
) : ParametrizedAction

data class StakeChunkOnly(
    val stake: Balance,
    val publicKey: PublicKey,
) : ParametrizedAction
