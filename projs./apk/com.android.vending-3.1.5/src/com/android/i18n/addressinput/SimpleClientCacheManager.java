package com.android.i18n.addressinput;

import com.android.i18n.addressinput.ClientCacheManager;

public class SimpleClientCacheManager implements ClientCacheManager {

   private static final String PUBLIC_ADDRESS_SERVER = "http://i18napis.appspot.com/address";


   public SimpleClientCacheManager() {}

   public String get(String var1) {
      return "";
   }

   public String getAddressServerUrl() {
      return "http://i18napis.appspot.com/address";
   }

   public void put(String var1, String var2) {}
}
