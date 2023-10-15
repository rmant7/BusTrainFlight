package ru.z8.louttsev.cheaptripmobile.androidApp.model.data

@kotlinx.serialization.Serializable
data class DirectRouteJson(
    val from: Int,
    val to: Int,
    val transport: Int,
    val price: Int,
    val duration: Int
)
