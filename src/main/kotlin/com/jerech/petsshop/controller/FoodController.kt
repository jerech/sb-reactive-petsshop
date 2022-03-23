package com.jerech.petsshop.controller

import com.jerech.petsshop.configuration.RequestContext
import com.jerech.petsshop.controller.dto.FoodRequest
import com.jerech.petsshop.controller.dto.FoodResponse
import com.jerech.petsshop.exception.ErrorHandler
import com.jerech.petsshop.service.FoodService
import io.getunleash.Unleash
import io.getunleash.UnleashContext
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
class FoodController(
    private val foodService: FoodService,
    private val unleash: Unleash,
    private val requestContext: RequestContext
) {

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
            .map { listFood ->
                listFood
                    .stream()
                    .map { food ->
                        FoodResponse(food.name, food.type, food.segment, food.proteinPercentage)
                    }
                    .collect(Collectors.toList())
            }
            .doOnNext { listFoodResponse -> logger.log(Level.INFO, "Cantidad de foods responses: " + listFoodResponse.size) }
            .map<ResponseEntity<*>> { ResponseEntity.ok(it) }
            .onErrorResume { error ->
                ErrorHandler.from(error).build(error.message)
            }
    }

    @GetMapping("quality/validate")
    fun validateAllFoodQuality(
        @RequestHeader("User-Id") userId: String,
        @RequestHeader("Customer-Segment") customerSegment: String
    ): Mono<ResponseEntity<*>> {
        requestContext.apply {
            this.userId = userId
            this.customerSegment = customerSegment
        }
        val context: UnleashContext = UnleashContext.builder()
            .userId(requestContext?.let { it.userId } ?: "").build()

        return if (unleash.isEnabled("food-quality-validation", context)) {
            foodService.getAll()
                .flatMap { foodService.validateQuality(it) }
                .map {
                    ResponseEntity.ok((if (it) "High quality for all food" else "The quality is small for all food"))
                }
        } else {
            Mono.just(ResponseEntity.ok("Validation not permited"))
        }
    }
}
