package com.jerech.petsshop.service.impl

import com.jerech.petsshop.model.Food
import com.jerech.petsshop.repository.FoodRepository
import com.jerech.petsshop.service.FoodService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
internal class FoodServiceImplTest {

    @Mock
    lateinit var foodRepository: FoodRepository

    @Test
    fun save() {
        //given
        `when`(foodRepository.save(any()))
            .thenReturn(Mono.just(Food(1, "Dogee", "DOG", "ADULT", 20f, LocalDateTime.now())))
        val  foodService: FoodService = FoodServiceImpl(foodRepository)

        //when
        val monoFood = foodService.save(Food(null, "Dogee", "DOG", "ADULT", 20f, LocalDateTime.now()))

        //then
        StepVerifier
            .create(monoFood)
            .consumeNextWith {
                assertEquals(it.id, 1)
                assertEquals(it.name, "Dogee")
                assertEquals(it.type, "DOG")
                assertEquals(it.segment, "ADULT")
                assertEquals(it.proteinPercentage, 20f)
            }
            .verifyComplete()

    }

    @Test
    fun getAllFoods() {
        //given
        val food1 = Food(null, "Food1", "Type", "Segment", 10f, LocalDateTime.now())
        val food2 = Food(null, "Food2", "Type", "Segment", 10f, LocalDateTime.now())
        `when`(foodRepository.findAll())
            .thenReturn(Flux.just(food1, food2))
        val  foodService: FoodService = FoodServiceImpl(foodRepository)

        //when
        val monoFoods = foodService.getAll()

        //then
        StepVerifier
            .create(monoFoods)
            .consumeNextWith {
                Assertions.assertThat(it.size).isEqualTo(2)
            }
            .verifyComplete()
    }
}