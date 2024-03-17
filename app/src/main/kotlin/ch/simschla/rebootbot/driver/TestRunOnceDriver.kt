package ch.simschla.rebootbot.driver

import ch.simschla.rebootbot.check.Checker

class TestRunOnceDriver(private val desiredCheckResult: DriverCheckResult = DriverCheckResult.END) : Driver {
    private var checked = false

    override fun check(checker: Checker): DriverCheckResult {
        if (checked) {
            return DriverCheckResult.END
        }
        checked = true
        checker.check()
        return desiredCheckResult
    }
}
