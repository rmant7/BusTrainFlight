package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemPathBindingImpl extends ItemPathBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.bookingButton, 5);
        sViewsWithIds.put(R.id.buyTicketButton, 6);
        sViewsWithIds.put(R.id.textView2, 7);
        sViewsWithIds.put(R.id.hostelworldButton, 8);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemPathBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private ItemPathBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[4]
            , (com.google.android.material.button.MaterialButton) bindings[5]
            , (com.google.android.material.button.MaterialButton) bindings[6]
            , (com.google.android.material.button.MaterialButton) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[2]
            );
        this.actionButton.setTag(null);
        this.pathMainView.setTag(null);
        this.pathPlan.setTag(null);
        this.storeTextView.setTag(null);
        this.transport.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
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
        if (BR.model == variableId) {
            setModel((ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path Model) {
        this.mModel = Model;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.model);
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
        ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path model = mModel;
        ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.TransportationType modelTransportationType = null;
        java.lang.String modelPathDuration = null;
        float modelEuroPrice = 0f;
        java.lang.String javaLangStringU2009StringValueOfModelEuroPrice = null;
        java.lang.String javaLangStringU2009StringValueOfModelEuroPriceChar0 = null;
        java.lang.String modelPathPlan = null;
        java.lang.String modelTransportationTypeToString = null;
        java.lang.String stringValueOfModelEuroPrice = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (model != null) {
                    // read model.transportationType
                    modelTransportationType = model.getTransportationType();
                    // read model.pathDuration
                    modelPathDuration = model.getPathDuration();
                    // read model.euroPrice
                    modelEuroPrice = model.getEuroPrice();
                    // read model.pathPlan
                    modelPathPlan = model.getPathPlan();
                }


                if (modelTransportationType != null) {
                    // read model.transportationType.toString()
                    modelTransportationTypeToString = modelTransportationType.toString();
                }
                // read String.valueOf(model.euroPrice)
                stringValueOfModelEuroPrice = java.lang.String.valueOf(modelEuroPrice);


                // read ("\u2009€ ") + (String.valueOf(model.euroPrice))
                javaLangStringU2009StringValueOfModelEuroPrice = ("\u2009€ ") + (stringValueOfModelEuroPrice);


                // read (("\u2009€ ") + (String.valueOf(model.euroPrice))) + ('0')
                javaLangStringU2009StringValueOfModelEuroPriceChar0 = (javaLangStringU2009StringValueOfModelEuroPrice) + ('0');
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.actionButton, javaLangStringU2009StringValueOfModelEuroPriceChar0);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.pathPlan, modelPathPlan);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.storeTextView, modelPathDuration);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.transport, modelTransportationTypeToString);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}