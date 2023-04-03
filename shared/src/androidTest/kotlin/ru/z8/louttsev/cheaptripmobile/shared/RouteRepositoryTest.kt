package ru.z8.louttsev.cheaptripmobile.shared

import android.content.Context
import android.support.test.runner.AndroidJUnit4
import androidx.test.core.app.ApplicationProvider
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.FullDbDataSource
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource.RouteDataSourceFullDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocalDb
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocalDbStorage
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.RouteDb
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle
import ru.z8.louttsev.cheaptripmobile.shared.model.RepositoryStrategy
import ru.z8.louttsev.cheaptripmobile.shared.model.RouteRepository
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Path
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route.Type.*
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Config(sdk = [24])
@RunWith(AndroidJUnit4::class)
class RouteRepositoryTest {
    private val dataSourceWrapper = object : DataSource<Route> {
        private val dataSource: FullDbDataSource<Route>

        init {
            val context = ApplicationProvider.getApplicationContext<Context>()
            val driver = DatabaseDriverFactory(context).getDriver(LocalDb.Schema, "fullDb.sqlite3")
            dataSource = RouteDataSourceFullDb(driver)
        }

        var isAvailable: Boolean = true

        override fun get(parameters: ParamsBundle): List<Route>? =
            if (isAvailable) {
                dataSource.get(parameters)
            } else {
                null
            }
    }

    private val dataStorage: LocalDbStorage<Route>

    init {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val driver = DatabaseDriverFactory(context).createDriver(LocalDb.Schema, "localDb.sqlite3")
        dataStorage = RouteDb(driver)
    }

    private val repositoryUnderTest = RouteRepository(
        dataSourceWrapper,
        dataStorage,
        RepositoryStrategy.CACHING
    )

    @Test
    fun getRoutes() {
        val fromLocation = Location(387, "Moscow")
        val toLocation = Location(388, "Saint Petersburg")

        val expectedResult = listOf(
            Route(
                GROUND,
                15.32F,
                687,
                listOf(Path(RIDE_SHARE, 15.32F, 687, "Moscow", "Saint Petersburg"))
            ),
            Route(
                FLYING,
                16.43F,
                224,
                listOf(Path(FLIGHT, 16.43F, 224, "Moscow", "Saint Petersburg"))
            ),
            Route(
                FIXED_WITHOUT_RIDE_SHARE,
                16.83F,
                765,
                listOf(Path(BUS, 16.83F, 765, "Moscow", "Saint Petersburg"))
            ),
            Route(
                DIRECT,
                17.64F,
                483,
                listOf(Path(TRAIN, 17.64F, 483, "Moscow", "Saint Petersburg"))
            )
        )

        assertTrue(dataSourceWrapper.isAvailable)
        val result: List<Route> =
            repositoryUnderTest.getRoutes(fromLocation, toLocation, Locale.EN)
        assertEquals(expectedResult, result)
        dataSourceWrapper.isAvailable = false
        val cachedResult: List<Route> =
            repositoryUnderTest.getRoutes(fromLocation, toLocation, Locale.EN)
        assertEquals(expectedResult, cachedResult)
    }
}
