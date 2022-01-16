package org.near.api.json

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.near.api.providers.model.changes.StateChangeCause
import org.near.api.providers.model.changes.StateChangeType

data class SingleStateChangeMixin(
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
    val change: StateChangeType,
    val cause: StateChangeCause
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface StateChangeCauseMixin

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
sealed interface StateChangeKindMixin
