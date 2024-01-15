package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.Checker
import ch.simschla.rebootbot.check.MajorityVotingChecker

data class MajorityVotingNetworkCheckerConfig(val of: List<NetworkCheckerConfig>) : NetworkCheckerConfig {
    override fun instantiate(): Checker {
        return MajorityVotingChecker(of.map { it.instantiate() })
    }
}
