package ch.simschla.rebootbot.reboot.domain.tplinkswitch

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import java.net.URI

class OverviewPage(private val page: Page, val baseURI: URI) {
    companion object {
        const val URI_PATH = ""
    }

    fun navigate() {
        if (!page.url().equals("$baseURI/$URI_PATH")) {
            page.navigate("$baseURI/$URI_PATH")
        }
        page.frame("mainFrame").getByText("System Info").waitFor()
    }

    fun openRebootPage(): RebootPage {
        val systemLink =
            page.frame("bottomLeftFrame").locator("a[href=\"SystemInfoRpm.htm\"]").getByText("System")
                .filter(Locator.FilterOptions().setHasNotText("System Info"))
        systemLink.waitFor()
        systemLink.click()

        val systemToolsLink =
            page.frame("bottomLeftFrame").locator("a[href=\"ConfigRpm.htm\"]")
                .getByText("System Tools")
                .filter(Locator.FilterOptions().setHasText("System Tools"))
        systemToolsLink.waitFor()
        systemToolsLink.click()

        val rebootPageLink = page.frame("bottomLeftFrame").getByText("System Reboot")
        rebootPageLink.waitFor()
        rebootPageLink.click()
        return RebootPage(page, baseURI)
    }
}
