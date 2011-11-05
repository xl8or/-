package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AssetStore;
import com.google.android.finsky.local.StoredLocalAsset;
import com.google.android.finsky.local.StoredLocalAssetVersion;
import java.util.Collection;

interface MemoryAssetStore extends AssetStore {

   void deleteAssetVersion(StoredLocalAssetVersion var1);

   boolean deleteAssetVersion(String var1);

   Collection<StoredLocalAssetVersion> getAssetVersions(String var1);

   StoredLocalAssetVersion insertAssetVersion(String var1, int var2, String var3, AssetState var4, long var5, long var7, long var9, long var11, long var13, String var15, Uri var16, boolean var17, Long var18, String var19, String var20, Obb var21, Obb var22);

   void notifyAssetAdded(StoredLocalAsset var1);

   void notifyAssetChanged(StoredLocalAsset var1, AssetState var2);

   void notifyAssetDeleted(String var1);

   void notifyAssetVersionChanged(StoredLocalAssetVersion var1, AssetState var2);
}
