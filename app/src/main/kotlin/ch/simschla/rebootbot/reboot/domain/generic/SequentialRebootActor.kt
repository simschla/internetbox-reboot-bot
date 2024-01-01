package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class SequentialRebootActor(private val actors: List<RebootActor>) : RebootActor {
    override fun reboot(dryRun: Boolean) {
        actors.forEach {
            logger.info { "Invoking RebootActor $it" + if (dryRun) " (dry run)" else "" }
            it.reboot(dryRun)
        }
    }

    override fun toString(): String {
        return "SequentialRebootActor(actors=$actors)"
    }
}
