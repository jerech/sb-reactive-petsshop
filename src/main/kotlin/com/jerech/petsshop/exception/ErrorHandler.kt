package com.jerech.petsshop.exception

import com.jerech.petsshop.exception.ErrorHandler.MapOfErrors.ERROR_HANDLER_BY_EXCEPTION_NAME
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

enum class ErrorHandler(
    exceptionName: String,
    val httpStatus: HttpStatus,
    val title: String
) {

    GENERIC_ERROR(GenericProblemException::class.java.name, HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC ERROR"),
    INVALID_PARAMS(WebExchangeBindException::class.java.name, HttpStatus.BAD_REQUEST, "Invalid Params");

    init {
        ERROR_HANDLER_BY_EXCEPTION_NAME[exceptionName] = this
    }

    object MapOfErrors {
        var ERROR_HANDLER_BY_EXCEPTION_NAME: MutableMap<String, ErrorHandler> = HashMap()
    }

    companion object {
        @JvmStatic
        fun from(error: Throwable): ErrorHandler {
            return ERROR_HANDLER_BY_EXCEPTION_NAME.getOrDefault(error::class.java.name, GENERIC_ERROR)
        }
    }

    fun build(detail: String?): Mono<ResponseEntity<ErrorResponse>> {
        return ErrorResponse(httpStatus, title, detail ?: "")
            .let {
                Mono.just(
                    ResponseEntity.status(httpStatus).body(it)
                )
            }
    }

    override fun toString(): String {
        return name
    }
}
