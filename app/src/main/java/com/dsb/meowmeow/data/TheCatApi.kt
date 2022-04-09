package com.dsb.meowmeow.data

import com.dsb.meowmeow.BuildConfig
import com.dsb.meowmeow.data.models.CatModelDto
import com.dsb.meowmeow.data.models.CatModelSize
import com.dsb.meowmeow.data.models.MimeType
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal interface TheCatApi {

    @Headers("x-api-key: ${BuildConfig.CAT_API_KEY}")
    @GET("v1/images/search")
    suspend fun getRandomCats(
        @Query("limit") limit: Int,
        @Query("mime_types") mimeTypes: String? = MimeType.ANIMATED.value,
        @Query("order") order: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: CatModelSize? = null,
        @Query("category_ids") categoryIds: String? = null,
        @Query("breed_id") breedId: String? = null,
    ): List<CatModelDto>
}