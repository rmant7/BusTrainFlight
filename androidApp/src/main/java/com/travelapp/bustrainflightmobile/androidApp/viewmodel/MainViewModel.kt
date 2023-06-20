/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package com.travelapp.bustrainflightmobile.androidApp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import com.travelapp.bustrainflightmobile.androidApp.currentLocale
import com.travelapp.bustrainflightmobile.androidApp.ioDispatcher
import com.travelapp.bustrainflightmobile.androidApp.model.LocationRepository
import com.travelapp.bustrainflightmobile.androidApp.model.RouteRepository
import com.travelapp.bustrainflightmobile.androidApp.model.data.Locale
import com.travelapp.bustrainflightmobile.androidApp.model.data.Location
import com.travelapp.bustrainflightmobile.androidApp.model.data.Location.Type
import com.travelapp.bustrainflightmobile.androidApp.model.data.Route
import com.travelapp.bustrainflightmobile.androidApp.uiDispatcher


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
    private val locationRepository: LocationRepository,
    private val routeRepository: RouteRepository
) : ViewModel(), KoinComponent {
    private var inputLocale = currentLocale

    var selectedOrigin: Location? = null
        private set

    private var selectedDestination: Location? = null

    private val routeBuildReadiness = MutableLiveData(isBothPointsSelected() && isPointsVarious())

    val currentRoutes = MutableLiveData<List<Route>>(emptyList())

    val anywhereNearestRoutes = MutableLiveData<MutableList<Pair<Int, MutableList<Route>>>>(
        mutableListOf()
    )
    val isAnywhereSelected = MutableLiveData(false)
    var isFirstTimeRun = true

//    val destinationSelectedHandler = object : DestinationSelectedHandler{
//        override fun onItemClicked(destinationPath: Pair<Int, List<Route>>) {
//            Napier.d(destinationPath.toString())
//            isAnywhereSelected.value = false
//            currentRoutes.value = destinationPath.second.toList()
//        }
//    }

    val origins = object : AutoCompleteHandler<Location> {
        private val locations = MutableLiveData<List<Location>>(emptyList())
        override val data: LiveData<List<Location>>
            get() = locations

        override var isBeingUpdated: Boolean = false
        override var isBeingBackspaced: Boolean = false
        override var wasSelected: Boolean = false

        override fun onTextChanged(text: String, locale: Locale, emptyResultHandler: () -> Unit) {
            currentRoutes.value = emptyList()
            anywhereNearestRoutes.value = mutableListOf()
            isAnywhereSelected.value = false
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
            if (isAnywhereSelected.value!!) {
                onAnywhereSelected()
            }
            if (selectedDestination == null) {
                onAnywhereSelected()
            }
        }

        override fun onItemReset() {
            selectedOrigin = null
            updateReadiness()
        }

        override fun isItemSelected(): Boolean = selectedOrigin != null

        override fun showAnywhereSelection() {}
        override fun hideAnywhereSelection() {}

        override fun onAnywhereSelected() {
            if (isFirstTimeRun) {
                isAnywhereSelected.value = true
                val result = routeRepository.getPackOfRoutesFromLocation(selectedOrigin!!)
                anywhereNearestRoutes.value = result
                isFirstTimeRun = false
            }
        }
    }

    fun loadMoreAnywhereRoutes() = routeRepository.getPackOfRoutesFromLocation(selectedOrigin!!)

    val destinations = object : AutoCompleteHandler<Location> {
        private val locations = MutableLiveData<List<Location>>(emptyList())
        override val data: LiveData<List<Location>>
            get() = locations

        override var isBeingUpdated: Boolean = false
        override var isBeingBackspaced: Boolean = false
        override var wasSelected: Boolean = false

        override fun onTextChanged(text: String, locale: Locale, emptyResultHandler: () -> Unit) {
            currentRoutes.value = emptyList()
            anywhereNearestRoutes.value = mutableListOf()
            isAnywhereSelected.value = false
            Napier.d("OnTextChanged")
            if (text.length == 1) {
                hideAnywhereSelection()
            }

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

        override fun showAnywhereSelection() {
            Napier.d("Anywhere shown")
            if (isOriginSelected()) {
                locations.value = listOf(Location(0, "Anywhere", ""))
            }
        }

        override fun hideAnywhereSelection() {
            if (isOriginSelected()) {
                locations.value = emptyList()
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

        override fun onAnywhereSelected() {
            Napier.d("Anywhere selected")
            selectedDestination = Location(0, "Anywhere", "")
            Napier.d("$selectedDestination")
            updateReadiness()
        }
    }

    val routes = object : GoButtonHandler {
        override val data: LiveData<List<Route>>
            get() = currentRoutes

        override val isReadyToBuild: LiveData<Boolean>
            get() = routeBuildReadiness

        override fun build(emptyResultHandler: () -> Unit, onUpdate: () -> Unit) {

            Napier.d("$selectedDestination")
            isAnywhereSelected.value = selectedDestination?.name == "Anywhere"

            if (isBothPointsSelected()) {
                viewModelScope.launch(ioDispatcher) {

                    if (isAnywhereSelected.value!!) {
                        val result = routeRepository.getPackOfRoutesFromLocation(selectedOrigin!!, newList = true)

                        withContext(uiDispatcher) {
                            Napier.d("$result")
                            anywhereNearestRoutes.value = result
                        }

                    } else {
                        val result = routeRepository.getRoutes(
                            from = selectedOrigin!!,
                            to = selectedDestination!!,
//                        locale = inputLocale
                        )

                        withContext(uiDispatcher) {
                            if (result.isEmpty()) {
                                emptyResultHandler()
                            } else {
                                currentRoutes.value = result
                                delay(100)
                                onUpdate()
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateReadiness() {
        Napier.d("${isBothPointsSelected()}")
        Napier.d("${isPointsVarious()}")
        routeBuildReadiness.value = isBothPointsSelected() && isPointsVarious()
    }

    private fun isBothPointsSelected(): Boolean {
        Napier.d("$selectedDestination")
        return selectedOrigin != null && selectedDestination != null
    }
//        selectedOrigin != null && selectedDestination != null && selectedDestination!!.name != "Anywhere"


    private fun isPointsVarious() =
        selectedOrigin != selectedDestination

    private fun isOriginSelected() =
        selectedOrigin != null
}