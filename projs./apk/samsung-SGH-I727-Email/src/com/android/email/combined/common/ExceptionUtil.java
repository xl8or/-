package com.android.email.combined.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.net.Uri;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;
import com.seven.util.SamsungUrlEncryptionUtils;

public class ExceptionUtil {

   private static Toast mToast = null;


   public ExceptionUtil() {}

   public static void exception(Activity var0) {
      Builder var1 = (new Builder(var0)).setMessage(2131166939);
      ExceptionUtil.2 var2 = new ExceptionUtil.2();
      var1.setPositiveButton(2131166260, var2).create().show();
   }

   public static void exception(Activity var0, int var1) {
      Builder var2 = (new Builder(var0)).setMessage(var1);
      ExceptionUtil.1 var3 = new ExceptionUtil.1();
      var2.setPositiveButton(2131166260, var3).create().show();
   }

   public static void exception(Context var0) {
      showToast(var0, 2131166939, 1);
   }

   public static void exception(Context var0, int var1) {
      showToast(var0, var1, 1);
   }

   public static void showDialogException(Activity var0, Exception var1) {
      try {
         ExceptionUtil.5 var2 = new ExceptionUtil.5();
         ExceptionUtil.6 var3 = new ExceptionUtil.6();
         showDialogException(var0, var1, var2, var3);
      } catch (RuntimeException var4) {
         var4.printStackTrace();
      }
   }

   public static void showDialogException(Activity var0, Exception var1, OnClickListener var2, OnKeyListener var3) {
      if(var0 != null) {
         if(var1 != null) {
            String var4;
            if(var1 instanceof RemoteException) {
               var4 = var0.getString(2131166951);
            } else {
               var4 = var0.getString(2131166951);
            }

            try {
               (new Builder(var0)).setTitle(2131166950).setMessage(var4).setOnKeyListener(var3).setPositiveButton(2131166260, var2).create().show();
            } catch (BadTokenException var6) {
               ;
            }
         }
      }
   }

   public static void showDialogPremiumExpired(Activity var0) {
      Builder var1 = (new Builder(var0)).setMessage(2131166952);
      ExceptionUtil.4 var2 = new ExceptionUtil.4(var0);
      Builder var3 = var1.setPositiveButton(2131166461, var2);
      ExceptionUtil.3 var4 = new ExceptionUtil.3();
      var3.setNegativeButton(2131166989, var4).create().show();
   }

   public static void showToast(Context var0, int var1, int var2) {
      if(mToast == null) {
         mToast = Toast.makeText(var0, var1, var2);
      } else {
         mToast.cancel();
         mToast.setText(var1);
         mToast.setDuration(var2);
      }

      mToast.show();
   }

   static class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }

   static class 4 implements OnClickListener {

      // $FF: synthetic field
      final Activity val$fromActivity;


      4(Activity var1) {
         this.val$fromActivity = var1;
      }

      public void onClick(DialogInterface var1, int var2) {
         StringBuilder var3 = (new StringBuilder()).append("http://socialhub.samsungapps.com/store/device/disclaimer.sh?");
         String var4 = SamsungUrlEncryptionUtils.getIMSIOfSubscriptionRenewalParams(this.val$fromActivity);
         String var5 = var3.append(var4).toString();
         Activity var6 = this.val$fromActivity;
         Uri var7 = Uri.parse(var5);
         Intent var8 = new Intent("android.intent.action.VIEW", var7);
         var6.startActivity(var8);
         var1.dismiss();
      }
   }

   static class 5 implements OnClickListener {

      5() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }

   static class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }

   static class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }

   static class 6 implements OnKeyListener {

      6() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         return false;
      }
   }
}
