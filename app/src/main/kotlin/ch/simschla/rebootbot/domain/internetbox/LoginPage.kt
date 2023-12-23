package ch.simschla.rebootbot.domain.internetbox

import com.microsoft.playwright.Page
import java.net.URL

class LoginPage(private val page: Page, private val baseURL: URL) {
    fun navigate() {
        page.navigate("$baseURL/#login")
    }

    fun doLogin(): OverviewPage {
        if (!page.url().contains("login")) {
            return OverviewPage(page, baseURL) // no login required
        }
        val inputPassword = page.locator("input[name=\"login-password\"]")
        inputPassword.waitFor()
        inputPassword.fill(System.getenv().getOrDefault("INTERNETBOX_PASSWORD", "password"))

        val loginButton = page.locator("a[name=\"login-button\"]")
        loginButton.waitFor()
        page.click("a[name=\"login-button\"]")
        page.waitForURL("**/${OverviewPage.URL_PATH}")
        return OverviewPage(page, baseURL)
    }
}
