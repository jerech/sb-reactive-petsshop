package com.jerech.petsshop.configuration.featureflag

import assertk.assertThat
import assertk.assertions.isEqualTo
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import io.getunleash.event.UnleashReady
import io.getunleash.repository.FeatureToggleResponse
import io.getunleash.repository.ToggleCollection
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.LoggerFactory

@ExtendWith(MockitoExtension::class)
class CustomSubcribesTest {

    @Mock
    lateinit var unleashReady: UnleashReady
    @Mock
    lateinit var featureToggleResponse: FeatureToggleResponse
    @Mock
    lateinit var toggleCollection: ToggleCollection

    private val logger: Logger = LoggerFactory.getLogger(CustomUnleashConfig.CustomSubcribes::class.java.simpleName) as Logger
    private val listAppender: ListAppender<ILoggingEvent> = ListAppender()

    @BeforeEach
    fun setUp() {
        listAppender.start()
        logger.addAppender(listAppender)
    }

    @Test
    fun onReadyCustomSubscribes() {
        // when
        CustomUnleashConfig.CustomSubcribes.onReady(unleashReady)
        val log = listAppender.list.stream().findFirst().get()
        // then
        assertThat(log.message)
            .isEqualTo("Unleash is ready")
    }
    @Test
    fun togglesFetchedCustomSubscribes() {
        // when
        CustomUnleashConfig.CustomSubcribes.togglesFetched(featureToggleResponse)
        val log = listAppender.list.stream().findFirst().get()
        // then
        assertThat(log.message)
            .isEqualTo("Fetch toggles with status: null")
    }

    @Test
    fun togglesBackedUpCustomSubscribes() {
        // when
        CustomUnleashConfig.CustomSubcribes.togglesBackedUp(toggleCollection)
        val log = listAppender.list.stream().findFirst().get()
        // then
        assertThat(log.message)
            .isEqualTo("Backup stored")
    }
}
