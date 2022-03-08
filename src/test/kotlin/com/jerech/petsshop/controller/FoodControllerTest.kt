package com.jerech.petsshop.controller

import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.model.Food
import com.jerech.petsshop.service.FoodService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class FoodControllerTest {

    lateinit var webTestClient: WebTestClient

    @Mock
    lateinit var foodService: FoodService

    @BeforeEach
    fun setUp() {
        webTestClient = WebTestClient.bindToController(FoodController(foodService)).build()
    }

    @Test
    fun createFoodSuccessfully() {
        //given
        `when`(foodService.save(any()))
            .thenReturn(Mono.just(Food(1, "Dogee", "DOG", "ADULT", 20f, LocalDateTime.now())))
        val requestBody = FoodRequest("Dogee", "DOG", "ADULT", 20f)
        //when
        webTestClient
            .post()
            .uri("/v1/food")
            .body(Mono.just(requestBody))
            .exchange()
            //then
            .expectStatus().isCreated
    }

    @Test
    fun createFood4xx() {
        //given
        val requestBody = FoodRequest("", "", "", 20f)

        //when
        webTestClient
            .post()
            .uri("/v1/food")
            .body(Mono.just(requestBody))
            .exchange()
            //then
            .expectStatus().is4xxClientError
    }

    @Test
    fun getAllSucessful() {
        //given
        `when`(foodService.getAll())
            .thenReturn(Mono.just(Collections.emptyList()))

        //when
        webTestClient
            .get()
            .uri("/v1/food")
            .exchange()
            //then
            .expectStatus().isOk
    }

}