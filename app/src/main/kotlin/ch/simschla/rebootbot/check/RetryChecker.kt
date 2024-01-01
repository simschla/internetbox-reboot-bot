package ch.simschla.rebootbot.check

class RetryChecker(private val delegate: Checker, private val retryCount: Int) : Checker {
    init {
        require(retryCount > 1) { "Retry count must be greater than 1" }
    }

    override fun check(): Boolean {
        var retry = 0
        while (retry < retryCount) {
            if (delegate.check()) {
                return true
            }
            retry++
        }
        return false
    }

    override fun toString(): String {
        return "RetryChecker(retryCount=$retryCount, delegate=$delegate)"
    }
}
