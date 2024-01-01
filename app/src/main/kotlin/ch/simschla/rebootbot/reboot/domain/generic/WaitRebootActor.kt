package ch.simschla.rebootbot.reboot.domain.generic

import ch.simschla.rebootbot.reboot.RebootActor
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class WaitRebootActor(private val waitSeconds: Double) : RebootActor {
    constructor(waitSeconds: Int) : this(waitSeconds.toDouble())

    override fun reboot(dryRun: Boolean) {
        val actualWaitSeconds = if (dryRun) 0.1 else this.waitSeconds
        logger.info { "Waiting $actualWaitSeconds seconds" + if (dryRun) " (dry run)" else "" }
        Thread.sleep((actualWaitSeconds * 1000).toLong())
    }

    override fun toString(): String {
        return "WaitRebootActor(waitSeconds=$waitSeconds)"
    }
}
