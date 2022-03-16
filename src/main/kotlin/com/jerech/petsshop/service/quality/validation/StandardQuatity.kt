package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import java.util.stream.Collectors

class StandardQuatity(nextValidator: FoodQualityValidator?) : FoodQualityValidator(nextValidator) {
    override fun validate(foodList: List<Food>): Boolean {

        val sizeToCompare = foodList
            .stream()
            .filter { it.proteinPercentage > STANDARD_QUALITY_PROTEIN }
            .collect(Collectors.toList())
            .size

        when (foodList.size) {
            sizeToCompare -> return nextValidator?.validate(foodList) ?: true
            else -> {
                println("Failed StandardQuatity validation")
                return false
            }
        }
    }

    companion object {
        private const val STANDARD_QUALITY_PROTEIN = 15f
    }
}
