package com.example.cryptoapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapplication.util.RetrofitInstance.api
import com.example.cryptoapplication.model.CryptoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModel():ViewModel() {
    val data=MutableLiveData<List<CryptoModel>>()
    var loading=MutableLiveData<Boolean>()
    var job : Job? = null
    fun takeData(){
        job= viewModelScope.launch(context = Dispatchers.IO) {
            val response=api.getData()
            withContext(Dispatchers.Main){
                loading.value=true
                if(response.isSuccessful){
                    loading.value=false
                    data.value = response.body()
                }else{
                    loading.value=true
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}