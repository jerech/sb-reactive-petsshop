package com.jerech.petsshop.configuration

import com.jerech.petsshop.service.quality.validation.HighQuatity
import com.jerech.petsshop.service.quality.validation.StandardQuatity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FoodQualityValidators {

    @Bean
    fun initialQualityValidator(): StandardQuatity {
        val highQuatity = HighQuatity(null)
        return StandardQuatity(highQuatity)
    }
}