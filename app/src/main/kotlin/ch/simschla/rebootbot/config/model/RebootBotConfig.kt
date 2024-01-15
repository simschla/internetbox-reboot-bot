package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.RebootBot
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.InputStream

data class RebootBotConfig(
    val networkChecker: NetworkCheckerConfig,
    val rebootActor: RebootActorConfig,
    val dryRun: Boolean = false,
) {
    companion object {
        fun fromResource(resourceName: String): RebootBotConfig {
            val inputStream = RebootBotConfig::class.java.getResourceAsStream(resourceName)
            return fromInputStream(inputStream!!)
        }

        fun fromInputStream(inputStream: InputStream): RebootBotConfig {
            val mapper = ObjectMapper(YAMLFactory())
            mapper.registerKotlinModule()

            return mapper.readValue(inputStream, RebootBotConfig::class.java)
        }
    }

    fun instantiate(): RebootBot {
        val networkChecker = networkChecker.instantiate()
        val rebootActor = rebootActor.instantiate()
        return RebootBot(networkChecker, rebootActor, dryRun)
    }
}
