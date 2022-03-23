package com.jerech.petsshop.service

interface FoodSearchService {
    fun search(text: String, pageNumber: Int, sortBy: Pair<String, String>)
}
