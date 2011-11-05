package com.google.android.finsky.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.receivers.PackageMonitorReceiver;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.vending.remoting.api.VendingApi;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.Date;

public class UninstallRefundTracker implements PackageMonitorReceiver.PackageStatusListener {

   private final AssetStore mAssetStore;
   private final Context mContext;


   public UninstallRefundTracker(Context var1, AssetStore var2, PackageMonitorReceiver var3) {
      this.mContext = var1;
      this.mAssetStore = var2;
      var3.attach(this);
   }

   private void refundIfNecessary(String var1) {
      LocalAsset var2 = this.mAssetStore.getAsset(var1);
      if(var2 != null) {
         boolean var8;
         label19: {
            Long var3 = var2.getRefundPeriodEndTime();
            if(var3 != null) {
               long var4 = var3.longValue();
               Date var6 = new Date(var4);
               Date var7 = new Date();
               if(var6.after(var7)) {
                  var8 = true;
                  break label19;
               }
            }

            var8 = false;
         }

         if(var8) {
            FinskyApp var9 = FinskyApp.get();
            String var10 = var2.getAccount();
            VendingApi var11 = var9.getVendingApi(var10);
            VendingProtos.RefundRequestProto var12 = new VendingProtos.RefundRequestProto();
            String var13 = var2.getAssetId();
            var12.setAssetId(var13);
            UninstallRefundTracker.1 var15 = new UninstallRefundTracker.1(var2);
            UninstallRefundTracker.2 var16 = new UninstallRefundTracker.2(var2);
            var11.refundAsset(var12, var15, var16);
         }
      }
   }

   public void onPackageAdded(String var1) {}

   public void onPackageAvailabilityChanged(String[] var1, boolean var2) {}

   public void onPackageChanged(String var1) {}

   public void onPackageRemoved(String var1, boolean var2) {
      if(!var2) {
         this.refundIfNecessary(var1);
      }
   }

   class 1 implements Response.Listener<VendingProtos.RefundResponseProto> {

      // $FF: synthetic field
      final LocalAsset val$localAsset;


      1(LocalAsset var2) {
         this.val$localAsset = var2;
      }

      public void onResponse(VendingProtos.RefundResponseProto var1) {
         switch(var1.getResult()) {
         case 0:
            this.val$localAsset.setRefundPeriodEndTime((Long)null);
            Looper var2 = Looper.getMainLooper();
            Handler var3 = new Handler(var2);
            UninstallRefundTracker.1.1 var4 = new UninstallRefundTracker.1.1();
            var3.post(var4);
            return;
         case 1:
            Object[] var6 = new Object[1];
            String var7 = var1.getResultDetail();
            var6[0] = var7;
            FinskyLog.d("Bad refund request: %s", var6);
            return;
         case 2:
            Object[] var8 = new Object[1];
            String var9 = this.val$localAsset.getPackage();
            var8[0] = var9;
            FinskyLog.d("Cannot refund asset removed by package manager: %s", var8);
            return;
         default:
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Toast.makeText(UninstallRefundTracker.this.mContext, 2131231037, 1).show();
         }
      }
   }

   class 2 implements Response.ErrorListener {

      // $FF: synthetic field
      final LocalAsset val$localAsset;


      2(LocalAsset var2) {
         this.val$localAsset = var2;
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[2];
         String var5 = this.val$localAsset.getPackage();
         var4[0] = var5;
         var4[1] = var2;
         FinskyLog.d("Refund failed for asset %s: %s", var4);
      }
   }
}
