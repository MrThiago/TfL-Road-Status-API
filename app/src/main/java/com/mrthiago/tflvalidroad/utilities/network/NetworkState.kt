/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.utilities.network

sealed class NetworkState<out R> {
    data class Success<out T>(val validData: T) : NetworkState<T>()
    data class Error(val error: String) : NetworkState<Nothing>()
}