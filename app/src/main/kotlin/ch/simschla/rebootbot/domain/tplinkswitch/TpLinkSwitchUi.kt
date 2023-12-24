package ch.simschla.rebootbot.domain.tplinkswitch

import ch.simschla.rebootbot.browser.BrowserInstance
import java.net.URL

class TpLinkSwitchUi(private val switchAdminUi: URL) {
    fun rebootSwitch() {
        println("Rebooting switch")
        BrowserInstance.create().use { browserInstance ->
            val loginPage = LoginPage(browserInstance.page, switchAdminUi)
            loginPage.navigate()
            val overviewPage = loginPage.doLogin()
            overviewPage.navigate()
            val rebootPage = overviewPage.openRebootPage()
            rebootPage.navigate()
            rebootPage.triggerReboot()
        }
    }
}
