/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ch.simschla.rebootbot

import ch.simschla.rebootbot.browser.BrowserDriverInstaller
import ch.simschla.rebootbot.check.MajorityVotingChecker
import ch.simschla.rebootbot.check.NetworkChecker
import ch.simschla.rebootbot.reboot.domain.generic.SequentialRebootActor
import ch.simschla.rebootbot.reboot.domain.generic.WaitRebootActor
import ch.simschla.rebootbot.reboot.domain.internetbox.InternetBoxUi
import ch.simschla.rebootbot.reboot.domain.tplinkswitch.TpLinkSwitchUi
import mu.KotlinLogging
import java.net.URI

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

private val logger = KotlinLogging.logger("ch.simschla.rebootbot.App")

fun main() {
    BrowserDriverInstaller.prepareBrowserBinaries()
    val networkCheckers =
        listOf(
            NetworkChecker(URI("https://www.ethz.ch")),
            NetworkChecker(URI("https://www.bluewin.ch")),
            NetworkChecker(targetURI = URI("https://www.swisscom.ch"), httpMethod = "GET"),
        )
    val networkChecker = MajorityVotingChecker(networkCheckers)
    val online = networkChecker.check()
    logger.info { "Online: $online" }
//    val internetBoxUi = InternetBoxUi(URI("https://192.168.1.1"))
//    internetBoxUi.rebootBox()
    val rebooters = listOf(InternetBoxUi(URI("https://192.168.1.1")), WaitRebootActor(18), TpLinkSwitchUi(URI("http://192.168.1.2")))
    val rebooter = SequentialRebootActor(rebooters)
    rebooter.reboot(dryRun = true)
}
