package com.posite.modern.data.model.shopping

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodingResult(
    @field:Json(name = "formatted_address") val formatted_address: String = ""
)