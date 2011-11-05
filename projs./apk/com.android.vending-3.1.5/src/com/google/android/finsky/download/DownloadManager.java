package com.google.android.finsky.download;

import android.database.Cursor;
import android.net.Uri;
import com.google.android.finsky.download.DownloadRequest;
import com.google.android.finsky.utils.ParameterizedRunnable;

public interface DownloadManager {

   void enqueue(DownloadRequest var1, ParameterizedRunnable<Uri> var2);

   Cursor queryAllDownloads();

   Cursor queryStatus(Uri var1);

   void remove(Uri var1);

   void removeAll();
}
