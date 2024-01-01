package ch.simschla.rebootbot.check

class AllChecker(private val checkers: List<Checker>) : Checker {
    override fun check(): Boolean {
        return checkers.all { it.check() }
    }

    override fun toString(): String {
        return "AllChecker(checkers=$checkers)"
    }
}
