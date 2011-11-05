package com.google.android.finsky.local;

import android.net.Uri;
import com.google.android.finsky.download.obb.Obb;
import com.google.android.finsky.local.AssetState;
import com.google.android.finsky.local.AutoUpdateState;

public interface LocalAsset {

   void cleanupInstallFailure();

   String getAccount();

   String getAssetId();

   AutoUpdateState getAutoUpdateState();

   Uri getContentUri();

   long getDownloadPendingTime();

   long getDownloadTime();

   String getExternalReferrer();

   long getInstallTime();

   Obb getObb(boolean var1);

   String getPackage();

   Long getRefundPeriodEndTime();

   String getSignature();

   long getSize();

   String getSource();

   AssetState getState();

   long getUninstallTime();

   int getVersionCode();

   boolean hasEverBeenInstalled();

   boolean isConsistent();

   boolean isDownloadingOrInstalling();

   boolean isForwardLocked();

   boolean isInstallable();

   boolean isInstalled();

   boolean isTransient();

   boolean isUninstallable();

   boolean isUpdate();

   void resetDownloadPendingState();

   void resetInstalledState();

   void resetInstallingState();

   void resetUninstalledState();

   void setAccount(String var1);

   void setAutoUpdateState(AutoUpdateState var1);

   void setExternalReferrer(String var1);

   void setObb(boolean var1, Obb var2);

   void setRefundPeriodEndTime(Long var1);

   void setStateCancelPending();

   void setStateDownloadCancelled();

   void setStateDownloadDeclined();

   void setStateDownloadFailed();

   void setStateDownloadPending(long var1, int var3, String var4, String var5, String var6);

   void setStateDownloading(long var1, Uri var3, long var4, String var6, boolean var7, Long var8, Obb var9, Obb var10);

   void setStateInstallFailed();

   void setStateInstalled(long var1);

   void setStateInstalling();

   void setStateUninstallFailed();

   void setStateUninstalled(long var1);

   void setStateUninstalling();
}
