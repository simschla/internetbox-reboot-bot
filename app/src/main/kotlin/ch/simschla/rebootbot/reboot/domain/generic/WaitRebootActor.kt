package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor

class WaitRebootActor(private val waitSeconds: Double) : RebootActor {
    constructor(waitSeconds: Int) : this(waitSeconds.toDouble())

    override fun reboot() {
        println("Waiting $waitSeconds seconds")
        Thread.sleep((waitSeconds * 1000).toLong())
    }

    override fun toString(): String {
        return "WaitRebootActor(waitSeconds=$waitSeconds)"
    }
}
