package ch.simschla.rebootbot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URL

class SystemSettingsPage(private val page: Page, private val baseURL: URL) {
    companion object {
        const val URL_PATH = "#system/settings/reset"
    }

    fun navigate() {
        if (!page.url().endsWith(URL_PATH)) {
            page.navigate("$baseURL/$URL_PATH")
        }
    }

    fun openRebootPage(): SystemSettingsRebootPage {
        val rebootTabLink = page.locator("li.tab-reboot a")
        rebootTabLink.waitFor()
        rebootTabLink.click()
        page.waitForURL("**/${SystemSettingsRebootPage.URL_PATH}")
        return SystemSettingsRebootPage(page, baseURL)
    }
}
