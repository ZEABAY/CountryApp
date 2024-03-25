package com.example.countries.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countries.model.Country
import com.example.countries.service.CountryAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FeedViewModel : ViewModel() {
    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val c1 = Country("a", "b", "c", "d", "e", "f")
        val c2 = Country("a2", "b2", "c2", "d2", "e2", "f4")
        val c3 = Country("a3", "b3", "c3", "d3", "e3", "f4")

        val countryList = arrayListOf<Country>(c1, c2, c3)

        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

        //getDataFromApi()
    }

    private fun getDataFromApi() {
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        countries.value = t
                        countryError.value = false
                        countryLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }
                })
        )

    }
}