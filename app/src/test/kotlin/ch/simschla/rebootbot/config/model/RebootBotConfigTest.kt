package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.FailAfterDurationChecker
import ch.simschla.rebootbot.check.NetworkChecker
import ch.simschla.rebootbot.reboot.domain.generic.SequentialRebootActor
import ch.simschla.rebootbot.reboot.domain.internetbox.InternetBoxUi
import kotlin.test.Test

class RebootBotConfigTest {
    @Test fun readsSimpleConfig() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-simple.yml")
        assert(config.networkChecker is UrlNetworkCheckerConfig)
        assert(config.rebootActor is InternetBoxUIRebootActorConfig)
        assert(config.dryRun == false)
    }

    @Test fun readsComplexConfig() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-complex.yml")
        assert(config.networkChecker is FailAfterDurationCheckerConfig)
        val failAfterDurationCheckerConfig = config.networkChecker as FailAfterDurationCheckerConfig
        assert(failAfterDurationCheckerConfig.seconds == 300L)
        assert(failAfterDurationCheckerConfig.from is MajorityVotingNetworkCheckerConfig)
        val majorityVotingCheckerConfig = failAfterDurationCheckerConfig.from as MajorityVotingNetworkCheckerConfig
        assert(majorityVotingCheckerConfig.of.contains(UrlNetworkCheckerConfig("https://www.google.com")))
        assert(majorityVotingCheckerConfig.of.contains(UrlNetworkCheckerConfig("https://www.microsoft.com", "GET")))
        assert(config.rebootActor is SequentialRebootActorConfig)
        val rootRebootActor = config.rebootActor as SequentialRebootActorConfig
        assert(rootRebootActor.of.contains(InternetBoxUIRebootActorConfig("https://192.168.1.1")))
        assert(rootRebootActor.of.contains(TpLinkSwitchUIRebootActorConfig("https://192.168.1.2")))
        assert(config.dryRun == true)
    }

    @Test fun instantiatesSimpleBot() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-simple.yml")
        val bot = config.instantiate()
        assert(bot.checker is NetworkChecker)
        assert(bot.rebootActor is InternetBoxUi)
        assert(bot.dryRun == false)
    }

    @Test fun instantiatesComplexBot() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-complex.yml")
        val bot = config.instantiate()
        assert(bot.checker is FailAfterDurationChecker)
        assert(bot.rebootActor is SequentialRebootActor)
        assert(bot.dryRun == true)
    }
}
