package com.sapan.restjet.utils

import com.sapan.restjet.data.RequestState
import java.net.URLEncoder

class UrlBuildException(message: String, cause: Throwable? = null): Exception(message, cause)

object UrlBuilder {
    fun buildUrl(state: RequestState): String {
        try {
            val baseUrl = state.baseUrl.trim().trimStart('/').trimEnd('/')
            val pathUrl = state.pathUrl.trim().trimStart('/')

            val url = if (pathUrl.isNotEmpty()) {
                "$baseUrl/$pathUrl"
            } else {
                baseUrl
            }

            return if (state.queryParameters.isNotEmpty()) {
                val queryString = buildQueryString(state.queryParameters)
                return "$url?$queryString"
            } else {
                url
            }
        } catch (e: Exception) {
            throw UrlBuildException("Error building URL", e)
        }
    }

    private fun buildQueryString(queryParams: Map<String, String>): String {
        return queryParams.entries
            .filter { (key, value) -> key.isNotEmpty() && value.isNotEmpty() }
            .joinToString(separator = "&") { (key, value) ->
                "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
            }
    }
}