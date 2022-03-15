package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class StandardQuatityTest {

    @Test
    fun validate() {
        //given
        val standardQuatity = StandardQuatity(null)
        val food1 = Food(1, "Food1", "Type", "Segment", 20f, LocalDateTime.now())
        val food2 = Food(2, "Food2", "Type", "Segment", 18f, LocalDateTime.now())
        val list = listOf(food1, food2)

        //when
        Assertions
            .assertThat(standardQuatity.validate(list))
            //then
            .isTrue()
    }
}