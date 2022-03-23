package com.jerech.petsshop.configuration.featureflag

import assertk.assertThat
import assertk.assertions.isInstanceOf
import com.jerech.petsshop.configuration.RequestContext
import io.getunleash.DefaultUnleash
import org.junit.jupiter.api.Test

internal class CustomUnleashConfigTest {

    @Test
    fun createUnleash() {
        val requestContext = RequestContext("1", "B")
        val customUnleashConfig = CustomUnleashConfig(requestContext)
        val unleash = customUnleashConfig.createUnleash()
        assertThat(unleash)
            .isInstanceOf(DefaultUnleash::class.java)
    }
}
