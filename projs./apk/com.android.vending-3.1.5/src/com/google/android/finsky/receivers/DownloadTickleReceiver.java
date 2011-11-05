package com.google.android.finsky.receivers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.FinskyInstance;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadReceiver;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.model.PurchaseStatusTracker;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.VendingPreferences;

public class DownloadTickleReceiver extends DownloadReceiver {

   public DownloadTickleReceiver() {}

   private Download.PackageProperties generatePackageProperties(Bundle var1, String var2) {
      StringBuilder var3 = (new StringBuilder()).append("assetid");
      String var5 = var3.append(var2).toString();
      String var6 = var1.getString(var5);
      StringBuilder var7 = (new StringBuilder()).append("asset_package");
      String var9 = var7.append(var2).toString();
      String var10 = var1.getString(var9);
      StringBuilder var11 = (new StringBuilder()).append("asset_signature");
      String var13 = var11.append(var2).toString();
      String var14 = var1.getString(var13);
      StringBuilder var15 = (new StringBuilder()).append("asset_size");
      String var17 = var15.append(var2).toString();
      Long var18 = Long.valueOf(Long.parseLong(var1.getString(var17)));
      StringBuilder var19 = (new StringBuilder()).append("asset_is_forward_locked");
      String var21 = var19.append(var2).toString();
      boolean var22 = Boolean.parseBoolean(var1.getString(var21));
      Long var23 = null;
      StringBuilder var24 = (new StringBuilder()).append("asset_refundtimeout");
      String var26 = var24.append(var2).toString();
      String var27 = var1.getString(var26);

      label13: {
         Long var28;
         try {
            var28 = Long.valueOf(var27);
         } catch (NumberFormatException var50) {
            Object[] var49 = new Object[]{var27};
            FinskyLog.w("Received refund period time end string : %s", var49);
            break label13;
         }

         var23 = var28;
      }

      StringBuilder var29 = (new StringBuilder()).append("user_email");
      String var31 = var29.append(var2).toString();
      String var32 = var1.getString(var31);
      StringBuilder var33 = (new StringBuilder()).append("asset_version_code");
      String var35 = var33.append(var2).toString();
      int var36 = Integer.parseInt(var1.getString(var35));
      Obb var40 = this.parseObb(var1, var10, (boolean)0, var2);
      Obb var44 = this.parseObb(var1, var10, (boolean)1, var2);
      AutoUpdateState var45 = AutoUpdateState.DEFAULT;
      long var46 = var18.longValue();
      return new Download.PackageProperties(var10, var45, var32, var36, var6, var22, var46, var14, var23, var40, var44);
   }

   private int getApplicationCount(Bundle var1) {
      int var2 = 0;

      String var4;
      do {
         StringBuilder var3 = (new StringBuilder()).append("assetid_");
         ++var2;
         var4 = var3.append(var2).toString();
      } while(var1.getString(var4) != null);

      return var2;
   }

   private String getAssetDownloadUrl(Bundle var1, boolean var2, String var3) {
      String var4 = var1.getString(var3);
      if(var2 && !var4.startsWith("https:")) {
         StringBuilder var5 = (new StringBuilder()).append("https");
         String var6 = var4.substring(4);
         var4 = var5.append(var6).toString();
      }

      return var4;
   }

   private Obb parseObb(Bundle var1, String var2, boolean var3, String var4) {
      int var5 = 1;

      Obb var13;
      while(true) {
         if(var5 > 2) {
            var13 = ObbFactory.createEmpty(var3, var2);
            break;
         }

         StringBuilder var6 = (new StringBuilder()).append("_").append(var5);
         String var8 = var6.append(var4).toString();
         String var9 = "additional_file_type" + var8;
         String var10 = var1.getString(var9);
         if(var10 == null) {
            Object[] var11 = new Object[1];
            Boolean var12 = Boolean.valueOf(var3);
            var11[0] = var12;
            FinskyLog.d("Not generating OBB (patch %b)", var11);
            var13 = ObbFactory.createEmpty(var3, var2);
            break;
         }

         label22: {
            if(var3) {
               if(var10.equals("OBB")) {
                  break label22;
               }
            } else if(var10.equals("OBB_PATCH")) {
               break label22;
            }

            String var14 = "additional_file_version_code" + var8;
            int var15 = Integer.parseInt(var1.getString(var14));
            String var16 = "additional_file_url" + var8;
            String var17 = var1.getString(var16);
            String var18 = "additional_file_size" + var8;
            long var19 = Long.parseLong(var1.getString(var18));
            Object[] var21 = new Object[]{var10};
            FinskyLog.d("Generating %s OBB", var21);
            ObbState var22 = ObbState.NOT_ON_STORAGE;
            var13 = ObbFactory.create(var3, var2, var15, var17, var19, var22);
            var13.syncStateWithStorage();
            break;
         }

         ++var5;
      }

      return var13;
   }

   public void onReceive(Context var1, Intent var2) {
      Object[] var3 = new Object[0];
      FinskyLog.d("Received tickle.", var3);
      if(var2.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
         this.setResultCode(-1);
      }

      if(var2.getStringExtra("from").equals("google.com")) {
         Bundle var4 = var2.getExtras();
         boolean var5 = var4.containsKey("server_initiated");
         String var6 = var4.getString("direct_download_key");
         if(var6 != null) {
            VendingPreferences.DIRECT_DOWNLOAD_KEY.put(var6);
         }

         int var7 = this.getApplicationCount(var4);
         AssetStore var8 = FinskyInstance.get().getAssetStore();

         for(int var9 = var7 + -1; var9 >= 0; var9 += -1) {
            String var10;
            if(var9 == 0) {
               var10 = "";
            } else {
               var10 = "_" + var9;
            }

            StringBuilder var11 = (new StringBuilder()).append("asset_secure");
            String var13 = var11.append(var10).toString();
            boolean var14 = Boolean.parseBoolean(var4.getString(var13));
            StringBuilder var15 = (new StringBuilder()).append("asset_blob_url");
            String var17 = var15.append(var10).toString();
            String var20 = this.getAssetDownloadUrl(var4, var14, var17);
            StringBuilder var21 = (new StringBuilder()).append("asset_name");
            String var23 = var21.append(var10).toString();
            String var24 = var4.getString(var23);
            StringBuilder var25 = (new StringBuilder()).append("download_auth_cookie_name");
            String var27 = var25.append(var10).toString();
            String var28 = var4.getString(var27);
            StringBuilder var29 = (new StringBuilder()).append("download_auth_cookie_value");
            String var31 = var29.append(var10).toString();
            String var32 = var4.getString(var31);
            Download.PackageProperties var35 = this.generatePackageProperties(var4, var10);
            PurchaseStatusTracker var36 = FinskyApp.get().getPurchaseStatusTracker();
            String var37 = var35.packageName;
            PurchaseStatusTracker.PurchaseState var38 = PurchaseStatusTracker.PurchaseState.PURCHASE_COMPLETED;
            byte var39 = 1;
            var36.switchState(var37, var39, var38);
            PurchaseStatusTracker var41 = FinskyApp.get().getPurchaseStatusTracker();
            String var42 = var35.packageName;
            var41.remove(var42);
            String var43 = var35.packageName;
            LocalAsset var44 = var8.getAsset(var43);
            if(!var5 && var44 == null) {
               Object[] var45 = new Object[1];
               String var46 = var35.packageName;
               var45[0] = var46;
               FinskyLog.w("Tickle for package %s ignored due to inconsistent AssetState", var45);
            } else {
               FinskyInstance.get().getInstaller().downloadAndInstallAsset(var20, var24, var35, var28, var32, (boolean)1);
            }
         }

      }
   }
}
