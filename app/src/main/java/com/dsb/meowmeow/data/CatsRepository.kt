package com.dsb.meowmeow.data

import com.dsb.meowmeow.model.CatModel
import javax.inject.Inject

internal class CatsRepository @Inject constructor(
    private val catApi: TheCatApi
) {
    suspend fun getCats(limit: Int): List<CatModel> {
        return try {
            catApi.getRandomCats(limit = limit).map { it.toCatModel() }
        } catch (exception: Exception) {
            emptyList()
        }
    }
}
