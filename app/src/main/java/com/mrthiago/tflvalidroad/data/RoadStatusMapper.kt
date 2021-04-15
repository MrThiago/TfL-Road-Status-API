/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.data

import com.mrthiago.tflvalidroad.data.responses.RoadSuccessResponse
import com.mrthiago.tflvalidroad.domain.RoadStatus
import javax.inject.Inject

class RoadStatusMapper @Inject constructor() : Function1<List<RoadSuccessResponse>, List<RoadStatus>> {
    override fun invoke(roadResponse: List<RoadSuccessResponse>): List<RoadStatus> {
        return roadResponse.map {
            RoadStatus(it.id, it.displayName, it.statusSeverity, it.statusSeverityDescription)
        }
    }
}