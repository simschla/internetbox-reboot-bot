package ch.simschla.rebootbot.driver

import ch.simschla.rebootbot.check.Checker

interface Driver {
    fun check(checker: Checker): DriverCheckResult
}
