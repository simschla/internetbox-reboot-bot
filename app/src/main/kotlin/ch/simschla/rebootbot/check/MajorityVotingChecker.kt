package ch.simschla.rebootbot.check

class MajorityVotingChecker(private val checkers: List<Checker>) : Checker {
    override fun check(): Boolean {
        val results = checkers.map { it to it.check() }
        val positiveChecks = results.filter { it.second }.map { it.first }
        val negativeChecks = results.filter { !it.second }.map { it.first }
        println("Positive checks: $positiveChecks, negative checks: $negativeChecks")
        return positiveChecks.size >= negativeChecks.size
    }

    override fun toString(): String {
        return "MajorityVotingChecker(checkers=$checkers)"
    }
}
