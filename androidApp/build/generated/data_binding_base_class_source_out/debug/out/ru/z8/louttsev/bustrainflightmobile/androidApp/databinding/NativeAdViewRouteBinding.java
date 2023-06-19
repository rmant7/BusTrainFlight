// Generated by data binding compiler. Do not edit!
package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import java.lang.Deprecated;
import java.lang.Object;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;

public abstract class NativeAdViewRouteBinding extends ViewDataBinding {
  @NonNull
  public final TextView actionButton;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final NativeAdView routeMainView;

  @NonNull
  public final TextView storeTextView;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final TextView titleTextView;

  @Bindable
  protected NativeAd mBindingAd;

  protected NativeAdViewRouteBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView actionButton, ImageView imageView, NativeAdView routeMainView,
      TextView storeTextView, TextView textView3, TextView titleTextView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.actionButton = actionButton;
    this.imageView = imageView;
    this.routeMainView = routeMainView;
    this.storeTextView = storeTextView;
    this.textView3 = textView3;
    this.titleTextView = titleTextView;
  }

  public abstract void setBindingAd(@Nullable NativeAd bindingAd);

  @Nullable
  public NativeAd getBindingAd() {
    return mBindingAd;
  }

  @NonNull
  public static NativeAdViewRouteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.native_ad_view_route, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static NativeAdViewRouteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<NativeAdViewRouteBinding>inflateInternal(inflater, R.layout.native_ad_view_route, root, attachToRoot, component);
  }

  @NonNull
  public static NativeAdViewRouteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.native_ad_view_route, null, false, component)
   */
  @NonNull
  @Deprecated
  public static NativeAdViewRouteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<NativeAdViewRouteBinding>inflateInternal(inflater, R.layout.native_ad_view_route, null, false, component);
  }

  public static NativeAdViewRouteBinding bind(@NonNull View view) {
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
  public static NativeAdViewRouteBinding bind(@NonNull View view, @Nullable Object component) {
    return (NativeAdViewRouteBinding)bind(component, view, R.layout.native_ad_view_route);
  }
}
