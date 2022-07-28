package com.chris.catgif

import com.google.gson.annotations.SerializedName

data class Cat (
    @SerializedName("id"          ) var id          : String,
    @SerializedName("name"        ) var name        : String,
    @SerializedName("description") var description  : String,
    @SerializedName("temperament") var temperament  : String,
    @SerializedName("image")       var image        : Image
    )

data class Image (
    @SerializedName("url"         ) var url         : String
    )
