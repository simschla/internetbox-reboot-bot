package ch.simschla.rebootbot.driver

import ch.simschla.rebootbot.check.Checker
import mu.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

class DelayedRetryingDriver(val delayBetweenChecks: Duration, val numberOfSequentiallyFailedChecksRequired: Int) : Driver {
    private val checkHistory = mutableListOf<CheckerHistoryEntry>()

    override fun check(checker: Checker): DriverCheckResult {
        if (checkHistory.filter { it.result == CheckerResult.FAIL }.count() >= numberOfSequentiallyFailedChecksRequired) {
            logger.info { "Number of sequential failed checks exceeds $numberOfSequentiallyFailedChecksRequired -- rebooting!" }
            checkHistory.clear()
            return DriverCheckResult.REBOOT
        }
        val lastCheck = checkHistory.lastOrNull()
        if (lastCheck != null && lastCheck.time + delayBetweenChecks.toMillis() > System.currentTimeMillis()) {
            val waitTilNextCheck = lastCheck.time + delayBetweenChecks.toMillis() - System.currentTimeMillis()
            logger.info { "Waiting for $waitTilNextCheck ms before next check." }
            Thread.sleep(waitTilNextCheck)
        }

        val result =
            if (checker.check()) {
                CheckerResult.SUCCESS
            } else {
                CheckerResult.FAIL
            }
        if (result == CheckerResult.SUCCESS) {
            checkHistory.clear() // only last one interests us
        }
        checkHistory.add(CheckerHistoryEntry(System.currentTimeMillis(), result))
        return DriverCheckResult.RECHECK
    }

    data class CheckerHistoryEntry(val time: Long, val result: CheckerResult)

    enum class CheckerResult {
        FAIL,
        SUCCESS,
    }
}
