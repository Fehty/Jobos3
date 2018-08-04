package com.fehtystudio.jobos3.Interface

import com.fehtystudio.jobos3.Data.ApiJobData
import retrofit2.Call
import retrofit2.http.GET

interface MyInterface {
    @GET("/api/show_jobs/?format=json")
    fun getJobData(): Call<List<ApiJobData>>
}