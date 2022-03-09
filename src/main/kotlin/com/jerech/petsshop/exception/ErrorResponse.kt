package com.jerech.petsshop.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.http.HttpStatus
import java.net.URI
import java.util.*

@JsonPropertyOrder(*["type", "status", "title", "detail", "instance"])
class ErrorResponse(val status: HttpStatus, val title: String, val detail: String,
                    val instance: URI =  URI.create("urn:uuid:" + UUID.randomUUID()))
