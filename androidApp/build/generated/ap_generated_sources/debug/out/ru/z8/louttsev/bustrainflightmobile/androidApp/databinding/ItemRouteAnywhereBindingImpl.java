package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemRouteAnywhereBindingImpl extends ItemRouteAnywhereBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.open_indicator, 3);
        sViewsWithIds.put(R.id.route_list, 4);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemRouteAnywhereBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ItemRouteAnywhereBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.CheckBox) bindings[3]
            , (androidx.recyclerview.widget.RecyclerView) bindings[4]
            , (android.widget.TextView) bindings[1]
            );
        this.actionButton.setTag(null);
        this.destinationMainView.setTag(null);
        this.titleTextView.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.routeDuration == variableId) {
            setRouteDuration((java.lang.String) variable);
        }
        else if (BR.routeDestination == variableId) {
            setRouteDestination((java.lang.String) variable);
        }
        else if (BR.price == variableId) {
            setPrice((java.lang.Float) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setRouteDuration(@Nullable java.lang.String RouteDuration) {
        this.mRouteDuration = RouteDuration;
    }
    public void setRouteDestination(@Nullable java.lang.String RouteDestination) {
        this.mRouteDestination = RouteDestination;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.routeDestination);
        super.requestRebind();
    }
    public void setPrice(@Nullable java.lang.Float Price) {
        this.mPrice = Price;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.price);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        float androidxDatabindingViewDataBindingSafeUnboxPrice = 0f;
        java.lang.String stringValueOfPrice = null;
        java.lang.String javaLangStringFromU2009StringValueOfPriceChar0 = null;
        java.lang.String javaLangStringFromU2009StringValueOfPrice = null;
        java.lang.String routeDestination = mRouteDestination;
        java.lang.Float price = mPrice;

        if ((dirtyFlags & 0xaL) != 0) {
        }
        if ((dirtyFlags & 0xcL) != 0) {



                // read androidx.databinding.ViewDataBinding.safeUnbox(price)
                androidxDatabindingViewDataBindingSafeUnboxPrice = androidx.databinding.ViewDataBinding.safeUnbox(price);


                // read String.valueOf(androidx.databinding.ViewDataBinding.safeUnbox(price))
                stringValueOfPrice = java.lang.String.valueOf(androidxDatabindingViewDataBindingSafeUnboxPrice);


                // read ("From: €\u2009") + (String.valueOf(androidx.databinding.ViewDataBinding.safeUnbox(price)))
                javaLangStringFromU2009StringValueOfPrice = ("From: €\u2009") + (stringValueOfPrice);


                // read (("From: €\u2009") + (String.valueOf(androidx.databinding.ViewDataBinding.safeUnbox(price)))) + ('0')
                javaLangStringFromU2009StringValueOfPriceChar0 = (javaLangStringFromU2009StringValueOfPrice) + ('0');
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.actionButton, javaLangStringFromU2009StringValueOfPriceChar0);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.titleTextView, routeDestination);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): routeDuration
        flag 1 (0x2L): routeDestination
        flag 2 (0x3L): price
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}