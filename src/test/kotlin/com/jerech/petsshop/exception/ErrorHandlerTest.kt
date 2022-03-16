package com.jerech.petsshop.exception

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.test.StepVerifier

@ExtendWith(MockitoExtension::class)
internal class ErrorHandlerTest {

    @Mock
    lateinit var methodParameter: MethodParameter
    @Mock
    lateinit var bindingResult: BindingResult

    @Test
    fun getErrorHandlerFromExceptionNameForInvalidParamsException() {
        val error = WebExchangeBindException(methodParameter, bindingResult)
        val errorHandler = ErrorHandler.from(error)
        assertThat(errorHandler.httpStatus)
            .isEqualTo(HttpStatus.BAD_REQUEST)

        assertThat(errorHandler.title)
            .isEqualTo("Invalid Params")
    }

    @Test
    fun getErrorHandlerFromExceptionNameForNotFoundException() {
        val error = Throwable(RuntimeException())
        val errorHandler = ErrorHandler.from(error)
        assertThat(errorHandler.httpStatus)
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        assertThat(errorHandler.title)
            .isEqualTo("GENERIC ERROR")
    }

    @Test
    fun getServerResponseForInvalidParamsException() {
        val errorHandler = ErrorHandler.INVALID_PARAMS
        val responseMono = errorHandler.build("Detalle error")

        StepVerifier
            .create(responseMono)
            .expectNextMatches { r -> HttpStatus.BAD_REQUEST == r.statusCode }
            .expectComplete()
            .verify()
    }

    @Test
    fun getServerResponseForGenericProblemException() {

        val errorHandler = ErrorHandler.GENERIC_ERROR
        val responseMono = errorHandler.build("Detalle error")

        StepVerifier
            .create(responseMono)
            .expectNextMatches { r ->
                HttpStatus.INTERNAL_SERVER_ERROR == r.statusCode
            }
            .expectComplete()
            .verify()
    }

    @Test
    fun toStringCheck() {
        val errorHandler = ErrorHandler.GENERIC_ERROR
        assertThat(errorHandler.toString())
            .isEqualTo("GENERIC_ERROR")
    }
}
