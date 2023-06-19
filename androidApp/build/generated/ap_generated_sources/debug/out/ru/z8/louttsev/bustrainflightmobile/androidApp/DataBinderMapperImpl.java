package ru.z8.louttsev.bustrainflightmobile.androidApp;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActionBarBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityContactsBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityDrawerBaseBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityMainBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ActivityTravelTipsBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemPathBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemRouteAnywhereBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemRouteBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.NativeAdViewAnywhereBindingImpl;
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.NativeAdViewRouteBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIONBAR = 1;

  private static final int LAYOUT_ACTIVITYCONTACTS = 2;

  private static final int LAYOUT_ACTIVITYDRAWERBASE = 3;

  private static final int LAYOUT_ACTIVITYMAIN = 4;

  private static final int LAYOUT_ACTIVITYTRAVELTIPS = 5;

  private static final int LAYOUT_ITEMPATH = 6;

  private static final int LAYOUT_ITEMROUTE = 7;

  private static final int LAYOUT_ITEMROUTEANYWHERE = 8;

  private static final int LAYOUT_NATIVEADVIEWANYWHERE = 9;

  private static final int LAYOUT_NATIVEADVIEWROUTE = 10;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(10);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.action_bar, LAYOUT_ACTIONBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_contacts, LAYOUT_ACTIVITYCONTACTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_drawer_base, LAYOUT_ACTIVITYDRAWERBASE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_main, LAYOUT_ACTIVITYMAIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_travel_tips, LAYOUT_ACTIVITYTRAVELTIPS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_path, LAYOUT_ITEMPATH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_route, LAYOUT_ITEMROUTE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_route_anywhere, LAYOUT_ITEMROUTEANYWHERE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.native_ad_view_anywhere, LAYOUT_NATIVEADVIEWANYWHERE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.native_ad_view_route, LAYOUT_NATIVEADVIEWROUTE);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIONBAR: {
          if ("layout/action_bar_0".equals(tag)) {
            return new ActionBarBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for action_bar is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCONTACTS: {
          if ("layout/activity_contacts_0".equals(tag)) {
            return new ActivityContactsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_contacts is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYDRAWERBASE: {
          if ("layout/activity_drawer_base_0".equals(tag)) {
            return new ActivityDrawerBaseBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_drawer_base is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYMAIN: {
          if ("layout/activity_main_0".equals(tag)) {
            return new ActivityMainBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTRAVELTIPS: {
          if ("layout/activity_travel_tips_0".equals(tag)) {
            return new ActivityTravelTipsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_travel_tips is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMPATH: {
          if ("layout/item_path_0".equals(tag)) {
            return new ItemPathBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_path is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMROUTE: {
          if ("layout/item_route_0".equals(tag)) {
            return new ItemRouteBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_route is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMROUTEANYWHERE: {
          if ("layout/item_route_anywhere_0".equals(tag)) {
            return new ItemRouteAnywhereBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_route_anywhere is invalid. Received: " + tag);
        }
        case  LAYOUT_NATIVEADVIEWANYWHERE: {
          if ("layout/native_ad_view_anywhere_0".equals(tag)) {
            return new NativeAdViewAnywhereBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for native_ad_view_anywhere is invalid. Received: " + tag);
        }
        case  LAYOUT_NATIVEADVIEWROUTE: {
          if ("layout/native_ad_view_route_0".equals(tag)) {
            return new NativeAdViewRouteBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for native_ad_view_route is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(8);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "bindingAd");
      sKeys.put(2, "binding_ad");
      sKeys.put(3, "model");
      sKeys.put(4, "price");
      sKeys.put(5, "routeDestination");
      sKeys.put(6, "routeDuration");
      sKeys.put(7, "viewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(10);

    static {
      sKeys.put("layout/action_bar_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.action_bar);
      sKeys.put("layout/activity_contacts_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_contacts);
      sKeys.put("layout/activity_drawer_base_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_drawer_base);
      sKeys.put("layout/activity_main_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_main);
      sKeys.put("layout/activity_travel_tips_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.activity_travel_tips);
      sKeys.put("layout/item_path_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_path);
      sKeys.put("layout/item_route_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_route);
      sKeys.put("layout/item_route_anywhere_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.item_route_anywhere);
      sKeys.put("layout/native_ad_view_anywhere_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.native_ad_view_anywhere);
      sKeys.put("layout/native_ad_view_route_0", ru.z8.louttsev.bustrainflightmobile.androidApp.R.layout.native_ad_view_route);
    }
  }
}
