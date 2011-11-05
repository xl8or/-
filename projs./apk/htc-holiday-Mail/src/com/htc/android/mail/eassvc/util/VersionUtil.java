package com.htc.android.mail.eassvc.util;

import android.content.Context;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class VersionUtil {

   public static final String VERSION_FILE_NAME = "VERSION";


   public VersionUtil() {}

   public static void delFile(Context var0, String var1) {
      File var2 = var0.getDir("config", 0);
      boolean var3 = (new File(var2, var1)).delete();
   }

   public static int getCurrentVersion(Context var0) {
      int var1 = -1;
      File var2 = getVersionFile(var0);
      int var3;
      if(!var2.exists()) {
         var3 = -1;
      } else {
         label15: {
            int var5;
            try {
               FileInputStream var4 = new FileInputStream(var2);
               var5 = (new DataInputStream(var4)).readInt();
            } catch (Exception var6) {
               var6.printStackTrace();
               break label15;
            }

            var1 = var5;
         }

         var3 = var1;
      }

      return var3;
   }

   private static File getVersionFile(Context var0) {
      File var1 = var0.getDir("config", 0);
      return new File(var1, "VERSION");
   }

   public static void moveFile(Context var0, String var1, long var2) {
      File var4 = var0.getDir("config", 0);
      StringBuilder var5 = (new StringBuilder()).append(var4);
      String var6 = File.separator;
      StringBuilder var7 = var5.append(var6);
      String var8 = String.valueOf(var2);
      String var9 = var7.append(var8).toString();
      File var10 = new File(var9);
      if(!var10.exists()) {
         boolean var11 = var10.mkdirs();
      }

      File var12 = new File(var4, var1);
      File var13 = new File(var10, var1);
      var12.renameTo(var13);
   }

   public static void setVersion(Context var0, int var1) {
      File var2 = getVersionFile(var0);

      try {
         if(var2.exists()) {
            boolean var3 = var2.createNewFile();
         }

         FileOutputStream var4 = new FileOutputStream(var2);
         DataOutputStream var5 = new DataOutputStream(var4);
         var5.writeInt(var1);
         var5.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }
}
