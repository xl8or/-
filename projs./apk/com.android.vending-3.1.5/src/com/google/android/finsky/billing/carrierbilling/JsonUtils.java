package com.google.android.finsky.billing.carrierbilling;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

   private JsonUtils() {}

   public static Boolean getBoolean(JSONObject var0, String var1) {
      Boolean var2;
      Boolean var3;
      try {
         var2 = Boolean.valueOf(var0.getBoolean(var1));
      } catch (JSONException var5) {
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static Integer getInt(JSONObject var0, String var1) {
      Integer var2 = null;

      Integer var3;
      try {
         var3 = Integer.valueOf(Integer.parseInt(var0.getString(var1)));
      } catch (NumberFormatException var6) {
         return var2;
      } catch (JSONException var7) {
         return var2;
      }

      var2 = var3;
      return var2;
   }

   public static Long getLong(JSONObject var0, String var1) {
      Long var2 = null;

      Long var3;
      try {
         var3 = Long.valueOf(Long.parseLong(var0.getString(var1)));
      } catch (NumberFormatException var6) {
         return var2;
      } catch (JSONException var7) {
         return var2;
      }

      var2 = var3;
      return var2;
   }

   public static JSONObject getObject(JSONObject var0, String var1) {
      JSONObject var2;
      JSONObject var3;
      try {
         var2 = var0.getJSONObject(var1);
      } catch (JSONException var5) {
         var3 = null;
         return var3;
      }

      var3 = var2;
      return var3;
   }

   public static String getString(JSONObject var0, String var1) {
      String var2;
      boolean var3;
      try {
         var2 = var0.getString(var1);
         var3 = "null".equals(var2);
      } catch (JSONException var5) {
         var2 = null;
         return var2;
      }

      if(var3) {
         var2 = null;
      }

      return var2;
   }

   public static JSONObject toLowerCase(JSONObject var0) throws JSONException {
      Iterator var1 = var0.keys();
      JSONObject var2 = new JSONObject();

      while(var1.hasNext()) {
         String var3 = (String)var1.next();
         Object var4 = var0.get(var3);
         if(var4 instanceof JSONObject) {
            var4 = toLowerCase((JSONObject)var4);
         }

         String var5 = var3.toLowerCase();
         var2.put(var5, var4);
      }

      return var2;
   }
}
