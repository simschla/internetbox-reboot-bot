package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor

class SequentialRebootActor(private val actors: List<RebootActor>) : RebootActor {
    override fun reboot() {
        actors.forEach {
            println("Invoking RebootActor $it")
            it.reboot()
        }
    }
}
