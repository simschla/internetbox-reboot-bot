package ch.simschla.rebootbot.check

class AnyChecker(private val checkers: List<Checker>) : Checker {
    override fun check(): Boolean {
        return checkers.any { it.check() }
    }
}
