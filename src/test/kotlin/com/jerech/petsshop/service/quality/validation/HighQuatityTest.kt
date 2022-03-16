package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class HighQuatityTest {

    @Test
    fun validate() {
        // given
        val highQuatity = HighQuatity(null)
        val food1 = Food(1, "Food1", "Type", "Segment", 30f, LocalDateTime.now())
        val food2 = Food(2, "Food2", "Type", "Segment", 32f, LocalDateTime.now())
        val list = listOf(food1, food2)

        // when
        Assertions
            .assertThat(highQuatity.validate(list))
            // then
            .isTrue()
    }
}
