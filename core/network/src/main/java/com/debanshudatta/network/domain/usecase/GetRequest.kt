package com.debanshudatta.network.domain.usecase

import com.debanshudatta.network.domain.Result
import com.debanshudatta.network.domain.error.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import javax.inject.Inject

class ClientWrapper @Inject constructor(val networkClient: HttpClient) {

    suspend inline fun <reified T> networkGetUsecase(
        endpoint: String,
        queries: Map<String, String>?,
    ): Result<T, NetworkError> {
        val response = try {
            networkClient.get(endpoint) {
                parameters {
                    if (queries != null) {
                        for ((key, value) in queries) {
                            parameter(key, value)
                        }
                    }
                }
            }
        } catch (ex: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (ex: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            in 200..299 -> {
                Result.Success(response.body<T>())
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}
