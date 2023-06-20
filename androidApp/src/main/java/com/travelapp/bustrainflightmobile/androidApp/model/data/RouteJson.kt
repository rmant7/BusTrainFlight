package com.travelapp.bustrainflightmobile.androidApp.model.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@kotlinx.serialization.Serializable
data class RouteJson(
    val from: Int,
    val to: Int,
    val price: Int,
    val duration: Int,
    @SerialName("direct_routes")
//    @Serializable(with = IntListSerializer::class)
    val directRoutes: List<Int>
)

object IntListSerializer : KSerializer<List<Int>> {

    override val descriptor: SerialDescriptor =
        ListSerializer(Int.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: List<Int>) {
        encoder.encodeString(value.joinToString(","))
    }

    override fun deserialize(decoder: Decoder): List<Int> {
        return decoder.decodeString().split(",").map { it.toInt() }
    }

}