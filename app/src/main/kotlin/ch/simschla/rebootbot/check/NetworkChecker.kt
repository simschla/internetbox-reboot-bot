package ch.simschla.rebootbot.check

import mu.KotlinLogging
import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

class NetworkChecker(private val targetURL: URL, private val httpMethod: String = "HEAD") : Checker {
    override fun check(): Boolean {
        return try {
            val client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()
            val request =
                HttpRequest.newBuilder()
                    .uri(targetURL.toURI())
                    .method(httpMethod, HttpRequest.BodyPublishers.noBody())
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())

            response.statusCode() == 200
        } catch (e: Exception) {
            logger.error { "Exception: $e" }
            false
        }
    }

    override fun toString(): String {
        return "NetworkChecker(targetURL=$targetURL, httpMethod='$httpMethod')"
    }
}
