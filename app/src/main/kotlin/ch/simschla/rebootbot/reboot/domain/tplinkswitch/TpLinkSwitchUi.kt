package ch.simschla.rebootbot.reboot.domain.tplinkswitch

import ch.simschla.rebootbot.browser.BrowserInstance
import ch.simschla.rebootbot.reboot.RebootActor
import java.net.URL

class TpLinkSwitchUi(private val switchAdminUi: URL) : RebootActor {
    override fun reboot() {
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
