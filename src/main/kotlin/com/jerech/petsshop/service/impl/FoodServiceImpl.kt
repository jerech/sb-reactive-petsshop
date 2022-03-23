package com.jerech.petsshop.service.impl

import com.jerech.petsshop.controller.FoodController
import com.jerech.petsshop.model.Food
import com.jerech.petsshop.repository.FoodRepository
import com.jerech.petsshop.service.FoodService
import com.jerech.petsshop.service.quality.validation.StandardQuality
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.logging.Level
import java.util.logging.Logger

@Service
class FoodServiceImpl(private val foodRepository: FoodRepository, private val initStepValidator: StandardQuality) : FoodService {

    val logger: Logger = Logger.getLogger(FoodController::class.java.simpleName)

    override fun save(food: Food): Mono<Food> {
        logger.log(Level.INFO, "save in service")
        return foodRepository.save(food)
    }

    override fun getAll(): Mono<List<Food>> {
        return foodRepository.findAll()
            .collectList()
    }

    override fun validateQuality(list: List<Food>): Mono<Boolean> {
        return Mono.just(initStepValidator)
            .map { it.validate(list) }
    }
}
