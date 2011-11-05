package com.google.android.finsky.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.finsky.activities.MainActivity;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.NotificationListener;
import com.google.android.finsky.utils.Notifier;

public class NotificationManager implements Notifier {

   private final int UNKNOWN_PACKAGE_ID;
   private final AssetStore mAssetStore;
   private final Context mContext;
   private NotificationListener mListener;


   public NotificationManager(Context var1, AssetStore var2) {
      int var3 = "unknown package".hashCode();
      this.UNKNOWN_PACKAGE_ID = var3;
      this.mContext = var1;
      this.mAssetStore = var2;
   }

   public static Intent createDefaultClickIntent(Context var0, String var1, String var2, String var3) {
      Intent var4;
      if(TextUtils.isEmpty(var1)) {
         var4 = new Intent();
         var4.setClass(var0, MainActivity.class);
      } else {
         Uri var8 = Uri.parse("https://market.android.com/details?id=" + var1);
         var4 = new Intent("android.intent.action.VIEW", var8, var0, MainActivity.class);
      }

      if(!TextUtils.isEmpty(var2)) {
         var4.putExtra("error_title", var2);
      }

      if(!TextUtils.isEmpty(var3)) {
         var4.putExtra("error_html_message", var3);
      }

      return var4;
   }

   private void showAppNotification(String var1, String var2, String var3, String var4, int var5) {
      if(this.mListener == null || !this.mListener.showAppAlert(var1, var3, var4)) {
         Intent var6 = createDefaultClickIntent(this.mContext, var1, var3, var4);
         this.showNotification(var1, var2, var3, var4, var5, var6);
      }
   }

   private void showDocNotification(String var1, String var2, String var3, String var4, int var5) {
      if(this.mListener == null || !this.mListener.showDocAlert(var1, var3, var4)) {
         Intent var6 = createDefaultClickIntent(this.mContext, var1, var3, var4);
         this.showNotification(var1, var2, var3, var4, var5, var6);
      }
   }

   private void showNotification(String var1, String var2, String var3, String var4, int var5, Intent var6) {
      PendingIntent var7 = PendingIntent.getActivity(this.mContext, 0, var6, 1073741824);
      long var8 = System.currentTimeMillis();
      Notification var10 = new Notification(var5, var2, var8);
      int var11 = var10.flags | 16;
      var10.flags = var11;
      Context var12 = this.mContext;
      var10.setLatestEventInfo(var12, var3, var4, var7);
      android.app.NotificationManager var13 = (android.app.NotificationManager)this.mContext.getSystemService("notification");
      Context var14 = this.mContext;
      var10.setLatestEventInfo(var14, var3, var4, var7);
      int var15;
      if(var1 == null) {
         var15 = this.UNKNOWN_PACKAGE_ID;
      } else {
         var15 = var1.hashCode();
      }

      var13.notify(var15, var10);
      Object[] var16 = new Object[]{var1, var3, var4};
      FinskyLog.d("Showing notification: [package=%s, Title=%s, Message=%s]", var16);
   }

   public void hideAllMessages(String var1) {
      android.app.NotificationManager var2 = (android.app.NotificationManager)this.mContext.getSystemService("notification");
      int var3;
      if(var1 == null) {
         var3 = this.UNKNOWN_PACKAGE_ID;
      } else {
         var3 = var1.hashCode();
      }

      var2.cancel(var3);
   }

   public void setNotificationListener(NotificationListener var1) {
      this.mListener = var1;
   }

   public void showCacheFullMessage(String var1, String var2) {
      Context var3 = this.mContext;
      Object[] var4 = new Object[]{var1};
      String var5 = var3.getString(2131230843, var4);
      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var1};
      String var8 = var6.getString(2131230844, var7);
      Context var9 = this.mContext;
      Object[] var10 = new Object[]{var1};
      String var11 = var9.getString(2131230845, var10);
      this.showAppNotification(var2, var5, var8, var11, 17301642);
   }

   public void showDownloadErrorMessage(String var1, String var2) {
      LocalAsset var3 = this.mAssetStore.getAsset(var2);
      byte var4;
      if(var3 != null) {
         var4 = var3.isUpdate();
      } else {
         var4 = 0;
      }

      if(var4 == 0) {
         Context var5 = this.mContext;
         Object[] var6 = new Object[]{var1};
         String var7 = var5.getString(2131230858, var6);
         Context var8 = this.mContext;
         Object[] var9 = new Object[]{var1};
         String var10 = var8.getString(2131230859, var9);
         Context var11 = this.mContext;
         Object[] var12 = new Object[]{var1};
         String var13 = var11.getString(2131230860, var12);
         this.showAppNotification(var2, var7, var10, var13, 17301642);
      } else {
         Context var16 = this.mContext;
         Object[] var17 = new Object[]{var1};
         String var18 = var16.getString(2131230861, var17);
         Context var19 = this.mContext;
         Object[] var20 = new Object[]{var1};
         String var21 = var19.getString(2131230862, var20);
         Context var22 = this.mContext;
         Object[] var23 = new Object[]{var1};
         String var24 = var22.getString(2131230863, var23);
         this.showAppNotification(var2, var18, var21, var24, 17301642);
      }
   }

   public void showExternalStorageFull(String var1, String var2) {
      Context var3 = this.mContext;
      Object[] var4 = new Object[]{var1};
      String var5 = var3.getString(2131230846, var4);
      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var1};
      String var8 = var6.getString(2131230847, var7);
      Context var9 = this.mContext;
      Object[] var10 = new Object[]{var1};
      String var11 = var9.getString(2131230848, var10);
      this.showAppNotification(var2, var5, var8, var11, 17301642);
   }

   public void showExternalStorageMissing(String var1, String var2) {
      Context var3 = this.mContext;
      Object[] var4 = new Object[]{var1};
      String var5 = var3.getString(2131230849, var4);
      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var1};
      String var8 = var6.getString(2131230850, var7);
      Context var9 = this.mContext;
      Object[] var10 = new Object[]{var1};
      String var11 = var9.getString(2131230851, var10);
      this.showAppNotification(var2, var5, var8, var11, 17301642);
   }

   public void showInstallationFailureMessage(String var1, String var2, String var3) {
      this.showAppNotification(var2, var3, var1, var3, 17301642);
   }

   public void showInstallingMessage(String var1, String var2, boolean var3) {
      Context var4 = this.mContext;
      int var5;
      if(var3) {
         var5 = 2131230866;
      } else {
         var5 = 2131230864;
      }

      String var6 = var4.getString(var5);
      Object[] var7 = new Object[]{var1};
      String var8 = String.format(var6, var7);
      Context var9 = this.mContext;
      int var10;
      if(var3) {
         var10 = 2131230867;
      } else {
         var10 = 2131230865;
      }

      String var11 = var9.getString(var10);
      Object[] var12 = new Object[]{var1};
      String var13 = String.format(var11, var12);
      Intent var14 = createDefaultClickIntent(this.mContext, var2, (String)null, (String)null);
      this.showNotification(var2, var8, var1, var13, 17301633, var14);
   }

   public void showMaliciousAssetRemovedMessage(String var1, String var2) {
      Context var3 = this.mContext;
      Object[] var4 = new Object[]{var1};
      String var5 = var3.getString(2131230852, var4);
      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var1};
      String var8 = var6.getString(2131230853, var7);
      Context var9 = this.mContext;
      Object[] var10 = new Object[]{var1};
      String var11 = var9.getString(2131230854, var10);
      this.showMessage(var5, var8, var11);
   }

   public void showMessage(String var1, String var2, String var3) {
      Intent var4 = createDefaultClickIntent(this.mContext, (String)null, var1, var3);
      this.showNotification((String)null, var1, var1, var3, 17301642, var4);
   }

   public void showNormalAssetRemovedMessage(String var1, String var2) {
      Context var3 = this.mContext;
      Object[] var4 = new Object[]{var1};
      String var5 = var3.getString(2131230855, var4);
      Context var6 = this.mContext;
      Object[] var7 = new Object[]{var1};
      String var8 = var6.getString(2131230856, var7);
      Context var9 = this.mContext;
      Object[] var10 = new Object[]{var1};
      String var11 = var9.getString(2131230857, var10);
      this.showAppNotification(var2, var5, var8, var11, 17301642);
   }

   public void showPurchaseErrorMessage(String var1, String var2, String var3, String var4) {
      this.showDocNotification(var4, var2, var1, var3, 17301642);
   }

   public void showSuccessfulInstallMessage(String var1, String var2, boolean var3) {
      Context var4 = this.mContext;
      int var5;
      if(var3) {
         var5 = 2131230870;
      } else {
         var5 = 2131230868;
      }

      String var6 = var4.getString(var5);
      Object[] var7 = new Object[]{var1};
      String var8 = String.format(var6, var7);
      Context var9 = this.mContext;
      int var10;
      if(var3) {
         var10 = 2131230871;
      } else {
         var10 = 2131230869;
      }

      String var11 = var9.getString(var10);
      Intent var12 = this.mContext.getPackageManager().getLaunchIntentForPackage(var2);
      this.showNotification(var2, var8, var1, var11, 2130837669, var12);
   }
}
