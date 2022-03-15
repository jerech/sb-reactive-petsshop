package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import java.util.stream.Collectors

class HighQuatity(nextValidator: FoodQualityValidator?): FoodQualityValidator(nextValidator) {
    override fun validate(foodList: List<Food>): Boolean {
        val standardProtein = 26f
        val sizeToCompare = foodList
            .stream()
            .filter{ it.proteinPercentage > standardProtein}
            .collect(Collectors.toList())
            .size

        if (foodList.size == sizeToCompare) {
            return nextValidator?.validate(foodList) ?: true
        } else {
            println("Failed HighQuatity validation")
            return false
        }
    }
}