package com.jerech.petsshop.service.quality.validation

import com.jerech.petsshop.model.Food
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

class StandardQuality(nextValidator: FoodQualityValidator?) : FoodQualityValidator(nextValidator) {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    override fun validate(foodList: List<Food>): Boolean {

        val quantityFoodsWithStandardQuantity = getQuantityFoodsIsStandardQuality(foodList)
        val quantityFoods = getQuantityFoods(foodList)

        return if (quantityFoods == quantityFoodsWithStandardQuantity) {
            validateIfNextValidatorExistsOrReturnTrue(foodList)
        } else {
            validationFailure()
        }
    }

    private fun getQuantityFoods(foodList: List<Food>): Int {
        return foodList.size
    }
    private fun getQuantityFoodsIsStandardQuality(foodList: List<Food>): Int {
        return foodList
            .stream()
            .filter { it.proteinPercentage > STANDARD_QUALITY_PROTEIN }
            .collect(Collectors.toList())
            .size
    }

    private fun validateIfNextValidatorExistsOrReturnTrue(foodList: List<Food>): Boolean {
        return nextValidator?.validate(foodList) ?: true
    }

    private fun validationFailure(): Boolean {
        logger.debug("Failed HighQuatity validation")
        return false
    }

    companion object {
        private const val STANDARD_QUALITY_PROTEIN = 15f
    }
}
