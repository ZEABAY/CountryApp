package com.example.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.model.Country

class CountryViewModel : ViewModel() {
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom() {
        val country = Country("Türkiye4", "Ankara4", "Asia4", "TRY4", "www.null.com", "Turkish4")
        countryLiveData.value = country
    }

}