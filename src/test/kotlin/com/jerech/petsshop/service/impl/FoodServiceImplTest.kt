package com.jerech.petsshop.service.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jerech.petsshop.model.Food
import com.jerech.petsshop.repository.FoodRepository
import com.jerech.petsshop.service.FoodService
import com.jerech.petsshop.service.quality.validation.HighQuality
import com.jerech.petsshop.service.quality.validation.StandardQuatity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

internal class FoodServiceImplTest {

    val foodRepository: FoodRepository = mock(FoodRepository::class.java)

    lateinit var initialQualityValidator: StandardQuatity

    @BeforeEach
    fun setUp() {
        val highQuatity = HighQuality(null)
        initialQualityValidator = StandardQuatity(highQuatity)
    }

    @Test
    fun save() {
        // given
        `when`(foodRepository.save(any()))
            .thenReturn(Mono.just(Food(1, "Dogee", "DOG", "ADULT", 20f, LocalDateTime.now())))
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val monoFood = foodService.save(Food(null, "Dogee", "DOG", "ADULT", 20f, LocalDateTime.now()))

        // then
        StepVerifier
            .create(monoFood)
            .expectNextMatches {
                it.id == 1 &&
                    it.name == "Dogee" &&
                    it.type == "DOG" &&
                    it.segment == "ADULT" &&
                    it.proteinPercentage == 20f
            }
            .expectComplete()
            .verify()
    }

    @Test
    fun getAllFoods() {
        // given
        val food1 = Food(null, "Food1", "Type", "Segment", 10f, LocalDateTime.now())
        val food2 = Food(null, "Food2", "Type", "Segment", 10f, LocalDateTime.now())
        `when`(foodRepository.findAll())
            .thenReturn(Flux.just(food1, food2))
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val monoFoods = foodService.getAll()

        // then
        StepVerifier
            .create(monoFoods)
            .consumeNextWith {
                assertThat(it.size).isEqualTo(2)
            }
            .verifyComplete()
    }

    @Test
    fun validateProtein() {
        // given
        val food1 = Food(1, "Food1", "Type", "Segment", 32f, LocalDateTime.now())
        val food2 = Food(2, "Food2", "Type", "Segment", 31f, LocalDateTime.now())
        val food3 = Food(3, "Food3", "Type", "Segment", 30f, LocalDateTime.now())
        val food4 = Food(4, "Food4", "Type", "Segment", 28f, LocalDateTime.now())
        val listFoods = listOf(food1, food2, food3, food4)
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val mono = foodService.validateQuality(listFoods)
        // then
        StepVerifier
            .create(mono)
            .consumeNextWith {
                assertThat(it).isEqualTo(true)
            }
            .verifyComplete()
    }

    @Test
    fun validateProteinFailed_standardValidation() {
        // given
        val food1 = Food(1, "Food1", "Type", "Segment", 32f, LocalDateTime.now())
        val food2 = Food(2, "Food2", "Type", "Segment", 31f, LocalDateTime.now())
        val food3 = Food(3, "Food3", "Type", "Segment", 30f, LocalDateTime.now())
        val food4 = Food(4, "Food4", "Type", "Segment", 10f, LocalDateTime.now())
        val listFoods = listOf(food1, food2, food3, food4)
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val mono = foodService.validateQuality(listFoods)
        // then
        StepVerifier
            .create(mono)
            .consumeNextWith {
                assertThat(it).isEqualTo(false)
            }
            .verifyComplete()
    }

    @Test
    fun validateProteinFailed_highValidation() {
        // given
        val food1 = Food(1, "Food1", "Type", "Segment", 32f, LocalDateTime.now())
        val food2 = Food(2, "Food2", "Type", "Segment", 31f, LocalDateTime.now())
        val food3 = Food(3, "Food3", "Type", "Segment", 30f, LocalDateTime.now())
        val food4 = Food(4, "Food4", "Type", "Segment", 25f, LocalDateTime.now())
        val listFoods = listOf(food1, food2, food3, food4)
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val mono = foodService.validateQuality(listFoods)
        // then
        StepVerifier
            .create(mono)
            .consumeNextWith {
                assertThat(it).isEqualTo(false)
            }
            .verifyComplete()
    }

    @Test
    fun validateProteinFailed_listEmpty() {
        // given
        val listFoods = emptyList<Food>()
        val foodService: FoodService = FoodServiceImpl(foodRepository, initialQualityValidator)

        // when
        val mono = foodService.validateQuality(listFoods)
        // then
        StepVerifier
            .create(mono)
            .consumeNextWith {
                assertThat(it).isEqualTo(true)
            }
            .verifyComplete()
    }
}
