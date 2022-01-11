package antlapit.near.api.providers.model.accesskey

import antlapit.near.api.providers.model.changes.StateChange
import antlapit.near.api.providers.model.changes.StateChangeCause
import antlapit.near.api.providers.model.primitives.CryptoHash

data class AccessKeysChangesContainer(
    val blockHash: CryptoHash,
    val changes: List<AccessKeyChange> = emptyList()
)

data class AccessKeyChange(
    val change: StateChange,
    val cause: StateChangeCause
)
