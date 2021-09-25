package com.nir.apod.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class APOD(
    @SerializedName("copyright") val copyright: String? = null,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String?,
    @SerializedName("hdurl") val hdurl: String? = null,
    @SerializedName("media_type") val media_type: String? = null,
    @SerializedName("service_version") val service_version: String? = null,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?
) : Serializable




