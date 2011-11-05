package com.google.android.finsky.local.checker;

import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.checker.SanityChecker;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.PackageInfoCache;
import java.util.Iterator;

public class InstallStateVerifierRule implements SanityChecker.SanityCheckRule {

   private PackageInfoCache mPackageInfoCache;


   public InstallStateVerifierRule(PackageInfoCache var1) {
      this.mPackageInfoCache = var1;
   }

   private void resetAsset(LocalAsset var1) {
      String var2 = var1.getPackage();
      AssetState var3 = var1.getState();
      boolean var4 = this.mPackageInfoCache.isPackageInstalled(var2);
      Object[] var5 = new Object[]{var2, null, null};
      String var6 = var3.toString();
      var5[1] = var6;
      Boolean var7 = Boolean.valueOf(var4);
      var5[2] = var7;
      FinskyLog.d("Resetting Asset Store State - Asset [%s] - State [%s] - Package exists (setting to INSTALLED) [%b]", var5);
      if(var4) {
         var1.resetInstalledState();
      } else {
         var1.resetUninstalledState();
      }
   }

   public int run(AssetStore var1) {
      int var2 = 0;
      Iterator var3 = var1.getAssets().iterator();

      while(var3.hasNext()) {
         LocalAsset var4 = (LocalAsset)var3.next();
         int[] var5 = InstallStateVerifierRule.1.$SwitchMap$com$google$android$finsky$local$AssetState;
         int var6 = var4.getState().ordinal();
         switch(var5[var6]) {
         case 1:
            PackageInfoCache var7 = this.mPackageInfoCache;
            String var8 = var4.getPackage();
            if(!var7.isPackageInstalled(var8)) {
               this.resetAsset(var4);
               ++var2;
            }
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
            this.resetAsset(var4);
            ++var2;
         }
      }

      return var2;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$local$AssetState = new int[AssetState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var1 = AssetState.INSTALLED.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var27) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var3 = AssetState.UNINSTALLING.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var26) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var5 = AssetState.INSTALLING.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var25) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var7 = AssetState.UNINSTALL_FAILED.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var24) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var9 = AssetState.INSTALL_FAILED.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var23) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var11 = AssetState.DOWNLOAD_DECLINED.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var22) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$google$android$finsky$local$AssetState;
            int var13 = AssetState.DOWNLOAD_FAILED.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var21) {
            ;
         }
      }
   }
}
