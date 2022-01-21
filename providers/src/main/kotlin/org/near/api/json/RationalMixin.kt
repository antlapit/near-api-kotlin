package org.near.api.json

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape= JsonFormat.Shape.ARRAY)
class RationalMixin(val start: Int, val endInclusive: Int)
