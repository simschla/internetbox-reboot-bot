package ch.simschla.rebootbot.reboot.domain.tplinkswitch

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import mu.KotlinLogging
import java.net.URI
import java.nio.file.Path

private val logger = KotlinLogging.logger {}

class RebootPage(private val page: Page, val baseURI: URI) {
    companion object {
        const val URI_PATH = ""
    }

    fun navigate() {
        if (!page.url().equals("$baseURI/$URI_PATH")) {
            page.navigate("$baseURI/$URI_PATH")
        }
        page.frame("mainFrame")
            .locator("legend")
            .filter(Locator.FilterOptions().setHasText("System Reboot"))
            .waitFor()
    }

    fun triggerReboot(dryRun: Boolean) {
        val rebootButton =
            page
                .frame("mainFrame")
                .locator("input[type= \"submit\"][value=\"Reboot\"]")
        rebootButton.waitFor()

        // will open javascript confirm
        page.onDialog { dialog ->
            if (dialog.message().contains("reboot the device")) {
                if (!dryRun) {
                    dialog.accept()
                } else {
                    logger.info { "Dry run: not rebooting" }
                    dialog.dismiss()
                }
                page.screenshot(Page.ScreenshotOptions().setPath(Path.of("screenshot.png")))
            } else {
                logger.error { "Unexpected dialog: ${dialog.message()}" }
                dialog.dismiss()
            }
        }

        rebootButton.click()
    }
}
