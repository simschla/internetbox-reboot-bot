package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.reboot.RebootActor
import ch.simschla.rebootbot.reboot.domain.generic.SequentialRebootActor

data class SequentialRebootActorConfig(val of: List<RebootActorConfig>) : RebootActorConfig {
    override fun instantiate(): RebootActor {
        return SequentialRebootActor(of.map { it.instantiate() })
    }
}
