package com.rx.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rx.application.model.House
import com.rx.application.model.Plant

class MainViewModel : ViewModel() {
    var currentTitle: MutableLiveData<String> = MutableLiveData()
    var houseData: MutableLiveData<MutableList<House>> = MutableLiveData()
    var plantData: MutableLiveData<MutableList<Plant>> = MutableLiveData()
}