// Generated by view binder compiler. Do not edit!
package ru.z8.louttsev.bustrainflightmobile.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.appbar.AppBarLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ru.z8.louttsev.bustrainflightmobile.androidApp.R;

public final class ContentLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FrameLayout activityContainer;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final TextView appBarTitle;

  @NonNull
  public final ImageView menuButton;

  @NonNull
  public final TextView tagline;

  @NonNull
  public final Toolbar toolBar;

  private ContentLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull FrameLayout activityContainer, @NonNull AppBarLayout appBarLayout,
      @NonNull TextView appBarTitle, @NonNull ImageView menuButton, @NonNull TextView tagline,
      @NonNull Toolbar toolBar) {
    this.rootView = rootView;
    this.activityContainer = activityContainer;
    this.appBarLayout = appBarLayout;
    this.appBarTitle = appBarTitle;
    this.menuButton = menuButton;
    this.tagline = tagline;
    this.toolBar = toolBar;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.activityContainer;
      FrameLayout activityContainer = ViewBindings.findChildViewById(rootView, id);
      if (activityContainer == null) {
        break missingId;
      }

      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.appBarTitle;
      TextView appBarTitle = ViewBindings.findChildViewById(rootView, id);
      if (appBarTitle == null) {
        break missingId;
      }

      id = R.id.menuButton;
      ImageView menuButton = ViewBindings.findChildViewById(rootView, id);
      if (menuButton == null) {
        break missingId;
      }

      id = R.id.tagline;
      TextView tagline = ViewBindings.findChildViewById(rootView, id);
      if (tagline == null) {
        break missingId;
      }

      id = R.id.toolBar;
      Toolbar toolBar = ViewBindings.findChildViewById(rootView, id);
      if (toolBar == null) {
        break missingId;
      }

      return new ContentLayoutBinding((ConstraintLayout) rootView, activityContainer, appBarLayout,
          appBarTitle, menuButton, tagline, toolBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
