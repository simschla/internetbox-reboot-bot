package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.Checker
import ch.simschla.rebootbot.check.NetworkChecker
import java.net.URI

data class UrlNetworkCheckerConfig(
    val url: String,
    val httpMethod: String = "HEAD",
) : NetworkCheckerConfig {
    override fun instantiate(): Checker {
        return NetworkChecker(URI(url), httpMethod)
    }
}
