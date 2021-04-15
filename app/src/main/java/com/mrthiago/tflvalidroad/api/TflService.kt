/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.api

import com.mrthiago.tflvalidroad.BuildConfig
import com.mrthiago.tflvalidroad.data.responses.RoadSuccessResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TflService {

    @GET("Road/{id}")
    suspend fun getRoadId(
        @Path("id") id: String, // Comma-separated list of road identifiers
        @Query("app_key") clientId: String = BuildConfig.TFL_ACCESS_KEY
    ): Response<List<RoadSuccessResponse>>
}