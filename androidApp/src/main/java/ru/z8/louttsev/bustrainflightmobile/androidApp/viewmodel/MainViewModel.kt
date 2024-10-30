/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.z8.louttsev.bustrainflightmobile.androidApp.currentLocale
import ru.z8.louttsev.bustrainflightmobile.androidApp.ioDispatcher
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.RouteRepository
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationData
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.LocationData.Type
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Route
import ru.z8.louttsev.bustrainflightmobile.androidApp.uiDispatcher
import javax.inject.Inject


/**
 * Declares UX logic for managing the data and handling the UI actions.
 *
 * @param locationRepository Read-only storage of available locations
 * @param routeRepository Read-only storage of available routes
 * @property origins Available origin locations
 * @property destinations Available destination locations
 * @property routes Found routes
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val routeRepository: RouteRepository
) : ViewModel() {
    private var inputLocale = currentLocale

    var selectedOrigin: LocationData? = null
        private set

    private var selectedDestination: LocationData? = null

    private val _selectedCityName = MutableLiveData<LocationData?>(null)
    val selectedCityName: LiveData<LocationData?> = _selectedCityName

    private val _buttonCityNameLabel = MutableLiveData<String>("")
    val buttonCityNameLabel: LiveData<String> = _buttonCityNameLabel

    private val _budgetTipsUrl = MutableLiveData<String>("")
    val budgetTipsUrl: LiveData<String> = _budgetTipsUrl

    private val _citiesNameList = MutableLiveData<JSONObject?>(null)
    val citiesNameList: LiveData<JSONObject?> = _citiesNameList

    private val routeBuildReadiness = MutableLiveData(isBothPointsSelected() && isPointsVarious())
    val getRouteBuildReadiness: LiveData<Boolean> get() = routeBuildReadiness

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

    val origins = object : AutoCompleteHandler<LocationData> {
        private val locations = MutableLiveData<List<LocationData>>(emptyList())
        override val data: LiveData<List<LocationData>>
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

        override fun onItemSelected(item: LocationData, invalidSelectionHandler: () -> Unit) {
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
//            if (isFirstTimeRun) {
            isAnywhereSelected.value = true
            val result =
                routeRepository.getPackOfRoutesFromLocation(selectedOrigin!!, newList = true) //////
            anywhereNearestRoutes.value = result
//                isFirstTimeRun = false
        }
//        }
    }

    fun loadMoreAnywhereRoutes() = routeRepository.getPackOfRoutesFromLocation(selectedOrigin!!)

    val destinations = object : AutoCompleteHandler<LocationData> {
        private val locations = MutableLiveData<List<LocationData>>(emptyList())
        override val data: LiveData<List<LocationData>>
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
                locations.value = listOf(LocationData(0, "Anywhere", ""))
            }
        }

        override fun hideAnywhereSelection() {
            if (isOriginSelected()) {
                locations.value = emptyList()
            }
        }

        override fun onItemSelected(item: LocationData, invalidSelectionHandler: () -> Unit) {
            selectedDestination = item
            _selectedCityName.value = item
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
            selectedDestination = LocationData(0, "Anywhere", "")
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
                        val result = routeRepository.getPackOfRoutesFromLocation(
                            selectedOrigin!!,
                            newList = true
                        )

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


    fun updateButtonCityNameLabel() {
        _buttonCityNameLabel.value = ""
    }

    fun updateCitiesNameList(jsonObject: JSONObject) {
        _citiesNameList.value = jsonObject
    }


    fun budgetTravelTips(cityNamesJson: JSONObject?) {
        if (cityNamesJson != null) {
            if (selectedCityName.value != null) {
                val matchingCityKey = cityNamesJson.keys().asSequence().find { key ->
                    key == selectedCityName.value?.id.toString()
                }
                if (matchingCityKey != null) {
                    val cityName = cityNamesJson.getString(matchingCityKey)

                    var capitalizedCityName =
                        cityName.replaceFirstChar { it.uppercase() }
                    if (cityName.contains("_")) {
                        capitalizedCityName = cityName.split("_")
                            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
                    }
                    _buttonCityNameLabel.value = capitalizedCityName
                    val url =
                        cityName?.let {
                            "https://cheaptrip.guru/budgettraveltips/tree/city_descriptions/en/${it}"
                        }
                    _budgetTipsUrl.value = url ?: ""
                }
            }
        }
    }

    fun anywhereBudgetTravelTips(path: Path): String {
        val citiesNameList = _citiesNameList.value
        var url: String? = null
        if (path.to.name != null && citiesNameList != null) {
            val matchingCityKey = citiesNameList.keys().asSequence().find { key ->
                citiesNameList.optString(key).equals(path.to.name, ignoreCase = true)
            }
            if (matchingCityKey != null) {
                val cityName = citiesNameList.getString(matchingCityKey)

//                    var capitalizedCityName =
//                        cityName.replaceFirstChar { it.uppercase() }
//                    if (cityName.contains("_")) {
//                        capitalizedCityName = cityName.split("_")
//                            .joinToString(" ") { it.replaceFirstChar { it.uppercase() } }
//                    }
                url =
                    cityName?.let {
                        "https://cheaptrip.guru/budgettraveltips/tree/city_descriptions/en/${it}"
                    }
            }
        }
        return url ?: ""
    }

}