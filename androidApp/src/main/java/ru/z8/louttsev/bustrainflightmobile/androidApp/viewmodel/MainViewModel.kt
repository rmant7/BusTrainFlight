/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import ru.z8.louttsev.bustrainflightmobile.androidApp.currentLocale
import ru.z8.louttsev.bustrainflightmobile.androidApp.ioDispatcher
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationsRepositoryJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.RoutesRepositoryJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location.Type
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Route
import ru.z8.louttsev.bustrainflightmobile.androidApp.uiDispatcher


/**
 * Declares UX logic for managing the data and handling the UI actions.
 *
 * @param locationRepository Read-only storage of available locations
 * @param routeRepository Read-only storage of available routes
 * @property origins Available origin locations
 * @property destinations Available destination locations
 * @property routes Found routes
 */
class MainViewModel(
    private val locationRepository: LocationsRepositoryJson,
    private val routeRepository: RoutesRepositoryJson
) : ViewModel(), KoinComponent {
    private var inputLocale = currentLocale

    private var selectedOrigin: Location? = null
    private var selectedDestination: Location? = null

    private val routeBuildReadiness = MutableLiveData(isBothPointsSelected() && isPointsVarious())

    val origins = object : AutoCompleteHandler<Location> {
        private val locations = MutableLiveData<List<Location>>(emptyList())
        override val data: LiveData<List<Location>>
            get() = locations

        override var isBeingUpdated: Boolean = false
        override var isBeingBackspaced: Boolean = false
        override var wasSelected: Boolean = false

        override fun onTextChanged(text: String, locale: Locale, emptyResultHandler: () -> Unit) {
            inputLocale = locale
            viewModelScope.launch(ioDispatcher) {

                val result = locationRepository.searchLocationsByName(
                    needle = text,
                    type = Type.FROM,
                    limit = 4,
                    locale = inputLocale
                )


                withContext(uiDispatcher) {
                    if (result.isEmpty()) {
                        emptyResultHandler()
                    } else {
                        locations.value = result
                    }
                }
            }
        }

        override fun onItemSelected(item: Location, invalidSelectionHandler: () -> Unit) {
            selectedOrigin = item
            updateReadiness()
            if (!isPointsVarious()) {
                invalidSelectionHandler()
            }
        }

        override fun onItemReset() {
            selectedOrigin = null
            updateReadiness()
        }

        override fun isItemSelected(): Boolean = selectedOrigin != null
    }

    val destinations = object : AutoCompleteHandler<Location> {
        private val locations = MutableLiveData<List<Location>>(emptyList())
        override val data: LiveData<List<Location>>
            get() = locations

        override var isBeingUpdated: Boolean = false
        override var isBeingBackspaced: Boolean = false
        override var wasSelected: Boolean = false

        override fun onTextChanged(text: String, locale: Locale, emptyResultHandler: () -> Unit) {
            inputLocale = locale
            viewModelScope.launch(ioDispatcher) {

                val result = locationRepository.searchLocationsByName(
                    needle = text,
                    type = Type.TO,
                    limit = 4,
                    locale = inputLocale
                )

                withContext(uiDispatcher) {
                    if (result.isEmpty()) {
                        emptyResultHandler()
                    } else {
                        locations.value = result
                    }
                }
            }
        }

        override fun onItemSelected(item: Location, invalidSelectionHandler: () -> Unit) {
            selectedDestination = item
            updateReadiness()
            if (!isPointsVarious()) {
                invalidSelectionHandler()
            }
        }

        override fun onItemReset() {
            selectedDestination = null
            updateReadiness()
        }

        override fun isItemSelected(): Boolean = selectedDestination != null
    }

    val routes = object : GoButtonHandler {
        private val routes = MutableLiveData<List<Route>>(emptyList())
        override val data: LiveData<List<Route>>
            get() = routes

        override val isReadyToBuild: LiveData<Boolean>
            get() = routeBuildReadiness

        override fun build(emptyResultHandler: () -> Unit, onUpdate: () -> Unit) {

            if (isBothPointsSelected()) {
                viewModelScope.launch(ioDispatcher) {
                    // null-safety was checked
//                    val result = oldRouteRepository.getRoutes(
//                        from = selectedOrigin!!,
//                        to = selectedDestination!!,
//                        locale = inputLocale
//                    )

                    val result = routeRepository.getRoutes(
                        from = selectedOrigin!!,
                        to = selectedDestination!!,
                        locale = inputLocale
                    )

                    withContext(uiDispatcher) {
                        if (result.isEmpty()) {
                            emptyResultHandler()
                        } else {
                            routes.value = result
                            delay(100)
                            onUpdate()
                        }
                    }
                }
            }
        }
    }

    private fun updateReadiness() {
        routeBuildReadiness.value = isBothPointsSelected() && isPointsVarious()
    }

    private fun isBothPointsSelected() =
        selectedOrigin != null && selectedDestination != null

    private fun isPointsVarious() =
        selectedOrigin != selectedDestination
}