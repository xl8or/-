package com.htc.android.mail.eassvc.core;

import android.content.Context;
import android.database.sqlite.SQLiteFullException;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.ConditionVariable;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.common.HttpClientFactory;
import com.htc.android.mail.eassvc.core.SyncException;
import com.htc.android.mail.eassvc.core.SyncListener;
import com.htc.android.mail.eassvc.core.SyncSource;
import com.htc.android.mail.eassvc.pim.EASLastSyncInfo;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.EASEventBroadcaster;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import org.apache.http.client.methods.HttpPost;

public abstract class BaseSyncSource implements SyncSource {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String EAS_SYNCSRC_CFG_COLLID = "CollID";
   private static final String EAS_SYNCSRC_CFG_ENABLED = "Enabled";
   private static final String EAS_SYNCSRC_CFG_FILE_ENCODING = "utf-8";
   private static final String EAS_SYNCSRC_CFG_FILE_FEATURE = "http://xmlpull.org/v1/doc/features.html#indent-output";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_ERROR_CODE = "LastSyncErrorCode";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_RESULT = "LastSyncResult";
   private static final String EAS_SYNCSRC_CFG_LAST_SYNC_TIME = "LastSyncTime";
   private static final String EAS_SYNCSRC_CFG_ROOT_SECTION = "Sync";
   private static final String EAS_SYNCSRC_CFG_SYNCKEY = "SyncKey";
   public static final int RETRY_TIMES = 2;
   private static final String TAG = "BaseSyncSource";
   public final Object LOCK;
   private boolean bDataChange;
   private boolean bIsRetry;
   protected SyncListener listener;
   protected ExchangeAccount mAccount;
   private int mCancelMode;
   protected String mCollID;
   Context mContext;
   protected boolean mEnabled;
   private AndroidHttpClient mHttpClient;
   private HttpPost mHttpPost;
   protected boolean mIsPause;
   protected boolean mIsRunning;
   private boolean mIsSyncCancel;
   protected EASLastSyncInfo mLastSyncInfo;
   protected ConditionVariable mPauseCondition;
   private double mProtocolVer;
   private int mRetryCount;
   protected String mSyncKey;
   File prefFile;
   Uri syncInfoURI;
   protected int type;


   public BaseSyncSource(Context var1, ExchangeAccount var2, int var3) {
      Object var4 = new Object();
      this.LOCK = var4;
      this.mHttpPost = null;
      this.mRetryCount = 0;
      this.bIsRetry = (boolean)0;
      this.mIsPause = (boolean)0;
      this.bDataChange = (boolean)0;
      this.mIsSyncCancel = (boolean)0;
      this.mCancelMode = 0;
      this.mPauseCondition = null;
      EASLastSyncInfo var5 = new EASLastSyncInfo();
      this.mLastSyncInfo = var5;
      this.mAccount = var2;
      this.mContext = var1;
      this.mEnabled = (boolean)0;
      this.mCollID = "";
      this.mSyncKey = "";
      this.mHttpClient = null;
      this.mLastSyncInfo.syncSrcType = var3;
      this.type = var3;
   }

   private void checkCancel() {
      if(this.mIsSyncCancel) {
         if(this.mCancelMode == 0) {
            throw new SyncException(600, "Sync be cancled by user");
         }
      }
   }

   private void checkCancel(int var1) {
      if(this.mIsSyncCancel) {
         int var2 = this.mCancelMode;
         if(var1 == var2) {
            String var3 = "Sync be cancled by user (mode=" + var1 + ")";
            throw new SyncException(600, var3);
         }
      }
   }

   private void checkPause() {
      if(this.isPause()) {
         EASEventBroadcaster.resendSyncStatus(this.mContext, this);
         this.pause();
         EASEventBroadcaster.resendSyncStatus(this.mContext, this);
      }
   }

   public void beginSync() throws SyncException {
      this.mIsRunning = (boolean)1;
      this.resetCancelFlag();
      SyncListener var1 = this.listener;
      long var2 = this.mAccount.accountId;
      var1.startSync(var2);
   }

   public void cancelSync() {
      if(DEBUG) {
         ExchangeAccount var1 = this.mAccount;
         StringBuilder var2 = (new StringBuilder()).append("cancelSync(): type=");
         int var3 = this.type;
         String var4 = var2.append(var3).toString();
         EASLog.d("BaseSyncSource", var1, var4);
      }

      this.setPause((boolean)0);
      BaseSyncSource.1 var5 = new BaseSyncSource.1();
      (new Thread(var5)).start();
      this.mIsSyncCancel = (boolean)1;
      this.mCancelMode = 0;
   }

   public void cancelSync(int var1) {
      if(DEBUG) {
         ExchangeAccount var2 = this.mAccount;
         StringBuilder var3 = (new StringBuilder()).append("cancelSync(): type=");
         int var4 = this.type;
         String var5 = var3.append(var4).append(", mode=").append(var1).toString();
         EASLog.d("BaseSyncSource", var2, var5);
      }

      if(var1 == 3) {
         this.mCancelMode = var1;
         this.mIsSyncCancel = (boolean)1;
      } else {
         this.setPause((boolean)0);
         this.cancelSync();
      }
   }

   public boolean deleteAccount() throws SQLiteFullException {
      if(DEBUG) {
         long var1 = this.mAccount.accountId;
         StringBuilder var3 = (new StringBuilder()).append("> deleteAccount()");
         int var4 = this.type;
         String var5 = var3.append(var4).toString();
         EASLog.d("BaseSyncSource", var1, var5);
      }

      boolean var6 = false;

      label21: {
         try {
            this.deletePIMAppData();
            this.removeSyncInfo();
         } catch (SQLiteFullException var12) {
            throw var12;
         } catch (Exception var13) {
            var13.printStackTrace();
            break label21;
         }

         var6 = true;
      }

      if(DEBUG) {
         long var7 = this.mAccount.accountId;
         StringBuilder var9 = (new StringBuilder()).append("< deleteAccount()");
         int var10 = this.type;
         String var11 = var9.append(var10).toString();
         EASLog.d("BaseSyncSource", var7, var11);
      }

      return var6;
   }

   public abstract void deletePIMAppData();

   public void endSync(int var1) throws SyncException {
      if(var1 != -1) {
         EASLastSyncInfo var2 = this.mLastSyncInfo;
         long var3 = System.currentTimeMillis();
         var2.lastSyncTime = var3;
         this.mLastSyncInfo.lastSyncResult = var1;
         if(var1 == 0) {
            this.mLastSyncInfo.lastSyncErrorCode = 0;
         }
      }

      try {
         this.saveData();
      } catch (Exception var16) {
         long var9 = this.mAccount.accountId;
         StringBuilder var11 = (new StringBuilder()).append("endSync(").append(var1).append(") ");
         int var12 = this.type;
         StringBuilder var13 = var11.append(var12).append(":");
         String var14 = var16.getMessage();
         String var15 = var13.append(var14).toString();
         EASLog.e("BaseSyncSource", var9, var15);
      }

      this.mIsRunning = (boolean)0;
      if(this.listener != null) {
         SyncListener var5 = this.listener;
         long var6 = this.mAccount.accountId;
         var5.endSync(var6, var1);
      }
   }

   public ExchangeAccount getAccount() {
      return this.mAccount;
   }

   public String getColID() {
      return this.mCollID;
   }

   public AndroidHttpClient getHttpClient() {
      synchronized(this){}

      AndroidHttpClient var1;
      try {
         if(this.mHttpClient == null) {
            this.resetHttpClient();
         }

         var1 = this.mHttpClient;
      } finally {
         ;
      }

      return var1;
   }

   public HttpPost getHttpPost() {
      synchronized(this){}

      HttpPost var1;
      try {
         var1 = this.mHttpPost;
      } finally {
         ;
      }

      return var1;
   }

   public int getLastSyncErrorCode() {
      return this.mLastSyncInfo.lastSyncErrorCode;
   }

   public EASLastSyncInfo getLastSyncInfo() {
      return this.mLastSyncInfo;
   }

   public int getLastSyncResult() {
      return this.mLastSyncInfo.lastSyncResult;
   }

   public long getLastSyncTime() {
      return this.mLastSyncInfo.lastSyncTime;
   }

   public SyncListener getListener() {
      return this.listener;
   }

   public Object getLock() {
      return this.LOCK;
   }

   public double getProtocolVer() {
      return this.mProtocolVer;
   }

   public int getRetryCount() {
      return this.mRetryCount;
   }

   public String getSyncKey() {
      return this.mSyncKey;
   }

   public int getType() {
      return this.type;
   }

   public boolean isCancelled() {
      return this.mIsSyncCancel;
   }

   public boolean isEnabled() {
      return this.mEnabled;
   }

   public boolean isPause() {
      return this.mIsPause;
   }

   public boolean isRunning() {
      return this.mIsRunning;
   }

   public void loadData() {
      // $FF: Couldn't be decompiled
   }

   protected void loadDataFromFile() {
      // $FF: Couldn't be decompiled
   }

   public boolean needRetry() {
      boolean var1;
      if(this.mRetryCount < 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void pause() {
      if(DEBUG) {
         long var1 = this.mAccount.accountId;
         StringBuilder var3 = (new StringBuilder()).append("pause(");
         int var4 = this.type;
         String var5 = var3.append(var4).append(")").toString();
         EASLog.d("BaseSyncSource", var1, var5);
      }

      if(this.mPauseCondition != null) {
         this.mPauseCondition.block();
      }
   }

   public void release() {
      synchronized(this){}

      try {
         if(DEBUG) {
            long var1 = this.mAccount.accountId;
            StringBuilder var3 = (new StringBuilder()).append("- release()");
            int var4 = this.type;
            String var5 = var3.append(var4).toString();
            EASLog.v("BaseSyncSource", var1, var5);
         }

         if(this.mHttpClient != null) {
            this.mHttpClient.close();
         }

         this.mHttpClient = null;
         if(this.mPauseCondition != null) {
            this.mPauseCondition.open();
         }
      } finally {
         ;
      }

   }

   public void releaseHttpClient() {
      synchronized(this){}

      try {
         if(this.mHttpClient != null) {
            this.mHttpClient.close();
         }

         this.mHttpClient = null;
      } finally {
         ;
      }

   }

   protected abstract void removeSyncInfo();

   public void resetCancelFlag() {
      this.mIsSyncCancel = (boolean)0;
      this.mCancelMode = 0;
   }

   public void resetHttpClient() {
      synchronized(this){}

      try {
         if(this.mHttpClient != null) {
            this.mHttpClient.close();
         }

         Context var1 = this.mContext;
         long var2 = this.mAccount.accountId;
         AndroidHttpClient var4 = HttpClientFactory.createHttpClient(var1, var2);
         this.mHttpClient = var4;
      } finally {
         ;
      }

   }

   public void resetRetry() {
      this.bIsRetry = (boolean)0;
      this.mRetryCount = 0;
   }

   protected void saveData() throws Exception {
      // $FF: Couldn't be decompiled
   }

   protected void saveDataToFile() throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void setCheckPoint() {
      this.checkPause();
      this.checkCancel();
   }

   public void setCheckPoint(int var1) {
      this.checkPause();
      this.checkCancel(var1);
   }

   public void setColID(String var1) throws Exception {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("- setColID(").append(var1).append(")");
         int var5 = this.type;
         String var6 = var4.append(var5).toString();
         EASLog.d("BaseSyncSource", var2, var6);
      }

      String var7 = this.mCollID;
      if(!EASSyncCommon.isStringEquals(var1, var7)) {
         this.mCollID = var1;
         this.saveData();
      }
   }

   public void setEnabled(boolean var1) {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("- setEnabled(").append(var1).append(")");
         int var5 = this.type;
         String var6 = var4.append(var5).toString();
         EASLog.d("BaseSyncSource", var2, var6);
      }

      if(this.mEnabled != var1) {
         this.mEnabled = var1;
         if(!var1) {
            this.mLastSyncInfo.lastSyncResult = 0;
         }

         try {
            this.saveData();
         } catch (Exception var15) {
            long var8 = this.mAccount.accountId;
            StringBuilder var10 = (new StringBuilder()).append("setEnabled(").append(var1).append(") ");
            int var11 = this.type;
            StringBuilder var12 = var10.append(var11).append(":");
            String var13 = var15.getMessage();
            String var14 = var12.append(var13).toString();
            EASLog.e("BaseSyncSource", var8, var14);
         }
      }
   }

   public void setHttpPost(HttpPost var1) {
      synchronized(this){}

      try {
         if(!this.bIsRetry) {
            this.mRetryCount = 0;
         }

         this.bIsRetry = (boolean)0;
         this.mHttpPost = var1;
      } finally {
         ;
      }

   }

   public void setLastSyncErrorCode(int var1) {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("- setLastSyncErrorCode()");
         int var5 = this.type;
         String var6 = var4.append(var5).toString();
         EASLog.d("BaseSyncSource", var2, var6);
      }

      if(this.mLastSyncInfo.lastSyncErrorCode != var1) {
         this.mLastSyncInfo.lastSyncErrorCode = var1;

         try {
            this.saveData();
         } catch (Exception var13) {
            long var8 = this.mAccount.accountId;
            StringBuilder var10 = (new StringBuilder()).append("setLastSyncErrorCode() ");
            String var11 = var13.getMessage();
            String var12 = var10.append(var11).toString();
            EASLog.e("BaseSyncSource", var8, var12);
         }
      }
   }

   public void setLastSyncResult(int var1) {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("- setLastSyncResult()");
         int var5 = this.type;
         String var6 = var4.append(var5).toString();
         EASLog.d("BaseSyncSource", var2, var6);
      }

      if(this.mLastSyncInfo.lastSyncResult != var1) {
         this.mLastSyncInfo.lastSyncResult = var1;

         try {
            this.saveData();
         } catch (Exception var13) {
            long var8 = this.mAccount.accountId;
            StringBuilder var10 = (new StringBuilder()).append("setLastSyncResult() ");
            String var11 = var13.getMessage();
            String var12 = var10.append(var11).toString();
            EASLog.e("BaseSyncSource", var8, var12);
         }
      }
   }

   public void setLastSyncTime(long var1) {
      if(DEBUG) {
         long var3 = this.mAccount.accountId;
         StringBuilder var5 = (new StringBuilder()).append("- setLastSyncTime()");
         int var6 = this.type;
         String var7 = var5.append(var6).toString();
         EASLog.d("BaseSyncSource", var3, var7);
      }

      if(this.mLastSyncInfo.lastSyncTime != var1) {
         this.mLastSyncInfo.lastSyncTime = var1;

         try {
            this.saveData();
         } catch (Exception var14) {
            long var9 = this.mAccount.accountId;
            StringBuilder var11 = (new StringBuilder()).append("setLastSyncTime() ");
            String var12 = var14.getMessage();
            String var13 = var11.append(var12).toString();
            EASLog.e("BaseSyncSource", var9, var13);
         }
      }
   }

   public void setListener(SyncListener var1) {
      this.listener = var1;
   }

   public void setPause(boolean var1) {
      if(DEBUG) {
         long var2 = this.mAccount.accountId;
         StringBuilder var4 = (new StringBuilder()).append("setPause(").append(var1).append(",");
         int var5 = this.type;
         String var6 = var4.append(var5).append(")").toString();
         EASLog.d("BaseSyncSource", var2, var6);
      }

      this.mIsPause = var1;
      if(this.mPauseCondition == null) {
         ConditionVariable var7 = new ConditionVariable((boolean)1);
         this.mPauseCondition = var7;
      }

      if(this.mIsPause) {
         this.mPauseCondition.close();
      } else {
         this.mPauseCondition.open();
      }
   }

   public void setProtocolVer(double var1) {
      this.mProtocolVer = var1;
   }

   public void setRetry() {
      this.bIsRetry = (boolean)1;
      int var1 = this.mRetryCount + 1;
      this.mRetryCount = var1;
   }

   public void setSyncKey(String var1) throws Exception {
      long var2 = this.mAccount.accountId;
      StringBuilder var4 = (new StringBuilder()).append("- setSyncKey() : from ");
      String var5 = this.mSyncKey;
      StringBuilder var6 = var4.append(var5).append(" to ").append(var1).append(", type=");
      int var7 = this.type;
      String var8 = var6.append(var7).toString();
      EASLog.d("BaseSyncSource", var2, var8);
      String var9 = this.mSyncKey;
      if(!EASSyncCommon.isStringEquals(var1, var9)) {
         this.mSyncKey = var1;
         this.saveData();
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         if(BaseSyncSource.this.mHttpPost != null) {
            try {
               BaseSyncSource.this.mHttpPost.abort();
            } catch (Exception var1) {
               var1.printStackTrace();
            }
         }

         if(BaseSyncSource.this.mHttpClient != null) {
            BaseSyncSource.this.resetHttpClient();
         }
      }
   }
}
