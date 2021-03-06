package com.jerech.petsshop.service

import com.jerech.petsshop.model.Food
import reactor.core.publisher.Mono

interface FoodService {

    /**
     * Save a new Food and return the new food saved
     * @author JereCh
     */
    fun save(food: Food): Mono<Food>

    /**
     * Return all foods saved
     * @author JereCh
     */
    fun getAll(): Mono<List<Food>>

    /**
     * Return if from foods quality is between parameters supported
     * @see com.jerech.petsshop.configuration.FoodQualityValidators.buildQualityValidator
     * @author JereCh
     */
    fun validateQuality(list: List<Food>): Mono<Boolean>
}
