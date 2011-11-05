package com.google.android.finsky.download;

import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadListenerRecovery;
import com.google.android.finsky.download.DownloadQueueListener;
import com.google.android.finsky.download.DownloadUriUrlMap;
import com.google.android.finsky.download.InternalDownload;
import com.google.android.finsky.utils.Notifier;
import java.util.Collection;

public interface DownloadQueue {

   void add(InternalDownload var1);

   void addListener(DownloadQueueListener var1);

   void addRecoveredDownload(InternalDownload var1);

   void cancelAll();

   Download get(String var1);

   Collection<Download> getAll();

   Download getByPackageName(String var1);

   DownloadListenerRecovery.DownloadListenerFilter getDownloadListenerFilter();

   Notifier getNotificationHelper();

   DownloadUriUrlMap getUriUrlMap();

   void removeListener(DownloadQueueListener var1);
}
