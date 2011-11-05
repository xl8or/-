package com.htc.android.mail.easdp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.AndroidHttpClient;
import android.os.Process;
import android.text.TextUtils;
import com.htc.android.mail.easdp.Common;
import com.htc.android.mail.easdp.EASDirectpush;
import com.htc.android.mail.easdp.util.ConnectCallback;
import com.htc.android.mail.easdp.util.HttpClientFactory;
import com.htc.android.mail.eassvc.core.WbxmlSerializer;
import com.htc.android.mail.eassvc.pim.ExchangeAccount;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.android.mail.eassvc.util.LockUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import org.apache.harmony.luni.util.Base64;
import org.apache.http.client.methods.HttpPost;
import org.xmlpull.v1.XmlSerializer;

public class DirectPushReceiver {

   public static final String ACTION_DP_CONNECT_TIMEOUT_DEF = "com.htc.eas.directpush.connect_timeout.action";
   public static final String ACTION_DP_HB_INTERVAL_DEF = "com.htc.eas.directpush.heartbeat.interval.action";
   public static final String ACTION_DP_KILL_SELF_CHECK_DEF = "com.htc.eas.directpush.kill_self_check.action";
   private static final boolean DEBUG = true;
   private static final long DPR_ABORT_TIMEOUT = 10000L;
   private static final long DPR_CONNECTION_RETRY_BOUND = 120000L;
   private static final long DPR_KILL_SELF_TIMEOUT = 60000L;
   private static final int DPR_PING_FAIL_TRY_INTVAL = 180000;
   public static final String EXTRA_DIRECTPUSH_TYPE = "com.htc.eas.directpush.type.extra";
   private static final int MAXIMUM_HEARTBEAT_INTERVAL = 1800;
   private static final int MINUTE_HEARTBEAT_INTERVAL = 600;
   public static final String TAG = "EAS_DPReceiver";
   private static AndroidHttpClient mHttpClient;
   private static Object mHttpPostLock = new Object();
   String ACTION_DP_CONNECT_TIMEOUT;
   String ACTION_DP_HB_INTERVAL;
   String ACTION_DP_KILL_SELF_CHECK;
   private LockUtil.EASWakeLock abortWakeLock;
   DirectPushReceiver.ResponseStatus dpRespStatus;
   private ExchangeAccount mAccount;
   boolean mCancelByAbort;
   private ArrayList<String> mChangedCollections;
   private ConnectCallback mConnCallback;
   private Context mContext;
   boolean mDoingAbortHttp;
   private boolean mForceShutdown;
   private int mHBIntervalDynamicGap;
   public int mHeartBeatInterval;
   PendingIntent mHeartbeatIntervalIntent;
   private HttpPost mHttpPost;
   IntentFilter mIintentFilter;
   PendingIntent mKillSelfIntent;
   private String mLoginCredential;
   PendingIntent mNetworkTimeoutIntent;
   private BroadcastReceiver mReceiver;
   private Object networkLock;
   private LockUtil.EASWakeLock networkWakeLock;


   public DirectPushReceiver(ExchangeAccount var1, Context var2) {
      ArrayList var3 = new ArrayList();
      this.mChangedCollections = var3;
      this.mHBIntervalDynamicGap = 240;
      this.mHeartBeatInterval = 1200;
      this.mCancelByAbort = (boolean)0;
      this.mDoingAbortHttp = (boolean)0;
      DirectPushReceiver.ResponseStatus var4 = new DirectPushReceiver.ResponseStatus();
      this.dpRespStatus = var4;
      DirectPushReceiver.2 var5 = new DirectPushReceiver.2();
      this.mConnCallback = var5;
      Object var6 = new Object();
      this.networkLock = var6;
      DirectPushReceiver.3 var7 = new DirectPushReceiver.3();
      this.mReceiver = var7;
      this.mContext = var2;
      this.init(var1);
      StringBuilder var8 = (new StringBuilder()).append("com.htc.eas.directpush.connect_timeout.action");
      long var9 = this.mAccount.accountId;
      String var11 = var8.append(var9).toString();
      this.ACTION_DP_CONNECT_TIMEOUT = var11;
      StringBuilder var12 = (new StringBuilder()).append("com.htc.eas.directpush.heartbeat.interval.action");
      long var13 = this.mAccount.accountId;
      String var15 = var12.append(var13).toString();
      this.ACTION_DP_HB_INTERVAL = var15;
      StringBuilder var16 = (new StringBuilder()).append("com.htc.eas.directpush.kill_self_check.action");
      long var17 = this.mAccount.accountId;
      String var19 = var16.append(var17).toString();
      this.ACTION_DP_KILL_SELF_CHECK = var19;
      IntentFilter var20 = new IntentFilter();
      this.mIintentFilter = var20;
      IntentFilter var21 = this.mIintentFilter;
      String var22 = this.ACTION_DP_CONNECT_TIMEOUT;
      var21.addAction(var22);
      IntentFilter var23 = this.mIintentFilter;
      String var24 = this.ACTION_DP_HB_INTERVAL;
      var23.addAction(var24);
      IntentFilter var25 = this.mIintentFilter;
      String var26 = this.ACTION_DP_KILL_SELF_CHECK;
      var25.addAction(var26);
      String var27 = this.ACTION_DP_CONNECT_TIMEOUT;
      Intent var28 = new Intent(var27);
      PendingIntent var29 = PendingIntent.getBroadcast(this.mContext, 0, var28, 134217728);
      this.mNetworkTimeoutIntent = var29;
      String var30 = this.ACTION_DP_HB_INTERVAL;
      Intent var31 = new Intent(var30);
      PendingIntent var32 = PendingIntent.getBroadcast(this.mContext, 0, var31, 134217728);
      this.mHeartbeatIntervalIntent = var32;
      String var33 = this.ACTION_DP_KILL_SELF_CHECK;
      Intent var34 = new Intent(var33);
      PendingIntent var35 = PendingIntent.getBroadcast(this.mContext, 0, var34, 134217728);
      this.mKillSelfIntent = var35;
   }

   private void cancelRunning() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      PendingIntent var2 = this.mHeartbeatIntervalIntent;
      var1.cancel(var2);
      DirectPushReceiver.1 var3 = new DirectPushReceiver.1();
      (new Thread(var3)).start();
   }

   private void cleanHeartbeatTimeoutChecker() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      PendingIntent var2 = this.mHeartbeatIntervalIntent;
      var1.cancel(var2);
   }

   private void cleanKillSelfTImerChecker() {
      this.mDoingAbortHttp = (boolean)0;
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      PendingIntent var2 = this.mKillSelfIntent;
      var1.cancel(var2);
   }

   private HttpPost createHttpPost(String var1, String var2) throws URISyntaxException {
      StringBuilder var3 = new StringBuilder();
      String var4;
      if(this.mAccount.requireSSL == 1) {
         var4 = "https";
      } else {
         var4 = "http";
      }

      StringBuilder var5 = var3.append(var4).append("://");
      String var6 = this.mAccount.serverName;
      StringBuilder var7 = var5.append(var6).append("/").append("Microsoft-Server-ActiveSync?").append("Cmd=").append(var1).append("&").append("User=");
      String var8 = this.mAccount.safeUserName;
      StringBuilder var9 = var7.append(var8).append("&").append("DeviceId=");
      String var10 = this.mAccount.deviceID;
      StringBuilder var11 = var9.append(var10).append("&").append("DeviceType=");
      String var12 = this.mAccount.deviceType;
      String var13 = var11.append(var12).toString();
      HttpPost var14 = new HttpPost(var13);
      var14.addHeader("Content-Type", "application/vnd.ms-sync.wbxml");
      var14.addHeader("MS-ASProtocolVersion", var2);
      StringBuilder var15 = (new StringBuilder()).append("Basic ");
      String var16 = this.mLoginCredential;
      String var17 = var15.append(var16).toString();
      var14.addHeader("Authorization", var17);
      return var14;
   }

   private byte[] createPingWBXMLOutput(EASDirectpush.DirectpushItem[] var1) throws IOException {
      if(this.mHeartBeatInterval <= 0) {
         ExchangeAccount var2 = this.mAccount;
         StringBuilder var3 = (new StringBuilder()).append(" mHeartBeatInterval = ");
         int var4 = this.mHeartBeatInterval;
         String var5 = var3.append(var4).append(", Get initial heartbeat.").toString();
         EASLog.d("EAS_DPReceiver", var2, var5);
         this.mHeartBeatInterval = 1200;
      }

      ExchangeAccount var6 = this.mAccount;
      StringBuilder var7 = (new StringBuilder()).append(" (use heartbeat) ");
      int var8 = this.mHeartBeatInterval;
      String var9 = var7.append(var8).toString();
      EASLog.d("EAS_DPReceiver", var6, var9);
      String var10 = null;
      ByteArrayOutputStream var11 = new ByteArrayOutputStream();
      WbxmlSerializer var12 = new WbxmlSerializer();
      String[] var13 = Common.EAS_PING_TBL;
      var12.setTagTable(13, var13);
      var12.setOutput(var11, (String)null);
      var12.startDocument("UTF-8", (Boolean)null);
      XmlSerializer var14 = var12.startTag((String)null, "Ping");
      XmlSerializer var15 = var12.startTag((String)null, "HeartbeatInterval");
      String var16 = String.valueOf(this.mHeartBeatInterval);
      var12.text(var16);
      XmlSerializer var18 = var12.endTag((String)null, "HeartbeatInterval");
      XmlSerializer var19 = var12.startTag((String)null, "Folders");
      int var20 = 0;

      while(true) {
         int var21 = var1.length;
         if(var20 >= var21) {
            XmlSerializer var36 = var12.endTag((String)null, "Folders");
            XmlSerializer var37 = var12.endTag((String)null, "Ping");
            var12.endDocument();
            var12.flush();
            ExchangeAccount var38 = this.mAccount;
            String var39 = "startPing listen: " + var10;
            EASLog.d("EAS_DPReceiver", var38, var39);
            return var11.toByteArray();
         }

         EASDirectpush.DirectpushItem var22 = var1[var20];
         XmlSerializer var23 = var12.startTag((String)null, "Folder");
         XmlSerializer var24 = var12.startTag((String)null, "Id");
         String var25 = var22.collectionId;
         var12.text(var25);
         XmlSerializer var27 = var12.endTag((String)null, "Id");
         XmlSerializer var28 = var12.startTag((String)null, "Class");
         if(var22.syncSourceType == 1) {
            XmlSerializer var29 = var12.text("Contacts");
         } else if(var22.syncSourceType == 2) {
            XmlSerializer var32 = var12.text("Calendar");
         } else if(var22.syncSourceType == 3) {
            XmlSerializer var33 = var12.text("Email");
         }

         XmlSerializer var30 = var12.endTag((String)null, "Class");
         XmlSerializer var31 = var12.endTag((String)null, "Folder");
         if(var10 == null) {
            var10 = var22.collectionId;
         } else {
            StringBuilder var34 = (new StringBuilder()).append(var10).append(",");
            String var35 = var22.collectionId;
            var10 = var34.append(var35).toString();
         }

         ++var20;
      }
   }

   private void setConnectFinish() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      PendingIntent var2 = this.mNetworkTimeoutIntent;
      var1.cancel(var2);
      this.setNetworkLock(var1);
   }

   private void setConnectStart() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      long var2 = (new Date()).getTime();
      long var4 = 30000L + var2;
      PendingIntent var6 = this.mNetworkTimeoutIntent;
      var1.set(0, var4, var6);
   }

   private void setHeartbeatTimeoutChecker() {
      long var1 = (new Date()).getTime();
      AlarmManager var3 = (AlarmManager)this.mContext.getSystemService("alarm");
      long var4 = (long)((this.mHeartBeatInterval + 30) * 1000) + var1;
      PendingIntent var6 = this.mHeartbeatIntervalIntent;
      var3.set(0, var4, var6);
   }

   private void setKillSelfTimerChecker() {
      this.mDoingAbortHttp = (boolean)1;
      long var1 = (new Date()).getTime();
      AlarmManager var3 = (AlarmManager)this.mContext.getSystemService("alarm");
      long var4 = 60000L + var1;
      PendingIntent var6 = this.mKillSelfIntent;
      var3.set(0, var4, var6);
   }

   private void setNetworkLock() {
      AlarmManager var1 = (AlarmManager)this.mContext.getSystemService("alarm");
      this.setNetworkLock(var1);
   }

   private void setNetworkLock(AlarmManager var1) {
      ExchangeAccount var2 = this.mAccount;
      EASLog.d("EAS_DPReceiver", var2, "setNetworkLock()");
      if(LockUtil.isPowerLockHeld(this.networkWakeLock)) {
         LockUtil.releasePowerLock(this.networkWakeLock);
      }

      LockUtil.EASWakeLock var3 = LockUtil.acquirePowerLockTimeout(this.mContext, "NetworkLock", 1000L);
      this.networkWakeLock = var3;
   }

   public AndroidHttpClient getHttpClient() {
      if(mHttpClient == null) {
         String var1 = Common.EAS_HTTP_AGENT;
         long var2 = this.mAccount.accountId;
         ConnectCallback var4 = this.mConnCallback;
         mHttpClient = HttpClientFactory.createHttpClient(var1, var2, var4);
      }

      return mHttpClient;
   }

   public void init(ExchangeAccount var1) {
      int var2 = var1.heartBeatInterval;
      this.mHeartBeatInterval = var2;
      this.mAccount = var1;
      if(TextUtils.isEmpty(this.mAccount.domainName)) {
         StringBuilder var3 = new StringBuilder();
         String var4 = this.mAccount.userName;
         StringBuilder var5 = var3.append(var4).append(":");
         String var6 = this.mAccount.password;
         String var7 = var5.append(var6).toString();
         this.mLoginCredential = var7;
      } else {
         StringBuilder var11 = new StringBuilder();
         String var12 = this.mAccount.domainName;
         StringBuilder var13 = var11.append(var12).append("\\");
         String var14 = this.mAccount.userName;
         StringBuilder var15 = var13.append(var14).append(":");
         String var16 = this.mAccount.password;
         String var17 = var15.append(var16).toString();
         this.mLoginCredential = var17;
      }

      try {
         byte[] var8 = this.mLoginCredential.getBytes();
         Charset var9 = Charset.forNameUEE("UTF-8");
         String var10 = Base64.encode(var8, var9);
         this.mLoginCredential = var10;
      } catch (UnsupportedEncodingException var19) {
         ;
      }
   }

   public boolean isCancelByAbort() {
      return this.mCancelByAbort;
   }

   public boolean isForceShutdown() {
      return this.mForceShutdown;
   }

   public void release() {
      ExchangeAccount var1 = this.mAccount;
      EASLog.d("EAS_DPReceiver", var1, "release()");
      this.cancelRunning();
   }

   public void releaseHttpClient() {
      if(mHttpClient != null) {
         mHttpClient.close();
      }

      mHttpClient = null;
   }

   public void reset() {
      this.mForceShutdown = (boolean)0;
      this.mCancelByAbort = (boolean)0;
   }

   public void resetHttpClient() {
      if(mHttpClient != null) {
         mHttpClient.close();
      }

      String var1 = Common.EAS_HTTP_AGENT;
      long var2 = this.mAccount.accountId;
      ConnectCallback var4 = this.mConnCallback;
      mHttpClient = HttpClientFactory.createHttpClient(var1, var2, var4);
   }

   public int startPing(EASDirectpush.DirectpushItem[] param1, ArrayList<String> param2, LockUtil.EASWakeLock param3) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public void stopPing() {
      ExchangeAccount var1 = this.mAccount;
      EASLog.d("EAS_DPReceiver", var1, "Stop DirectPush.");
      this.mForceShutdown = (boolean)1;
      this.cancelRunning();
      if(mHttpClient != null) {
         mHttpClient.close();
      }

      mHttpClient = null;
   }

   class 2 implements ConnectCallback {

      2() {}

      public void onConnectFinish() {
         ExchangeAccount var1 = DirectPushReceiver.this.mAccount;
         EASLog.d("EAS_DPReceiver", var1, "onConnectFinish()");
         DirectPushReceiver.this.setConnectFinish();
      }

      public void onConnectStart() {
         ExchangeAccount var1 = DirectPushReceiver.this.mAccount;
         EASLog.d("EAS_DPReceiver", var1, "onConnectStart()");
         DirectPushReceiver.this.setConnectStart();
      }
   }

   class ResponseStatus {

      static final int INIT = 0;
      static final int NO_RESPONSE = 2;
      static final int OK = 1;
      public int currentStatus = 0;
      public int prevStatus = 0;


      ResponseStatus() {}

      public void addStatus(int var1) {
         int var2 = this.currentStatus;
         this.prevStatus = var2;
         this.currentStatus = var1;
      }
   }

   class 3 extends BroadcastReceiver {

      3() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         ExchangeAccount var4 = DirectPushReceiver.this.mAccount;
         String var5 = "> onReceive, intent: " + var3;
         EASLog.d("EAS_DPReceiver", var4, var5);
         String var6 = DirectPushReceiver.this.ACTION_DP_HB_INTERVAL;
         if(var3.equals(var6)) {
            if(DirectPushReceiver.this.abortWakeLock != null) {
               LockUtil.releasePowerLock(DirectPushReceiver.this.abortWakeLock);
               LockUtil.EASWakeLock var7 = DirectPushReceiver.this.abortWakeLock = null;
            }

            DirectPushReceiver var8 = DirectPushReceiver.this;
            LockUtil.EASWakeLock var9 = LockUtil.acquirePowerLockTimeout(DirectPushReceiver.this.mContext, "DP-Abort", 10000L);
            var8.abortWakeLock = var9;
            DirectPushReceiver.this.dpRespStatus.addStatus(2);
            DirectPushReceiver var11 = DirectPushReceiver.this;
            int var12 = DirectPushReceiver.this.mHeartBeatInterval;
            int var13 = DirectPushReceiver.this.mHBIntervalDynamicGap;
            int var14 = var12 - var13;
            var11.mHeartBeatInterval = var14;
            if(DirectPushReceiver.this.mHeartBeatInterval < 600) {
               DirectPushReceiver.this.mHeartBeatInterval = 600;
            }

            ExchangeAccount var15 = DirectPushReceiver.this.mAccount;
            StringBuilder var16 = (new StringBuilder()).append(" mHeartBeatInterval = ");
            int var17 = DirectPushReceiver.this.mHeartBeatInterval;
            String var18 = var16.append(var17).toString();
            EASLog.d("EAS_DPReceiver", var15, var18);
            DirectPushReceiver.3.1 var19 = new DirectPushReceiver.3.1();
            (new Thread(var19)).start();
         } else {
            String var21 = DirectPushReceiver.this.ACTION_DP_CONNECT_TIMEOUT;
            if(var3.equals(var21)) {
               DirectPushReceiver.3.2 var22 = new DirectPushReceiver.3.2();
               (new Thread(var22)).start();
            } else {
               String var23 = DirectPushReceiver.this.ACTION_DP_KILL_SELF_CHECK;
               if(var3.equals(var23) && DirectPushReceiver.this.mDoingAbortHttp) {
                  ExchangeAccount var24 = DirectPushReceiver.this.mAccount;
                  EASLog.e("EAS_DPReceiver", var24, "Abort http fail! kill self to restart directpush.");
                  Process.killProcess(Process.myPid());
               }
            }
         }

         ExchangeAccount var20 = DirectPushReceiver.this.mAccount;
         EASLog.d("EAS_DPReceiver", var20, "< onReceive");
      }

      class 2 implements Runnable {

         2() {}

         public void run() {
            Object var1 = DirectPushReceiver.mHttpPostLock;
            synchronized(var1) {
               if(DirectPushReceiver.this.mHttpPost != null) {
                  ExchangeAccount var2 = DirectPushReceiver.this.mAccount;
                  EASLog.d("EAS_DPReceiver", var2, "connect timeout- abort httpPost");
                  DirectPushReceiver.this.mHttpPost.abort();
                  HttpPost var3 = DirectPushReceiver.this.mHttpPost = null;
                  DirectPushReceiver.this.setKillSelfTimerChecker();
                  DirectPushReceiver.this.mCancelByAbort = (boolean)1;
                  ExchangeAccount var4 = DirectPushReceiver.this.mAccount;
                  EASLog.d("EAS_DPReceiver", var4, "connect timeout- abort httpPost done.");
               }

            }
         }
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            Object var1 = DirectPushReceiver.mHttpPostLock;
            synchronized(var1) {
               if(DirectPushReceiver.this.mHttpPost != null) {
                  ExchangeAccount var2 = DirectPushReceiver.this.mAccount;
                  EASLog.d("EAS_DPReceiver", var2, "heartbeat- abort httpPost");
                  DirectPushReceiver.this.mHttpPost.abort();
                  HttpPost var3 = DirectPushReceiver.this.mHttpPost = null;
                  DirectPushReceiver.this.setKillSelfTimerChecker();
                  DirectPushReceiver.this.mCancelByAbort = (boolean)1;
                  ExchangeAccount var4 = DirectPushReceiver.this.mAccount;
                  EASLog.d("EAS_DPReceiver", var4, "heartbeat- abort httpPost done.");
               }

            }
         }
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         Object var1 = DirectPushReceiver.mHttpPostLock;
         synchronized(var1) {
            if(DirectPushReceiver.this.mHttpPost != null) {
               ExchangeAccount var2 = DirectPushReceiver.this.mAccount;
               EASLog.d("EAS_DPReceiver", var2, "cancelRunning- abort httpPost");

               try {
                  DirectPushReceiver.this.mHttpPost.abort();
               } catch (Exception var6) {
                  var6.printStackTrace();
               }

               HttpPost var3 = DirectPushReceiver.this.mHttpPost = null;
               DirectPushReceiver.this.setKillSelfTimerChecker();
               ExchangeAccount var4 = DirectPushReceiver.this.mAccount;
               EASLog.d("EAS_DPReceiver", var4, "cancelRunning- abort httpPost done.");
            }

         }
      }
   }
}
