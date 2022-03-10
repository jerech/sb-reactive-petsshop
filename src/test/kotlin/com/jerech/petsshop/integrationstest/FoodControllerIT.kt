package com.jerech.petsshop.integrationstest

import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.controller.dto.FoodResponse
import com.jerech.petsshop.model.Food
import com.jerech.petsshop.repository.FoodRepository
import com.jerech.petsshop.service.FoodService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.body
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@TestMethodOrder(OrderAnnotation::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class FoodControllerIT {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @SpyBean
    lateinit var foodRepository: FoodRepository

    @SpyBean
    lateinit var foodService: FoodService

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
        val countDownLatch = CountDownLatch(1)
        foodRepository.deleteAll()
            .doOnSuccess{
                countDownLatch.countDown()
            }
            .subscribe()
        countDownLatch.await(1L, TimeUnit.SECONDS)
        Assertions.assertThat(countDownLatch.count).isEqualTo(0)
    }

    @Test
    @Order(1)
    fun `create food successfully` () {
        //given
        val requestBody = FoodRequest("Dogee", "DOG", "ADULT", 20f)
        //when
        webTestClient
            .post()
            .uri("/v1/food")
            .body(Mono.just(requestBody))
            .exchange()
            //then
            .expectStatus().isCreated

        verify(foodService).save(any())
        verify(foodRepository).save(any())

        val monoFood: Mono<Food> = foodRepository.findById(1)
        StepVerifier
            .create(monoFood)
            .consumeNextWith {
                Assertions.assertThat(it.name).isEqualTo("Dogee")
                Assertions.assertThat(it.type).isEqualTo("DOG")
                Assertions.assertThat(it.segment).isEqualTo("ADULT")
            }
            .verifyComplete()

    }

    @Test
    @Order(2)
    fun `get all foods`() {
        //given
        val food1 = Food(null, "Food1", "Type", "Segment", 10f, LocalDateTime.now())
        val food2 = Food(null, "Food2", "Type", "Segment", 16f, LocalDateTime.now())
        val foodResponse1 = FoodResponse( "Food1", "Type", "Segment", 10f)
        val foodResponse2 = FoodResponse( "Food2", "Type", "Segment", 16f)

        foodRepository.save(food1).block()
        foodRepository.save(food2).block()

        //when
        webTestClient
            .get()
            .uri("/v1/food")
            .exchange()
            //then
            .expectStatus().isOk
            .expectBodyList(FoodResponse::class.java)
            .consumeWith<WebTestClient.ListBodySpec<FoodResponse>> {
                response ->
                Assertions.assertThat(response.responseBody!![0])
                    .isEqualTo(foodResponse1)
                Assertions.assertThat(response.responseBody!![1])
                    .isEqualTo(foodResponse2)
            }
            .hasSize(2)

    }

}