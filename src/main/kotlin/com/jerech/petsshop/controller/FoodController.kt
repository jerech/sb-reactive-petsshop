package com.jerech.petsshop.controller

import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.controller.dto.FoodResponse
import com.jerech.petsshop.exception.ErrorHandler
import com.jerech.petsshop.model.Food
import com.jerech.petsshop.service.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/v1/food")
class FoodController(private val foodService: FoodService) {

    val logger: Logger = Logger.getLogger(FoodController::class.java.simpleName)

    @PostMapping
    fun save(@Valid @RequestBody foodRequest: Mono<FoodRequest>): Mono<ResponseEntity<*>> {
        return foodRequest
            .doOnNext { logger.log(Level.INFO, "Init save method") }
            .map { request -> request.mapToFood() }
            .flatMap { food -> foodService.save(food) }
            .map<ResponseEntity<*>> { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            .onErrorResume { error ->
                ErrorHandler.from(error).build(error.message)
            }
    }

    @GetMapping
    fun getAll(): Mono<ResponseEntity<*>> {
        return foodService.getAll()
            .map { mapToFoodResponseList(it) }
            .doOnNext { listFoodResponse -> printFoodQuantity(listFoodResponse.size) }
            .map<ResponseEntity<*>> { ResponseEntity.ok(it) }
            .onErrorResume { error ->
                ErrorHandler.from(error).build(error.message)
            }
    }

    private fun mapToFoodResponseList(listFood: List<Food>): List<FoodResponse> {
        return listFood
            .stream()
            .map { createFoodResponse(it) }
            .collect(Collectors.toList())
    }

    private fun createFoodResponse(food: Food): FoodResponse {
        return FoodResponse(food.name, food.type, food.segment, food.proteinPercentage)
    }

    private fun printFoodQuantity(quantity: Int) {
        logger.log(Level.INFO, "Food quantity for all method: $quantity")
    }
}
