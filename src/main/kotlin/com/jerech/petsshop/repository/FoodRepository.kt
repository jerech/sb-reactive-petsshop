package com.jerech.petsshop.repository

import com.jerech.petsshop.model.Food
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface FoodRepository: ReactiveCrudRepository<Food, Int> {

}
