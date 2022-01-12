package antlapit.near.api.json

import antlapit.near.api.providers.model.changes.StateChangeCause
import antlapit.near.api.providers.model.changes.StateChangeType
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class SingleStateChangeMixin(
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    val change: StateChangeType,
    val cause: StateChangeCause
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface StateChangeCauseMixin

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface StateChangeKindMixin
