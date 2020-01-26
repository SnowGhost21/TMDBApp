package com.arctouch.codechallenge.view.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _successRequest = MutableLiveData<Boolean>()
    val successRequest: LiveData<Boolean> = _successRequest

    protected val _errorsRequest = MutableLiveData<Boolean>()
    val errorRequest: LiveData<Boolean> = _errorsRequest
}