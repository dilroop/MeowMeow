package com.dsb.meowmeow.data

import com.dsb.meowmeow.data.models.ApiRequestFilter
import com.dsb.meowmeow.data.models.ApiRequestFilter.FilterProperties
import com.dsb.meowmeow.data.models.ApiResultFilter
import com.dsb.meowmeow.model.CatModel
import javax.inject.Inject

internal class CatsRepository @Inject constructor(
    private val catApi: TheCatApi
) {
    suspend fun getCats(
        apiRequestFilters: List<ApiRequestFilter>,
        apiResultFilters: List<ApiResultFilter>
    ): List<CatModel> {
        var filterProperties = FilterProperties()
        apiRequestFilters.forEach { filter ->
            filterProperties = filter.applyFilter(filterProperties)
        }
        val results = try {
             catApi.getRandomCats(
                limit = 9,
                size = filterProperties.size,
                mimeTypes = filterProperties.mimeType?.value,
                categoryIds = filterProperties.categories
            ).map { it.toCatModel() }
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
        return apiResultFilters.fold(results) { catModels, resultFilter ->
            resultFilter.filter(catModels)
        }
    }
}
