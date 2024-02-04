package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URI

class OverviewPage(private val page: Page, val baseURI: URI) {
    companion object {
        const val URI_PATH = "#overview"
    }

    fun navigate() {
        if (!page.url().endsWith(URI_PATH)) {
            page.navigate("$baseURI/$URI_PATH")
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
        page.waitForURL("**/${DiagnosisPage.URI_PATH}")
        return DiagnosisPage(page, baseURI)
    }

    fun openSystemSettingsPage(): SystemSettingsPage {
        val internetBoxLink = page.locator("li.navigation-item.menu-system>a")
        internetBoxLink.waitFor()
        internetBoxLink.click()
        page.waitForURL("**/${SystemSettingsPage.URI_PATH}")
        return SystemSettingsPage(page, baseURI)
    }
}
