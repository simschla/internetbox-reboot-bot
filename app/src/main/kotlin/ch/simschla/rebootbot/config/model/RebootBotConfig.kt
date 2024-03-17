package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.RebootBot
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.InputStream
import java.time.temporal.ChronoUnit

data class RebootBotConfig(
    val driver: DriverConfig = DelayedRetryingDriverConfig(timeBetweenChecks = 5L, timeUnit = ChronoUnit.MINUTES, maxRetries = 5),
    val networkChecker: NetworkCheckerConfig = UrlNetworkCheckerConfig("https://www.google.com"),
    val rebootActor: RebootActorConfig,
    val dryRun: Boolean = false,
) {
    companion object {
        fun fromResource(resourceName: String): RebootBotConfig {
            val inputStream = RebootBotConfig::class.java.getResourceAsStream(resourceName)
            return fromInputStream(inputStream!!)
        }

        fun fromInputStream(inputStream: InputStream): RebootBotConfig {
            val mapper =
                ObjectMapper(YAMLFactory())
                    .registerModule(
                        KotlinModule.Builder()
                            .configure(KotlinFeature.NullIsSameAsDefault, true)
                            .build(),
                    )

            return mapper.readValue(inputStream, RebootBotConfig::class.java)
        }
    }

    fun instantiate(): RebootBot {
        val driver = driver.instantiate()
        val networkChecker = networkChecker.instantiate()
        val rebootActor = rebootActor.instantiate()
        return RebootBot(driver, networkChecker, rebootActor, dryRun)
    }
}
