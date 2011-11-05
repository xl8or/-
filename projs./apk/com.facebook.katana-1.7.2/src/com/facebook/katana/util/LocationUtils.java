package com.facebook.katana.util;

import java.net.URLEncoder;

public class LocationUtils {

   public LocationUtils() {}

   public static String generateGeoIntentURI(String var0, double var1, double var3) {
      StringBuilder var5 = (new StringBuilder()).append("geo:0,0?q=");
      String var6 = URLEncoder.encode(var0);
      return var5.append(var6).append("@").append(var1).append(",").append(var3).toString();
   }

   public static String generateGoogleMapsURL(double var0, double var2, int var4, int var5, int var6) {
      StringBuilder var7 = new StringBuilder("http://maps.google.com/maps/api/staticmap?");
      String var8 = "center=" + var0 + "," + var2;
      var7.append(var8);
      String var10 = "&zoom=" + var4;
      var7.append(var10);
      String var12 = "&size=" + var6 + "x" + var5;
      var7.append(var12);
      String var14 = "&markers=" + var0 + "," + var2;
      var7.append(var14);
      StringBuilder var16 = var7.append("&sensor=false");
      return var7.toString();
   }
}
