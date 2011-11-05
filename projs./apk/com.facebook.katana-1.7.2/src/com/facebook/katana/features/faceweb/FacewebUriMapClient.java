package com.facebook.katana.features.faceweb;

import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.features.UriMapClient;
import com.facebook.katana.features.faceweb.FacewebUriMap;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Map;

class FacewebUriMapClient extends UriMapClient {

   public static final int MEMORY_TTL = 3600;
   public static final int PERSISTENT_STORE_TTL = 3600;
   public static String TAG = Utils.getClassName(FacewebUriMapClient.class);


   FacewebUriMapClient() {}

   public int getCacheTtl(Object var1, UriTemplateMap<IntentUriHandler.UriHandler> var2) {
      return 3600;
   }

   protected Map<String, String> getDevMappings() {
      return FacewebUriMap.LOCAL_DEV_MAPPINGS;
   }

   public String getKey(Object var1) {
      return "fw:urimap";
   }

   public int getPersistentStoreTtl(Object var1, UriTemplateMap<IntentUriHandler.UriHandler> var2) {
      return 3600;
   }

   protected String getProjectMapSetting() {
      return "urimap";
   }

   protected String getProjectName() {
      return "android_faceweb";
   }

   protected String getTag() {
      return TAG;
   }

   protected IntentUriHandler.UriHandler getUriHandler(String var1) {
      return new FacewebUriMap.FacewebUriHandler(var1);
   }
}
