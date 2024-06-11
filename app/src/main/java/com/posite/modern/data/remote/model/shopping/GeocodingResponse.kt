package com.posite.modern.data.remote.model.shopping

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeocodingResponse(
    @field:Json(name = "results") val results: List<GeocodingResult>,
    @field:Json(name = "status") val status: String
)
