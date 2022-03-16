package com.jerech.petsshop.exception

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

internal class InvalidParamExceptionTest {

    @Test
    fun createInvalidParamException() {
        val invalidParamException = InvalidParamException("message")

        assertThat(invalidParamException)
            .isInstanceOf(RuntimeException::class.java)

        assertThat(invalidParamException.message)
            .isEqualTo("message")
    }
}
