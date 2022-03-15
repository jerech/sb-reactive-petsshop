package com.jerech.petsshop.exception

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class GenericProblemExceptionTest {

    @Test
    fun createGenericProblemException() {
        val genericProblemException = GenericProblemException("message")

        Assertions
            .assertThat(genericProblemException)
            .isInstanceOf(RuntimeException::class.java)

        Assertions
            .assertThat(genericProblemException.message)
            .isEqualTo("message")

    }
}