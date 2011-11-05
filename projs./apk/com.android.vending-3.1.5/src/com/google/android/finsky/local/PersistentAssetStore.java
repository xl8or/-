package com.google.android.finsky.local;

import com.google.android.finsky.local.LocalAssetRecord;
import com.google.android.finsky.local.LocalAssetVersionRecord;
import java.util.List;

public interface PersistentAssetStore {

   void deleteAsset(String var1);

   void deleteAssetVersion(String var1);

   List<LocalAssetRecord> getAllAssets();

   List<LocalAssetVersionRecord> getAllVersions();

   void insertAsset(LocalAssetRecord var1);

   void insertAssetVersion(LocalAssetVersionRecord var1);
}
