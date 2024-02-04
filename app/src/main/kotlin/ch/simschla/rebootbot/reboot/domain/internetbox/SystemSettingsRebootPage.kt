package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import mu.KotlinLogging
import java.net.URI
import java.nio.file.Path

private val logger = KotlinLogging.logger {}

class SystemSettingsRebootPage(private val page: Page, private val baseURI: URI) {
    companion object {
        const val URI_PATH = "#system/settings/reboot"
    }

    fun navigate() {
        if (!page.url().endsWith(URI_PATH)) {
            page.navigate("$baseURI/$URI_PATH")
        }
    }

    fun triggerReboot(dryRun: Boolean) {
        val rebootButton = page.locator("div.reboot a.reboot-gateway")
        rebootButton.waitFor()
        rebootButton.click()
        // opens confirmation dialog
        val confirmButton = page.locator("div.reboot-gateway a.start-reboot")
        confirmButton.waitFor()
        if (!dryRun) {
            confirmButton.click()
        } else {
            logger.info { "Dry run: not rebooting" }
        }
        page.screenshot(Page.ScreenshotOptions().setPath(Path.of("screenshot.png")))
    }
}
