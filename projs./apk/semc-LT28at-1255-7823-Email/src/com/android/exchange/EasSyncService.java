package com.android.exchange;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Entity;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.android.email.SecurityPolicy;
import com.android.email.Utility;
import com.android.email.mail.Address;
import com.android.email.mail.MessagingException;
import com.android.email.mail.PackedString;
import com.android.email.provider.EmailContent;
import com.android.email.service.IEmailServiceCallback;
import com.android.exchange.AbstractSyncService;
import com.android.exchange.Eas;
import com.android.exchange.EasAuthenticationException;
import com.android.exchange.EasOutboxService;
import com.android.exchange.IllegalHeartbeatException;
import com.android.exchange.MeetingResponseRequest;
import com.android.exchange.PartRequest;
import com.android.exchange.StaleFolderListException;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.AbstractSyncAdapter;
import com.android.exchange.adapter.MeetingResponseParser;
import com.android.exchange.adapter.Parser;
import com.android.exchange.adapter.ProvisionParser;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.provider.Calendar;
import com.android.exchange.provider.GalResult;
import com.android.exchange.utility.CalendarUtilities;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class EasSyncService extends AbstractSyncService {

   private static final String ACCOUNT_MAILBOX_SLEEP_TEXT = "Account mailbox sleeping for 20m";
   private static final int ACCOUNT_MAILBOX_SLEEP_TIME = 1200000;
   private static final String AND_FREQUENCY_PING_PUSH_AND_NOT_ACCOUNT_MAILBOX = " AND syncInterval IN (-3,-2) AND type!=\"68\"";
   private static final String AUTO_DISCOVER_PAGE = "/autodiscover/autodiscover.xml";
   private static final int AUTO_DISCOVER_REDIRECT_CODE = 451;
   private static final String AUTO_DISCOVER_SCHEMA_PREFIX = "http://schemas.microsoft.com/exchange/autodiscover/mobilesync/";
   private static final int CHUNK_SIZE = 16384;
   private static final int COMMAND_TIMEOUT = 30000;
   private static final int CONNECTION_TIMEOUT = 20000;
   public static final boolean DEBUG_GAL_SERVICE = false;
   public static final String EAS_12_POLICY_TYPE = "MS-EAS-Provisioning-WBXML";
   public static final String EAS_2_POLICY_TYPE = "MS-WAP-Provisioning-XML";
   private static final String EMAIL_WINDOW_SIZE = "5";
   private static final int HTTP_NEED_PROVISIONING = 449;
   private static final int MAX_LOOPING_COUNT = 5;
   private static final int MAX_PING_FAILURES = 1;
   private static final int MAX_PING_WAIT_COUNT = 30;
   public static final String PIM_WINDOW_SIZE = "4";
   private static final String PING_COMMAND = "Ping";
   private static final int PING_FALLBACK_INBOX = 5;
   private static final int PING_FALLBACK_PIM = 25;
   private static final int PING_FUDGE_LOW = 10;
   private static final int PING_HEARTBEAT_INCREMENT = 180;
   private static final int PING_MINUTES = 60;
   private static final int PING_STARTING_HEARTBEAT = 470;
   private static final int POST_LOCK_TIMEOUT = 10000;
   private static final int PROTOCOL_PING_STATUS_COMPLETED = 1;
   private static final String PROVISION_STATUS_OK = "1";
   private static final String PROVISION_STATUS_PARTIAL = "2";
   private static final int WATCHDOG_TIMEOUT_ALLOWANCE = 30000;
   private static final String WHERE_ACCOUNT_AND_SYNC_INTERVAL_PING = "accountKey=? and syncInterval=-3";
   private static final String WHERE_ACCOUNT_KEY_AND_SERVER_ID = "accountKey=? and serverId=?";
   private static final String WHERE_PUSH_HOLD_NOT_ACCOUNT_MAILBOX = "accountKey=? and syncInterval=-4";
   String mAuthString;
   private String[] mBindArguments;
   private String mCmdString;
   public ContentResolver mContentResolver;
   protected String mDeviceId;
   String mDeviceType;
   public String mHostAddress;
   public boolean mIsValid;
   public String mPassword;
   private volatile HttpPost mPendingPost;
   private ArrayList<String> mPingChangeList;
   int mPingForceHeartbeat;
   int mPingHeartbeat;
   boolean mPingHeartbeatDropped;
   private int mPingHighWaterMark;
   int mPingMaxHeartbeat;
   int mPingMinHeartbeat;
   private boolean mPostAborted;
   private boolean mPostReset;
   public String mProtocolVersion;
   public Double mProtocolVersionDouble;
   private boolean mSsl;
   private boolean mTrustSsl;
   public String mUserName;


   public EasSyncService() {
      this("EAS Validation");
   }

   public EasSyncService(Context var1, EmailContent.Mailbox var2) {
      super(var1, var2);
      this.mProtocolVersion = "2.5";
      this.mDeviceId = null;
      this.mDeviceType = "Android";
      this.mAuthString = null;
      this.mCmdString = null;
      this.mSsl = (boolean)1;
      this.mTrustSsl = (boolean)0;
      String[] var3 = new String[2];
      this.mBindArguments = var3;
      this.mPendingPost = null;
      this.mPingForceHeartbeat = 120;
      this.mPingMinHeartbeat = 290;
      this.mPingMaxHeartbeat = 1010;
      this.mPingHeartbeat = 470;
      this.mPingHighWaterMark = 0;
      this.mPingHeartbeatDropped = (boolean)0;
      this.mPostAborted = (boolean)0;
      this.mPostReset = (boolean)0;
      this.mIsValid = (boolean)1;
      ContentResolver var4 = var1.getContentResolver();
      this.mContentResolver = var4;
      if(this.mAccount == null) {
         this.mIsValid = (boolean)0;
      } else {
         long var5 = this.mAccount.mHostAuthKeyRecv;
         EmailContent.HostAuth var7 = EmailContent.HostAuth.restoreHostAuthWithId(var1, var5);
         if(var7 == null) {
            this.mIsValid = (boolean)0;
         } else {
            byte var8;
            if((var7.mFlags & 1) != 0) {
               var8 = 1;
            } else {
               var8 = 0;
            }

            this.mSsl = (boolean)var8;
            byte var9;
            if((var7.mFlags & 8) != 0) {
               var9 = 1;
            } else {
               var9 = 0;
            }

            this.mTrustSsl = (boolean)var9;
         }
      }
   }

   private EasSyncService(String var1) {
      super(var1);
      this.mProtocolVersion = "2.5";
      this.mDeviceId = null;
      this.mDeviceType = "Android";
      this.mAuthString = null;
      this.mCmdString = null;
      this.mSsl = (boolean)1;
      this.mTrustSsl = (boolean)0;
      String[] var2 = new String[2];
      this.mBindArguments = var2;
      this.mPendingPost = null;
      this.mPingForceHeartbeat = 120;
      this.mPingMinHeartbeat = 290;
      this.mPingMaxHeartbeat = 1010;
      this.mPingHeartbeat = 470;
      this.mPingHighWaterMark = 0;
      this.mPingHeartbeatDropped = (boolean)0;
      this.mPostAborted = (boolean)0;
      this.mPostReset = (boolean)0;
      this.mIsValid = (boolean)1;
   }

   private String acknowledgeProvision(String var1, String var2) throws IOException {
      return this.acknowledgeProvisionImpl(var1, var2, (boolean)0);
   }

   private String acknowledgeProvisionImpl(String var1, String var2, boolean var3) throws IOException {
      Serializer var4 = new Serializer();
      Serializer var5 = var4.start(901).start(902);
      Serializer var6 = var4.start(903);
      String var7 = this.getPolicyType();
      var4.data(904, var7);
      var4.data(905, var1);
      var4.data(907, var2);
      Serializer var11 = var4.end().end();
      if(var3) {
         Serializer var12 = var4.start(908);
         Serializer var13 = var4.data(907, "1");
         Serializer var14 = var4.end();
      }

      var4.end().done();
      byte[] var15 = var4.toByteArray();
      HttpResponse var16 = this.sendHttpClientPost("Provision", var15);
      String var19;
      if(var16.getStatusLine().getStatusCode() == 200) {
         InputStream var17 = var16.getEntity().getContent();
         ProvisionParser var18 = new ProvisionParser(var17, this);
         if(var18.parse()) {
            var19 = var18.getPolicyKey();
            return var19;
         }
      }

      var19 = null;
      return var19;
   }

   private void acknowledgeRemoteWipe(String var1) throws IOException {
      this.acknowledgeProvisionImpl(var1, "1", (boolean)1);
   }

   private void cacheAuthAndCmdString() {
      String var1;
      if(this.mUserName.indexOf(92) == 0) {
         var1 = this.mUserName.substring(1);
      } else {
         var1 = this.mUserName;
      }

      String var2 = URLEncoder.encode(var1);
      StringBuilder var3 = (new StringBuilder()).append(var1).append(':');
      String var4 = this.mPassword;
      String var5 = var3.append(var4).toString();
      StringBuilder var6 = (new StringBuilder()).append("Basic ");
      String var7 = Base64.encodeToString(var5.getBytes(), 2);
      String var8 = var6.append(var7).toString();
      this.mAuthString = var8;
      StringBuilder var9 = (new StringBuilder()).append("&User=").append(var2).append("&DeviceId=");
      String var10 = this.mDeviceId;
      StringBuilder var11 = var9.append(var10).append("&DeviceType=");
      String var12 = this.mDeviceType;
      String var13 = var11.append(var12).toString();
      this.mCmdString = var13;
   }

   private ProvisionParser canProvision() throws IOException {
      Serializer var1 = new Serializer();
      Serializer var2 = var1.start(901).start(902);
      Serializer var3 = var1.start(903);
      String var4 = this.getPolicyType();
      var3.data(904, var4).end().end().end().done();
      byte[] var5 = var1.toByteArray();
      HttpResponse var6 = this.sendHttpClientPost("Provision", var5);
      ProvisionParser var9;
      if(var6.getStatusLine().getStatusCode() == 200) {
         InputStream var7 = var6.getEntity().getContent();
         ProvisionParser var8 = new ProvisionParser(var7, this);
         if(var8.parse()) {
            if(var8.hasSupportablePolicySet()) {
               var9 = var8;
               return var9;
            } else {
               String var10 = var8.getPolicyKey();
               if(this.acknowledgeProvision(var10, "2") != null) {
                  var9 = var8;
               } else {
                  var9 = null;
               }

               return var9;
            }
         }
      }

      var9 = null;
      return var9;
   }

   private void doProgressCallback(long var1, long var3, int var5) {
      try {
         IEmailServiceCallback var6 = SyncManager.callback();
         var6.loadAttachmentStatus(var1, var3, 1, var5);
      } catch (RemoteException var14) {
         ;
      }
   }

   private void doStatusCallback(long var1, long var3, int var5) {
      try {
         IEmailServiceCallback var6 = SyncManager.callback();
         var6.loadAttachmentStatus(var1, var3, var5, 0);
      } catch (RemoteException var14) {
         ;
      }
   }

   private ClientConnectionManager getClientConnectionManager() {
      return SyncManager.getClientConnectionManager();
   }

   private String getEmailFilter() {
      String var1 = "3";
      switch(this.mAccount.mSyncLookback) {
      case 1:
         var1 = "1";
         break;
      case 2:
         var1 = "2";
         break;
      case 3:
         var1 = "3";
         break;
      case 4:
         var1 = "4";
         break;
      case 5:
         var1 = "5";
         break;
      case 6:
         var1 = "0";
      }

      return var1;
   }

   private HttpClient getHttpClient(int var1) {
      BasicHttpParams var2 = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(var2, 20000);
      HttpConnectionParams.setSoTimeout(var2, var1);
      HttpConnectionParams.setSocketBufferSize(var2, 8192);
      ClientConnectionManager var3 = this.getClientConnectionManager();
      DefaultHttpClient var4 = new DefaultHttpClient(var3, var2);
      Context var5 = this.mContext;
      HttpHost var6 = this.getPreferredHttpHost(var5, (String)null);
      if(var6 != null) {
         HttpParams var7 = var4.getParams().setParameter("http.route.default-proxy", var6);
      }

      return var4;
   }

   private String getPolicyType() {
      String var1;
      if(this.mProtocolVersionDouble.doubleValue() >= 12.0D) {
         var1 = "MS-EAS-Provisioning-WBXML";
      } else {
         var1 = "MS-WAP-Provisioning-XML";
      }

      return var1;
   }

   private HttpHost getPreferredHttpHost(Context var1, String var2) {
      HttpHost var5;
      if(!this.isLocalHost(var2) && !this.isNetworkWifi(var1)) {
         String var3 = Proxy.getHost(var1);
         if(var3 != null) {
            int var4 = Proxy.getPort(var1);
            var5 = new HttpHost(var3, var4, "http");
            return var5;
         }
      }

      var5 = null;
      return var5;
   }

   private HttpPost getRedirect(HttpResponse var1, HttpPost var2) {
      Header var3 = var1.getFirstHeader("X-MS-Location");
      HttpPost var6;
      if(var3 != null) {
         String var4 = var3.getValue();
         if(var4 != null && var4.startsWith("http")) {
            URI var5 = URI.create(var4);
            var2.setURI(var5);
            var6 = var2;
            return var6;
         }
      }

      var6 = null;
      return var6;
   }

   private boolean isLikelyNatFailure(String var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else if(var1.contains("reset by peer")) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isLocalHost(String var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         label37: {
            label28: {
               boolean var4;
               try {
                  String var3 = URI.create(var1).getHost();
                  if(var3 == null) {
                     break label28;
                  }

                  if(var3.equalsIgnoreCase("localhost") || var3.equals("127.0.0.1")) {
                     break label37;
                  }

                  var4 = var3.equals("[::1]");
               } catch (IllegalArgumentException var6) {
                  break label28;
               }

               if(var4) {
                  break label37;
               }
            }

            var2 = false;
            return var2;
         }

         var2 = true;
      }

      return var2;
   }

   private boolean isNetworkWifi(Context var1) {
      boolean var2;
      if(var1 == null) {
         var2 = false;
      } else {
         ConnectivityManager var3 = (ConnectivityManager)var1.getSystemService("connectivity");
         if(var3 != null) {
            NetworkInfo var4 = var3.getActiveNetworkInfo();
            if(var4 != null && var4.getType() == 1) {
               var2 = true;
               return var2;
            }
         }

         var2 = false;
      }

      return var2;
   }

   private String makeUriString(String var1, String var2) throws IOException {
      if(this.mAuthString == null || this.mCmdString == null) {
         this.cacheAuthAndCmdString();
      }

      StringBuilder var3 = new StringBuilder();
      String var4;
      if(this.mSsl) {
         if(this.mTrustSsl) {
            var4 = "httpts";
         } else {
            var4 = "https";
         }
      } else {
         var4 = "http";
      }

      StringBuilder var5 = var3.append(var4).append("://");
      String var6 = this.mHostAddress;
      String var7 = var5.append(var6).append("/Microsoft-Server-ActiveSync").toString();
      if(var1 != null) {
         StringBuilder var8 = (new StringBuilder()).append(var7).append("?Cmd=").append(var1);
         String var9 = this.mCmdString;
         var7 = var8.append(var9).toString();
      }

      if(var2 != null) {
         var7 = var7 + var2;
      }

      return var7;
   }

   private int parsePingResult(InputStream param1, ContentResolver param2, HashMap<String, Integer> param3) throws IOException, StaleFolderListException, IllegalHeartbeatException {
      // $FF: Couldn't be decompiled
   }

   private HttpResponse postAutodiscover(HttpClient var1, HttpPost var2, boolean var3) throws IOException, MessagingException {
      String[] var4 = new String[1];
      StringBuilder var5 = (new StringBuilder()).append("Posting autodiscover to: ");
      URI var6 = var2.getURI();
      String var7 = var5.append(var6).toString();
      var4[0] = var7;
      this.userLog(var4);
      HttpResponse var8 = this.executePostWithTimeout(var1, var2, 30000);
      int var9 = var8.getStatusLine().getStatusCode();
      HttpResponse var14;
      if(var9 == 451) {
         var2 = this.getRedirect(var8, var2);
         if(var2 != null) {
            String[] var10 = new String[1];
            StringBuilder var11 = (new StringBuilder()).append("Posting autodiscover to redirect: ");
            URI var12 = var2.getURI();
            String var13 = var11.append(var12).toString();
            var10[0] = var13;
            this.userLog(var10);
            var14 = this.executePostWithTimeout(var1, var2, 30000);
            return var14;
         }
      } else {
         if(var9 == 401) {
            if(!var3 || !this.mUserName.contains("@")) {
               throw new MessagingException(5);
            }

            int var15 = this.mUserName.indexOf(64);
            String var16 = this.mUserName.substring(0, var15);
            this.mUserName = var16;
            this.cacheAuthAndCmdString();
            String[] var17 = new String[]{"401 received; trying username: ", null};
            String var18 = this.mUserName;
            var17[1] = var18;
            this.userLog(var17);
            var2.removeHeaders("Authorization");
            String var19 = this.mAuthString;
            var2.setHeader("Authorization", var19);
            var14 = this.postAutodiscover(var1, var2, (boolean)0);
            return var14;
         }

         if(var9 != 200) {
            String[] var20 = new String[1];
            String var21 = "Code: " + var9 + ", throwing IOException";
            var20[0] = var21;
            this.userLog(var20);
            throw new IOException();
         }
      }

      var14 = var8;
      return var14;
   }

   private void pushFallback(long var1) {
      EmailContent.Mailbox var3 = EmailContent.Mailbox.restoreMailboxWithId(this.mContext, var1);
      if(var3 != null) {
         ContentValues var4 = new ContentValues();
         byte var5 = 25;
         if(var3.mType == 0) {
            var5 = 5;
         }

         Integer var6 = Integer.valueOf(var5);
         var4.put("syncInterval", var6);
         ContentResolver var7 = this.mContentResolver;
         Uri var8 = ContentUris.withAppendedId(EmailContent.Mailbox.CONTENT_URI, var1);
         var7.update(var8, var4, (String)null, (String[])null);
         StringBuilder var10 = (new StringBuilder()).append("*** PING ERROR LOOP: Set ");
         String var11 = var3.mDisplayName;
         String var12 = var10.append(var11).append(" to ").append(var5).append(" min sync").toString();
         this.errorLog(var12);
         SyncManager.kick("push fallback");
      }
   }

   private void runPingLoop() throws IOException, StaleFolderListException, IllegalHeartbeatException {
      // $FF: Couldn't be decompiled
   }

   public static GalResult searchGal(Context param0, long param1, String param3) {
      // $FF: Couldn't be decompiled
   }

   private void sendMeetingResponseMail(EmailContent.Message var1, int var2) {
      String var3 = var1.mMeetingInfo;
      PackedString var4 = new PackedString(var3);
      if(var4 != null) {
         String var5 = var4.get("ORGMAIL");
         boolean[] var6 = new boolean[]{true};
         Address[] var7 = Address.parse(var5, var6);
         if(var7.length == 1) {
            String var8 = var7[0].getAddress();
            String var9 = var4.get("DTSTAMP");
            String var10 = var4.get("DTSTART");
            String var11 = var4.get("DTEND");
            ContentValues var12 = new ContentValues();
            Entity var13 = new Entity(var12);
            String var14 = CalendarUtilities.convertEmailDateTimeToCalendarDateTime(var9);
            var12.put("DTSTAMP", var14);
            Long var15 = Long.valueOf(Utility.parseEmailDateTimeToMillis(var10));
            var12.put("dtstart", var15);
            Long var16 = Long.valueOf(Utility.parseEmailDateTimeToMillis(var11));
            var12.put("dtend", var16);
            String var17 = var4.get("LOC");
            var12.put("eventLocation", var17);
            String var18 = var4.get("TITLE");
            var12.put("title", var18);
            var12.put("organizer", var8);
            ContentValues var19 = new ContentValues();
            Integer var20 = Integer.valueOf(1);
            var19.put("attendeeRelationship", var20);
            String var21 = this.mAccount.mEmailAddress;
            var19.put("attendeeEmail", var21);
            Uri var22 = Calendar.Attendees.CONTENT_URI;
            var13.addSubValue(var22, var19);
            ContentValues var23 = new ContentValues();
            Integer var24 = Integer.valueOf(2);
            var23.put("attendeeRelationship", var24);
            var23.put("attendeeEmail", var8);
            Uri var25 = Calendar.Attendees.CONTENT_URI;
            var13.addSubValue(var25, var23);
            short var26;
            switch(var2) {
            case 1:
               var26 = 64;
               break;
            case 2:
            default:
               var26 = 256;
               break;
            case 3:
               var26 = 128;
            }

            Context var27 = this.mContext;
            String var28 = var4.get("UID");
            EmailContent.Account var29 = this.mAccount;
            EmailContent.Message var30 = CalendarUtilities.createMessageForEntity(var27, var13, var26, var28, var29);
            if(var30 != null) {
               Context var31 = this.mContext;
               long var32 = this.mAccount.mId;
               EasOutboxService.sendMessage(var31, var32, var30);
            }
         }
      }
   }

   private void setupProtocolVersion(EasSyncService var1, Header var2) throws MessagingException {
      String var3 = var2.getValue();
      String[] var4 = new String[]{"Server supports versions: ", var3};
      this.userLog(var4);
      String[] var5 = var3.split(",");
      String var6 = null;
      String[] var7 = var5;
      int var8 = var5.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String var10 = var7[var9];
         if(var10.equals("2.5") || var10.equals("12.0")) {
            var6 = var10;
         }
      }

      if(var6 == null) {
         String var11 = this.TAG;
         String var12 = "No supported EAS versions: " + var3;
         Log.w(var11, var12);
         throw new MessagingException(9);
      } else {
         var1.mProtocolVersion = var6;
         Double var14 = Double.valueOf(Double.parseDouble(var6));
         var1.mProtocolVersionDouble = var14;
         if(var1.mAccount != null) {
            var1.mAccount.mProtocolVersion = var6;
         }
      }
   }

   private void sleep(long var1, boolean var3) {
      if(var3) {
         long var4 = this.mMailboxId;
         long var6 = 5000L + var1;
         SyncManager.runAsleep(var4, var6);
      }

      try {
         Thread.sleep(var1);
      } catch (InterruptedException var9) {
         return;
      }

      if(var3) {
         if(!Thread.currentThread().isInterrupted()) {
            SyncManager.runAwake(this.mMailboxId);
         }
      }
   }

   private boolean tryProvision() throws IOException {
      ProvisionParser var1 = this.canProvision();
      boolean var8;
      if(var1 != null) {
         SecurityPolicy var2 = SecurityPolicy.getInstance(this.mContext);
         SecurityPolicy.PolicySet var3 = var1.getPolicySet();
         EmailContent.Account var4 = this.mAccount;
         Context var5 = this.mContext;
         if(var3.writeAccount(var4, (String)null, (boolean)1, var5)) {
            long var6 = this.mAccount.mId;
            var2.updatePolicies(var6);
         }

         if(var1.getRemoteWipe()) {
            if(!var2.isActiveAdmin()) {
               var8 = false;
            } else {
               try {
                  String var9 = var1.getPolicyKey();
                  this.acknowledgeRemoteWipe(var9);
               } catch (Exception var18) {
                  ;
               }

               var2.remoteWipe();
               var8 = false;
            }

            return var8;
         }

         if(var2.isActive(var3)) {
            String var10 = var1.getPolicyKey();
            String var11 = this.acknowledgeProvision(var10, "1");
            if(var11 != null) {
               EmailContent.Account var12 = this.mAccount;
               Context var13 = this.mContext;
               var3.writeAccount(var12, var11, (boolean)1, var13);
               SyncManager.releaseSecurityHold(this.mAccount);
               var8 = true;
               return var8;
            }
         } else {
            long var15 = this.mAccount.mId;
            var2.policiesRequired(var15);
         }
      }

      var8 = false;
      return var8;
   }

   public boolean alarm() {
      // $FF: Couldn't be decompiled
   }

   public File createUniqueFileInternal(String var1, String var2) {
      File var3;
      if(var1 == null) {
         var3 = this.mContext.getFilesDir();
      } else {
         var3 = new File(var1);
      }

      if(!var3.exists()) {
         boolean var4 = var3.mkdirs();
      }

      File var5 = new File(var3, var2);
      File var6;
      if(!var5.exists()) {
         var6 = var5;
      } else {
         int var7 = var2.lastIndexOf(46);
         String var8 = var2;
         String var9 = "";
         if(var7 != -1) {
            var8 = var2.substring(0, var7);
            var9 = var2.substring(var7);
         }

         int var10 = 2;

         while(true) {
            if(var10 >= Integer.MAX_VALUE) {
               var6 = null;
               break;
            }

            String var11 = var8 + '-' + var10 + var9;
            var5 = new File(var3, var11);
            if(!var5.exists()) {
               var6 = var5;
               break;
            }

            ++var10;
         }
      }

      return var6;
   }

   protected HttpResponse executePostWithTimeout(HttpClient var1, HttpPost var2, int var3) throws IOException {
      return this.executePostWithTimeout(var1, var2, var3, (boolean)0);
   }

   protected HttpResponse executePostWithTimeout(HttpClient param1, HttpPost param2, int param3, boolean param4) throws IOException {
      // $FF: Couldn't be decompiled
   }

   protected void getAttachment(PartRequest param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   String getTargetCollectionClassFromCursor(Cursor var1) {
      int var2 = var1.getInt(5);
      String var3;
      if(var2 == 66) {
         var3 = "Contacts";
      } else if(var2 == 65) {
         var3 = "Calendar";
      } else {
         var3 = "Email";
      }

      return var3;
   }

   protected boolean isAuthError(int var1) {
      boolean var2;
      if(var1 != 401 && var1 != 403) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   protected boolean isProvisionError(int var1) {
      boolean var2;
      if(var1 != 449 && var1 != 403) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   void parseAction(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.next();
         if(var3 == 3 && var1.getName().equals("Action")) {
            return;
         }

         if(var3 == 2) {
            String var4 = var1.getName();
            if(!var4.equals("Error")) {
               if(var4.equals("Redirect")) {
                  String var5 = this.TAG;
                  StringBuilder var6 = (new StringBuilder()).append("Redirect: ");
                  String var7 = var1.nextText();
                  String var8 = var6.append(var7).toString();
                  Log.d(var5, var8);
               } else if(var4.equals("Settings")) {
                  this.parseSettings(var1, var2);
               }
            }
         }
      }
   }

   void parseAutodiscover(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.nextTag();
         if(var3 == 3 && var1.getName().equals("Autodiscover")) {
            return;
         }

         if(var3 == 2 && var1.getName().equals("Response")) {
            this.parseResponse(var1, var2);
         }
      }
   }

   void parseResponse(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.next();
         if(var3 == 3 && var1.getName().equals("Response")) {
            return;
         }

         if(var3 == 2) {
            String var4 = var1.getName();
            if(var4.equals("User")) {
               this.parseUser(var1, var2);
            } else if(var4.equals("Action")) {
               this.parseAction(var1, var2);
            }
         }
      }
   }

   void parseServer(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      boolean var3 = false;

      while(true) {
         int var4 = var1.next();
         if(var4 == 3 && var1.getName().equals("Server")) {
            return;
         }

         if(var4 == 2) {
            String var5 = var1.getName();
            if(var5.equals("Type")) {
               if(var1.nextText().equals("MobileSync")) {
                  var3 = true;
               }
            } else if(var3 && var5.equals("Url")) {
               String var6 = var1.nextText().toLowerCase();
               if(var6.startsWith("https://") && var6.endsWith("/microsoft-server-activesync")) {
                  int var7 = var6.lastIndexOf(47);
                  String var8 = var6.substring(8, var7);
                  var2.mAddress = var8;
                  String[] var9 = new String[1];
                  StringBuilder var10 = (new StringBuilder()).append("Autodiscover, server: ");
                  String var11 = var2.mAddress;
                  String var12 = var10.append(var11).toString();
                  var9[0] = var12;
                  this.userLog(var9);
               }
            }
         }
      }
   }

   void parseSettings(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.next();
         if(var3 == 3 && var1.getName().equals("Settings")) {
            return;
         }

         if(var3 == 2 && var1.getName().equals("Server")) {
            this.parseServer(var1, var2);
         }
      }
   }

   void parseUser(XmlPullParser var1, EmailContent.HostAuth var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.next();
         if(var3 == 3 && var1.getName().equals("User")) {
            return;
         }

         if(var3 == 2) {
            String var4 = var1.getName();
            if(var4.equals("EMailAddress")) {
               String var5 = var1.nextText();
               String[] var6 = new String[1];
               String var7 = "Autodiscover, email: " + var5;
               var6[0] = var7;
               this.userLog(var6);
            } else if(var4.equals("DisplayName")) {
               String var8 = var1.nextText();
               String[] var9 = new String[1];
               String var10 = "Autodiscover, user: " + var8;
               var9[0] = var10;
               this.userLog(var9);
            }
         }
      }
   }

   public void reset() {
      Object var1 = this.getSynchronizer();
      synchronized(var1) {
         if(this.mPendingPost != null) {
            URI var2 = this.mPendingPost.getURI();
            if(var2 != null && var2.getQuery().startsWith("Cmd=Ping")) {
               String[] var3 = new String[]{"Reset, aborting Ping"};
               this.userLog(var3);
               this.mPostReset = (boolean)1;
               this.mPendingPost.abort();
            }
         }

      }
   }

   void resetHeartbeats(int var1) {
      String[] var2 = new String[1];
      String var3 = "Resetting min/max heartbeat, legal = " + var1;
      var2[0] = var3;
      this.userLog(var2);
      int var4 = this.mPingHeartbeat;
      if(var1 > var4) {
         if(this.mPingMinHeartbeat < var1) {
            this.mPingMinHeartbeat = var1;
         }

         if(this.mPingForceHeartbeat < var1) {
            this.mPingForceHeartbeat = var1;
         }

         int var5 = this.mPingMinHeartbeat;
         int var6 = this.mPingMaxHeartbeat;
         if(var5 > var6) {
            this.mPingMaxHeartbeat = var1;
         }
      } else {
         int var7 = this.mPingHeartbeat;
         if(var1 < var7) {
            this.mPingMaxHeartbeat = var1;
            int var8 = this.mPingMaxHeartbeat;
            int var9 = this.mPingMinHeartbeat;
            if(var8 < var9) {
               this.mPingMinHeartbeat = var1;
            }
         }
      }

      this.mPingHeartbeat = var1;
      this.mPingHeartbeatDropped = (boolean)0;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }

   public void runAccountMailbox() throws IOException, Parser.EasParserException {
      // $FF: Couldn't be decompiled
   }

   protected HttpResponse sendHttpClientOptions() throws IOException {
      HttpClient var1 = this.getHttpClient(30000);
      URI var2 = URI.create(this.makeUriString("OPTIONS", (String)null));
      HttpOptions var3 = new HttpOptions(var2);
      this.setHeaders(var3, (boolean)0);
      return var1.execute(var3);
   }

   protected HttpResponse sendHttpClientPost(String var1, HttpEntity var2) throws IOException {
      return this.sendHttpClientPost(var1, var2, 30000);
   }

   protected HttpResponse sendHttpClientPost(String var1, HttpEntity var2, int var3) throws IOException {
      HttpClient var4 = this.getHttpClient(var3);
      boolean var5 = var1.equals("Ping");
      String var6 = null;
      boolean var7 = false;
      if(!var1.startsWith("SmartForward&") && !var1.startsWith("SmartReply&")) {
         if(var1.startsWith("SendMail&")) {
            var7 = true;
         }
      } else {
         int var8 = var1.indexOf(38);
         var6 = var1.substring(var8);
         var1 = var1.substring(0, var8);
         var7 = true;
      }

      URI var9 = URI.create(this.makeUriString(var1, var6));
      HttpPost var10 = new HttpPost(var9);
      if(var7) {
         var10.setHeader("Content-Type", "message/rfc822");
      } else if(var2 != null) {
         var10.setHeader("Content-Type", "application/vnd.ms-sync.wbxml");
      }

      byte var11;
      if(!var1.equals("Ping")) {
         var11 = 1;
      } else {
         var11 = 0;
      }

      this.setHeaders(var10, (boolean)var11);
      var10.setEntity(var2);
      return this.executePostWithTimeout(var4, var10, var3, var5);
   }

   protected HttpResponse sendHttpClientPost(String var1, byte[] var2) throws IOException {
      ByteArrayEntity var3 = new ByteArrayEntity(var2);
      return this.sendHttpClientPost(var1, var3, 30000);
   }

   protected void sendMeetingResponse(MeetingResponseRequest var1) throws IOException {
      Context var2 = this.mContext;
      long var3 = var1.mMessageId;
      EmailContent.Message var5 = EmailContent.Message.restoreMessageWithId(var2, var3);
      if(var5 != null) {
         Context var6 = this.mContext;
         long var7 = var5.mMailboxKey;
         EmailContent.Mailbox var9 = EmailContent.Mailbox.restoreMailboxWithId(var6, var7);
         if(var9 != null) {
            Serializer var10 = new Serializer();
            Serializer var11 = var10.start(519).start(521);
            String var12 = Integer.toString(var1.mResponse);
            var10.data(524, var12);
            String var14 = var9.mServerId;
            var10.data(518, var14);
            String var16 = var5.mServerId;
            var10.data(520, var16);
            var10.end().end().done();
            byte[] var18 = var10.toByteArray();
            HttpResponse var19 = this.sendHttpClientPost("MeetingResponse", var18);
            int var20 = var19.getStatusLine().getStatusCode();
            if(var20 == 200) {
               int var21 = (int)var19.getEntity().getContentLength();
               InputStream var22 = var19.getEntity().getContent();
               if(var21 != 0) {
                  boolean var23 = (new MeetingResponseParser(var22, this)).parse();
                  int var24 = var1.mResponse;
                  this.sendMeetingResponseMail(var5, var24);
               }
            } else if(this.isAuthError(var20)) {
               throw new EasAuthenticationException();
            } else {
               String[] var25 = new String[1];
               String var26 = "Meeting response request failed, code: " + var20;
               var25[0] = var26;
               this.userLog(var25);
               throw new IOException();
            }
         }
      }
   }

   protected HttpResponse sendPing(byte[] var1, int var2) throws IOException {
      Thread var3 = Thread.currentThread();
      StringBuilder var4 = new StringBuilder();
      String var5 = this.mAccount.mDisplayName;
      String var6 = var4.append(var5).append(": Ping").toString();
      var3.setName(var6);
      if(Eas.USER_LOG) {
         String[] var7 = new String[1];
         StringBuilder var8 = (new StringBuilder()).append("Send ping, timeout: ").append(var2).append("s, high: ");
         int var9 = this.mPingHighWaterMark;
         String var10 = var8.append(var9).append('s').toString();
         var7[0] = var10;
         this.userLog(var7);
      }

      ByteArrayEntity var11 = new ByteArrayEntity(var1);
      int var12 = (var2 + 5) * 1000;
      return this.sendHttpClientPost("Ping", var11, var12);
   }

   void setHeaders(HttpRequestBase var1, boolean var2) {
      String var3 = this.mAuthString;
      var1.setHeader("Authorization", var3);
      String var4 = this.mProtocolVersion;
      var1.setHeader("MS-ASProtocolVersion", var4);
      var1.setHeader("Connection", "keep-alive");
      StringBuilder var5 = new StringBuilder();
      String var6 = this.mDeviceType;
      String var7 = var5.append(var6).append('/').append("0.3").toString();
      var1.setHeader("User-Agent", var7);
      if(var2) {
         String var8 = "0";
         if(this.mAccount != null) {
            String var9 = this.mAccount.mSecuritySyncKey;
            if(!TextUtils.isEmpty(var9)) {
               var8 = var9;
            }
         }

         var1.setHeader("X-MS-PolicyKey", var8);
      }
   }

   protected boolean setupService() {
      Context var1 = this.mContext;
      long var2 = this.mAccount.mId;
      EmailContent.Account var4 = EmailContent.Account.restoreAccountWithId(var1, var2);
      this.mAccount = var4;
      boolean var5;
      if(this.mAccount == null) {
         var5 = false;
      } else {
         Context var6 = this.mContext;
         long var7 = this.mMailbox.mId;
         EmailContent.Mailbox var9 = EmailContent.Mailbox.restoreMailboxWithId(var6, var7);
         this.mMailbox = var9;
         if(this.mMailbox == null) {
            var5 = false;
         } else {
            Thread var10 = Thread.currentThread();
            this.mThread = var10;
            Process.setThreadPriority(10);
            String var11 = this.mThread.getName();
            this.TAG = var11;
            Context var12 = this.mContext;
            long var13 = this.mAccount.mHostAuthKeyRecv;
            EmailContent.HostAuth var15 = EmailContent.HostAuth.restoreHostAuthWithId(var12, var13);
            if(var15 == null) {
               var5 = false;
            } else {
               String var16 = var15.mAddress;
               this.mHostAddress = var16;
               String var17 = var15.mLogin;
               this.mUserName = var17;
               String var18 = var15.mPassword;
               this.mPassword = var18;
               String var19 = this.mAccount.mProtocolVersion;
               this.mProtocolVersion = var19;
               if(this.mProtocolVersion == null) {
                  this.mProtocolVersion = "2.5";
               }

               Double var20 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
               this.mProtocolVersionDouble = var20;
               var5 = true;
            }
         }
      }

      return var5;
   }

   public void stop() {
      this.mStop = (boolean)1;
      Object var1 = this.getSynchronizer();
      synchronized(var1) {
         if(this.mPendingPost != null) {
            this.mPendingPost.abort();
         }

      }
   }

   public void sync(AbstractSyncAdapter param1) throws IOException {
      // $FF: Couldn't be decompiled
   }

   public Bundle tryAutodiscover(String param1, String param2) throws RemoteException {
      // $FF: Couldn't be decompiled
   }

   public void validateAccount(String param1, String param2, String param3, int param4, boolean param5, boolean param6, Context param7) throws MessagingException {
      // $FF: Couldn't be decompiled
   }
}
