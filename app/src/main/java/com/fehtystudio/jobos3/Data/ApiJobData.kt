package com.fehtystudio.jobos3.Data

import com.google.gson.annotations.SerializedName

data class ApiJobData(
        @SerializedName("location") var location: String,
        @SerializedName("contract_type") var contractType: String,
        @SerializedName("contract_time") var contractTime: String,
        @SerializedName("title") var title: String,
        @SerializedName("description") var description: String,
        @SerializedName("company") var company: String,
        @SerializedName("service") var service: String,
        @SerializedName("url") var url: String,
        @SerializedName("salary") var salary: String
)