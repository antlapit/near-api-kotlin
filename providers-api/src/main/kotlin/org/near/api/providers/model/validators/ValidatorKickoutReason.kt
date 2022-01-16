package org.near.api.providers.model.validators

import org.near.api.providers.model.primitives.Balance
import org.near.api.providers.model.primitives.NumBlocks

sealed interface ValidatorKickoutReason {

    /**
     * Slashed validators are kicked out.
     */
    object Slashed : ValidatorKickoutReason

    /**
     * Validator didn't produce enough blocks.
     */
    data class NotEnoughBlocks(val produced: NumBlocks, val expected: NumBlocks) : ValidatorKickoutReason

    /**
     * Validator didn't produce enough chunks.
     */
    data class NotEnoughChunks(val produced: NumBlocks, val expected: NumBlocks) : ValidatorKickoutReason

    /**
     * Validator unstaked themselves.
     */
    object Unstaked : ValidatorKickoutReason

    /**
     * Validator stake is now below threshold
     */
    data class NotEnoughStake(val stakeU128: Balance, val thresholdU128: Balance) : ValidatorKickoutReason

    /**
     * Enough stake but is not chosen because of seat limits.
     */
    object DidNotGetASeat : ValidatorKickoutReason
}
