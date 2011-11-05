package com.facebook.katana.util;

import android.os.Bundle;
import com.facebook.katana.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class URLQueryBuilder {

   public static final String AMPERSAND = "&";
   public static final String EQUALS = "=";


   public URLQueryBuilder() {}

   public static StringBuilder buildQueryString(Bundle var0) {
      TreeMap var1 = new TreeMap();
      Iterator var2 = var0.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = var0.getString(var3);
         var1.put(var3, var4);
      }

      return buildQueryString((Map)var1);
   }

   public static StringBuilder buildQueryString(Map<String, String> var0) {
      StringBuilder var1;
      if(var0 == null) {
         var1 = new StringBuilder("");
      } else {
         StringBuilder var2 = new StringBuilder();
         boolean var3 = true;
         Iterator var4 = var0.keySet().iterator();

         while(true) {
            if(!var4.hasNext()) {
               var1 = var2;
               break;
            }

            String var5 = (String)var4.next();
            if(var3) {
               var3 = false;
            } else {
               StringBuilder var12 = var2.append("&");
            }

            String var6 = (String)var0.get(var5);

            try {
               String var7 = URLEncoder.encode(var5, "UTF-8");
               StringBuilder var8 = var2.append(var7).append("=");
               String var9;
               if(var6 != null) {
                  var9 = URLEncoder.encode(var6, "UTF-8");
               } else {
                  var9 = "";
               }

               var8.append(var9);
            } catch (UnsupportedEncodingException var13) {
               Log.e("URLQueryBuilder", "UTF-8 encoding not supported on this system.", var13);
               var1 = null;
               break;
            }
         }
      }

      return var1;
   }

   public static String buildSignature(String var0) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest var1 = MessageDigest.getInstance("MD5");
      var1.reset();
      byte[] var2 = var0.getBytes("UTF-8");
      int var3 = var2.length;
      var1.update(var2, 0, var3);
      byte[] var4 = var1.digest();
      int var5 = var4.length * 2;
      StringBuilder var6 = new StringBuilder(var5);
      int var7 = 0;

      while(true) {
         int var8 = var4.length;
         if(var7 >= var8) {
            return var6.toString();
         }

         int var9 = var4[var7] & 255;
         if(var9 < 16) {
            StringBuilder var10 = var6.append("0");
         }

         String var11 = Integer.toHexString(var9).toLowerCase();
         var6.append(var11);
         ++var7;
      }
   }

   public static StringBuilder buildSignedQueryString(Bundle var0, String var1) {
      TreeMap var2 = new TreeMap();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         String var5 = var0.getString(var4);
         var2.put(var4, var5);
      }

      return buildSignedQueryString((Map)var2, var1);
   }

   public static StringBuilder buildSignedQueryString(Map<String, String> var0, String var1) {
      StringBuilder var4;
      try {
         String var2 = buildSignature(signatureString(var0, var1));
         var0.put("sig", var2);
      } catch (Exception var6) {
         var4 = null;
         return var4;
      }

      var4 = buildQueryString(var0);
      return var4;
   }

   protected static List<String> keysInSignatureOrder(Map<String, String> var0) {
      Set var1 = var0.keySet();
      HashSet var2 = new HashSet(var1);
      ArrayList var3 = new ArrayList(var2);
      Collections.sort(var3);
      return var3;
   }

   public static Bundle parseQueryString(String var0) {
      Bundle var1;
      if(var0 == null) {
         var1 = new Bundle();
      } else {
         Bundle var2 = new Bundle();
         String[] var3 = var0.split("&");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String[] var6 = var3[var5].split("=");
            String var7 = URLDecoder.decode(var6[0]);
            String var8;
            if(var6.length > 1) {
               var8 = URLDecoder.decode(var6[1]);
            } else {
               var8 = "";
            }

            var2.putString(var7, var8);
         }

         var1 = var2;
      }

      return var1;
   }

   public static String signatureString(Map<String, String> var0, String var1) {
      StringBuilder var2 = new StringBuilder(256);
      Iterator var3 = keysInSignatureOrder(var0).iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         StringBuilder var5 = var2.append(var4).append("=");
         String var6 = (String)var0.get(var4);
         var5.append(var6);
      }

      var2.append(var1);
      return var2.toString();
   }
}
