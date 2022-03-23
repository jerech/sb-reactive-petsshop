package com.jerech.petsshop.configuration

import org.springframework.stereotype.Component

@Component
class RequestContext(var userId: String?, var customerSegment: String?)
