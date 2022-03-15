package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food

abstract class FoodQualityValidator(val nextValidator: FoodQualityValidator?) {
    abstract fun validate(foodList: List<Food>): Boolean

}