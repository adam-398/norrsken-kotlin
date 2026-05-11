package com.example.norskenn_kotlin


data class LocationResponse ( //class for the response from the API
    val address: LocationAddress //class for the address object in the response
)

data class LocationAddress(
    val village: String?,
    val town: String?,
    val city: String?,
    val municipality: String?,
    val county: String?
)