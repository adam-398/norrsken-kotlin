package com.example.norskenn_kotlin


data class LocationResponse ( //class for the response from the API
    val address: LocationAddress //class for the address object in the response
)

data class LocationAddress ( //class for the next level of the address object
    val village: String //bottom level when you hit a primitive type
)