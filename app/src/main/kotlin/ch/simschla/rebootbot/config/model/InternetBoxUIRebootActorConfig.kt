package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.reboot.RebootActor
import ch.simschla.rebootbot.reboot.domain.internetbox.InternetBoxUi
import java.net.URL

data class InternetBoxUIRebootActorConfig(val url: String) : RebootActorConfig {
    override fun instantiate(): RebootActor {
        return InternetBoxUi(URL(url))
    }
}
