package ch.simschla.rebootbot.reboot.domain.internetbox

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import java.net.URI

class DiagnosisPage(private val page: Page, private val baseURI: URI) {
    companion object {
        const val URI_PATH = "#diagnostics/info/overview"
    }

    fun navigate() {
        if (!page.url().endsWith(URI_PATH)) {
            page.navigate("$baseURI/$URI_PATH")
        }
    }

    fun getInternetStatus(): Boolean {
        val internetStatusRow =
            page.locator("div[class=\"table-row\"]")
                .filter(Locator.FilterOptions().setHas(page.getByText("Internet connection")))
        internetStatusRow.waitFor()
        return internetStatusRow.innerHTML().contains("Connected")
    }
}
