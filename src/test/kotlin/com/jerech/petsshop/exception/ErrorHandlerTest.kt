package com.jerech.petsshop.exception;

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.test.StepVerifier

class ErrorHandlerTest {

    @Test
    fun getErrorHandlerFromExceptionNameForInvalidParamsException() {
        val errorHandler = ErrorHandler.from(WebExchangeBindException::class.java.name)
        Assertions
            .assertThat(errorHandler.httpStatus)
            .isEqualTo(HttpStatus.BAD_REQUEST);

        Assertions
            .assertThat(errorHandler.title)
            .isEqualTo("Invalid Params");

    }

    @Test
    fun getErrorHandlerFromExceptionNameForNotFoundException() {
        val errorHandler = ErrorHandler.from(RuntimeException::class.java.name)
        Assertions
            .assertThat(errorHandler.httpStatus)
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        Assertions
            .assertThat(errorHandler.title)
            .isEqualTo("GENERIC ERROR")
    }

    @Test
    fun getServerResponseForInvalidParamsException() {
        val errorHandler = ErrorHandler.INVALID_PARAMS;
        val responseMono = errorHandler.build("Detalle error")

        StepVerifier
            .create(responseMono)
            .consumeNextWith { r ->  Assertions
                .assertThat(r.statusCode)
                .isEqualTo(HttpStatus.BAD_REQUEST)
            }
            .verifyComplete()
    }

    @Test
    fun getServerResponseForGenericProblemException() {

        val errorHandler = ErrorHandler.GENERIC_ERROR
        val responseMono = errorHandler.build("Detalle error")

        StepVerifier
            .create(responseMono)
            .consumeNextWith { r -> Assertions
                .assertThat(r.statusCode)
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
            }
            .verifyComplete();
    }
}
