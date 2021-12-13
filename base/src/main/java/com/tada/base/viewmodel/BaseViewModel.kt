package com.tada.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tada.base.network.DataStateError
import kotlinx.coroutines.launch
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {
    //todo many thing like loading, dialog....

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorLiveData = MutableLiveData<DataStateError>()
    val errorLiveData: LiveData<DataStateError>
        get() = _errorLiveData


    fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                block()
            } catch (exception: Exception) {
                _errorLiveData.postValue(DataStateError(exception))
            } finally {
                _isLoading.value = false
            }
        }
    }
}