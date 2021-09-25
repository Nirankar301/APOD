package com.nir.apod.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nir.apod.data.IAPODRepository
import com.nir.apod.model.APOD
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

class APODViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val apodRepository: IAPODRepository
) : ViewModel() {

    // LiveData responsible to provide updated apod to view
    val apodLiveData = MutableLiveData<APOD>()
    // LiveData responsible to provide error info to view
    val errorLiveData = MutableLiveData<Boolean>()
    val dateMismatchLiveData = MutableLiveData<Boolean>()
    // LiveData responsible to inform view about the need of loading indicator
    val isLoading = MutableLiveData<Boolean>()

    fun getAPOD() {
        isLoading.postValue(true)
        viewModelScope.launch(dispatcher) {
            runCatching {
                apodRepository.getAPOD()
            }.map {
                isLoading.postValue(false)
                if (it == null){
                   errorLiveData.postValue(true)
                } else {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val currentDate = dateFormat.format(Date())
                    if (currentDate != it.date) {
                        dateMismatchLiveData.postValue(true)
                    }
                    apodLiveData.postValue(it)
                    Timber.d("APOD -> $it")
                }
            }.getOrElse {
                isLoading.postValue(false)
                errorLiveData.postValue(true)
            }
        }
    }
}