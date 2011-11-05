package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.ObbState;
import com.google.android.finsky.local.AssetState;

public interface LocalAssetVersion {

   String getAssetId();

   Uri getContentUri();

   long getDownloadPendingTime();

   long getDownloadTime();

   long getInstallTime();

   ObbState getMainObbState();

   String getPackageName();

   ObbState getPatchObbState();

   String getReferrer();

   Long getRefundPeriodEndTime();

   String getSignature();

   long getSize();

   String getSource();

   AssetState getState();

   long getUninstallTime();

   int getVersionCode();

   boolean isForwardLocked();
}
