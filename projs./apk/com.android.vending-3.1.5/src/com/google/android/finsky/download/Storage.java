package com.google.android.finsky.download;

import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

public class Storage {

   public Storage() {}

   public static long cachePartitionAvailableSpace() {
      String var0 = Environment.getDownloadCacheDirectory().getPath();
      StatFs var1 = new StatFs(var0);
      long var2 = (long)var1.getAvailableBlocks();
      long var4 = (long)var1.getBlockSize();
      return var2 * var4;
   }

   public static long dataPartitionAvailableSpace() {
      String var0 = Environment.getDataDirectory().getPath();
      StatFs var1 = new StatFs(var0);
      long var2 = (long)var1.getAvailableBlocks();
      long var4 = (long)var1.getBlockSize();
      return var2 * var4;
   }

   public static boolean externalStorageAvailable() {
      String var0 = Environment.getExternalStorageState();
      return "mounted".equals(var0);
   }

   public static long externalStorageAvailableSpace() {
      long var0;
      if(!externalStorageAvailable()) {
         var0 = 65535L;
      } else {
         String var2 = Environment.getExternalStorageDirectory().getPath();
         StatFs var3 = new StatFs(var2);
         long var4 = (long)var3.getAvailableBlocks();
         long var6 = (long)var3.getBlockSize();
         var0 = var4 * var6;
      }

      return var0;
   }

   public static Uri getFileUriForContentUri(Uri param0) {
      // $FF: Couldn't be decompiled
   }
}
