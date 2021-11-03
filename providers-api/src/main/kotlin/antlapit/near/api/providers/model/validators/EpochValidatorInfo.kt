package antlapit.near.api.providers.model.validators

import antlapit.near.api.providers.model.BlockHeight

data class EpochValidatorInfo(
    val currentValidators: List<CurrentEpochValidatorInfo> = emptyList(),
    val nextValidators: List<NextEpochValidatorInfo> = emptyList(),
    val currentFishermen: List<ValidatorStakeView> = emptyList(),
    val nextFishermen: List<ValidatorStakeView> = emptyList(),
    val currentProposals: List<ValidatorStakeView> = emptyList(),
    val prevEpochKickout: List<ValidatorKickoutView> = emptyList(),
    val epochStartHeight: BlockHeight
)
