package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.Checker
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(MajorityVotingNetworkCheckerConfig::class, name = "majority"),
    JsonSubTypes.Type(UrlNetworkCheckerConfig::class, name = "url"),
)
interface NetworkCheckerConfig {
    fun instantiate(): Checker
}
