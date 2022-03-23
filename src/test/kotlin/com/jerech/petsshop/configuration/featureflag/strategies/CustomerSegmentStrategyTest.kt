package com.jerech.petsshop.configuration.featureflag.strategies

import assertk.Assert
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jerech.petsshop.configuration.RequestContext
import org.junit.jupiter.api.Test

internal class CustomerSegmentStrategyTest {

    private val requestContext = RequestContext("1", "B")

    @Test
    fun getName() {
        // given
        val customerSegmentStrategy = CustomerSegmentStrategy(requestContext)

        // when
        val strategyName = customerSegmentStrategy.name

        // then
        assertThat(strategyName)
            .isEqualTo("CustomerSegment")
    }

    @Test
    fun isEnabled() {
        // given
        val customerSegmentStrategy = CustomerSegmentStrategy(requestContext)

        // when
        val isEnabled = customerSegmentStrategy.isEnabled(mutableMapOf(Pair("name", "B")))

        // then
        assertThat(isEnabled)
            .isEqualTo(true)
    }
}
