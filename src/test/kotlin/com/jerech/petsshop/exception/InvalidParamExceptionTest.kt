package com.jerech.petsshop.exception

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class InvalidParamExceptionTest {
    @Test
    fun createInvalidParamException() {
        val invalidParamException = InvalidParamException("message")

        Assertions
            .assertThat(invalidParamException)
            .isInstanceOf(RuntimeException::class.java)

        Assertions
            .assertThat(invalidParamException.message)
            .isEqualTo("message")

    }
}