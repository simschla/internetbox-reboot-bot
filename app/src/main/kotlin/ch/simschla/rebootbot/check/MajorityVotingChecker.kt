package ch.simschla.rebootbot.check

class MajorityVotingChecker(private val checkers: List<Checker>) : Checker {
    override fun check(): Boolean {
        val positiveChecks = checkers.count { it.check() }
        val negativeChecks = checkers.count { !it.check() }
        return positiveChecks >= negativeChecks
    }
}
