package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AutoUpdateState;
import com.google.android.finsky.local.LocalAsset;
import com.google.android.finsky.local.LocalAssetVersion;
import java.util.Collection;
import java.util.List;

public interface AssetStore {

   void addListener(AssetStore.LocalAssetChangeListener var1);

   boolean deleteAsset(String var1);

   LocalAsset getAsset(Uri var1);

   LocalAsset getAsset(String var1);

   LocalAsset getAssetById(String var1);

   Collection<LocalAsset> getAssets();

   Collection<LocalAsset> getAssetsByAccount(String var1);

   Collection<LocalAsset> getAssetsByState(AssetState var1);

   List<LocalAssetVersion> getCompleteVersionHistory(String var1);

   LocalAsset insertAsset(String var1, AutoUpdateState var2, String var3, int var4, String var5, AssetState var6, long var7, long var9, long var11, long var13, long var15, String var17, Uri var18, boolean var19, Long var20, String var21, String var22, Obb var23, Obb var24);

   LocalAsset insertAsset(String var1, AutoUpdateState var2, String var3, int var4, String var5, String var6, String var7, long var8);

   void removeListener(AssetStore.LocalAssetChangeListener var1);

   int repairMappings();

   public interface LocalAssetChangeListener {

      void onAssetAdded(LocalAsset var1);

      void onAssetChanged(LocalAsset var1, AssetState var2);

      void onAssetDeleted(String var1);
   }
}
