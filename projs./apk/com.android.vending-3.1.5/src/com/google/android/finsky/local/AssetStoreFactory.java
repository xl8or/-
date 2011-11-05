package com.google.android.finsky.local;

import android.content.Context;
import com.google.android.finsky.download.obb.ObbFactory;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.MemoryAssetStoreImpl;
import com.google.android.finsky.local.PersistentAssetStoreImpl;
import com.google.android.finsky.utils.Utils;

public class AssetStoreFactory {

   public AssetStoreFactory() {}

   public AssetStore makeAssetStore(Context var1) {
      Utils.ensureOnMainThread();
      PersistentAssetStoreImpl var2 = new PersistentAssetStoreImpl(var1);
      MemoryAssetStoreImpl var3 = new MemoryAssetStoreImpl(var2);
      ObbFactory.initialize(var3);
      MemoryAssetStoreImpl var4 = var3.initialize();
      return var3;
   }
}
