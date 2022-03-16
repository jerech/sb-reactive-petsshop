package com.jerech.petsshop.configuration

import com.jerech.petsshop.service.quality.validation.HighQuatity
import com.jerech.petsshop.service.quality.validation.StandardQuatity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FoodQualityValidators {

    /**
     * Create config for initilize food quality validators.
     * POO Pattern use for this funtionallity is Chain of Representation
     */
    @Bean
    fun buildQualityValidator(): StandardQuatity {
        val highQuatity = HighQuatity(null)
        return StandardQuatity(highQuatity)
    }
}