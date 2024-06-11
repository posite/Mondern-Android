package com.posite.modern.data.remote.model.meal

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @field:Json(name = "categories") val categories: List<Category>
)