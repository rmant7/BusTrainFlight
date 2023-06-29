// Generated by data binding compiler. Do not edit!
package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.Deprecated;
import java.lang.Object;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel;

public abstract class ActivityMainBinding extends ViewDataBinding {
  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final TextView appBarTitle;

  @NonNull
  public final MaterialButton clearButton;

  @NonNull
  public final TextInputLayout destinationInputLayout;

  @NonNull
  public final AutoCompleteTextView destinationTextView;

  @NonNull
  public final LinearLayout drawerLayout;

  @NonNull
  public final FloatingActionButton fab;

  @NonNull
  public final MaterialButton goButton;

  @NonNull
  public final NestedScrollView nestedScrollView;

  @NonNull
  public final TextInputLayout originInputLayout;

  @NonNull
  public final AutoCompleteTextView originTextView;

  @NonNull
  public final RecyclerView routeListAnywhereRecyclerView;

  @NonNull
  public final RecyclerView routeListRecyclerView;

  @NonNull
  public final TextView tagline;

  @NonNull
  public final TextView textView;

  @NonNull
  public final Toolbar toolBar;

  @Bindable
  protected MainViewModel mViewModel;

  protected ActivityMainBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppBarLayout appBarLayout, TextView appBarTitle, MaterialButton clearButton,
      TextInputLayout destinationInputLayout, AutoCompleteTextView destinationTextView,
      LinearLayout drawerLayout, FloatingActionButton fab, MaterialButton goButton,
      NestedScrollView nestedScrollView, TextInputLayout originInputLayout,
      AutoCompleteTextView originTextView, RecyclerView routeListAnywhereRecyclerView,
      RecyclerView routeListRecyclerView, TextView tagline, TextView textView, Toolbar toolBar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.appBarLayout = appBarLayout;
    this.appBarTitle = appBarTitle;
    this.clearButton = clearButton;
    this.destinationInputLayout = destinationInputLayout;
    this.destinationTextView = destinationTextView;
    this.drawerLayout = drawerLayout;
    this.fab = fab;
    this.goButton = goButton;
    this.nestedScrollView = nestedScrollView;
    this.originInputLayout = originInputLayout;
    this.originTextView = originTextView;
    this.routeListAnywhereRecyclerView = routeListAnywhereRecyclerView;
    this.routeListRecyclerView = routeListRecyclerView;
    this.tagline = tagline;
    this.textView = textView;
    this.toolBar = toolBar;
  }

  public abstract void setViewModel(@Nullable MainViewModel viewModel);

  @Nullable
  public MainViewModel getViewModel() {
    return mViewModel;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_main, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMainBinding>inflateInternal(inflater, R.layout.activity_main, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_main, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMainBinding>inflateInternal(inflater, R.layout.activity_main, null, false, component);
  }

  public static ActivityMainBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityMainBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMainBinding)bind(component, view, R.layout.activity_main);
  }
}
