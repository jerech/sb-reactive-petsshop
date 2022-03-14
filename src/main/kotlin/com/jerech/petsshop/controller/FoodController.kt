package com.jerech.petsshop.controller

import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.controller.dto.FoodResponse
import com.jerech.petsshop.exception.ErrorHandler
import com.jerech.petsshop.service.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
        logger.log(Level.INFO, "Init save")
        return foodRequest
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
            .map { listFood -> listFood
                .stream()
                .map { food -> FoodResponse(food.name, food.type, food.segment, food.proteinPercentage) }
                .collect(Collectors.toList())
            }
            .doOnNext { listFoodResponse -> logger.log(Level.INFO, "Cantidad de foods responses: " + listFoodResponse.size) }
            .map<ResponseEntity<*>> { ResponseEntity.ok(it) }
            .onErrorResume { error ->
                ErrorHandler.from(error).build(error.message)
            }
    }
}