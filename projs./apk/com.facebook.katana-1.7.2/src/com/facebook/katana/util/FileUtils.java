package com.facebook.katana.util;

import android.content.Context;
import com.facebook.katana.util.StringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

   public FileUtils() {}

   public static String buildFilename(Context var0) {
      StringBuilder var1 = new StringBuilder();
      String var2 = var0.getFilesDir().getAbsolutePath();
      StringBuilder var3 = var1.append(var2).append("/");
      String var4 = StringUtils.randomString(4);
      return var3.append(var4).toString();
   }

   public static void copy(InputStream var0, OutputStream var1) throws IOException {
      try {
         byte[] var2 = new byte[1024];

         while(true) {
            int var3 = var0.read(var2);
            if(var3 <= 0) {
               return;
            }

            var1.write(var2, 0, var3);
         }
      } finally {
         var0.close();
         var1.close();
      }
   }

   public static void deleteFilesInDirectory(String var0) {
      File var1 = new File(var0);
      if(var1.exists()) {
         File[] var2 = var1.listFiles();
         if(var2 != null) {
            int var3 = 0;

            while(true) {
               int var4 = var2.length;
               if(var3 >= var4) {
                  return;
               }

               boolean var5 = var2[var3].delete();
               ++var3;
            }
         }
      }
   }

   public static byte[] getBytesFromFile(File var0) throws IOException {
      FileInputStream var1 = new FileInputStream(var0);
      long var2 = var0.length();
      if(var2 > 2147483647L) {
         ;
      }

      byte[] var4 = new byte[(int)var2];
      int var5 = 0;

      while(true) {
         int var6 = var4.length;
         if(var5 >= var6) {
            break;
         }

         int var7 = var4.length - var5;
         int var8 = var1.read(var4, var5, var7);
         if(var8 < 0) {
            break;
         }

         var5 += var8;
      }

      int var9 = var4.length;
      if(var5 < var9) {
         StringBuilder var10 = (new StringBuilder()).append("Could not completely read file ");
         String var11 = var0.getName();
         String var12 = var10.append(var11).toString();
         throw new IOException(var12);
      } else {
         var1.close();
         return var4;
      }
   }
}
