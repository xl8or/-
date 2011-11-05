package com.facebook.katana.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import com.facebook.katana.util.FileUtils;
import com.facebook.katana.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TempFileManager {

   private static final String IMG_SUFFIX = ".jpg";
   private static final String PATH = "/Android/data/com.facebook.katana/cache/";
   private static final String PREFIX = ".facebook_";
   private static final String TAG = TempFileManager.class.getName();
   private static final long TTL = 60000L;


   public TempFileManager() {}

   public static Uri addImage(String var0) {
      Uri var1;
      if(!isExternalStorageMounted()) {
         var1 = null;
      } else {
         File var2 = getDirectory();

         File var3;
         try {
            var3 = File.createTempFile(".facebook_", ".jpg", var2);
         } catch (IOException var10) {
            Log.d(TAG, "Cannot create temp file", var10);
            var1 = null;
            return var1;
         }

         File var4 = var3;

         Uri var11;
         try {
            FileInputStream var5 = new FileInputStream(var0);
            FileOutputStream var6 = new FileOutputStream(var4);
            FileUtils.copy(var5, var6);
            var11 = Uri.fromFile(var4);
         } catch (Exception var9) {
            Log.d(TAG, "Error: ", var9);
            var1 = null;
            return var1;
         }

         var1 = var11;
      }

      return var1;
   }

   public static void cleanup() {
      if(isExternalStorageMounted()) {
         TempFileManager.1 var0 = new TempFileManager.1();
         File[] var1 = new File[1];
         File var2 = getDirectory();
         var1[0] = var2;
         var0.execute(var1);
      }
   }

   private static File getDirectory() {
      File var0;
      if(!isExternalStorageMounted()) {
         var0 = null;
      } else {
         File var1 = Environment.getExternalStorageDirectory();
         StringBuilder var2 = new StringBuilder();
         String var3 = var1.toString();
         String var4 = var2.append(var3).append("/Android/data/com.facebook.katana/cache/").toString();
         File var5 = new File(var4);
         boolean var6 = var5.mkdirs();
         var0 = var5;
      }

      return var0;
   }

   private static boolean isExternalStorageMounted() {
      return Environment.getExternalStorageState().equals("mounted");
   }

   static class 1 extends AsyncTask<File, Integer, Object> {

      1() {}

      protected Object doInBackground(File ... var1) {
         long var2 = System.currentTimeMillis();
         File[] var4 = var1;
         int var5 = var1.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File var7 = var4[var6];
            if(!var7.isDirectory()) {
               String var8 = TempFileManager.TAG;
               StringBuilder var9 = (new StringBuilder()).append("Attempted to clean a non-directory ");
               String var10 = var7.toString();
               String var11 = var9.append(var10).toString();
               Log.e(var8, var11);
            } else {
               File[] var12 = var7.listFiles();
               int var13 = 0;

               while(true) {
                  int var14 = var12.length;
                  if(var13 >= var14) {
                     break;
                  }

                  long var15 = var12[var13].lastModified();
                  if(var2 - var15 > 60000L) {
                     boolean var17 = var12[var13].delete();
                  }

                  ++var13;
               }
            }
         }

         return null;
      }
   }
}
