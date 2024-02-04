package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URI

class SystemSettingsPage(private val page: Page, private val baseURI: URI) {
    companion object {
        const val URI_PATH = "#system/settings/reset"
    }

    fun navigate() {
        if (!page.url().endsWith(URI_PATH)) {
            page.navigate("$baseURI/$URI_PATH")
        }
    }

    fun openRebootPage(): SystemSettingsRebootPage {
        val rebootTabLink = page.locator("li.tab-reboot a")
        rebootTabLink.waitFor()
        rebootTabLink.click()
        page.waitForURL("**/${SystemSettingsRebootPage.URI_PATH}")
        return SystemSettingsRebootPage(page, baseURI)
    }
}
