package com.sapan.restjet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapan.restjet.data.HttpMethod
import com.sapan.restjet.data.RequestState
import com.sapan.restjet.data.ResponseState
import com.sapan.restjet.network.RetrofitClient
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class RequestResponseViewModel @Inject constructor() : ViewModel() {
    private val _requestState = MutableStateFlow(RequestState())
    val requestState: StateFlow<RequestState> get() = _requestState

    private val _responseState = MutableStateFlow(ResponseState())
    val responseState: StateFlow<ResponseState> get() = _responseState

    private var currentRequestJob: Job? = null

    fun updateBaseUrl(baseUrl: String) {
        _requestState.value = _requestState.value.copy(baseUrl = baseUrl)
    }

    fun updatePathUrl(pathUrl: String) {
        _requestState.value = _requestState.value.copy(pathUrl = pathUrl)
    }

    fun updateBody(body: String) {
        _requestState.value = _requestState.value.copy(body = body)
    }

    fun updateHttpMethod(httpMethod: HttpMethod) {
        _requestState.value = _requestState.value.copy(action = httpMethod)
    }

    fun addHeader(key: String, value: String): Boolean {
        val newHeaders = _requestState.value.headers.toMutableMap()
        newHeaders[key] = value
        _requestState.value = _requestState.value.copy(headers = newHeaders)
        return true
    }

    fun addQueryParams(key: String, value: String): Boolean {
        val newQueryParams = _requestState.value.queryParameters.toMutableMap()
        newQueryParams[key] = value
        _requestState.value = _requestState.value.copy(queryParameters = newQueryParams)
        return true
    }

    fun sendRequest() {
        currentRequestJob?.cancel()
        currentRequestJob = viewModelScope.launch {
            try {
                _responseState.update {
                    it.copy(
                        isLoading = true,
                        error = null,
                        statusCode = "",
                        responseBody = "",
                        responseHeaders = emptyMap(),
                        responseTime = null
                    )
                }

                val startTime = System.currentTimeMillis()
                val url = buildUrl()
                val headers = _requestState.value.headers.toMutableMap()

                if (_requestState.value.body.isNotEmpty() &&
                    !headers.containsKey("Content-Type")) {
                    headers["Content-Type"] = "application/json"
                }

                val resonse: Response<ResponseBody> = when(_requestState.value.action) {
                    HttpMethod.GET   -> makeGetRequest(url, headers)
                    HttpMethod.POST  -> makePostRequest(url, headers)
                    HttpMethod.PUT   -> makePutRequest(url, headers)
                    HttpMethod.DELETE-> makeDeleteRequest(url, headers)
                    HttpMethod.PATCH -> makePatchRequest(url, headers)
                    else -> makeGetRequest(url, headers)
                }

                val endTime = System.currentTimeMillis()

                _responseState.update {
                    it.copy(
                        isLoading = false,
                        statusCode = resonse.code().toString(),
                        responseBody = resonse.body()?.string() ?: "",
                        responseHeaders = resonse.headers().toMap(),
                        responseTime = endTime - startTime
                    )
                }

            } catch (e: HttpException) {
                _responseState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message,
                        responseTime = null
                    )
                }
            } catch (e: Exception) {
                _responseState.update {
                    it.copy(
                        isLoading = false,
                        error = "Unexpected error: ${e.message}",
                        responseTime = null
                    )
                }
            }
        }
    }

    fun clearResponse() {
        _responseState.value = ResponseState()
    }

    fun clearRequest() {
        _requestState.value = RequestState()
    }

    fun buildUrl(): String {
        val state = _requestState.value
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
            _responseState.value = _responseState.value.copy(error = e.message)
            return ""
        }
    }

    fun resetAll() {
        clearRequest()
        clearResponse()
    }

    private fun buildQueryString(queryParams: Map<String, String>): String {
        return queryParams.entries
            .filter { (key, value) -> key.isNotEmpty() && value.isNotEmpty() }
            .joinToString(separator = "&") { (key, value) ->
                "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
            }
    }

    private suspend fun makeGetRequest(
        url: String,
        headers: Map<String, String>
    ): Response<ResponseBody> {
        return RetrofitClient.apiService.getRequest(url, headers)
    }

    private suspend fun makePostRequest(
        url: String,
        headers: Map<String, String>
    ): Response<ResponseBody> {
        val requestBody = _requestState.value.body.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        return RetrofitClient.apiService.putRequest(url, headers, requestBody)
    }

    private suspend fun makePutRequest(
        url: String,
        headers: Map<String, String>
    ): Response<ResponseBody> {
        val requestBody = _requestState.value.body.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        return RetrofitClient.apiService.putRequest(url, headers, requestBody)
    }

    private suspend fun makePatchRequest(
        url: String,
        headers: Map<String, String>
    ): Response<ResponseBody> {
        val requestBody = _requestState.value.body.toRequestBody(
            "application/json; charset=utf-8".toMediaTypeOrNull()
        )
        return RetrofitClient.apiService.patchRequest(url, headers, requestBody)
    }

    private suspend fun makeDeleteRequest(
        url: String,
        headers: Map<String, String>
    ): Response<ResponseBody> {
        return RetrofitClient.apiService.deleteRequest(url, headers)
    }

    private fun cancelRequest() {
        currentRequestJob?.cancel()
        _responseState.update {
            it.copy(isLoading = false)
        }
    }
}