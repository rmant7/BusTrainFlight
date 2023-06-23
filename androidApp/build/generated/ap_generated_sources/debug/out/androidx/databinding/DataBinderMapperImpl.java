package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new ru.z8.louttsev.bustrainflightmobile.androidApp.DataBinderMapperImpl());
  }
}
