package com.dsb.meowmeow.model

data class CatModel(
    val categories: List<CatCategory>,
    val id: String,
    val url: String,
    val height: Int,
    val width: Int
)