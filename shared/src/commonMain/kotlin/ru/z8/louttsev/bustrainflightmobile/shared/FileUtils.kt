package ru.z8.louttsev.bustrainflightmobile.shared

expect object FileUtils {
    fun readTextFile(filePath: String): String
}