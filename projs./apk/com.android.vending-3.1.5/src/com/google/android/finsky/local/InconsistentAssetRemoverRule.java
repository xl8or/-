package com.google.android.finsky.local;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.checker.SanityChecker;
import com.google.android.finsky.services.CheckinAssetStoreListener;
import com.google.android.finsky.services.ReconstructDatabaseService;
import com.google.android.finsky.utils.FinskyLog;
import java.util.Collection;
import java.util.Iterator;

public class InconsistentAssetRemoverRule implements SanityChecker.SanityCheckRule {

   private final CheckinAssetStoreListener mCheckinAssetStoreListener;
   private final Context mContext;


   public InconsistentAssetRemoverRule(Context var1, CheckinAssetStoreListener var2) {
      this.mContext = var1;
      this.mCheckinAssetStoreListener = var2;
   }

   private void deleteAll(AssetStore var1, Collection<LocalAsset> var2) {
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         String var4 = ((LocalAsset)var3.next()).getPackage();
         var1.deleteAsset(var4);
      }

   }

   public int run(AssetStore var1) {
      Collection var2 = var1.getAssets();
      int var3 = var1.repairMappings();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         LocalAsset var5 = (LocalAsset)var4.next();
         if(!var5.isConsistent()) {
            Object[] var6 = new Object[1];
            String var7 = var5.getPackage();
            var6[0] = var7;
            FinskyLog.d("Detected an inconsistent LocalAsset. Deleting assets and reconstructing. PackageName [%s]", var6);
            this.mCheckinAssetStoreListener.suspend();
            this.deleteAll(var1, var2);
            Looper var8 = this.mContext.getMainLooper();
            Handler var9 = new Handler(var8);
            InconsistentAssetRemoverRule.1 var10 = new InconsistentAssetRemoverRule.1();
            var9.post(var10);
            int var12 = var2.size();
            var3 += var12;
            break;
         }
      }

      return var3;
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         ReconstructDatabaseService.forceReconstruct(InconsistentAssetRemoverRule.this.mContext);
      }
   }
}
