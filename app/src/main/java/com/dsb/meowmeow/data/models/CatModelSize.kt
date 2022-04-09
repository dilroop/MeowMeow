package com.dsb.meowmeow.data.models

import com.google.gson.annotations.SerializedName

enum class CatModelSize {
    @SerializedName("full") FULL,
    @SerializedName("med") MEDIUM,
    @SerializedName("small") SMALL,
    @SerializedName("thumb") THUMBNAIL,
}