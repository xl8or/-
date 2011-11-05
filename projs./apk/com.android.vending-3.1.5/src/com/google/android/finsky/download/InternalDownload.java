package com.google.android.finsky.download;

import android.net.Uri;
import com.google.android.finsky.download.Download;
import com.google.android.finsky.download.DownloadManager;
import com.google.android.finsky.download.DownloadProgress;
import com.google.android.finsky.download.DownloadRequest;
import java.util.List;

public interface InternalDownload extends Download {

   DownloadRequest createDownloadRequest(String var1, String var2);

   Uri getRequestedDestination();

   long getSize();

   Download.DownloadState getState();

   List<InternalDownload> getWrappedObbDownloads();

   Uri internalGetContentUri();

   void onNotificationClicked();

   void setContentUri(Uri var1);

   void setDownloadManager(DownloadManager var1);

   void setHttpStatus(int var1);

   void setState(Download.DownloadState var1);

   void updateDownloadProgress(DownloadProgress var1);
}
