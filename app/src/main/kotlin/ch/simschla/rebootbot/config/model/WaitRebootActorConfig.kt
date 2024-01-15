package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.reboot.RebootActor
import ch.simschla.rebootbot.reboot.domain.generic.WaitRebootActor

data class WaitRebootActorConfig(val seconds: Int) : RebootActorConfig {
    override fun instantiate(): RebootActor {
        return WaitRebootActor(seconds)
    }
}
