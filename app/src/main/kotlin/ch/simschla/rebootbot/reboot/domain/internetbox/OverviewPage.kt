package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URL

class OverviewPage(private val page: Page, val baseURL: URL) {
    companion object {
        const val URL_PATH = "#overview"
    }

    fun navigate() {
        if (!page.url().endsWith(URL_PATH)) {
            page.navigate("$baseURL/$URL_PATH")
        }
        page.getByText("Home network overview").waitFor()
    }

    fun assertExpertMode() {
        val userModeSwitcher = page.locator("#user-mode-switcher")
        userModeSwitcher.waitFor()
        // check if expert mode is active
        // if the element located by userModeSwitcher has the class "checked" then expert mode is active

        if (!userModeSwitcher.getAttribute("class").contains("checked")) {
            // enable expert mode
            userModeSwitcher.click()
        }
    }

    fun openDiagnosisPage(): DiagnosisPage {
        val diagnosisLink = page.locator("li.menu-diagnostics a")
        diagnosisLink.waitFor()
        diagnosisLink.click()
        page.waitForURL("**/${DiagnosisPage.URL_PATH}")
        return DiagnosisPage(page, baseURL)
    }

    fun openSystemSettingsPage(): SystemSettingsPage {
        val internetBoxLink = page.locator("li.navigation-item.menu-system>a")
        internetBoxLink.waitFor()
        internetBoxLink.click()
        page.waitForURL("**/${SystemSettingsPage.URL_PATH}")
        return SystemSettingsPage(page, baseURL)
    }
}
