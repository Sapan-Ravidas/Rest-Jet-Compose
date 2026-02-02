package com.sapan.restjet.data

enum class HttpMethod(val string: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH");

    companion object {
        fun fromString(string: String): HttpMethod {
            return HttpMethod.entries.find { it.string == string } ?: GET
        }
    }
}

data class RequestState (
    val action: HttpMethod = HttpMethod.GET,
    val baseUrl: String = "",
    val pathUrl: String = "",
    val headers: Map<String, String> = mapOf(),
    val queryParameters: Map<String, String> = mapOf(),
    val body: String = "",
)

data class ResponseState(
    val statusCode: String = "",
    val responseBody: String = "",
    val responseHeaders: List<String> = listOf(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val responseTime: Long? = null
) {
    val isComplete = (statusCode.isNotEmpty() && responseBody.isNotEmpty()) || error != null
}
