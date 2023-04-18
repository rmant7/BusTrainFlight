package ru.z8.louttsev.cheaptripmobile.shared.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.IntListSerializer

@kotlinx.serialization.Serializable
data class RouteJson(
    val from: Int,
    val to: Int,
    val price: Int,
    val duration: Int,
    @SerialName("direct_routes")
    @Serializable(with = IntListSerializer::class)
    val directRoutes: List<Int>
)
