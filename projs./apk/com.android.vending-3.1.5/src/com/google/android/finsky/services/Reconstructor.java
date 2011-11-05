package com.google.android.finsky.services;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.PackageInfoCache;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Reconstructor {

   private AssetStore mAssetStore;
   private List<VendingProtos.AssetIdentifierProto> mAssets;
   private PackageInfoCache mPackageInfoCache;


   public Reconstructor(PackageInfoCache var1, AssetStore var2, List<VendingProtos.AssetIdentifierProto> var3) {
      this.mPackageInfoCache = var1;
      this.mAssetStore = var2;
      this.mAssets = var3;
   }

   private void doRecordPackageInstalled(VendingProtos.AssetIdentifierProto var1, String var2) {
      String var3 = var1.getPackageName();
      int var4 = var1.getVersionCode();
      AssetStore var5 = this.mAssetStore;
      AutoUpdateState var6 = AutoUpdateState.DEFAULT;
      String var7 = var1.getAssetId();
      AssetState var8 = AssetState.INSTALLED;
      Obb var9 = ObbFactory.createEmpty((boolean)0, var3);
      Obb var10 = ObbFactory.createEmpty((boolean)1, var3);
      var5.insertAsset(var3, var6, var2, var4, var7, var8, 0L, 0L, 0L, 0L, 0L, (String)null, (Uri)null, (boolean)0, (Long)null, (String)null, (String)null, var9, var10);
   }

   private VendingProtos.AssetIdentifierProto findMatchingProto(int var1, List<VendingProtos.AssetIdentifierProto> var2) {
      Iterator var3 = var2.iterator();

      VendingProtos.AssetIdentifierProto var4;
      do {
         if(!var3.hasNext()) {
            var4 = null;
            break;
         }

         var4 = (VendingProtos.AssetIdentifierProto)var3.next();
      } while(var4.getVersionCode() != var1);

      return var4;
   }

   private Map<String, List<VendingProtos.AssetIdentifierProto>> protosGroupedByPackage() {
      HashMap var1 = Maps.newHashMap();

      VendingProtos.AssetIdentifierProto var3;
      Object var5;
      for(Iterator var2 = this.mAssets.iterator(); var2.hasNext(); ((List)var5).add(var3)) {
         var3 = (VendingProtos.AssetIdentifierProto)var2.next();
         String var4 = var3.getPackageName();
         var5 = (List)var1.get(var4);
         if(var5 == null) {
            var5 = Lists.newArrayList();
            var1.put(var4, var5);
         }
      }

      return var1;
   }

   private int recordPackageInstalled(VendingProtos.AssetIdentifierProto var1, int var2, String var3) {
      byte var4 = 1;
      String var5 = var1.getPackageName();
      LocalAsset var6 = this.mAssetStore.getAsset(var5);
      if(var6 == null) {
         this.doRecordPackageInstalled(var1, var3);
      } else {
         String var7 = var6.getAccount();
         if(!var3.equals(var7)) {
            AssetState var8 = var6.getState();
            AssetState var9 = AssetState.INSTALLED;
            if(var8 == var9) {
               var4 = 0;
               return var4;
            }
         }

         if(var6.getState().isTransient()) {
            StringBuilder var10 = (new StringBuilder()).append("About to mark asset ").append(var5).append(" as ").append("installed in local store during a reconstruct, but turns out its state is ").append("currently transient: ");
            String var11 = var6.getState().name();
            String var12 = var10.append(var11).append(". Skipping asset! ").toString();
            Object[] var13 = new Object[0];
            FinskyLog.e(var12, var13);
            var4 = 0;
         } else {
            AssetState var14 = var6.getState();
            AssetState var15 = AssetState.INSTALLED;
            if(var14 == var15 && var6.getVersionCode() != var2) {
               StringBuilder var16 = (new StringBuilder()).append("During reconstruct for account ").append(var3).append(", seeing asset ").append(var5).append(" with version ").append(var2).append(" from package manager, but the one in local asset store is").append(" version ");
               int var17 = var6.getVersionCode();
               String var18 = var16.append(var17).toString();
               Object[] var19 = new Object[0];
               FinskyLog.e(var18, var19);
            }

            AssetState var20 = var6.getState();
            AssetState var21 = AssetState.INSTALLED;
            if(var20 == var21 && var6.getVersionCode() == var2) {
               var4 = 0;
            } else {
               this.mAssetStore.deleteAsset(var5);
               this.doRecordPackageInstalled(var1, var3);
            }
         }
      }

      return var4;
   }

   public int reconstruct(String var1) {
      int var2 = 0;
      Map var3 = this.protosGroupedByPackage();
      Iterator var4 = var3.keySet().iterator();

      while(var4.hasNext()) {
         String var5 = (String)var4.next();
         int var6 = this.mPackageInfoCache.getPackageVersion(var5);
         if(var6 == -1) {
            this.mAssetStore.deleteAsset(var5);
            ++var2;
         } else {
            List var8 = (List)var3.get(var5);
            VendingProtos.AssetIdentifierProto var9 = this.findMatchingProto(var6, var8);
            if(var9 == null) {
               this.mAssetStore.deleteAsset(var5);
               ++var2;
            } else {
               int var11 = this.recordPackageInstalled(var9, var6, var1);
               var2 += var11;
            }
         }
      }

      return var2;
   }
}
