package com.dsb.meowmeow.data.models

import com.dsb.meowmeow.model.CatCategory
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CatCategoryDto(
    @SerializedName("id") private val id: Int,
    @SerializedName("name") private val name: String,
) {
    fun toCategory(): CatCategory = CatCategory(id = id, category = name)
}