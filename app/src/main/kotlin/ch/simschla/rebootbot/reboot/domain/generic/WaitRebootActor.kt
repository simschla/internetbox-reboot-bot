package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor

class WaitRebootActor(private val waitSeconds: Double) : RebootActor {
    override fun reboot() {
        Thread.sleep((waitSeconds * 1000).toLong())
    }
}
