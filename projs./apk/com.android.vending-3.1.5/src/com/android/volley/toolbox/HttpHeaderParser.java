package com.android.volley.toolbox;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class HttpHeaderParser {

   public HttpHeaderParser() {}

   public static Cache.Entry parseCacheHeaders(NetworkResponse var0) {
      long var1 = System.currentTimeMillis();
      Map var3 = var0.headers;
      long var4 = 0L;
      long var6 = 0L;
      long var8 = 0L;
      String var10 = (String)var3.get("Date");
      if(var10 != null) {
         var4 = parseDateAsEpoch(var10);
      }

      String var11 = (String)var3.get("Expires");
      if(var11 != null) {
         var6 = parseDateAsEpoch(var11);
      }

      String var12 = (String)var3.get("ETag");
      if(var4 > 0L && var6 >= var4) {
         long var13 = var6 - var4;
         var8 = var1 + var13;
      }

      Cache.Entry var15 = new Cache.Entry();
      byte[] var16 = var0.data;
      var15.data = var16;
      var15.etag = var12;
      var15.softTtl = var8;
      long var17 = var15.softTtl;
      var15.ttl = var17;
      var15.serverDate = var4;
      return var15;
   }

   public static String parseCharset(Map<String, String> var0) {
      String var1 = (String)var0.get("Content-Type");
      String var6;
      if(var1 != null) {
         String[] var2 = var1.split(";");
         int var3 = 1;

         while(true) {
            int var4 = var2.length;
            if(var3 >= var4) {
               break;
            }

            String[] var5 = var2[var3].trim().split("=");
            if(var5.length == 2 && var5[0].equals("charset")) {
               var6 = var5[1];
               return var6;
            }

            ++var3;
         }
      }

      var6 = "ISO-8859-1";
      return var6;
   }

   public static long parseDateAsEpoch(String var0) {
      long var1;
      long var3;
      try {
         var1 = DateUtils.parseDate(var0).getTime();
      } catch (DateParseException var6) {
         var3 = 0L;
         return var3;
      }

      var3 = var1;
      return var3;
   }
}
