package com.google.android.finsky.local.checker;

import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.utils.FinskyLog;

public class SanityChecker {

   private final AssetStore mAssetStore;
   private final SanityChecker.SanityCheckRule[] mRules;


   public SanityChecker(AssetStore var1, SanityChecker.SanityCheckRule ... var2) {
      this.mAssetStore = var1;
      this.mRules = var2;
   }

   public void run() {
      long var1 = System.currentTimeMillis();
      int var3 = 0;
      SanityChecker.SanityCheckRule[] var4 = this.mRules;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SanityChecker.SanityCheckRule var7 = var4[var6];
         AssetStore var8 = this.mAssetStore;
         int var9 = var7.run(var8);
         var3 += var9;
      }

      Object[] var10 = new Object[2];
      Integer var11 = Integer.valueOf(var3);
      var10[0] = var11;
      Long var12 = Long.valueOf(System.currentTimeMillis() - var1);
      var10[1] = var12;
      FinskyLog.d("# LocalAssets corrected : %d\nSanity check took : %d ms", var10);
   }

   public interface SanityCheckRule {

      int run(AssetStore var1);
   }
}
