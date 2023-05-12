package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.*
import android.widget.*
import androidx.appcompat.app.ActionBar.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.adapters.AutoCompleteLocationsListAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.adapters.RouteListAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityMainBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Location
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.AutoCompleteHandler
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel
import java.util.*
import kotlin.text.RegexOption.*
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale

/**
 * Declares main UI controller.
 */
class MainActivity : DrawerBaseActivity() {
    private lateinit var mInputMethodManager: InputMethodManager

    private val model: MainViewModel by inject()

    @SuppressLint("InflateParams", "SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        mInputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            viewModel = model // ignore probably IDE error message "Cannot access class..."
        }
        setContentView(binding.root)

        drawerBaseBinding.root.findViewById<TextView>(R.id.appBarTitle).text = "BusTrainFlight"

        if (resources.getBoolean(R.bool.isPhone)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
//        actionBarBinding.menuButton.setOnClickListener {
//            binding.drawerLayout?.openDrawer(GravityCompat.END)
//        }

        with(binding) {

            originTextView.setup(
                handler = model.origins,
                inputLayout = binding.originInputLayout
            )

//            originClearIcon.setOnClickListener {
//                model.origins.onItemReset()
//                originTextView.clearText()
//                originTextView.requestFocus()
//                mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
//            }

            destinationTextView.setup(
                handler = model.destinations,
                inputLayout = binding.destinationInputLayout
            )

//            destinationClearIcon.setOnClickListener {
//                model.destinations.onItemReset()
//                destinationTextView.clearText()
//                destinationTextView.requestFocus()
//                mInputMethodManager.showSoftInput(destinationTextView, SHOW_IMPLICIT)
//            }

            (clearButton as MaterialButton).setOnClickListener {
                model.origins.onItemReset()
                originTextView.clearText()
                model.destinations.onItemReset()
                destinationTextView.clearText()
                originTextView.requestFocus()
                mInputMethodManager.showSoftInput(originTextView, SHOW_IMPLICIT)
            }

            (goButton as MaterialButton).setup(
                isReady = model.routes.isReadyToBuild,
                listener = {
                    model.routes.build(
                        emptyResultHandler = { showNoResultsMessage() },
                        onUpdate = {
                            routeList.smoothScrollToPosition(0)
                            logo?.visibility = View.GONE
                            resultsTextView?.visibility = View.VISIBLE
                            routeList.visibility = View.VISIBLE
                        }
                    )

                    routeList.visibility = View.GONE
                    logo?.visibility = View.INVISIBLE
                    resultsTextView?.visibility = View.INVISIBLE
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

                    val allowable = Regex("^[-a-z .]+$", IGNORE_CASE)

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
        if (handler.data.value!!.isNotEmpty()) {
            val suitableLocation = handler.data.value!!.first()

            setText(suitableLocation.name)
            handler.onItemSelected(
                suitableLocation,
                invalidSelectionHandler = ::showInvalidSelectionMessage
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
}

