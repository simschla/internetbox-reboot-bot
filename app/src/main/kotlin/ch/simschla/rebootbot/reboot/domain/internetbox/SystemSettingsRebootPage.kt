package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import mu.KotlinLogging
import java.net.URL
import java.nio.file.Path

private val logger = KotlinLogging.logger {}

class SystemSettingsRebootPage(private val page: Page, private val baseURL: URL) {
    companion object {
        const val URL_PATH = "#system/settings/reboot"
    }

    fun navigate() {
        if (!page.url().endsWith(URL_PATH)) {
            page.navigate("$baseURL/$URL_PATH")
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
