package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.travelapp.bustrainflightmobile.androidApp.DataBinderMapperImpl());
  }
}
