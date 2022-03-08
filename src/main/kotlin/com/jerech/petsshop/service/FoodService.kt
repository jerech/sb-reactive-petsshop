package com.jerech.petsshop.service

import com.jerech.petsshop.model.Food
import reactor.core.publisher.Mono

interface FoodService {

    fun save(food: Food): Mono<Food>

    fun getAll(): Mono<List<Food>>

}
