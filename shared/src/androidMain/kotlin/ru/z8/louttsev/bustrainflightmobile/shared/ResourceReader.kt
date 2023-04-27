package ru.z8.louttsev.bustrainflightmobile.shared

import java.io.InputStreamReader

// Android resources are available via the class loader
actual class ResourceReader {
    actual fun readResource(name: String): String =
        javaClass.classLoader!!.getResourceAsStream(name).use { stream ->
            InputStreamReader(stream).use { reader ->
                reader.readText()
            }
        }
}