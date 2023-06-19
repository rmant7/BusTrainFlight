// Generated by data binding compiler. Do not edit!
package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.Deprecated;
import java.lang.Object;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Route;

public abstract class ItemRouteBinding extends ViewDataBinding {
  @NonNull
  public final TextView actionButton;

  @NonNull
  public final CheckBox openIndicator;

  @NonNull
  public final RecyclerView pathList;

  @NonNull
  public final ConstraintLayout routeMainView;

  @NonNull
  public final TextView storeTextView;

  @NonNull
  public final TextView titleTextView;

  @NonNull
  public final LinearLayout transportIconContainer;

  @NonNull
  public final HorizontalScrollView transportIconContainerScroll;

  @Bindable
  protected Route mModel;

  protected ItemRouteBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView actionButton, CheckBox openIndicator, RecyclerView pathList,
      ConstraintLayout routeMainView, TextView storeTextView, TextView titleTextView,
      LinearLayout transportIconContainer, HorizontalScrollView transportIconContainerScroll) {
    super(_bindingComponent, _root, _localFieldCount);
    this.actionButton = actionButton;
    this.openIndicator = openIndicator;
    this.pathList = pathList;
    this.routeMainView = routeMainView;
    this.storeTextView = storeTextView;
    this.titleTextView = titleTextView;
    this.transportIconContainer = transportIconContainer;
    this.transportIconContainerScroll = transportIconContainerScroll;
  }

  public abstract void setModel(@Nullable Route model);

  @Nullable
  public Route getModel() {
    return mModel;
  }

  @NonNull
  public static ItemRouteBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_route, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemRouteBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemRouteBinding>inflateInternal(inflater, R.layout.item_route, root, attachToRoot, component);
  }

  @NonNull
  public static ItemRouteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_route, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemRouteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemRouteBinding>inflateInternal(inflater, R.layout.item_route, null, false, component);
  }

  public static ItemRouteBinding bind(@NonNull View view) {
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
  public static ItemRouteBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemRouteBinding)bind(component, view, R.layout.item_route);
  }
}
