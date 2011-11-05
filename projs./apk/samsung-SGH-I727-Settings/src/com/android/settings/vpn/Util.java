package com.android.settings.vpn;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class Util {

   private Util() {}

   static boolean copyFiles(File var0, File var1) throws IOException {
      boolean var2;
      if(var0.equals(var1)) {
         var2 = false;
      } else {
         if(var0.isDirectory()) {
            if(!var1.exists()) {
               boolean var3 = var1.mkdir();
            }

            String[] var4 = var0.list();
            int var5 = 0;

            while(true) {
               int var6 = var4.length;
               if(var5 >= var6) {
                  break;
               }

               String var7 = var4[var5];
               File var8 = new File(var0, var7);
               String var9 = var4[var5];
               File var10 = new File(var1, var9);
               copyFiles(var8, var10);
               ++var5;
            }
         } else if(var0.exists()) {
            FileInputStream var12 = new FileInputStream(var0);
            FileOutputStream var13 = new FileOutputStream(var1);
            byte[] var14 = new byte[1024];

            while(true) {
               int var15 = var12.read(var14);
               if(var15 <= 0) {
                  var12.close();
                  var13.close();
                  break;
               }

               var13.write(var14, 0, var15);
            }
         }

         var2 = true;
      }

      return var2;
   }

   static boolean copyFiles(String var0, String var1) throws IOException {
      File var2 = new File(var0);
      File var3 = new File(var1);
      return copyFiles(var2, var3);
   }

   private static AlertDialog createErrorDialog(Context var0, String var1, OnClickListener var2) {
      Builder var3 = (new Builder(var0)).setTitle(17039380).setIcon(17301543).setMessage(var1);
      if(var2 != null) {
         var3.setPositiveButton(2131231901, var2);
      } else {
         Builder var5 = var3.setPositiveButton(17039370, (OnClickListener)null);
      }

      return var3.create();
   }

   static void deleteFile(File var0) {
      deleteFile(var0, (boolean)1);
   }

   static void deleteFile(File var0, boolean var1) {
      if(var0.isDirectory()) {
         File[] var2 = var0.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            deleteFile(var2[var4], (boolean)1);
         }
      }

      if(var1) {
         boolean var5 = var0.delete();
      }
   }

   static void deleteFile(String var0) {
      deleteFile(new File(var0));
   }

   static void deleteFile(String var0, boolean var1) {
      deleteFile(new File(var0), var1);
   }

   static boolean isFileOrEmptyDirectory(String var0) {
      File var1 = new File(var0);
      boolean var2;
      if(!var1.isDirectory()) {
         var2 = true;
      } else {
         String[] var3 = var1.list();
         if(var3 != null && var3.length != 0) {
            var2 = false;
         } else {
            var2 = true;
         }
      }

      return var2;
   }

   static void showErrorMessage(Context var0, String var1) {
      createErrorDialog(var0, var1, (OnClickListener)null).show();
   }

   static void showErrorMessage(Context var0, String var1, OnClickListener var2) {
      createErrorDialog(var0, var1, var2).show();
   }

   static void showLongToastMessage(Context var0, int var1) {
      Toast.makeText(var0, var1, 1).show();
   }

   static void showLongToastMessage(Context var0, String var1) {
      Toast.makeText(var0, var1, 1).show();
   }

   static void showShortToastMessage(Context var0, int var1) {
      Toast.makeText(var0, var1, 0).show();
   }

   static void showShortToastMessage(Context var0, String var1) {
      Toast.makeText(var0, var1, 0).show();
   }
}
