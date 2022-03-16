package com.jerech.petsshop.exception

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import org.junit.jupiter.api.Test

internal class GenericProblemExceptionTest {

    @Test
    fun createGenericProblemException() {
        val genericProblemException = GenericProblemException("message")

        assertThat(genericProblemException)
            .isInstanceOf(RuntimeException::class.java)

        assertThat(genericProblemException.message)
            .isEqualTo("message")
    }
}
