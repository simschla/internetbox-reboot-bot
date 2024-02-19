package ch.simschla.rebootbot.browser

import com.microsoft.playwright.impl.driver.Driver
import java.util.concurrent.TimeUnit

/**
 * We would love to use {@code com.microsoft.playwright.CLI} directly, but since it ends in
 * {@code System.exit}, this is not an option.
 */
class BrowserDriverInstaller {
    companion object {
        fun prepareBrowserBinaries() {
            val driver: Driver = Driver.ensureDriverInstalled(emptyMap(), false)
            val pb: ProcessBuilder = driver.createProcessBuilder()
            pb.command().addAll(listOf("install", "chromium"))
            pb.inheritIO()
            val process = pb.start()
            process.waitFor(5, TimeUnit.MINUTES)
            if (process.exitValue() != 0) {
                throw IllegalStateException("Failed to install drivers (exit value: ${process.exitValue()}. Aborting")
            }
        }
    }
}
