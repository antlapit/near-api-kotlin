package antlapit.near.api.json

import antlapit.near.api.providers.model.changes.StateChange
import antlapit.near.api.providers.model.changes.StateChangeCause
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class AccessKeyChangeMixin(
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    val change: StateChange,
    val cause: StateChangeCause
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface StateChangeCauseMixin