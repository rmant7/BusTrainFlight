package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NativeAdViewAnywhereBindingImpl extends NativeAdViewAnywhereBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final com.google.android.gms.ads.nativead.NativeAdView mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NativeAdViewAnywhereBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private NativeAdViewAnywhereBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[5]
            , (android.widget.ImageView) bindings[1]
            , (android.widget.RatingBar) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            );
        this.actionButton.setTag(null);
        this.imageView.setTag(null);
        this.mboundView0 = (com.google.android.gms.ads.nativead.NativeAdView) bindings[0];
        this.mboundView0.setTag(null);
        this.ratingBar.setTag(null);
        this.storeTextView.setTag(null);
        this.titleTextView.setTag(null);
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
        if (BR.binding_ad == variableId) {
            setBindingAd((com.google.android.gms.ads.nativead.NativeAd) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBindingAd(@Nullable com.google.android.gms.ads.nativead.NativeAd BindingAd) {
        this.mBindingAd = BindingAd;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.binding_ad);
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
        java.lang.Double bindingAdStarRating = null;
        float bindingAdStarRatingFloatValue = 0f;
        java.lang.String bindingAdHeadline = null;
        com.google.android.gms.ads.nativead.NativeAd.Image bindingAdIcon = null;
        com.google.android.gms.ads.nativead.NativeAd bindingAd = mBindingAd;
        java.lang.String bindingAdPrice = null;
        java.lang.String bindingAdCallToAction = null;
        android.graphics.drawable.Drawable bindingAdIconDrawable = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (bindingAd != null) {
                    // read binding_ad.starRating
                    bindingAdStarRating = bindingAd.getStarRating();
                    // read binding_ad.headline
                    bindingAdHeadline = bindingAd.getHeadline();
                    // read binding_ad.icon
                    bindingAdIcon = bindingAd.getIcon();
                    // read binding_ad.price
                    bindingAdPrice = bindingAd.getPrice();
                    // read binding_ad.callToAction
                    bindingAdCallToAction = bindingAd.getCallToAction();
                }


                if (bindingAdStarRating != null) {
                    // read binding_ad.starRating.floatValue()
                    bindingAdStarRatingFloatValue = bindingAdStarRating.floatValue();
                }
                if (bindingAdIcon != null) {
                    // read binding_ad.icon.drawable
                    bindingAdIconDrawable = bindingAdIcon.getDrawable();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.actionButton, bindingAdCallToAction);
            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.imageView, bindingAdIconDrawable);
            androidx.databinding.adapters.RatingBarBindingAdapter.setRating(this.ratingBar, bindingAdStarRatingFloatValue);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.storeTextView, bindingAdPrice);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.titleTextView, bindingAdHeadline);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): binding_ad
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}