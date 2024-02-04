package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.Checker
import ch.simschla.rebootbot.check.FailAfterDurationChecker
import java.time.Duration

data class FailAfterDurationCheckerConfig(val seconds: Long = 1800, val from: NetworkCheckerConfig) : NetworkCheckerConfig {
    override fun instantiate(): Checker {
        return FailAfterDurationChecker(Duration.ofSeconds(seconds), from.instantiate())
    }
}
