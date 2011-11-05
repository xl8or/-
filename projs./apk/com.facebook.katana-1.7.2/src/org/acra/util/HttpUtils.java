package org.acra.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import org.acra.util.HttpRequest;
import org.apache.http.client.ClientProtocolException;

public class HttpUtils {

   public HttpUtils() {}

   public static void doPost(Map<?, ?> var0, URL var1, String var2, String var3) throws ClientProtocolException, IOException {
      StringBuilder var4 = new StringBuilder();
      Iterator var5 = var0.keySet().iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         if(var4.length() != 0) {
            StringBuilder var7 = var4.append('&');
         }

         Object var8 = var0.get(var6);
         if(var8 == null) {
            var8 = "";
         }

         String var9 = URLEncoder.encode(var6.toString(), "UTF-8");
         StringBuilder var10 = var4.append(var9).append('=');
         String var11 = URLEncoder.encode(var8.toString(), "UTF-8");
         var10.append(var11);
      }

      HttpRequest var13 = new HttpRequest;
      String var14;
      if(isNull(var2)) {
         var14 = null;
      } else {
         var14 = var2;
      }

      String var15;
      if(isNull(var3)) {
         var15 = null;
      } else {
         var15 = var3;
      }

      var13.<init>(var14, var15);
      String var16 = var1.toString();
      String var17 = var4.toString();
      var13.sendPost(var16, var17);
   }

   private static boolean isNull(String var0) {
      boolean var1;
      if(var0 != null && var0 != "ACRA-NULL-STRING") {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
