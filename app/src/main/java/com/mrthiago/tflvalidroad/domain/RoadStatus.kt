/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.domain

// Domain Model
data class RoadStatus(
    val id: String,
    val displayName: String,
    val statusSeverity: String,
    val statusSeverityDescription: String
)