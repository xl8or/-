package com.sonyericsson.email.utils.customization;

import android.net.Uri;
import android.util.Log;
import com.android.email.provider.EmailContent;
import com.sonyericsson.email.utils.customization.Customization;
import java.util.Arrays;

public final class AccountData implements Cloneable {

   static final int DEFAULT_CHECK_INTERVAL_SECONDS = 0;
   static final int DEFAULT_EAS_CHECK_INTERVAL_SECONDS = 254;
   static final boolean DEFAULT_HAS_EMAIL_NOTIFICATION = true;
   static final boolean DEFAULT_HAS_FULL_EMAIL_LOGIN = true;
   static final boolean DEFAULT_HAS_NOTIFICATION_VIBRATE = false;
   static final int DEFAULT_INBOX_SIZE = 50;
   static final boolean DEFAULT_IS_SHOW_LINKED_PICTURES = false;
   static final String DEFAULT_NOTIFICATION_TONE = "";
   private static final String EMPTY_STRING = "";
   static final String ENCRYPT_SSL = "ssl";
   static final String ENCRYPT_TLS = "tls";
   static final String NOTIFICATION_TONE_SILENT = "";
   private static final boolean NO_DATA_BOOL = false;
   private static final int NO_DATA_INT = 255;
   private static final String NO_DATA_STRING = "";
   private static final Uri NO_DATA_URI = null;
   static final String PATH_START_CONTENT = "content://";
   static final String PROTOCOL_INCOMING_IMAP = "imap";
   static final String PROTOCOL_INCOMING_POP3 = "pop3";
   private static final String PROTOCOL_OUTGOING = "smtp";
   static final int[] VALID_CHECK_INTERVALS = new int[]{0, 300, 600, 900, 1800, 3600};
   static final int[] VALID_EAS_CHECK_INTERVALS = new int[]{-2, 0, 300, 600, 900, 1800, 3600};
   static final int[] VALID_INBOX_SIZES = new int[]{0, 25, 50, 100, 300, 800};
   static final String XML_DATA_NOTIFICATION_TONE_SILENT = "silent";
   static final String XML_DATA_PROTOCOL_INCOMING_POP = "pop";
   private Uri mBrandedIconUri;
   private String mBrandedLabel;
   private int[] mCheckIntervalList;
   private int mCheckIntervalSeconds;
   private String mDomain = "";
   private int[] mEasCheckIntervalList;
   private int mEasCheckIntervalSeconds;
   private String mEmailAddress = "";
   private boolean mHasEmailNotifications;
   private boolean mHasIncomingFullEmailLogin;
   private boolean mHasNotificationVibrate;
   private boolean mHasOutgoingAuthentication;
   private boolean mHasOutgoingFullEmailLogin;
   private int mInboxSize;
   private String mIncomingEncryption;
   private String mIncomingPassword = "";
   private int mIncomingPort;
   private String mIncomingProtocol;
   private String mIncomingServer;
   private String mIncomingUsername = "";
   private boolean mIsSetCheckIntervalList;
   private boolean mIsSetCheckIntervalSeconds;
   private boolean mIsSetEasCheckIntervalList;
   private boolean mIsSetEasCheckIntervalSeconds;
   private boolean mIsSetHasEmailNotifications;
   private boolean mIsSetHasNotificationVibrate;
   private boolean mIsSetNotificationTone;
   private boolean mIsShowLinkedPictures;
   private String mNotificationTone;
   private String mOutgoingEncryption;
   private String mOutgoingPassword = "";
   private int mOutgoingPort;
   private String mOutgoingServer;
   private String mOutgoingUsername = "";
   private String mSignature;
   private int mSysPropMailpush;


   public AccountData() {
      Uri var1 = NO_DATA_URI;
      this.mBrandedIconUri = var1;
      this.mBrandedLabel = "";
      this.mIncomingProtocol = "";
      this.mIncomingServer = "";
      this.mIncomingEncryption = "";
      this.mIncomingPort = -1;
      this.mHasIncomingFullEmailLogin = (boolean)1;
      this.mOutgoingServer = "";
      this.mOutgoingEncryption = "";
      this.mOutgoingPort = -1;
      this.mHasOutgoingAuthentication = (boolean)0;
      this.mHasOutgoingFullEmailLogin = (boolean)1;
      this.mIsSetCheckIntervalSeconds = (boolean)0;
      this.mIsSetEasCheckIntervalSeconds = (boolean)0;
      this.mIsSetCheckIntervalList = (boolean)0;
      this.mIsSetEasCheckIntervalList = (boolean)0;
      this.mIsSetHasEmailNotifications = (boolean)0;
      this.mIsSetNotificationTone = (boolean)0;
      this.mIsSetHasNotificationVibrate = (boolean)0;
      this.mCheckIntervalSeconds = -1;
      this.mEasCheckIntervalSeconds = -1;
      this.mCheckIntervalList = null;
      this.mEasCheckIntervalList = null;
      this.mHasEmailNotifications = (boolean)0;
      this.mNotificationTone = "";
      this.mHasNotificationVibrate = (boolean)0;
      this.mInboxSize = -1;
      this.mIsShowLinkedPictures = (boolean)0;
      this.mSysPropMailpush = 0;
      this.mSignature = "";
   }

   private int validateCheckInterval(int var1, int[] var2) {
      int var3 = var2[0];
      int[] var4 = var2;
      int var5 = var2.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var4[var6];
         if(var1 == var7) {
            var3 = var1;
            break;
         }
      }

      return var3;
   }

   private int[] validateCheckIntervalList(int[] var1, int[] var2) {
      int[] var3 = new int[var1.length];
      int[] var4 = var1;
      int var5 = var1.length;
      int var6 = 0;

      int var9;
      for(int var7 = 0; var6 < var5; var7 = var9) {
         int var8 = var4[var6];
         if(this.validateCheckInterval(var8, var2) == var8) {
            var9 = var7 + 1;
            var3[var7] = var8;
         } else {
            var9 = var7;
         }

         ++var6;
      }

      int[] var10;
      if(var3.length > 0) {
         var10 = var3;
      } else {
         var10 = var2;
      }

      return var10;
   }

   private int validateInboxSize(int var1) {
      int var2 = 50;
      int[] var3 = VALID_INBOX_SIZES;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var3[var5];
         if(var1 == var6) {
            var2 = var1;
         }
      }

      return var2;
   }

   private String validateIncomingProtocol(String var1) {
      String var2 = var1.trim().toLowerCase();
      if(var2.equals("pop")) {
         var2 = "pop3";
      }

      return var2;
   }

   private int validateInt(String var1) {
      int var2 = -1;

      int var3;
      try {
         var3 = Integer.parseInt(var1.trim());
      } catch (NumberFormatException var7) {
         String var5 = "Error while parsing String into Int: " + var1;
         int var6 = Log.e("Email", var5, var7);
         return var2;
      }

      var2 = var3;
      return var2;
   }

   private String validatePath(String var1) {
      String var2 = "";
      if(var1 != null) {
         var2 = var1.trim();
         if(!var1.equalsIgnoreCase("silent") && !var1.startsWith("content://")) {
            StringBuilder var3 = new StringBuilder();
            String var4 = Customization.CMZ_PATH;
            StringBuilder var5 = var3.append(var4);
            String var6 = Customization.CMZ_CONTENT_PATH;
            String var7 = var5.append(var6).append("/").toString();
            var2 = var7 + var1;
         }
      }

      return var2;
   }

   private Uri validateUriPath(String var1) {
      Uri var2 = NO_DATA_URI;

      Uri var3;
      try {
         var3 = Uri.parse(this.validatePath(var1));
      } catch (NullPointerException var6) {
         int var5 = Log.e("Email", "Error while parsing Uri for Branded Icon.", var6);
         return var2;
      }

      var2 = var3;
      return var2;
   }

   public EmailContent.Account getAccount() {
      EmailContent.Account var1 = new EmailContent.Account();
      EmailContent.HostAuth var2 = new EmailContent.HostAuth();
      var1.mHostAuthRecv = var2;
      EmailContent.HostAuth var3 = new EmailContent.HostAuth();
      var1.mHostAuthSend = var3;
      String var4 = this.mEmailAddress;
      var1.setEmailAddress(var4);
      String var5 = this.mEmailAddress;
      var1.setDisplayName(var5);
      EmailContent.HostAuth var6 = var1.mHostAuthRecv;
      String var7 = this.mIncomingUsername;
      var6.mLogin = var7;
      EmailContent.HostAuth var8 = var1.mHostAuthRecv;
      String var9 = this.mIncomingPassword;
      var8.mPassword = var9;
      EmailContent.HostAuth var10 = var1.mHostAuthSend;
      String var11 = this.mOutgoingUsername;
      var10.mLogin = var11;
      EmailContent.HostAuth var12 = var1.mHostAuthSend;
      String var13 = this.mOutgoingPassword;
      var12.mPassword = var13;
      EmailContent.HostAuth var14 = var1.mHostAuthRecv;
      String var15 = this.mIncomingProtocol;
      var14.mProtocol = var15;
      EmailContent.HostAuth var16 = var1.mHostAuthRecv;
      String var17 = this.mIncomingServer;
      var16.mAddress = var17;
      String var18 = this.mIncomingEncryption;
      if("ssl".equalsIgnoreCase(var18)) {
         EmailContent.HostAuth var19 = var1.mHostAuthRecv;
         int var20 = var19.mFlags | 1;
         var19.mFlags = var20;
      } else {
         String var37 = this.mIncomingEncryption;
         if("tls".equalsIgnoreCase(var37)) {
            EmailContent.HostAuth var38 = var1.mHostAuthRecv;
            int var39 = var38.mFlags | 2;
            var38.mFlags = var39;
         }
      }

      EmailContent.HostAuth var21 = var1.mHostAuthRecv;
      int var22 = var21.mFlags | 4;
      var21.mFlags = var22;
      EmailContent.HostAuth var23 = var1.mHostAuthRecv;
      int var24 = this.mIncomingPort;
      var23.mPort = var24;
      var1.mHostAuthSend.mProtocol = "smtp";
      EmailContent.HostAuth var25 = var1.mHostAuthSend;
      String var26 = this.mOutgoingServer;
      var25.mAddress = var26;
      EmailContent.HostAuth var27 = var1.mHostAuthSend;
      int var28 = this.mOutgoingPort;
      var27.mPort = var28;
      String var29 = this.mOutgoingEncryption;
      if("ssl".equalsIgnoreCase(var29)) {
         EmailContent.HostAuth var30 = var1.mHostAuthSend;
         int var31 = var30.mFlags | 1;
         var30.mFlags = var31;
      } else {
         String var40 = this.mOutgoingEncryption;
         if("tls".equalsIgnoreCase(var40)) {
            EmailContent.HostAuth var41 = var1.mHostAuthSend;
            int var42 = var41.mFlags | 2;
            var41.mFlags = var42;
         }
      }

      if(this.mHasOutgoingAuthentication) {
         EmailContent.HostAuth var32 = var1.mHostAuthSend;
         int var33 = var32.mFlags | 4;
         var32.mFlags = var33;
      }

      if(this.mCheckIntervalSeconds == 0) {
         var1.setSyncInterval(-1);
      } else {
         int var43 = this.mCheckIntervalSeconds / 60;
         var1.setSyncInterval(var43);
      }

      String var34 = this.mNotificationTone;
      var1.setRingtone(var34);
      int var35 = 0;
      if(this.mHasEmailNotifications) {
         var35 |= 1;
      }

      if(this.mHasNotificationVibrate) {
         var35 |= 2;
      }

      int var36 = var35 | 128;
      var1.setFlags(var36);
      return var1;
   }

   public Uri getBrandedIconUri() {
      return this.mBrandedIconUri;
   }

   public String getBrandedLabel() {
      return this.mBrandedLabel;
   }

   public int[] getCheckIntervalList() {
      int[] var1;
      if(this.mIsSetCheckIntervalList) {
         var1 = this.mCheckIntervalList;
      } else {
         int[] var2 = VALID_CHECK_INTERVALS;
         int var3 = VALID_CHECK_INTERVALS.length;
         var1 = Arrays.copyOf(var2, var3);
      }

      return var1;
   }

   public int getCheckIntervalSeconds() {
      int var1;
      if(this.mIsSetCheckIntervalSeconds) {
         var1 = this.mCheckIntervalSeconds;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public String getDomain() {
      return this.mDomain;
   }

   public int[] getEasCheckIntervalList() {
      int[] var1;
      if(this.mIsSetEasCheckIntervalList) {
         var1 = this.mEasCheckIntervalList;
      } else {
         int[] var2 = VALID_EAS_CHECK_INTERVALS;
         int var3 = VALID_EAS_CHECK_INTERVALS.length;
         var1 = Arrays.copyOf(var2, var3);
      }

      return var1;
   }

   public int getEasCheckIntervalSeconds() {
      int var1;
      if(this.mIsSetEasCheckIntervalSeconds) {
         var1 = this.mEasCheckIntervalSeconds;
      } else {
         var1 = -1;
      }

      return var1;
   }

   public String getEmailAddress() {
      return this.mEmailAddress;
   }

   public int getInboxSize() {
      this.mInboxSize = 50;
      return this.mInboxSize;
   }

   public String getIncomingEncryption() {
      return this.mIncomingEncryption;
   }

   public String getIncomingPassword() {
      return this.mIncomingPassword;
   }

   public int getIncomingPort() {
      return this.mIncomingPort;
   }

   public String getIncomingProtocol() {
      return this.mIncomingProtocol;
   }

   public String getIncomingServer() {
      return this.mIncomingServer;
   }

   public String getIncomingUsername() {
      return this.mIncomingUsername;
   }

   public String getNotificationTone() {
      String var1;
      if(this.mIsSetNotificationTone) {
         var1 = this.mNotificationTone;
      } else {
         var1 = "";
      }

      return var1;
   }

   public String getOutgoingEncryption() {
      return this.mOutgoingEncryption;
   }

   public String getOutgoingPassword() {
      return this.mOutgoingPassword;
   }

   public int getOutgoingPort() {
      return this.mOutgoingPort;
   }

   public String getOutgoingServer() {
      return this.mOutgoingServer;
   }

   public String getOutgoingUsername() {
      return this.mOutgoingUsername;
   }

   public String getSignature() {
      return this.mSignature;
   }

   public int getSysPropMailpush() {
      return this.mSysPropMailpush;
   }

   public boolean hasEmailNotifications() {
      boolean var1;
      if(this.mIsSetHasEmailNotifications) {
         var1 = this.mHasEmailNotifications;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean hasIncomingFullEmailLogin() {
      return this.mHasIncomingFullEmailLogin;
   }

   public boolean hasNotificationVibrate() {
      boolean var1;
      if(this.mIsSetHasNotificationVibrate) {
         var1 = this.mHasNotificationVibrate;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasOutgoingAuthentication() {
      return this.mHasOutgoingAuthentication;
   }

   public boolean hasOutgoingFullEmailLogin() {
      return this.mHasOutgoingFullEmailLogin;
   }

   public boolean isSetCheckIntervalList() {
      return this.mIsSetCheckIntervalList;
   }

   public boolean isSetCheckIntervalSeconds() {
      return this.mIsSetCheckIntervalSeconds;
   }

   public boolean isSetEasCheckIntervalList() {
      return this.mIsSetEasCheckIntervalList;
   }

   public boolean isSetEasCheckIntervalSeconds() {
      return this.mIsSetEasCheckIntervalSeconds;
   }

   public boolean isSetHasEmailNotifications() {
      return this.mIsSetHasEmailNotifications;
   }

   public boolean isSetHasNotificationVibrate() {
      return this.mIsSetHasNotificationVibrate;
   }

   public boolean isSetNotificationTone() {
      return this.mIsSetNotificationTone;
   }

   public boolean isShowLinkedPictures() {
      this.mIsShowLinkedPictures = (boolean)0;
      return this.mIsShowLinkedPictures;
   }

   public AccountData makeCopy() {
      AccountData var1;
      try {
         this = (AccountData)super.clone();
      } catch (CloneNotSupportedException var3) {
         var1 = null;
         return var1;
      }

      var1 = this;
      return var1;
   }

   public void merge(AccountData var1) {
      if(var1 != null) {
         if(!this.isSetCheckIntervalList() && var1.isSetCheckIntervalList()) {
            int[] var2 = var1.getCheckIntervalList();
            this.setCheckIntervalList(var2);
         }

         if(!this.isSetEasCheckIntervalList() && var1.isSetEasCheckIntervalList()) {
            int[] var3 = var1.getEasCheckIntervalList();
            this.setEasCheckIntervalList(var3);
         }

         if(!this.isSetCheckIntervalSeconds() && var1.isSetCheckIntervalSeconds()) {
            int var4 = var1.getCheckIntervalSeconds();
            this.setCheckIntervalSeconds(var4);
         }

         if(!this.isSetEasCheckIntervalSeconds() && var1.isSetEasCheckIntervalSeconds()) {
            int var5 = var1.getEasCheckIntervalSeconds();
            this.setEasCheckIntervalSeconds(var5);
         }

         if(!this.isSetHasEmailNotifications() && var1.isSetHasEmailNotifications()) {
            boolean var6 = var1.hasEmailNotifications();
            this.setHasEmailNotifications(var6);
         }

         if(!this.isSetNotificationTone() && var1.isSetNotificationTone()) {
            String var7 = var1.getNotificationTone();
            this.setNotificationTone(var7);
         }

         if(!this.isSetHasNotificationVibrate()) {
            if(var1.isSetHasNotificationVibrate()) {
               boolean var8 = var1.hasNotificationVibrate();
               this.setHasNotificationVibrate(var8);
            }
         }
      }
   }

   public void setBrandedIconUri(Uri var1) {
      this.mBrandedIconUri = var1;
   }

   protected void setBrandedIconUri(String var1) {
      Uri var2 = this.validateUriPath(var1);
      this.setBrandedIconUri(var2);
   }

   public void setBrandedLabel(String var1) {
      String var2 = var1.trim();
      this.mBrandedLabel = var2;
   }

   public void setCheckIntervalList(int[] var1) {
      this.mIsSetCheckIntervalList = (boolean)1;
      int[] var2 = VALID_CHECK_INTERVALS;
      int[] var3 = this.validateCheckIntervalList(var1, var2);
      this.mCheckIntervalList = var3;
   }

   public void setCheckIntervalSeconds(int var1) {
      this.mIsSetCheckIntervalSeconds = (boolean)1;
      int[] var2 = VALID_CHECK_INTERVALS;
      int var3 = this.validateCheckInterval(var1, var2);
      this.mCheckIntervalSeconds = var3;
   }

   protected void setCheckIntervalSeconds(String var1) {
      int var2 = this.validateInt(var1);
      this.setCheckIntervalSeconds(var2);
   }

   public void setDomain(String var1) {
      String var2 = var1.trim().toLowerCase();
      this.mDomain = var2;
   }

   public void setEasCheckIntervalList(int[] var1) {
      this.mIsSetEasCheckIntervalList = (boolean)1;
      int[] var2 = VALID_EAS_CHECK_INTERVALS;
      int[] var3 = this.validateCheckIntervalList(var1, var2);
      this.mEasCheckIntervalList = var3;
   }

   public void setEasCheckIntervalSeconds(int var1) {
      this.mIsSetEasCheckIntervalSeconds = (boolean)1;
      int[] var2 = VALID_EAS_CHECK_INTERVALS;
      int var3 = this.validateCheckInterval(var1, var2);
      this.mEasCheckIntervalSeconds = var3;
   }

   protected void setEasCheckIntervalSeconds(String var1) {
      int var2 = this.validateInt(var1);
      this.setEasCheckIntervalSeconds(var2);
   }

   public void setEmailAddress(String var1) {
      String var2 = var1.trim();
      this.mEmailAddress = var2;
   }

   protected void setHasEmailNotifications(String var1) {
      boolean var2 = Boolean.valueOf(var1.trim()).booleanValue();
      this.setHasEmailNotifications(var2);
   }

   public void setHasEmailNotifications(boolean var1) {
      this.mIsSetHasEmailNotifications = (boolean)1;
      this.mHasEmailNotifications = var1;
   }

   protected void setHasIncomingFullEmailLogin(String var1) {
      boolean var2 = Boolean.valueOf(var1.trim()).booleanValue();
      this.mHasIncomingFullEmailLogin = var2;
   }

   public void setHasIncomingFullEmailLogin(boolean var1) {
      this.mHasIncomingFullEmailLogin = var1;
   }

   protected void setHasNotificationVibrate(String var1) {
      boolean var2 = Boolean.valueOf(var1.trim()).booleanValue();
      this.setHasNotificationVibrate(var2);
   }

   public void setHasNotificationVibrate(boolean var1) {
      this.mIsSetHasNotificationVibrate = (boolean)1;
      this.mHasNotificationVibrate = var1;
   }

   protected void setHasOutgoingAuthentication(String var1) {
      boolean var2 = Boolean.valueOf(var1.trim()).booleanValue();
      this.mHasOutgoingAuthentication = var2;
   }

   public void setHasOutgoingAuthentication(boolean var1) {
      this.mHasOutgoingAuthentication = var1;
   }

   protected void setHasOutgoingFullEmailLogin(String var1) {
      boolean var2 = Boolean.valueOf(var1.trim()).booleanValue();
      this.mHasOutgoingFullEmailLogin = var2;
   }

   public void setHasOutgoingFullEmailLogin(boolean var1) {
      this.mHasOutgoingFullEmailLogin = var1;
   }

   public void setIncomingEncryption(String var1) {
      String var2 = var1.trim().toLowerCase();
      this.mIncomingEncryption = var2;
   }

   public void setIncomingPassword(String var1) {
      String var2 = var1.trim();
      this.mIncomingPassword = var2;
   }

   public void setIncomingPort(int var1) {
      this.mIncomingPort = var1;
   }

   protected void setIncomingPort(String var1) {
      int var2 = this.validateInt(var1);
      this.mIncomingPort = var2;
   }

   public void setIncomingProtocol(String var1) {
      String var2 = this.validateIncomingProtocol(var1);
      this.mIncomingProtocol = var2;
   }

   public void setIncomingServer(String var1) {
      String var2 = var1.trim();
      this.mIncomingServer = var2;
   }

   public void setIncomingUsername(String var1) {
      String var2 = var1.trim();
      this.mIncomingUsername = var2;
   }

   public void setNotificationTone(String var1) {
      this.mIsSetNotificationTone = (boolean)1;
      String var2 = this.validatePath(var1);
      this.mNotificationTone = var2;
   }

   public void setOutgoingEncryption(String var1) {
      String var2 = var1.trim().toLowerCase();
      this.mOutgoingEncryption = var2;
   }

   public void setOutgoingPassword(String var1) {
      String var2 = var1.trim();
      this.mOutgoingPassword = var2;
   }

   public void setOutgoingPort(int var1) {
      this.mOutgoingPort = var1;
   }

   protected void setOutgoingPort(String var1) {
      int var2 = this.validateInt(var1);
      this.mOutgoingPort = var2;
   }

   public void setOutgoingServer(String var1) {
      String var2 = var1.trim();
      this.mOutgoingServer = var2;
   }

   public void setOutgoingUsername(String var1) {
      String var2 = var1.trim();
      this.mOutgoingUsername = var2;
   }

   public void setSignature(String var1) {
      this.mSignature = var1;
   }

   public void setSysPropMailpush(int var1) {
      this.mSysPropMailpush = var1;
   }

   protected void setSysPropMailpush(String var1) {
      int var2 = this.validateInt(var1);
      this.mSysPropMailpush = var2;
   }
}
