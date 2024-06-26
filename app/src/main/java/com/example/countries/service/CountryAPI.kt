package com.example.countries.service

import com.example.countries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {

    // BASE -> https://raw.githubusercontent.com/
    // EXT-> atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>


}