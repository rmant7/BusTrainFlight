package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import io.github.aakira.napier.Napier
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.adapters.AnywhereListAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.adapters.AutoCompleteLocationsListAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.adapters.RouteListAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityMainBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.AutoCompleteHandler
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel
import java.util.*
import kotlin.text.RegexOption.*


/**
 * Declares main UI controller.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mInputMethodManager: InputMethodManager

    private val model: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onResume() {
        super.onResume()
        model.updateReadiness()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("InflateParams", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

//        loadAppOpenAd()

        mInputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = model // ignore probably IDE error message "Cannot access class..."
        }
        setContentView(binding.root)

        val toolbar = binding.root.findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)

//        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "BusTrainFlight"

        if (resources.getBoolean(R.bool.isPhone)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        model.isAnywhereSelected.observe(this) { selected ->
            with(binding) {
                if (selected) {
                    routeListAnywhereRecyclerView.visibility = View.VISIBLE
                    routeListRecyclerView.visibility = View.GONE
                } else {
                    routeListAnywhereRecyclerView.visibility = View.GONE
                    routeListRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        with(binding) {

            originTextView.setup(
                handler = model.origins,
                inputLayout = binding.originInputLayout,
                false
            )

            originClearIcon.setOnClickListener {
                model.origins.onItemReset()
                originTextView.clearText()
                originTextView.requestFocus()
                mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
            }

            destinationTextView.setup(
                handler = model.destinations,
                inputLayout = binding.destinationInputLayout,
                true
            )

            destinationClearIcon.setOnClickListener {
                model.destinations.onItemReset()
                destinationTextView.clearText()
                destinationTextView.requestFocus()
                mInputMethodManager.showSoftInput(destinationTextView, SHOW_IMPLICIT)
            }

            clearButton.setOnClickListener {
                model.origins.onItemReset()
                originTextView.clearText()
                model.destinations.onItemReset()
                destinationTextView.clearText()
                originTextView.requestFocus()
                mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
            }

            reverse.setOnClickListener {
                if (model.getRouteBuildReadiness.value == true) {
                    val destinationLocation = model.destinations.data.value!!.first()
                    val originLocation = model.origins.data.value!!.first()
                    originTextView.setText("")
                    model.origins.onItemReset()
                    originTextView.clearText()
                    model.destinations.onItemReset()
                    destinationTextView.clearText()
                    originTextView.requestFocus()
                    mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
                    mInputMethodManager.showSoftInput(destinationTextView, SHOW_IMPLICIT)

                    destinationTextView.setText(originLocation.name)
                    model.destinations.onItemSelected(
                        originLocation,
                        invalidSelectionHandler = ::showWrongChoiceError
                    )
                    destinationTextView.performCompletion()

                    originTextView.setText(destinationLocation.name)
                    model.origins.onItemSelected(
                        destinationLocation,
                        invalidSelectionHandler = ::showWrongChoiceError
                    )
                    originTextView.performCompletion()
                }
            }

            goButton.setup(
                isReady = model.routes.isReadyToBuild,
                listener = {
                    model.routes.build(
                        emptyResultHandler = { showNoResultsMessage() },
                        onUpdate = {
                            routeListRecyclerView.smoothScrollToPosition(0)
//                            resultsTextView.visibility = View.VISIBLE
                            routeListRecyclerView.visibility = View.VISIBLE
                        }
                    )

                    routeListRecyclerView.visibility = View.GONE
//                    resultsTextView.visibility = View.INVISIBLE
                    mInputMethodManager.hideSoftInputFromWindow(
                        it.windowToken,
                        HIDE_NOT_ALWAYS
                    )
                }
            )

            with(routeListRecyclerView) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = RouteListAdapter(model.currentRoutes)
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        itemPosition: Int,
                        parent: RecyclerView
                    ) {
                        outRect.bottom = resources.getDimension(R.dimen.route_item_margin).toInt()
                    }
                })
            }
            with(routeListAnywhereRecyclerView) {
                val anywhereListAdapter = AnywhereListAdapter(
                    model.anywhereNearestRoutes,
//                    model.destinationSelectedHandler
                )
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = anywhereListAdapter
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        itemPosition: Int,
                        parent: RecyclerView
                    ) {
                        outRect.bottom = resources.getDimension(R.dimen.route_item_margin).toInt()
                    }
                })

                nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
                    if (!anywhereListAdapter.isListEmpty()) {
                        if (nestedScrollView.getChildAt(0).bottom <= (nestedScrollView.height + nestedScrollView.scrollY)) {
                            anywhereListAdapter.addRoutes(model.loadMoreAnywhereRoutes())
                        }
                    }
                }

                val display = windowManager.defaultDisplay
                fab.translationX = display.width * 6 / 8f
                nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    fab.translationY = scrollY.toFloat() + display.height / 3
                    if (nestedScrollView.scrollY > 1200) fab.visibility = View.VISIBLE
                    else fab.visibility = View.GONE
                    fab.setOnClickListener { view ->
                        nestedScrollView.smoothScrollTo(0, display.height / 3, childCount * 50)
                        fab.visibility = View.GONE
                    }
                }
            }
        }
    }

//    private fun createWithFactory(
//        create: () -> ViewModel
//    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            @Suppress("UNCHECKED_CAST")
//            return create.invoke() as T
//        }
//    }

    private fun AutoCompleteTextView.setup(
        handler: AutoCompleteHandler<Location>,
        inputLayout: TextInputLayout,
        isDestination: Boolean
    ) {
        threshold = 1

        setAdapter(
            AutoCompleteLocationsListAdapter(handler.data)
        )

        filters = arrayOf(
            object : InputFilter {
                override fun filter(
                    source: CharSequence,
                    start: Int,
                    end: Int,
                    dest: Spanned?,
                    dstart: Int,
                    dend: Int
                ): CharSequence {
                    val changedText = source.subSequence(start, end)

                    val allowable = Regex("^[-a-z .]+$", IGNORE_CASE)

                    @Suppress("LiftReturnOrAssignment")
                    if (source.isNotEmpty() && !allowable.matches(changedText)) {
                        inputLayout.showErrorMessage(getString(R.string.not_allowable_character_error_message))
                        return source.filter { allowable.matches(it.toString()) }
                    } else {
                        inputLayout.hideErrorMessage()
                        if (checkTheCorrectnessOfTheInput()) showWrongChoiceError()
                        else inputLayout.hideInvalidInputMessage()
                        return source
                    }
                }
            }
        )

        doBeforeTextChanged { _, _, count, after ->
            Log.d("asdfg", "doBeforeTextChanged")
            Napier.d("OnBeforeTextChanged")
            handler.isBeingBackspaced = after < count
            handler.wasSelected = handler.isItemSelected()
        }

        doAfterTextChanged { changedEditableText: Editable? ->
            if (checkForEmptyString() && !checkForCoincidenceOfPoints() &&
                binding.originTextView.text.toString() != "Anywhere"
            )
                binding.reverse.visibility = View.VISIBLE
            else binding.reverse.visibility = View.GONE
        }

        addTextChangedListener { changedEditableText: Editable? ->
            Napier.d("OnTextChanged")
            Log.d("asdfg", "addTextChangedListener")

            if (text.toString() != "Anywhere") {

                handler.onItemReset()
                handler.isBeingUpdated = true

                val changedText = changedEditableText.toString()

                handler.onTextChanged(
                    text = changedText,
                    locale = getInputLocale(changedText),
                    emptyResultHandler = {
                        if (textLength() > 1) {
                            showNoResultsMessage()
                            selectSuitableLocation(handler)
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Sorry, for now, we support only English input",
                                Toast.LENGTH_SHORT
                            ).apply { show() }
                            clearText()
                        }
                    }
                )
            }
            if (binding.originTextView.text.toString().isNotEmpty())
                binding.originClearIcon.visibility = View.VISIBLE
            else binding.originClearIcon.visibility = View.GONE
            if (binding.destinationTextView.text.toString().isNotEmpty())
                binding.destinationClearIcon.visibility = View.VISIBLE
            else binding.destinationClearIcon.visibility = View.GONE
        }

        setOnEditorActionListener { _, actionId, _ ->
            Napier.d("OnEditorAction")
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    dismissDropDown()
                    true
                }

                else -> false
            }
        }

        setOnItemClickListener { parent: AdapterView<*>, _, position: Int, _ ->

            Napier.d("OnItemClicked")

            val selectedLocation = parent.getItemAtPosition(position) as Location

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(rootView.windowToken, 0)

            if (selectedLocation.name == "Anywhere") {
//                handler.onItemReset()
                handler.isBeingUpdated = true
                handler.onAnywhereSelected()
//                binding.resultsTextView.visibility = View.VISIBLE
            } else {
                handler.onItemSelected(
                    selectedLocation,
                    invalidSelectionHandler = ::showWrongChoiceError
                )
            }
            performCompletion()
        }

        setOnDismissListener {
            Napier.d("OnDismiss")
            fun firstSymbolInputted() = textLength() == 1 && !handler.isBeingBackspaced
            fun lastSymbolDeleted() = handler.wasSelected && handler.isBeingBackspaced

            if (!handler.isItemSelected() && textLength() > 0) {
                if (!handler.isBeingUpdated || firstSymbolInputted() || lastSymbolDeleted()) {
                    selectSuitableLocation(handler)
                }
            }
            handler.isBeingUpdated = false
            handler.wasSelected = false
        }

        setOnFocusChangeListener { _, hasFocus ->
            if (!model.isAnywhereSelected.value!!) {
                if (!hasFocus) {
                    inputLayout.hideErrorMessage()
                    if (checkTheCorrectnessOfTheInput()) showWrongChoiceError()
                    else inputLayout.hideInvalidInputMessage()
                } else if (hasFocus && text.isEmpty() && isDestination && model.selectedOrigin != null) {
                    handler.showAnywhereSelection()
                    postDelayed({ showDropDown() }, 200)
                }
            }
            Napier.d("OnFocusChanged")
        }
        setOnClickListener {
            Napier.d("OnClick")
            if (!model.isAnywhereSelected.value!!) {
                if (text.isEmpty() && isDestination && model.selectedOrigin != null) {
//                requestFocus()
                    if (!isPopupShowing) {
                        showDropDown()
                    }
                }
            }
        }
    }

    private fun AutoCompleteTextView.textLength() = text.toString().length

    private fun getInputLocale(text: String): Locale = Locale.fromLanguageCode(
        if (Regex("^[-а-яё .]+$", IGNORE_CASE).matches(text)) {
            "ru"
        } else {
            "en"
        }
    )

    private var mNoDataErrorToast: Toast? = null

    private fun showNoResultsMessage() {
        mNoDataErrorToast?.let {
            it.cancel()
            mNoDataErrorToast = null
        }

        mNoDataErrorToast = Toast.makeText(
            this@MainActivity,
            getString(R.string.no_data_error_message),
            Toast.LENGTH_SHORT
        ).apply { show() }
    }

    private fun showInvalidSelectionMessage() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.invalid_selection_error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun AutoCompleteTextView.clearText() {
        text.clear()
    }

    private fun AutoCompleteTextView.selectSuitableLocation(handler: AutoCompleteHandler<Location>) {
        if (handler.data.value!!.isNotEmpty()) {
            val suitableLocation = handler.data.value!!.first()

            setText(suitableLocation.name)
            handler.onItemSelected(
                suitableLocation,
                invalidSelectionHandler = ::showWrongChoiceError
            )
            performCompletion()
        }
    }

    private fun MaterialButton.setup(isReady: LiveData<Boolean>, listener: (View) -> Unit) {
        isReady.observeForever {
            if (it) {
                isClickable = true
                setOnClickListener(listener)
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.orange_button_background
                )
            } else {
                setOnClickListener(null)
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.inactive_button_background
                )
                isClickable = false
            }
        }

        setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.performClick()
            }
        }
    }

    private fun TextInputLayout.showErrorMessage(message: String) {
        error = message
    }

    private fun TextInputLayout.hideErrorMessage() {
        error = null
    }

    private fun TextInputLayout.hideInvalidInputMessage() {
        binding.destinationInputLayout.boxStrokeColor =
            resources.getColor(R.color.border_color_selector)
        binding.destinationInputLayout.helperText = ""
    }

    private fun showWrongChoiceError() {
        binding.destinationInputLayout.boxStrokeColor = resources.getColor(R.color.error)
        binding.destinationInputLayout.helperText =
            getString(R.string.invalid_selection_error_message)
        //binding.destinationInputLayout.showErrorMessage(getString(R.string.invalid_selection_error_message))
    }

    private fun checkTheCorrectnessOfTheInput(): Boolean {
        return checkForCoincidenceOfPoints() && checkForEmptyString()
    }

    private fun checkForCoincidenceOfPoints(): Boolean {
        return binding.originTextView.text.toString() ==
                binding.destinationTextView.text.toString()
    }

    private fun checkForEmptyString(): Boolean {
        return binding.originTextView.text.toString().isNotEmpty() &&
                binding.destinationTextView.text.toString().isNotEmpty()
    }
}
