package ch.simschla.rebootbot.reboot.domain.tplinkswitch

import com.microsoft.playwright.Page
import java.net.URL

class LoginPage(private val page: Page, private val baseURL: URL) {
    fun navigate() {
        page.navigate("$baseURL")
    }

    fun doLogin(): OverviewPage {
//        if (!page.url().contains("login")) {
//            return OverviewPage(page, baseURL) // no login required
//        }
        val inputUserName = page.locator("input[name=\"username\"]")
        val inputPassword = page.locator("input[name=\"password\"]")
        inputUserName.waitFor()
        inputUserName.fill(System.getenv().getOrDefault("TPLINK_USERNAME", "admin"))
        inputPassword.waitFor()
        inputPassword.fill(System.getenv().getOrDefault("TPLINK_PASSWORD", "password"))

        val loginButton = page.locator("input[type = \"submit\"][name=\"logon\"]")
        loginButton.waitFor()
        loginButton.click()
        return OverviewPage(page, baseURL)
    }
}
