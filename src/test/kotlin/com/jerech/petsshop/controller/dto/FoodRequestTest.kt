package com.jerech.petsshop.controller.dto

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

internal class FoodRequestTest {

    @Test
    fun mapToFood() {
        val request = FoodRequest("name", "type", "segment", 10f)
        val food = request.mapToFood()
        Assertions
            .assertThat("name")
            .isEqualTo(food.name)
        Assertions
            .assertThat("type")
            .isEqualTo(food.type)
        Assertions
            .assertThat("segment")
            .isEqualTo(food.segment)
        Assertions
            .assertThat(10f)
            .isEqualTo(food.proteinPercentage)
    }
}
