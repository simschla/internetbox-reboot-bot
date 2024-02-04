package ch.simschla.rebootbot.check

import mu.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

class FailAfterDurationChecker(private val duration: Duration, private val delegate: Checker) : Checker {
    private var lastSuccess = System.currentTimeMillis()

    override fun check(): Boolean {
        val delegateCheck = delegate.check()
        lastSuccess =
            if (delegateCheck) {
                System.currentTimeMillis()
            } else {
                lastSuccess
            }
        if (delegateCheck) {
            return true
        }
        if (isDurationElapsed()) {
            logger.info { "Delegate check failed for longer than $duration -- failing!" }
            return false
        }
        logger.info { "Delegate check failed, but duration $duration not elapsed yet -- not failing." }
        return true
    }

    override fun toString(): String {
        return "FailAfterDurationChecker(duration=$duration, delegate=$delegate)"
    }

    private fun isDurationElapsed() = System.currentTimeMillis() - lastSuccess > duration.toMillis()
}
