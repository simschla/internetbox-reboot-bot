package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor

class SequentialRebootActor(private val actors: List<RebootActor>) : RebootActor {
    override fun reboot(dryRun: Boolean) {
        actors.forEach {
            println("Invoking RebootActor $it" + if (dryRun) " (dry run)" else "")
            it.reboot(dryRun)
        }
    }

    override fun toString(): String {
        return "SequentialRebootActor(actors=$actors)"
    }
}
