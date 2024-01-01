package ch.simschla.rebootbot.reboot.domain.internetbox

import ch.simschla.rebootbot.browser.BrowserInstance
import ch.simschla.rebootbot.reboot.RebootActor
import java.net.URL

class InternetBoxUi(private val internetBoxAdminUi: URL) : RebootActor {
    override fun reboot() {
        BrowserInstance.create().use { browserInstance ->
            val loginPage = LoginPage(browserInstance.page, internetBoxAdminUi)
            loginPage.navigate()
            val overviewPage = loginPage.doLogin()
            overviewPage.navigate()

            overviewPage.assertExpertMode()

            val diagnosisPage = overviewPage.openDiagnosisPage()
            val online = diagnosisPage.getInternetStatus()
            println("Internetbox Internet Status: $online")
            val systemSettingsPage = overviewPage.openSystemSettingsPage()
            systemSettingsPage.navigate()
            val rebootBoxPage = systemSettingsPage.openRebootPage()
            rebootBoxPage.navigate()
            rebootBoxPage.triggerReboot()
        }
    }

    override fun toString(): String {
        return "InternetBoxUi(internetBoxAdminUi=$internetBoxAdminUi)"
    }
}
