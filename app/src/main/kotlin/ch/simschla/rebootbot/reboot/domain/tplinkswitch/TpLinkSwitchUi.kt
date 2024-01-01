package ch.simschla.rebootbot.reboot.domain.tplinkswitch

import ch.simschla.rebootbot.browser.BrowserInstance
import ch.simschla.rebootbot.reboot.RebootActor
import java.net.URL

class TpLinkSwitchUi(private val switchAdminUi: URL) : RebootActor {
    override fun reboot(dryRun: Boolean) {
        BrowserInstance.create().use { browserInstance ->
            val loginPage = LoginPage(browserInstance.page, switchAdminUi)
            loginPage.navigate()
            val overviewPage = loginPage.doLogin()
            overviewPage.navigate()
            val rebootPage = overviewPage.openRebootPage()
            rebootPage.navigate()
            rebootPage.triggerReboot(dryRun)
        }
    }

    override fun toString(): String {
        return "TpLinkSwitchUi(switchAdminUi=$switchAdminUi)"
    }
}
