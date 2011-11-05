package com.facebook.katana.features;

import com.facebook.katana.IntentUriHandler;
import com.facebook.katana.features.DeepLinkUriMap;
import com.facebook.katana.features.UriMapClient;
import com.facebook.katana.util.UriTemplateMap;
import com.facebook.katana.util.Utils;
import java.util.Map;

class DeepLinkUriMapClient extends UriMapClient {

   public static final int MEMORY_TTL = 3600;
   public static final int PERSISTENT_STORE_TTL = 3600;
   public static String TAG = Utils.getClassName(DeepLinkUriMapClient.class);


   DeepLinkUriMapClient() {}

   public int getCacheTtl(Object var1, UriTemplateMap<IntentUriHandler.UriHandler> var2) {
      return 3600;
   }

   protected Map<String, String> getDevMappings() {
      return DeepLinkUriMap.LOCAL_DEV_MAPPINGS;
   }

   public String getKey(Object var1) {
      return "fw:deeplinkurimap";
   }

   public int getPersistentStoreTtl(Object var1, UriTemplateMap<IntentUriHandler.UriHandler> var2) {
      return 3600;
   }

   protected String getProjectMapSetting() {
      return "deeplinkurimap";
   }

   protected String getProjectName() {
      return "android_deep_links";
   }

   protected String getTag() {
      return TAG;
   }

   protected IntentUriHandler.UriHandler getUriHandler(String var1) {
      return new DeepLinkUriMap.DeepLinkUriHandler(var1);
   }
}
