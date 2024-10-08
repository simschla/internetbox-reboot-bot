/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ch.simschla.rebootbot

import ch.simschla.rebootbot.browser.BrowserDriverInstaller
import ch.simschla.rebootbot.check.MajorityVotingChecker
import ch.simschla.rebootbot.check.NetworkChecker
import ch.simschla.rebootbot.config.model.RebootBotConfig
import ch.simschla.rebootbot.reboot.domain.generic.SequentialRebootActor
import ch.simschla.rebootbot.reboot.domain.generic.WaitRebootActor
import ch.simschla.rebootbot.reboot.domain.internetbox.InternetBoxUi
import ch.simschla.rebootbot.reboot.domain.tplinkswitch.TpLinkSwitchUi
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import mu.KotlinLogging
import java.net.URI

class App : CliktCommand() {
    private val configFile: String? by option(help = "The configuration file")
        .file(mustExist = true, mustBeReadable = true, canBeFile = true, canBeDir = false)
        .convert { it.readText() }

    private val initDeps: Boolean by option(help = "Initialize dependencies")
        .flag(default = false)

    override fun run() {
        if (initDeps) {
            logger.info { "Initializing dependencies, exiting afterwards. " }
            BrowserDriverInstaller.prepareBrowserBinaries()
            return
        }
        if (configFile != null) {
            logger.info { "Config file: $configFile" }
            runConfiguration()
        } else {
            logger.info { "No config file given, running demo setup" }
            runDemoSetup()
        }
    }

    private fun runConfiguration() {
        val rebootBotConfig = RebootBotConfig.fromInputStream(configFile!!.byteInputStream(Charsets.UTF_8))
        val rebootBot = rebootBotConfig.instantiate()
        rebootBot.run() // TODO loop?
    }

    private fun runDemoSetup() {
        val networkCheckers =
            listOf(
                NetworkChecker(URI("https://www.ethz.ch")),
                NetworkChecker(URI("https://www.bluewin.ch")),
                NetworkChecker(targetURI = URI("https://www.swisscom.ch"), httpMethod = "GET"),
            )
        val networkChecker = MajorityVotingChecker(networkCheckers)
        val online = networkChecker.check()
        logger.info { "Online: $online" }
        val rebooters = listOf(InternetBoxUi(URI("https://192.168.1.1")), WaitRebootActor(18), TpLinkSwitchUi(URI("http://192.168.1.2")))
        val rebooter = SequentialRebootActor(rebooters)
        rebooter.reboot(dryRun = true)
    }
}

private val logger = KotlinLogging.logger("ch.simschla.rebootbot.App")

fun main(args: Array<String>) {
    BrowserDriverInstaller.prepareBrowserBinaries()
    App().main(args)
}
