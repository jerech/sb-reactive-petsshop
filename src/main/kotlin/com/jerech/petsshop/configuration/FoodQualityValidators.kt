package com.jerech.petsshop.configuration

import com.jerech.petsshop.service.quality.validation.HighQuality
import com.jerech.petsshop.service.quality.validation.StandardQuatity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FoodQualityValidators {

    /**
     * Create config for initilize food quality validators.
     * POO Pattern use for this funtionallity is Chain of Representation
     * @author JereCh
     */
    @Bean
    fun buildQualityValidator(): StandardQuatity {
        val highQuatity = HighQuality(null)
        return StandardQuatity(highQuatity)
    }
}
