package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.reboot.RebootActor
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(InternetBoxUIRebootActorConfig::class, name = "internet-box"),
    JsonSubTypes.Type(TpLinkSwitchUIRebootActorConfig::class, name = "tp-link-switch"),
    JsonSubTypes.Type(WaitRebootActorConfig::class, name = "wait"),
    JsonSubTypes.Type(SequentialRebootActorConfig::class, name = "sequential"),
)
interface RebootActorConfig {
    fun instantiate(): RebootActor
}
