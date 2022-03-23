package com.jerech.petsshop.configuration.featureflag

import com.jerech.petsshop.configuration.RequestContext
import com.jerech.petsshop.configuration.featureflag.strategies.CustomerSegmentStrategy
import io.getunleash.DefaultUnleash
import io.getunleash.Unleash
import io.getunleash.event.UnleashReady
import io.getunleash.event.UnleashSubscriber
import io.getunleash.repository.FeatureToggleResponse
import io.getunleash.repository.ToggleCollection
import io.getunleash.util.UnleashConfig
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomUnleashConfig(private val requestContext: RequestContext) {
    @Bean
    fun createUnleash(): Unleash {
        val unleashConfig = UnleashConfig.builder()
            .appName("petsshop-api")
            .instanceId("your-instance-1")
            .unleashAPI("http://localhost:4242/api/")
            .customHttpHeader("Authorization", "*:default.2aaf5fcd065fe47ce0aac2a83eb3a36c8d59012e24529b247af2cb72")
            .subscriber(CustomSubcribes)
            .backupFile("unleash-features.json")
            .synchronousFetchOnInitialisation(true)
            .build()
        return DefaultUnleash(unleashConfig, CustomerSegmentStrategy(requestContext))
    }

    companion object CustomSubcribes : UnleashSubscriber {
        val log = LoggerFactory.getLogger(CustomSubcribes::class.java.simpleName)
        override fun onReady(ready: UnleashReady) {
            log.debug("Unleash is ready")
        }

        override fun togglesFetched(toggleResponse: FeatureToggleResponse) {
            log.debug("Fetch toggles with status: " + toggleResponse.getStatus())
        }

        override fun togglesBackedUp(toggleCollection: ToggleCollection) {
            log.debug("Backup stored")
        }
    }
}
