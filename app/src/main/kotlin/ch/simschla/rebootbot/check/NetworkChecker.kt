package ch.simschla.rebootbot.check

import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val logger = KotlinLogging.logger {}

class NetworkChecker(private val targetURI: URI, private val httpMethod: String = "HEAD") : Checker {
    override fun check(): Boolean {
        return try {
            val client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()
            val request =
                HttpRequest.newBuilder()
                    .uri(targetURI)
                    .method(httpMethod, HttpRequest.BodyPublishers.noBody())
                    .timeout(java.time.Duration.ofSeconds(10))
                    .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            val responseCode = response.statusCode()
            logger.debug { "Response code for $httpMethod $targetURI: $responseCode" }
            if (responseCode != 200) {
                logger.info { "Check for $httpMethod $targetURI failed. Response code: $responseCode" }
            } else {
                logger.debug { "Check for $httpMethod $targetURI succeeded. Response code: $responseCode" }
            }
            return responseCode == 200
        } catch (e: Exception) {
            logger.error { "Exception: $e" }
            false
        }
    }

    override fun toString(): String {
        return "NetworkChecker(targetURI=$targetURI, httpMethod='$httpMethod')"
    }
}
