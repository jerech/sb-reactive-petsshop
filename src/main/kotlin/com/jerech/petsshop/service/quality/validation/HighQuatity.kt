package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import java.util.stream.Collectors

class HighQuatity(nextValidator: FoodQualityValidator?) : FoodQualityValidator(nextValidator) {
    override fun validate(foodList: List<Food>): Boolean {

        val sizeToCompare = foodList
            .stream()
            .filter { it.proteinPercentage > HIGH_QUALITY_PROTEIN }
            .collect(Collectors.toList())
            .size

        when (foodList.size) {
            sizeToCompare -> return nextValidator?.validate(foodList) ?: true
            else -> {
                println("Failed HighQuatity validation")
                return false
            }
        }
    }

    companion object {
        private const val HIGH_QUALITY_PROTEIN = 26f
    }
}
