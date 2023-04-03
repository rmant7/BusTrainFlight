package ru.z8.louttsev.cheaptripmobile.shared

import android.content.Context
import android.support.test.runner.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.FullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.FullDbDataSource
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.LocationDataSourceFullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocalDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocalDbStorage
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocationDb
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle
import ru.z8.louttsev.cheaptripmobile.shared.model.LocationRepository
import ru.z8.louttsev.cheaptripmobile.shared.model.RepositoryStrategy
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Config(sdk = [24])
@RunWith(AndroidJUnit4::class)
class LocationRepositoryTest {
    private val dataSourceWrapper = object : DataSource<Location> {
        private val dataSource: FullDbDataSource<Location>

        init {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val driver = DatabaseDriverFactory(context).getDriver(FullDb.Schema, "fullDb.sqlite3")
            dataSource = LocationDataSourceFullDb(driver)
        }

        var isAvailable = true

        override fun get(parameters: ParamsBundle): List<Location>? =
            if (isAvailable) {
                dataSource.get(parameters)
            } else {
                null
            }
    }

    private val dataStorage: LocalDbStorage<Location>

    init {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val driver = DatabaseDriverFactory(context).createDriver(LocalDb.Schema, "localDb.sqlite3")
        dataStorage = LocationDb(driver)
    }

    private val repositoryUnderTest = LocationRepository(
        dataSourceWrapper,
        dataStorage,
        RepositoryStrategy.CACHING
    )

    @Test
    fun searchLocationsByName() {
        val expectedResult = listOf(
            Location(254, "Monte Carlo"),
            Location(261, "Montpellier"),
            Location(387, "Moscow"),
            Location(759, "Modesto"),
            Location(766, "Moreno Valley"),
            Location(780, "Montreal"),
            Location(121, "Bournemouth"),
            Location(154, "Clermont-Ferrand"),
            Location(262, "Malmo"),
            Location(281, "Plymouth")
        )

        assertTrue(dataSourceWrapper.isAvailable)
        val result: List<Location> =
            repositoryUnderTest.searchLocationsByName(needle = "Mo", locale = Locale.EN)
        assertEquals(expectedResult, result)
        dataSourceWrapper.isAvailable = false
        val cachedResult: List<Location> =
            repositoryUnderTest.searchLocationsByName(needle = "Mo", locale = Locale.EN)
        assertEquals(expectedResult, cachedResult)
    }
}