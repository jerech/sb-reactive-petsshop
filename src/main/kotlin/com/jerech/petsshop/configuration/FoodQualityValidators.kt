package com.jerech.petsshop.configuration

import com.jerech.petsshop.service.quality.validation.HighQuality
import com.jerech.petsshop.service.quality.validation.StandardQuality
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
    fun buildQualityValidator(): StandardQuality {
        val highQuatity = HighQuality(null)
        return StandardQuality(highQuatity)
    }
}
