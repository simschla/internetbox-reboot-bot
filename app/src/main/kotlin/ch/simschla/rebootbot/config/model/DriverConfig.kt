package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.driver.Driver
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
)
@JsonSubTypes(
    JsonSubTypes.Type(DelayedRetryingDriverConfig::class, name = "delayed-retry"),
)
interface DriverConfig {
    fun instantiate(): Driver
}
