package com.dsb.meowmeow.data.models

import com.dsb.meowmeow.data.models.Constants.HATS_CATEGORY_ID
import com.dsb.meowmeow.model.CatModel

internal sealed class ApiRequestFilter {
    data class AnimatedImageFilter(val enable: Boolean) : ApiRequestFilter() {
        override fun applyFilter(properties: FilterProperties): FilterProperties {
            if (!enable) return properties
            val newMime = when (properties.mimeType) {
                MimeType.ANIMATED, null -> MimeType.ANIMATED
                MimeType.STATIC -> null
            }
            return properties.copy(mimeType = newMime)
        }
    }

    data class StaticImageFilter(val enable: Boolean) : ApiRequestFilter() {
        override fun applyFilter(properties: FilterProperties): FilterProperties {
            if (!enable) return properties
            val newMime = when (properties.mimeType) {
                MimeType.STATIC, null -> MimeType.STATIC
                MimeType.ANIMATED -> null
            }
            return properties.copy(mimeType = newMime)
        }
    }

    data class OnlyHatsImagesFilter(val enable: Boolean) : ApiRequestFilter() {
        override fun applyFilter(properties: FilterProperties): FilterProperties {
            if (!enable) return properties
            if (properties.categories != null) {
                val found = properties.categories
                    .split(",")
                    .find { it == HATS_CATEGORY_ID.toString() }
                if (found != null) {
                    return properties
                }
            }
            val category = if (properties.categories == null) {
                HATS_CATEGORY_ID.toString()
            } else {
                "${properties.categories},$HATS_CATEGORY_ID"
            }
            return properties.copy(categories = category)
        }
    }

    abstract fun applyFilter(properties: FilterProperties): FilterProperties

    data class FilterProperties(
        val mimeType: MimeType? = null,
        val size: CatModelSize? = null,
        val categories: String? = null
    )
}

sealed class ApiResultFilter {
    data class NoHatsFilter(val enable: Boolean) : ApiResultFilter() {
        override fun filter(list: List<CatModel>): List<CatModel> {
            if (!enable) return list
            return list.filter { it.categories.isEmpty() || it.categories.find { it.id == HATS_CATEGORY_ID } == null }
        }
    }

    abstract fun filter(list: List<CatModel>): List<CatModel>
}
