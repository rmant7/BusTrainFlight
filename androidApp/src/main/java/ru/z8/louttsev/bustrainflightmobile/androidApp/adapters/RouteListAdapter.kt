/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.adapters

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemRouteBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Route

/**
 * Declares adapter for route list as result of searching.
 *
 * @param liveData Observable source of routes data
 */
class RouteListAdapter(
    liveData: LiveData<List<Route>>
) : RecyclerView.Adapter<RouteListAdapter.ViewHolder>() {
    private var mRoutes: List<Route>
    private val animationDuration = 200L

    init {
        mRoutes = liveData.value!!
        liveData.observeForever {
            mRoutes = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val binding = ItemRouteBinding.inflate(LayoutInflater.from(context))

        with(binding) {
            root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            pathList.layoutManager = LinearLayoutManager(context)
        }

        return ViewHolder(binding)
    }

    private fun slideView(view: View, currentHeight: Int, newHeight: Int) {

        val slideAnimator = ValueAnimator
            .ofInt(currentHeight, newHeight)
            .setDuration(animationDuration)


        /* We use an update listener which listens to each tick
         * and manually updates the height of the view  */

        slideAnimator.addUpdateListener { animation1 ->
            val value = animation1.animatedValue as Int
            view.layoutParams.height = value
            view.requestLayout()
        }
        slideAnimator.duration = animationDuration
        slideAnimator.interpolator = AccelerateDecelerateInterpolator()
        slideAnimator.start()

        /*  We use an animationSet to play the animation  */

//        AnimatorSet().apply {
//            interpolator = AccelerateDecelerateInterpolator()
//            play(slideAnimator)
//            start()
//        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRoute = mRoutes[position]

        with(holder.binding) {
            val context = root.context

            model = currentRoute // ignore probably IDE error message "Cannot access class..."
            pathList.adapter = PathListAdapter(currentRoute.directPaths)
            executePendingBindings()

            root.setOnClickListener { openIndicator.isChecked = !openIndicator.isChecked }
            openIndicator.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    pathList.visibility = View.VISIBLE
                    euroPrice.visibility = View.GONE
                    duration.visibility = View.GONE
                } else {
                    pathList.visibility = View.GONE
                    euroPrice.visibility = View.VISIBLE
                    duration.visibility = View.VISIBLE
                }
            }
            openIndicator.isChecked = false

            transportIconContainer.addIcons(currentRoute.directPaths, context)
        }
    }

    private fun LinearLayout.addIcons(paths: List<Path>, context: Context) {
        removeAllViews()

        paths.forEach { path ->
            addView(
                ImageView(context).apply {
                    setImageResource(path.transportationType.imageResource)
                    setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                    val padding =
                        context.resources.getDimension(R.dimen.transport_icon_margin).toInt()
                    setPadding(padding)
                }
            )
        }
    }

    override fun getItemCount(): Int = mRoutes.size

    class ViewHolder(val binding: ItemRouteBinding) : RecyclerView.ViewHolder(binding.root)
}