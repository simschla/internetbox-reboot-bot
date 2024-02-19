package ch.simschla.rebootbot.browser

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserContext
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright

class BrowserInstance : AutoCloseable {
    private val playwrightDelegate = lazy { Playwright.create() }
    val playwright: Playwright by playwrightDelegate
    val browser: Browser by lazy { playwright.chromium().launch(BrowserType.LaunchOptions().setHeadless(true)) }
    val context: BrowserContext by lazy { browser.newContext(Browser.NewContextOptions().setIgnoreHTTPSErrors(true)) }
    val page: Page by lazy { context.newPage() }

    companion object {
        fun create(): BrowserInstance {
            return BrowserInstance()
        }
    }

    override fun close() {
        // only close the playwright instance if it has been initialized
        if (playwrightDelegate.isInitialized()) {
            playwright.close()
        }
    }
}
