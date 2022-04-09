package com.dsb.meowmeow.data.models

import com.dsb.meowmeow.model.CatModel
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CatModelDto(
    @SerializedName("categories") val categories: List<CatCategoryDto>? = null,
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int
) {
    fun toCatModel() = CatModel(
        categories = categories?.map { it.toCategory() } ?: emptyList(),
        id = id,
        url = url,
        height = height,
        width = width
    )
}