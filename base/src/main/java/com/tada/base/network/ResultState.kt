package com.tada.base.network

sealed class ResultState<out T> {
    // in case we use StateFlow, it does not allow initializing with a null value, it's good to set initial value to IDLE
    sealed class State : ResultState<Nothing>() {
        object IDLE : State()
        object Empty : State()
        object Loading : State()

        // Sometimes it's done but it returns nothing, it's kind of signal to the caller knows that it's done
        object Done : State()
        data class Retrying(val attempt: Int, val maxAttempts: Int) : State()
    }

    sealed class DataState<out T> : ResultState<T>() {
        data class Success<out T>(val data: T) : DataState<T>()
        data class Error(val cause: Throwable) : DataState<Nothing>()
    }
}
