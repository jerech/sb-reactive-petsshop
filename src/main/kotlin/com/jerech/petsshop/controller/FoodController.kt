package com.jerech.petsshop.controller

import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.controller.dto.FoodResponse
import com.jerech.petsshop.service.FoodService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.WebExchangeBindException
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
    fun save(@Valid @RequestBody foodRequest: Mono<FoodRequest>): Mono<ResponseEntity<Void>> {
        logger.log(Level.INFO, "Init save")
        return foodRequest
            .map { foodRequest -> foodRequest.mapToFood() }
            .flatMap { food -> foodService.save(food) }
            .map<ResponseEntity<Void>?> { ResponseEntity.status(HttpStatus.CREATED).build() }
            .onErrorResume {
                if (it is WebExchangeBindException)
                    Mono.just(ResponseEntity.badRequest().build())
                 else
                    Mono.just(ResponseEntity.internalServerError().build())
            }
    }

    @GetMapping
    fun getAll(): Mono<ResponseEntity<List<FoodResponse>>> {
        return foodService.getAll()
            .map { listFood -> listFood
                .stream()
                .map { food -> FoodResponse(food.name, food.type, food.segment, food.proteinPercentage) }
                .collect(Collectors.toList())
            }
            .doOnNext { listFoodResponse -> logger.log(Level.INFO, "Cantidad de foods responses: " + listFoodResponse.size) }
            .map<ResponseEntity<List<FoodResponse>>?> { ResponseEntity.ok(it) }
            .onErrorResume { Mono.just(ResponseEntity.internalServerError().build()) }
    }
}