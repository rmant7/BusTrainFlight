package ru.z8.louttsev.bustrainflightmobile.shared

import java.io.File

actual object FileUtils {
    actual fun readTextFile(filePath: String): String {
        val file = File(filePath)
        return file.readText()
    }
}