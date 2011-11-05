package com.htc.android.mail.eassvc.util;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DirectpushPropUtil {

   static File mDpPrefFile;
   static Object mFileLock = new Object();


   public DirectpushPropUtil() {}

   public static boolean delete(Context var0) {
      boolean var3;
      if(mDpPrefFile == null) {
         File var1 = var0.getDir("config", 0);
         File var2 = var0.getDir("config", 0);
         mDpPrefFile = new File(var2, "dp_config.prefs");
         var3 = mDpPrefFile.delete();
      } else {
         var3 = false;
      }

      return var3;
   }

   public static Properties loadFile(Context var0) throws IOException {
      if(mDpPrefFile == null) {
         File var1 = var0.getDir("config", 0);
         File var2 = var0.getDir("config", 0);
         mDpPrefFile = new File(var2, "dp_config.prefs");
      }

      Properties var3 = new Properties();
      Object var4 = mFileLock;
      synchronized(var4) {
         if(mDpPrefFile != null && mDpPrefFile.exists()) {
            File var5 = mDpPrefFile;
            FileInputStream var6 = new FileInputStream(var5);
            var3.load(var6);
            var6.close();
         }

         return var3;
      }
   }

   public static boolean removeProp(Properties var0, String var1) {
      boolean var3;
      if(var0.containsKey(var1)) {
         var0.remove(var1);
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public static void saveFile(Context var0, Properties var1) throws IOException {
      if(mDpPrefFile == null) {
         File var2 = var0.getDir("config", 0);
         File var3 = var0.getDir("config", 0);
         mDpPrefFile = new File(var3, "dp_config.prefs");
      }

      if(!var1.containsKey("version")) {
         String var4 = String.valueOf(1);
         var1.put("version", var4);
      }

      Object var6 = mFileLock;
      synchronized(var6) {
         File var7 = mDpPrefFile;
         FileOutputStream var8 = new FileOutputStream(var7);
         var1.store(var8, (String)null);
         var8.close();
      }
   }

   public static boolean updateProp(Properties var0, String var1, String var2) {
      boolean var3;
      if(var0 != null && var1 != null) {
         if(var0.containsKey(var1)) {
            if(var2 == null) {
               var0.remove(var1);
               var3 = true;
               return var3;
            }

            if(!var0.get(var1).equals(var2)) {
               var0.put(var1, var2);
               var3 = true;
               return var3;
            }
         } else if(var2 != null) {
            var0.put(var1, var2);
            var3 = true;
            return var3;
         }

         var3 = false;
      } else {
         var3 = false;
      }

      return var3;
   }
}
