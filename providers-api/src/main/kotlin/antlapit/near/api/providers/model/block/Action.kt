package antlapit.near.api.providers.model.block

import antlapit.near.api.providers.model.accesskey.AccessKey
import antlapit.near.api.providers.primitives.AccountId
import antlapit.near.api.providers.primitives.Balance
import antlapit.near.api.providers.primitives.Gas
import antlapit.near.api.providers.primitives.PublicKey

open class Action

class CreateAccountAction : Action()

data class DeployContractAction(
    val code: String
) : Action()

data class FunctionCallAction(
    val methodName: String,
    val args: String,
    val gas: Gas,
    val deposit: Balance,
) : Action()

data class TransferAction(
    val deposit: Balance
) : Action()

data class StakeAction(
    val stake: Balance,
    val publicKey: PublicKey,
) : Action()

data class AddKeyAction(
    val publicKey: PublicKey,
    val accessKey: AccessKey,
) : Action()

data class DeleteKeyAction(
    val publicKey: PublicKey,
) : Action()

data class DeleteAccountAction(
    val beneficiaryId: AccountId,
) : Action()

data class StakeChunkOnlyAction(
    val stake: Balance,
    val publicKey: PublicKey,
) : Action()
