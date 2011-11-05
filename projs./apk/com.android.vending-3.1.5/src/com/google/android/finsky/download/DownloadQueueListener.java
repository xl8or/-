package com.google.android.finsky.download;

import com.google.android.finsky.download.Download;

public interface DownloadQueueListener {

   void onAdd(Download var1);

   void onUpdate();
}
