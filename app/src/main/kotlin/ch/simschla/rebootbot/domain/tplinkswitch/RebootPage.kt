package ch.simschla.rebootbot.domain.tplinkswitch

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import java.net.URL
import java.nio.file.Path

class RebootPage(private val page: Page, val baseURL: URL) {
    companion object {
        const val URL_PATH = ""
    }

    fun navigate() {
        if (!page.url().equals("$baseURL/${URL_PATH}")) {
            page.navigate("$baseURL/$URL_PATH")
        }
        page.frame("mainFrame")
            .locator("legend")
            .filter(Locator.FilterOptions().setHasText("System Reboot"))
            .waitFor()
    }

    fun triggerReboot() {
        val rebootButton =
            page
                .frame("mainFrame")
                .locator("input[type= \"submit\"][value=\"Reboot\"]")
        rebootButton.waitFor()

        // will open javascript confirm
        page.onDialog { dialog ->
            if (dialog.message().contains("reboot the device")) {
//                dialog.accept()
                println("Would accept the dialog, but skipping for now")
                dialog.dismiss()
                page.screenshot(Page.ScreenshotOptions().setPath(Path.of("screenshot.png")))
            } else {
                println("Unexpected dialog: ${dialog.message()}")
                dialog.dismiss()
            }
        }

        rebootButton.click()
    }
}
