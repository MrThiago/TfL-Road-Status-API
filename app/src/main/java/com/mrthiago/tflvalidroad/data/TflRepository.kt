/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.data

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.mrthiago.tflvalidroad.api.TflService
import com.mrthiago.tflvalidroad.data.responses.RoadErrorResponse
import com.mrthiago.tflvalidroad.domain.RoadStatus
import com.mrthiago.tflvalidroad.utilities.network.NetworkState
import javax.inject.Inject

class TflRepository @Inject constructor(
    private val service: TflService,
    private val mapper: RoadStatusMapper
) {

    suspend fun getRoadStatus(id: String): NetworkState<List<RoadStatus>> {
        return try {
            val response = service.getRoadId(id = id)
            val result = response.body()

            if (response.isSuccessful && result != null) {
                NetworkState.Success(mapper(result))
            } else {
                // Try to parse Error into RoadErrorResponse model
                val roadInvalidResponse: RoadErrorResponse? = try {
                    Gson().fromJson(
                        response.errorBody()?.charStream()?.readText(),
                        RoadErrorResponse::class.java
                    )
                } catch (e: JsonSyntaxException) {
                    null
                }
                val errorMessage = roadInvalidResponse?.message ?: response.message()
                NetworkState.Error(errorMessage)
            }
        } catch (e: Exception) {
            var errorMessage = e.message ?: "There was an Unknown Error"
            if (errorMessage.contains("Unable to resolve host")) {
                errorMessage = "No Internet connection, please check network connection and try again."
            }

            NetworkState.Error(errorMessage)
        }
    }
}