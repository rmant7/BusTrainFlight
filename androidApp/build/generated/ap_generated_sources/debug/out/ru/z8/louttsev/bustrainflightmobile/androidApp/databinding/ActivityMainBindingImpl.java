package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityMainBindingImpl extends ActivityMainBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.drawerLayout, 1);
        sViewsWithIds.put(R.id.appBarLayout, 2);
        sViewsWithIds.put(R.id.toolBar, 3);
        sViewsWithIds.put(R.id.appBarTitle, 4);
        sViewsWithIds.put(R.id.tagline, 5);
        sViewsWithIds.put(R.id.textView, 6);
        sViewsWithIds.put(R.id.origin_input_layout, 7);
        sViewsWithIds.put(R.id.origin_text_view, 8);
        sViewsWithIds.put(R.id.destination_input_layout, 9);
        sViewsWithIds.put(R.id.destination_text_view, 10);
        sViewsWithIds.put(R.id.clearButton, 11);
        sViewsWithIds.put(R.id.goButton, 12);
        sViewsWithIds.put(R.id.route_list_anywhere_recycler_view, 13);
        sViewsWithIds.put(R.id.route_list_recycler_view, 14);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityMainBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds));
    }
    private ActivityMainBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.google.android.material.appbar.AppBarLayout) bindings[2]
            , (android.widget.TextView) bindings[4]
            , (com.google.android.material.button.MaterialButton) bindings[11]
            , (com.google.android.material.textfield.TextInputLayout) bindings[9]
            , (android.widget.AutoCompleteTextView) bindings[10]
            , (android.widget.LinearLayout) bindings[1]
            , (com.google.android.material.button.MaterialButton) bindings[12]
            , (androidx.core.widget.NestedScrollView) bindings[0]
            , (com.google.android.material.textfield.TextInputLayout) bindings[7]
            , (android.widget.AutoCompleteTextView) bindings[8]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (androidx.appcompat.widget.Toolbar) bindings[3]
            );
        this.nestedScrollView.setTag(null);
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
        if (BR.viewModel == variableId) {
            setViewModel((ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setViewModel(@Nullable ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel ViewModel) {
        this.mViewModel = ViewModel;
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
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}