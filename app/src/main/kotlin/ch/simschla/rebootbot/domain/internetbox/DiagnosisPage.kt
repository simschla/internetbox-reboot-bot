package ch.simschla.rebootbot.domain.internetbox

import com.microsoft.playwright.Locator
import com.microsoft.playwright.Page
import java.net.URL

class DiagnosisPage(private val page: Page, private val baseURL: URL) {
    companion object {
        const val URL_PATH = "#diagnostics/info/overview"
    }

    fun navigate() {
        if (!page.url().endsWith(URL_PATH)) {
            page.navigate("$baseURL/$URL_PATH")
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
