package com.google.android.finsky.utils;

import com.google.android.finsky.utils.NotificationListener;

public interface Notifier {

   void hideAllMessages(String var1);

   void setNotificationListener(NotificationListener var1);

   void showCacheFullMessage(String var1, String var2);

   void showDownloadErrorMessage(String var1, String var2);

   void showExternalStorageFull(String var1, String var2);

   void showExternalStorageMissing(String var1, String var2);

   void showInstallationFailureMessage(String var1, String var2, String var3);

   void showInstallingMessage(String var1, String var2, boolean var3);

   void showMaliciousAssetRemovedMessage(String var1, String var2);

   void showMessage(String var1, String var2, String var3);

   void showNormalAssetRemovedMessage(String var1, String var2);

   void showPurchaseErrorMessage(String var1, String var2, String var3, String var4);

   void showSuccessfulInstallMessage(String var1, String var2, boolean var3);
}
