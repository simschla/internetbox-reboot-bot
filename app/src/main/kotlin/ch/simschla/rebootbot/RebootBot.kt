package ch.simschla.rebootbot

import ch.simschla.rebootbot.check.Checker
import ch.simschla.rebootbot.driver.Driver
import ch.simschla.rebootbot.driver.DriverCheckResult
import ch.simschla.rebootbot.reboot.RebootActor

class RebootBot(
    val driver: Driver,
    val checker: Checker,
    val rebootActor: RebootActor,
    val dryRun: Boolean,
) {
    fun run() {
        var driverCheckResult: DriverCheckResult
        do {
            driverCheckResult = driver.check(checker)
            if (driverCheckResult == DriverCheckResult.REBOOT) {
                rebootActor.reboot(dryRun = dryRun)
            }
        } while (driverCheckResult != DriverCheckResult.END)
    }
}
