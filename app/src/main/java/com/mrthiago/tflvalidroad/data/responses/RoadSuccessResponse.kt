/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.data.responses

// Success Response DTO
data class RoadSuccessResponse(
    val `$type`: String,
    val bounds: String,
    val displayName: String,
    val envelope: String,
    val id: String,
    val statusSeverity: String,
    val statusSeverityDescription: String,
    val url: String
)