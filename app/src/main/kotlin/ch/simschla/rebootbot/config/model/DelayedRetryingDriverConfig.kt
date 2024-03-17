package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.driver.DelayedRetryingDriver
import ch.simschla.rebootbot.driver.Driver
import java.time.Duration
import java.time.temporal.ChronoUnit

class DelayedRetryingDriverConfig(
    val timeBetweenChecks: Long,
    val timeUnit: ChronoUnit,
    val maxRetries: Int,
) : DriverConfig {
    override fun instantiate(): Driver {
        return DelayedRetryingDriver(
            Duration.of(timeBetweenChecks, timeUnit),
            maxRetries,
        )
    }
}
