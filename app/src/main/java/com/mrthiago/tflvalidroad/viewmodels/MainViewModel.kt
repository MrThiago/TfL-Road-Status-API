/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrthiago.tflvalidroad.data.TflRepository
import com.mrthiago.tflvalidroad.domain.RoadStatus
import com.mrthiago.tflvalidroad.utilities.DispatcherProvider
import com.mrthiago.tflvalidroad.utilities.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TflRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class TflDataState {
        class Success(val data: List<RoadStatus>) : TflDataState()
        class Failure(val message: String = "Sorry, there was an error. Try again later.") : TflDataState()
        object Empty : TflDataState()
        object Loading : TflDataState()
    }

    private val _serviceRoadStatusData = MutableStateFlow<TflDataState>(TflDataState.Empty)
    val serviceRoadStatusData: StateFlow<TflDataState> = _serviceRoadStatusData

    fun getRoadById(id: String) {
        viewModelScope.launch(dispatchers.io) {
            _serviceRoadStatusData.value = TflDataState.Loading

            when (val result = repository.getRoadStatus(id)) {
                is NetworkState.Success -> {
                    _serviceRoadStatusData.value = TflDataState.Success(result.validData)
                }
                is NetworkState.Error -> {
                    _serviceRoadStatusData.value = TflDataState.Failure(result.error)
                }
            }
        }
    }
}