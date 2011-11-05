package com.htc.android.mail.eassvc.core;

import android.net.http.AndroidHttpClient;
import com.htc.android.mail.eassvc.core.SyncException;
import com.htc.android.mail.eassvc.core.SyncListener;
import com.htc.android.mail.eassvc.pim.EASLastSyncInfo;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import org.apache.http.client.methods.HttpPost;

public interface SyncSource {

   void beginSync() throws SyncException;

   void cancelSync() throws SyncException;

   boolean deleteAccount();

   void deletePIMAppData();

   void endSync(int var1) throws SyncException;

   ExchangeAccount getAccount();

   String getColID();

   AndroidHttpClient getHttpClient();

   HttpPost getHttpPost();

   int getLastSyncErrorCode();

   EASLastSyncInfo getLastSyncInfo();

   int getLastSyncResult();

   long getLastSyncTime();

   SyncListener getListener();

   Object getLock();

   int getRetryCount();

   String getSyncKey();

   int getType();

   boolean isCancelled();

   boolean isEnabled();

   boolean isPause();

   boolean isRunning();

   boolean needRetry();

   void pause();

   void release();

   void releaseHttpClient();

   void resetCancelFlag();

   void resetHttpClient();

   void setCheckPoint();

   void setColID(String var1) throws Exception;

   void setEnabled(boolean var1);

   void setHttpPost(HttpPost var1);

   void setLastSyncErrorCode(int var1);

   void setLastSyncResult(int var1);

   void setListener(SyncListener var1);

   void setPause(boolean var1);

   void setRetry();

   void setSyncKey(String var1) throws Exception;
}
