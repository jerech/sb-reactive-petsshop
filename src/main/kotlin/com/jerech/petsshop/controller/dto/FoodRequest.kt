package com.jerech.petsshop.controller.dto

import com.jerech.petsshop.model.Food
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class FoodRequest(@field:NotBlank(message = "Campo requerido") val name: String, val type: String, val segment: String, val proteinPercentage: Float) {

    fun mapToFood(): Food {
        return Food(null, name, type, segment, proteinPercentage, LocalDateTime.now())
    }
}
