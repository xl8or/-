package com.google.android.vending.model;

import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.vending.model.Asset;
import com.google.android.vending.remoting.protos.VendingProtos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AssetList {

   private final List<Asset> mAssets;


   public AssetList() {
      ArrayList var1 = Lists.newArrayList();
      this.mAssets = var1;
   }

   public AssetList(VendingProtos.AssetsResponseProto var1) {
      this();
      if(var1 != null && var1.getAssetList() != null) {
         Iterator var3 = var1.getAssetList().iterator();

         while(var3.hasNext()) {
            VendingProtos.ExternalAssetProto var4 = (VendingProtos.ExternalAssetProto)var3.next();
            Asset var5 = new Asset(var4);
            this.addAsset(var5);
         }

      } else {
         Object[] var2 = new Object[0];
         FinskyLog.e("Received a null AssetsResponseProto or asset list.", var2);
      }
   }

   public void addAsset(Asset var1) {
      this.mAssets.add(var1);
   }

   public List<Asset> getAssets() {
      return this.mAssets;
   }
}
