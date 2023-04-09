package ru.z8.louttsev.cheaptripmobile.shared.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable
//data class LocationMapJson(
//    val location: Map<Int, LocationJson>
//)

@Serializable
data class LocationJson(
    val id: Int,
    val name: String,
    @SerialName("country_name")
    val countryName: String,
    val latitude: Double,
    val longitude: Double
)


