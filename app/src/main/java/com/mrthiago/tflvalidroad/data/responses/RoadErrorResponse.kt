/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.data.responses

// Error Response DTO
data class RoadErrorResponse(
    val `$type`: String,
    val exceptionType: String,
    val httpStatus: String,
    val httpStatusCode: Int,
    val message: String,
    val relativeUri: String,
    val timestampUtc: String
)