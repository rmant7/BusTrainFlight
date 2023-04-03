package ru.z8.louttsev.cheaptripmobile.androidApp

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import dev.icerock.moko.mvvm.livedata.LiveData
import ru.z8.louttsev.cheaptripmobile.androidApp.adapters.AutoCompleteLocationsListAdapter
import ru.z8.louttsev.cheaptripmobile.androidApp.adapters.RouteListAdapter
import ru.z8.louttsev.cheaptripmobile.androidApp.databinding.ActivityMainBinding
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.viewmodel.AutoCompleteHandler
import ru.z8.louttsev.cheaptripmobile.shared.viewmodel.MainViewModel
import kotlin.text.RegexOption.*

/**
 * Declares main UI controller.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var mInputMethodManager: InputMethodManager

    @SuppressLint("InflateParams", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val model by viewModels<MainViewModel> {
            createWithFactory { MainViewModel(App.sLocationRepository, App.sRouteRepository) }
        }

        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = model // ignore probably IDE error message "Cannot access class..."
        }

        setContentView(binding.root)

        if (resources.getBoolean(R.bool.isPhone)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        supportActionBar?.apply {
            customView = layoutInflater.inflate(R.layout.action_bar, null)
            customView.layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            displayOptions = DISPLAY_SHOW_CUSTOM
        }

        with(binding) {
            originTextView.setup(
                handler = model.origins,
                inputLayout = binding.originInputLayout
            )

            originClearIcon.setOnClickListener {
                model.origins.onItemReset()
                originTextView.clearText()
                originTextView.requestFocus()
                mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
            }

            destinationTextView.setup(
                handler = model.destinations,
                inputLayout = binding.destinationInputLayout
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

            goButton.setup(
                isReady = model.routes.isReadyToBuild,
                listener = {
                    model.routes.build(
                        emptyResultHandler = { showNoResultsMessage() },
                        onUpdate = {
                            routeList.smoothScrollToPosition(0)
                            logo?.visibility = View.GONE
                            routeList.visibility = View.VISIBLE
                        }
                    )

                    routeList.visibility = View.GONE
                    logo?.visibility = View.VISIBLE
                    mInputMethodManager.hideSoftInputFromWindow(
                        it.windowToken,
                        HIDE_NOT_ALWAYS
                    )
                }
            )

            with(routeList) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = RouteListAdapter(model.routes.data)
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
        }
    }

    private fun createWithFactory(
        create: () -> ViewModel
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return create.invoke() as T
        }
    }

    private fun AutoCompleteTextView.setup(
        handler: AutoCompleteHandler<Location>,
        inputLayout: TextInputLayout
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

                    val allowable = Regex("^[-a-zа-яё .]+$", IGNORE_CASE)

                    @Suppress("LiftReturnOrAssignment")
                    if (source.isNotEmpty() && !allowable.matches(changedText)) {
                        inputLayout.showErrorMessage(getString(R.string.not_allowable_character_error_message))
                        return source.filter { allowable.matches(it.toString()) }
                    } else {
                        inputLayout.hideErrorMessage()
                        return source
                    }
                }
            }
        )

        doBeforeTextChanged { _, _, count, after ->
            handler.isBeingBackspaced = after < count
            handler.wasSelected = handler.isItemSelected()
        }

        addTextChangedListener { changedEditableText: Editable? ->
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
                        clearText()
                    }
                }
            )
        }

        setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    dismissDropDown()
                    true
                }
                else -> false
            }
        }

        setOnItemClickListener { parent: AdapterView<*>, _, position: Int, _ ->
            val selectedLocation = parent.getItemAtPosition(position) as Location

            handler.onItemSelected(
                selectedLocation,
                invalidSelectionHandler = ::showInvalidSelectionMessage
            )
            performCompletion()
        }

        setOnDismissListener {
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
            if (!hasFocus) {
                inputLayout.hideErrorMessage()
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
        if (handler.data.value.isNotEmpty()) {
            val suitableLocation = handler.data.value.first()

            setText(suitableLocation.name)
            handler.onItemSelected(
                suitableLocation,
                invalidSelectionHandler = ::showInvalidSelectionMessage
            )
            performCompletion()
        }
    }

    private fun Button.setup(isReady: LiveData<Boolean>, listener: (View) -> Unit) {
        isReady.addObserver {
            if (it) {
                setOnClickListener(listener)
                setBackgroundColor(
                    ContextCompat.getColor(this@MainActivity, R.color.colorAccent)
                )
            } else {
                setOnClickListener(null)
                setBackgroundColor(
                    ContextCompat.getColor(this@MainActivity, R.color.colorInactiveViewBackground)
                )
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
}
