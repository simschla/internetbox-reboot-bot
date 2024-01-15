package ch.simschla.rebootbot

import ch.simschla.rebootbot.check.Checker
import ch.simschla.rebootbot.reboot.RebootActor

class RebootBot(
    val checker: Checker,
    val rebootActor: RebootActor,
    val dryRun: Boolean,
) {
    fun run() {
        if (checker.check()) {
            rebootActor.reboot()
        }
    }
}
