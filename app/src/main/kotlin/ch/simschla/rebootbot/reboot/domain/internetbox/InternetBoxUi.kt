package ch.simschla.rebootbot.reboot.domain.internetbox

import ch.simschla.rebootbot.browser.BrowserInstance
import ch.simschla.rebootbot.reboot.RebootActor
import mu.KotlinLogging
import java.net.URI

private val logger = KotlinLogging.logger {}

class InternetBoxUi(private val internetBoxAdminUi: URI) : RebootActor {
    override fun reboot(dryRun: Boolean) {
        BrowserInstance.create().use { browserInstance ->
            val loginPage = LoginPage(browserInstance.page, internetBoxAdminUi)
            loginPage.navigate()
            val overviewPage = loginPage.doLogin()
            overviewPage.navigate()

            overviewPage.assertExpertMode()

            val diagnosisPage = overviewPage.openDiagnosisPage()
            val online = diagnosisPage.getInternetStatus()
            logger.info { "Internetbox Internet Status: $online" }
            val systemSettingsPage = overviewPage.openSystemSettingsPage()
            systemSettingsPage.navigate()
            val rebootBoxPage = systemSettingsPage.openRebootPage()
            rebootBoxPage.navigate()
            rebootBoxPage.triggerReboot(dryRun)
        }
    }

    override fun toString(): String {
        return "InternetBoxUi(internetBoxAdminUi=$internetBoxAdminUi)"
    }
}
