package ch.simschla.rebootbot.reboot

interface RebootActor {
    fun reboot(dryRun: Boolean = true)
}
