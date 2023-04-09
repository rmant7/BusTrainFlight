package ru.z8.louttsev.cheaptripmobile.shared

expect object FileUtils {
    fun readTextFile(filePath: String): String
}