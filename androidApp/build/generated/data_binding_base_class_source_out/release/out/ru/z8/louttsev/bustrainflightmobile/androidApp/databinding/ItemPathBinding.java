// Generated by data binding compiler. Do not edit!
package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.material.button.MaterialButton;
import java.lang.Deprecated;
import java.lang.Object;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path;

public abstract class ItemPathBinding extends ViewDataBinding {
  @NonNull
  public final TextView actionButton;

  @NonNull
  public final MaterialButton bookingButton;

  @NonNull
  public final MaterialButton buyTicketButton;

  @NonNull
  public final MaterialButton hostelworldButton;

  @NonNull
  public final ConstraintLayout pathMainView;

  @NonNull
  public final TextView pathPlan;

  @NonNull
  public final TextView storeTextView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView transport;

  @Bindable
  protected Path mModel;

  protected ItemPathBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView actionButton, MaterialButton bookingButton, MaterialButton buyTicketButton,
      MaterialButton hostelworldButton, ConstraintLayout pathMainView, TextView pathPlan,
      TextView storeTextView, TextView textView2, TextView transport) {
    super(_bindingComponent, _root, _localFieldCount);
    this.actionButton = actionButton;
    this.bookingButton = bookingButton;
    this.buyTicketButton = buyTicketButton;
    this.hostelworldButton = hostelworldButton;
    this.pathMainView = pathMainView;
    this.pathPlan = pathPlan;
    this.storeTextView = storeTextView;
    this.textView2 = textView2;
    this.transport = transport;
  }

  public abstract void setModel(@Nullable Path model);

  @Nullable
  public Path getModel() {
    return mModel;
  }

  @NonNull
  public static ItemPathBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_path, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemPathBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemPathBinding>inflateInternal(inflater, R.layout.item_path, root, attachToRoot, component);
  }

  @NonNull
  public static ItemPathBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_path, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemPathBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemPathBinding>inflateInternal(inflater, R.layout.item_path, null, false, component);
  }

  public static ItemPathBinding bind(@NonNull View view) {
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
  public static ItemPathBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemPathBinding)bind(component, view, R.layout.item_path);
  }
}
