package ru.z8.louttsev.cheaptripmobile.shared

expect class ResourceReader() {
    fun readResource(name: String): String
}