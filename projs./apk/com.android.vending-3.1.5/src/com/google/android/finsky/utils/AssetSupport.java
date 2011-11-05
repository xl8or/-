package com.google.android.finsky.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.finsky.utils.PackageManagerHelper;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;

public class AssetSupport {

   public static final int ALERT_ID_CONFIRM_UNINSTALL = 1;
   public static final String KEY_PACKAGE_NAME = "package_name";
   private static final String TAG_REFUND_FAILURE_DIALOG = "refund_failure";
   private static final String TAG_UNINSTALL_CONFIRM_DIALOG = "uninstall_confirm";


   public AssetSupport() {}

   public static void bindAutoUpdateSection(String var0, View var1) {
      LocalAsset var2 = FinskyInstance.get().getAssetStore().getAsset(var0);
      View var3 = var1.findViewById(2131755031);
      if(var2 != null && (var2.isDownloadingOrInstalling() || var2.isInstalled())) {
         byte var10;
         label29: {
            var3.setVisibility(0);
            AutoUpdateState var4 = var2.getAutoUpdateState();
            PackageInfoCache var5 = FinskyApp.get().getPackageInfoCache();
            String var6 = var2.getPackage();
            boolean var7 = var5.isSystemPackage(var6);
            AutoUpdateState var8 = AutoUpdateState.ENABLED;
            if(var4 != var8) {
               label27: {
                  if(var7) {
                     AutoUpdateState var9 = AutoUpdateState.DISABLED;
                     if(var4 != var9) {
                        break label27;
                     }
                  }

                  var10 = 0;
                  break label29;
               }
            }

            var10 = 1;
         }

         CheckBox var11 = (CheckBox)var3.findViewById(2131755032);
         Looper var12 = var1.getContext().getMainLooper();
         Handler var13 = new Handler(var12);
         AssetSupport.3 var14 = new AssetSupport.3(var11, (boolean)var10);
         var13.post(var14);
         AssetSupport.4 var16 = new AssetSupport.4(var2);
         var11.setOnCheckedChangeListener(var16);
         byte var17;
         if(!var2.isInstalled() && !var2.isDownloadingOrInstalling()) {
            var17 = 0;
         } else {
            var17 = 1;
         }

         var11.setEnabled((boolean)var17);
         var3.findViewById(2131755033).setEnabled((boolean)var17);
         AssetSupport.5 var18 = new AssetSupport.5(var11, var0);
         var3.setOnClickListener(var18);
      } else {
         var3.setVisibility(8);
      }
   }

   public static void refund(String var0, AssetSupport.RefundListener var1) {
      LocalAsset var2 = FinskyInstance.get().getAssetStore().getAsset(var0);
      FinskyApp var3 = FinskyApp.get();
      String var4 = var2.getAccount();
      VendingApi var5 = var3.getVendingApi(var4);
      VendingProtos.RefundRequestProto var6 = new VendingProtos.RefundRequestProto();
      String var7 = var2.getAssetId();
      var6.setAssetId(var7);
      AssetSupport.1 var9 = new AssetSupport.1(var2, var1);
      AssetSupport.2 var10 = new AssetSupport.2(var1);
      var5.refundAsset(var6, var9, var10);
   }

   public static void showRefundFailureDialog(FragmentManager var0) {
      SimpleAlertDialog.newInstance(2131231035, 2131231036, 2131231030, -1).show(var0, "refund_failure");
   }

   public static void showUninstallConfirmationDialog(String var0, Fragment var1) {
      FragmentManager var2 = var1.getFragmentManager();
      if(var2.findFragmentByTag("uninstall_confirm") == null) {
         boolean var3 = FinskyApp.get().getPackageInfoCache().isSystemPackage(var0);
         int var4;
         if(var3) {
            var4 = 2131230938;
         } else {
            var4 = 2131230940;
         }

         int var5;
         if(var3) {
            var5 = 2131230939;
         } else {
            var5 = 2131230941;
         }

         SimpleAlertDialog var6 = SimpleAlertDialog.newInstance(var4, var5, 2131231030, 2131230813);
         Bundle var7 = new Bundle();
         var7.putString("package_name", var0);
         var6.setCallback(var1, 1, var7);
         var6.show(var2, "uninstall_confirm");
      }
   }

   public static void uninstall(String var0) {
      if(!TextUtils.isEmpty(var0)) {
         LocalAsset var1 = FinskyInstance.get().getAssetStore().getAsset(var0);
         if(var1 != null) {
            var1.setStateUninstalling();
         }

         PackageManagerHelper.uninstallPackage(var0);
      }
   }

   static class 1 implements Response.Listener<VendingProtos.RefundResponseProto> {

      // $FF: synthetic field
      final AssetSupport.RefundListener val$listener;
      // $FF: synthetic field
      final LocalAsset val$localAsset;


      1(LocalAsset var1, AssetSupport.RefundListener var2) {
         this.val$localAsset = var1;
         this.val$listener = var2;
      }

      public void onResponse(VendingProtos.RefundResponseProto var1) {
         switch(var1.getResult()) {
         case 0:
            this.val$localAsset.setRefundPeriodEndTime((Long)null);
            AssetSupport.RefundListener var2 = this.val$listener;
            AssetSupport.RefundResult var3 = AssetSupport.RefundResult.SUCCESS;
            var2.onComplete(var3);
            return;
         case 1:
            AssetSupport.RefundListener var6 = this.val$listener;
            AssetSupport.RefundResult var7 = AssetSupport.RefundResult.BAD_REQUEST;
            var6.onComplete(var7);
            return;
         case 2:
            this.val$localAsset.setRefundPeriodEndTime((Long)null);
            AssetSupport.RefundListener var4 = this.val$listener;
            AssetSupport.RefundResult var5 = AssetSupport.RefundResult.CANNOT_REFUND;
            var4.onComplete(var5);
            return;
         default:
         }
      }
   }

   static class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final AssetSupport.RefundListener val$listener;


      2(AssetSupport.RefundListener var1) {
         this.val$listener = var1;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         AssetSupport.RefundListener var4 = this.val$listener;
         AssetSupport.RefundResult var5 = AssetSupport.RefundResult.NETWORK_ERROR;
         var4.onComplete(var5);
      }
   }

   static class 3 implements Runnable {

      // $FF: synthetic field
      final CheckBox val$autoUpdateCheckBox;
      // $FF: synthetic field
      final boolean val$isAutoUpdateEnabled;


      3(CheckBox var1, boolean var2) {
         this.val$autoUpdateCheckBox = var1;
         this.val$isAutoUpdateEnabled = var2;
      }

      public void run() {
         CheckBox var1 = this.val$autoUpdateCheckBox;
         boolean var2 = this.val$isAutoUpdateEnabled;
         var1.setChecked(var2);
      }
   }

   static class 4 implements OnCheckedChangeListener {

      // $FF: synthetic field
      final LocalAsset val$localAsset;


      4(LocalAsset var1) {
         this.val$localAsset = var1;
      }

      public void onCheckedChanged(CompoundButton var1, boolean var2) {
         LocalAsset var3 = this.val$localAsset;
         AutoUpdateState var4;
         if(var2) {
            var4 = AutoUpdateState.ENABLED;
         } else {
            var4 = AutoUpdateState.DISABLED;
         }

         var3.setAutoUpdateState(var4);
      }
   }

   static class 5 implements OnClickListener {

      // $FF: synthetic field
      final CheckBox val$autoUpdateCheckBox;
      // $FF: synthetic field
      final String val$packageName;


      5(CheckBox var1, String var2) {
         this.val$autoUpdateCheckBox = var1;
         this.val$packageName = var2;
      }

      public void onClick(View var1) {
         this.val$autoUpdateCheckBox.toggle();
         if(FinskyApp.get().getAnalytics() != null) {
            Analytics var2 = FinskyApp.get().getAnalytics();
            StringBuilder var3 = (new StringBuilder()).append("autoUpdate?doc=");
            String var4 = this.val$packageName;
            StringBuilder var5 = var3.append(var4).append("&autoupdateenabled=");
            boolean var6 = this.val$autoUpdateCheckBox.isChecked();
            String var7 = var5.append(var6).toString();
            var2.logPageView((String)null, (String)null, var7);
         }
      }
   }

   public static enum RefundResult {

      // $FF: synthetic field
      private static final AssetSupport.RefundResult[] $VALUES;
      BAD_REQUEST("BAD_REQUEST", 2),
      CANNOT_REFUND("CANNOT_REFUND", 1),
      NETWORK_ERROR("NETWORK_ERROR", 3),
      SUCCESS("SUCCESS", 0);


      static {
         AssetSupport.RefundResult[] var0 = new AssetSupport.RefundResult[4];
         AssetSupport.RefundResult var1 = SUCCESS;
         var0[0] = var1;
         AssetSupport.RefundResult var2 = CANNOT_REFUND;
         var0[1] = var2;
         AssetSupport.RefundResult var3 = BAD_REQUEST;
         var0[2] = var3;
         AssetSupport.RefundResult var4 = NETWORK_ERROR;
         var0[3] = var4;
         $VALUES = var0;
      }

      private RefundResult(String var1, int var2) {}
   }

   public interface RefundListener {

      void onComplete(AssetSupport.RefundResult var1);
   }
}
