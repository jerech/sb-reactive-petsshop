package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import java.util.stream.Collectors

class StandardQuatity(nextValidator: FoodQualityValidator?): FoodQualityValidator(nextValidator) {
    override fun validate(foodList: List<Food>): Boolean {
        val standardProtein = 15f
        val listStandardQuatity = foodList
            .stream()
            .filter{ it.proteinPercentage > standardProtein}
            .collect(Collectors.toList())

        if (listStandardQuatity.size == foodList.size){
            return nextValidator?.validate(foodList) ?: true
        } else {
            println("Failed StandardQuatity validation")
            return false
        }
    }
}