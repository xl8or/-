package com.htc.android.mail;

import java.util.HashMap;

public class MimeTypeMap {

   private static HashMap<String, String> TypeMap = new HashMap();
   public static String mDefaultMimetype = "application/octet-stream";


   static {
      Object var0 = TypeMap.put("bmp", "image/bmp");
      Object var1 = TypeMap.put("png", "image/png");
      Object var2 = TypeMap.put("jpg", "image/jpeg");
      Object var3 = TypeMap.put("jpe", "image/jpeg");
      Object var4 = TypeMap.put("jpeg", "image/jpeg");
      Object var5 = TypeMap.put("gif", "image/gif");
      Object var6 = TypeMap.put("mpo", "image/mpo");
      Object var7 = TypeMap.put("jps", "image/jps");
      Object var8 = TypeMap.put("mp3", "audio/mpeg");
      Object var9 = TypeMap.put("mid", "audio/midi");
      Object var10 = TypeMap.put("midi", "audio/midi");
      Object var11 = TypeMap.put("kar", "audio/midi");
      Object var12 = TypeMap.put("wav", "audio/x-wav");
      Object var13 = TypeMap.put("m4a", "application/octet-stream");
      Object var14 = TypeMap.put("3gp", "audio/3gpp");
      Object var15 = TypeMap.put("amr", "audio/amr");
      Object var16 = TypeMap.put("ogg", "audio/ogg");
      Object var17 = TypeMap.put("aac", "application/octet-stream");
      Object var18 = TypeMap.put("imy", "audio/imelody");
      Object var19 = TypeMap.put("3gp", "video/3gpp");
      Object var20 = TypeMap.put("3gpp", "video/3gpp");
      Object var21 = TypeMap.put("3g2", "video/3gpp2");
      Object var22 = TypeMap.put("mp4", "video/mp4");
      Object var23 = TypeMap.put("wmv", "video/x-ms-wmv");
      Object var24 = TypeMap.put("vcf", "text/x-vcard");
      Object var25 = TypeMap.put("vcs", "text/calendar");
   }

   public MimeTypeMap() {}

   public static String getContentType(String var0) {
      int var1 = var0.indexOf("/");
      String var2;
      if(var1 == -1) {
         var2 = null;
      } else {
         var2 = var0.substring(0, var1);
      }

      return var2;
   }

   public static String getMimetype(String var0) {
      String var1;
      if(var0 == null) {
         var1 = null;
      } else {
         String var2 = getSubFilename(var0);
         if(var2 == null) {
            var1 = mDefaultMimetype;
         } else {
            HashMap var3 = TypeMap;
            String var4 = var2.toLowerCase();
            if(var3.containsKey(var4)) {
               HashMap var5 = TypeMap;
               String var6 = var2.toLowerCase();
               var1 = (String)var5.get(var6);
            } else {
               android.webkit.MimeTypeMap var7 = android.webkit.MimeTypeMap.getSingleton();
               String var8 = var2.toLowerCase();
               String var9 = var7.getMimeTypeFromExtension(var8);
               if(var9 != null) {
                  var1 = var9;
               } else {
                  var1 = mDefaultMimetype;
               }
            }
         }
      }

      return var1;
   }

   public static String getMimetype(String var0, String var1) {
      String var2;
      if(var0 == null) {
         var2 = var1;
      } else {
         String var3 = getSubFilename(var0);
         if(var3 == null) {
            var2 = var1;
         } else {
            HashMap var4 = TypeMap;
            String var5 = var3.toLowerCase();
            if(var4.containsKey(var5)) {
               HashMap var6 = TypeMap;
               String var7 = var3.toLowerCase();
               var2 = (String)var6.get(var7);
            } else {
               android.webkit.MimeTypeMap var8 = android.webkit.MimeTypeMap.getSingleton();
               String var9 = var3.toLowerCase();
               String var10 = var8.getMimeTypeFromExtension(var9);
               if(var10 != null) {
                  var2 = var10;
               } else {
                  var2 = var1;
               }
            }
         }
      }

      return var2;
   }

   public static String getSubFilename(String var0) {
      int var1 = var0.lastIndexOf(".");
      String var2;
      if(var1 == -1) {
         var2 = null;
      } else {
         int var3 = var1 + 1;
         int var4 = var0.length();
         var2 = var0.substring(var3, var4);
      }

      return var2;
   }

   @Deprecated
   public static boolean isSupport(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         String var2 = getSubFilename(var0);
         if(var2 == null) {
            var1 = false;
         } else {
            HashMap var3 = TypeMap;
            String var4 = var2.toLowerCase();
            if(var3.containsKey(var4)) {
               var1 = true;
            } else {
               var1 = false;
            }
         }
      }

      return var1;
   }

   @Deprecated
   public static boolean isSupportByMIME(String var0) {
      boolean var1;
      if(var0 == null) {
         var1 = false;
      } else {
         HashMap var2 = TypeMap;
         String var3 = var0.toLowerCase();
         if(var2.containsValue(var3)) {
            var1 = true;
         } else {
            var1 = false;
         }
      }

      return var1;
   }
}
