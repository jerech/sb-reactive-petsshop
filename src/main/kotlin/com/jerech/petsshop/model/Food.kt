package com.jerech.petsshop.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Food(@Id var id: Int?,
                var name: String,
                var type: String,
                var segment: String,
                var proteinPercentage: Float,
                var createdDate: LocalDateTime
)
