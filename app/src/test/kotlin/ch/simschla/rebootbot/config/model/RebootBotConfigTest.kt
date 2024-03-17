package ch.simschla.rebootbot.config.model

import ch.simschla.rebootbot.check.FailAfterDurationChecker
import ch.simschla.rebootbot.check.NetworkChecker
import ch.simschla.rebootbot.reboot.domain.generic.SequentialRebootActor
import ch.simschla.rebootbot.reboot.domain.internetbox.InternetBoxUi
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue
import java.time.temporal.ChronoUnit

class RebootBotConfigTest {
    @Test
    fun readsSimpleConfig() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-simple.yml")

        expect {
            that(config.driver).isA<DelayedRetryingDriverConfig>()
            that(config.networkChecker).isA<UrlNetworkCheckerConfig>()
            that(config.rebootActor).isA<InternetBoxUIRebootActorConfig>()
            that(config.dryRun).isFalse()
        }
    }

    @Test fun readsComplexConfig() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-complex.yml")

        expectThat(config) {
            get { driver }.isA<DelayedRetryingDriverConfig>()
            get { driver as DelayedRetryingDriverConfig }.and {
                get { timeUnit }.isEqualTo(ChronoUnit.MINUTES)
                get { timeBetweenChecks }.isEqualTo(10L)
                get { maxRetries }.isEqualTo(5)
            }

            get { networkChecker }.isA<FailAfterDurationCheckerConfig>()
            get { networkChecker as FailAfterDurationCheckerConfig }.and {
                get { seconds }.isEqualTo(300L)
                get { from }.isA<MajorityVotingNetworkCheckerConfig>()
                get { from as MajorityVotingNetworkCheckerConfig }.and {
                    get { of }.isA<List<UrlNetworkCheckerConfig>>()
                    get { of[0] }.isA<UrlNetworkCheckerConfig>()
                    get { of[0] as UrlNetworkCheckerConfig }.and {
                        get { url }.isEqualTo("https://www.google.com")
                        get { httpMethod }.isEqualTo("HEAD")
                    }
                    get { of[3] }.isA<UrlNetworkCheckerConfig>()
                    get { of[3] as UrlNetworkCheckerConfig }.and {
                        get { url }.isEqualTo("https://www.microsoft.com")
                        get { httpMethod }.isEqualTo("GET")
                    }
                }
            }

            get { rebootActor }.isA<SequentialRebootActorConfig>()
            get { rebootActor as SequentialRebootActorConfig }.and {
                get { of }.isA<List<RebootActorConfig>>()
                get { of[0] }.isA<InternetBoxUIRebootActorConfig>()
                get { of[0] as InternetBoxUIRebootActorConfig }.and {
                    get { url }.isEqualTo("https://192.168.1.1")
                }
                get { of[2] }.isA<TpLinkSwitchUIRebootActorConfig>()
                get { of[2] as TpLinkSwitchUIRebootActorConfig }.and {
                    get { url }.isEqualTo("https://192.168.1.2")
                }
            }

            get { dryRun }.isTrue()
        }
    }

    @Test fun instantiatesSimpleBot() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-simple.yml")
        val bot = config.instantiate()

        expectThat(bot) {
            get { checker }.isA<NetworkChecker>()
            get { rebootActor }.isA<InternetBoxUi>()
            get { dryRun }.isFalse()
        }
    }

    @Test fun instantiatesComplexBot() {
        val config = RebootBotConfig.fromResource("/reboot-bot-config-complex.yml")
        val bot = config.instantiate()

        expectThat(bot) {
            get { checker }.isA<FailAfterDurationChecker>()
            get { rebootActor }.isA<SequentialRebootActor>()
            get { dryRun }.isTrue()
        }
    }
}
