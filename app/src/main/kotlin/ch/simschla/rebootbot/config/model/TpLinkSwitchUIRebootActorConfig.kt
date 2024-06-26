package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.reboot.RebootActor
import ch.simschla.rebootbot.reboot.domain.tplinkswitch.TpLinkSwitchUi
import java.net.URI

data class TpLinkSwitchUIRebootActorConfig(val url: String) : RebootActorConfig {
    override fun instantiate(): RebootActor {
        return TpLinkSwitchUi(URI(url))
    }
}
