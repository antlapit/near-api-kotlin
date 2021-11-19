package antlapit.near.api.providers.model.primitives

typealias ChallengesResult = List<SlashedValidator>

data class SlashedValidator(
    val accountId: AccountId,
    val isDoubleSign: Boolean
)
