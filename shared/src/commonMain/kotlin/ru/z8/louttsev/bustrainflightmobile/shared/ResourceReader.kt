package ru.z8.louttsev.bustrainflightmobile.shared

expect class ResourceReader() {
    fun readResource(name: String): String
}