package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URI

class LoginPage(private val page: Page, private val baseURI: URI) {
    fun navigate() {
        page.navigate("$baseURI/#login")
    }

    fun doLogin(): OverviewPage {
        if (!page.url().contains("login")) {
            return OverviewPage(page, baseURI) // no login required
        }
        val inputPassword = page.locator("input[name=\"login-password\"]")
        inputPassword.waitFor()
        inputPassword.fill(System.getenv().getOrDefault("INTERNETBOX_PASSWORD", "password"))

        val loginButton = page.locator("a[name=\"login-button\"]")
        loginButton.waitFor()
        page.click("a[name=\"login-button\"]")
        page.waitForURL("**/${OverviewPage.URI_PATH}")
        return OverviewPage(page, baseURI)
    }
}
