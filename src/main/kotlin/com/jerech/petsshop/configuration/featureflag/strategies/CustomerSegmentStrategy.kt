package com.jerech.petsshop.configuration.featureflag.strategies

import com.jerech.petsshop.configuration.RequestContext
import io.getunleash.strategy.Strategy

class CustomerSegmentStrategy(private val requestContext: RequestContext?) : Strategy {

    override fun getName(): String {
        return "CustomerSegment"
    }

    override fun isEnabled(parameters: MutableMap<String, String>): Boolean {
        return parameters.get("name").equals(requestContext?.customerSegment)
    }
}
