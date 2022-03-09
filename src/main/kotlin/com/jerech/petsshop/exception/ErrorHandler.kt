package com.jerech.petsshop.exception;

import com.jerech.petsshop.exception.ErrorHandler.MapOfErrors.ERROR_HANDLER_BY_EXCEPTION_NAME
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono

enum class ErrorHandler(
    private val exceptionName: String,
    private val httpStatus: HttpStatus,
    private val title: String
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
        fun from(exceptionName: String) : ErrorHandler {
            return ERROR_HANDLER_BY_EXCEPTION_NAME.getOrDefault(exceptionName, GENERIC_ERROR)
        }
    }

    fun build(detail: String?): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(httpStatus, title, detail!!)
        return Mono.just(ResponseEntity.status(httpStatus)
            .body(errorResponse))
    }

    override fun toString(): String {
        return name
    }

}
