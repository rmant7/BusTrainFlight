package model

import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.*
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle.*
import ru.z8.louttsev.cheaptripmobile.shared.model.DataStorage
import ru.z8.louttsev.cheaptripmobile.shared.model.RepositoryStrategy
import ru.z8.louttsev.cheaptripmobile.shared.model.RepositoryStrategy.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class RepositoryStrategyTest {
    private val dataSourceFake = object : DataSource<String> {
        var isAvailable: Boolean = true

        override fun get(parameters: ParamsBundle): List<String>? {
            if (!isAvailable) {
                return null
            }
            val needle = parameters.get(Key.NEEDLE).toString()

            return if (needle.isEmpty() || needle.isBlank()) {
                emptyList()
            } else {
                listOf(
                    "$needle-started-string"
                )
            }
        }
    }

    private val dataStorageFake = object : DataStorage<String> {
        val storage = emptyMap<String, String>().toMutableMap()

        override fun put(data: List<String>, parameters: ParamsBundle) {
            data.forEach { storage[it] = it }
        }

        override fun get(parameters: ParamsBundle): List<String> {
            val needle = parameters.get(Key.NEEDLE).toString()
            return if (needle.isEmpty() || needle.isBlank()) {
                emptyList()
            } else {
                storage.filterKeys { it.contains(needle) }.values.toList()
            }
        }
    }

    private val loadersUnderTest =
        emptyList<Pair<RepositoryStrategy, (ParamsBundle) -> List<String>>>()
            .toMutableList()
            .also {
                values().forEach { strategy ->
                    it.add(
                        strategy to strategy.combineLoaderFrom(
                            dataSourceFake,
                            dataStorageFake
                        )
                    )
                }
            }
            .toList()

    @Test
    fun loaderTest() {
        loadersUnderTest.forEach {
            val (strategy, loader) = it
            when (strategy) {
                BACKUP -> {
                    assertTrue(dataSourceFake.isAvailable)
                    val params = ParamsBundle().apply { put(Key.NEEDLE, "Mos") }
                    val result = loader(params)
                    assertEquals(result, dataStorageFake.storage.values.toList())
                    dataSourceFake.isAvailable = false
                    val cachedResult = loader(params)
                    assertEquals(result, cachedResult)
                }
                CACHING -> {
                    val params = ParamsBundle().apply { put(Key.NEEDLE, "Mos") }
                    val result = loader(params)
                    assertEquals(result, dataStorageFake.storage.values.toList())
                    val cachedResult = loader(params)
                    assertEquals(result, cachedResult)
                }
                else -> fail("Uncovered case: ${strategy.name}")
            }
        }
    }
}