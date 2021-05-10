package com.rx.application.http

import com.rx.application.http.model.HouseResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object {
        const val BASE_URL: String = "https://data.taipei/api/v1/dataset/"
    }

    @GET("5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a?scope=resourceAquire")
    fun getHouseData(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<Response<HouseResponse?>?>?

    @GET("f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
    fun getPlantData(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Observable<Response<HouseResponse?>?>?
}