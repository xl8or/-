package com.google.android.gsf;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LoginData implements Parcelable {

   public static final Creator<LoginData> CREATOR = new LoginData.1();
   public String mAuthtoken;
   public String mCaptchaAnswer;
   public byte[] mCaptchaData;
   public String mCaptchaMimeType;
   public String mCaptchaToken;
   public String mEncryptedPassword;
   public int mFlags;
   public String mJsonString;
   public String mOAuthAccessToken;
   public String mPassword;
   public String mService;
   public String mSid;
   public LoginData.Status mStatus;
   public String mUsername;


   public LoginData() {
      this.mUsername = null;
      this.mEncryptedPassword = null;
      this.mPassword = null;
      this.mService = null;
      this.mCaptchaToken = null;
      this.mCaptchaData = null;
      this.mCaptchaMimeType = null;
      this.mCaptchaAnswer = null;
      this.mFlags = 0;
      this.mStatus = null;
      this.mJsonString = null;
      this.mSid = null;
      this.mAuthtoken = null;
      this.mOAuthAccessToken = null;
   }

   private LoginData(Parcel var1) {
      this.mUsername = null;
      this.mEncryptedPassword = null;
      this.mPassword = null;
      this.mService = null;
      this.mCaptchaToken = null;
      this.mCaptchaData = null;
      this.mCaptchaMimeType = null;
      this.mCaptchaAnswer = null;
      this.mFlags = 0;
      this.mStatus = null;
      this.mJsonString = null;
      this.mSid = null;
      this.mAuthtoken = null;
      this.mOAuthAccessToken = null;
      this.readFromParcel(var1);
   }

   // $FF: synthetic method
   LoginData(Parcel var1, LoginData.1 var2) {
      this(var1);
   }

   public LoginData(LoginData var1) {
      this.mUsername = null;
      this.mEncryptedPassword = null;
      this.mPassword = null;
      this.mService = null;
      this.mCaptchaToken = null;
      this.mCaptchaData = null;
      this.mCaptchaMimeType = null;
      this.mCaptchaAnswer = null;
      this.mFlags = 0;
      this.mStatus = null;
      this.mJsonString = null;
      this.mSid = null;
      this.mAuthtoken = null;
      this.mOAuthAccessToken = null;
      String var2 = var1.mUsername;
      this.mUsername = var2;
      String var3 = var1.mEncryptedPassword;
      this.mEncryptedPassword = var3;
      String var4 = var1.mPassword;
      this.mPassword = var4;
      String var5 = var1.mService;
      this.mService = var5;
      String var6 = var1.mCaptchaToken;
      this.mCaptchaToken = var6;
      byte[] var7 = var1.mCaptchaData;
      this.mCaptchaData = var7;
      String var8 = var1.mCaptchaMimeType;
      this.mCaptchaMimeType = var8;
      String var9 = var1.mCaptchaAnswer;
      this.mCaptchaAnswer = var9;
      int var10 = var1.mFlags;
      this.mFlags = var10;
      LoginData.Status var11 = var1.mStatus;
      this.mStatus = var11;
      String var12 = var1.mJsonString;
      this.mJsonString = var12;
      String var13 = var1.mSid;
      this.mSid = var13;
      String var14 = var1.mAuthtoken;
      this.mAuthtoken = var14;
      String var15 = var1.mOAuthAccessToken;
      this.mOAuthAccessToken = var15;
   }

   public int describeContents() {
      return 0;
   }

   public String dump() {
      StringBuilder var1 = new StringBuilder();
      StringBuilder var2 = var1.append("         status: ");
      LoginData.Status var3 = this.mStatus;
      var1.append(var3);
      StringBuilder var5 = var1.append("\n       username: ");
      String var6 = this.mUsername;
      var1.append(var6);
      StringBuilder var8 = var1.append("\n       password: ");
      String var9 = this.mPassword;
      var1.append(var9);
      StringBuilder var11 = var1.append("\n   enc password: ");
      String var12 = this.mEncryptedPassword;
      var1.append(var12);
      StringBuilder var14 = var1.append("\n        service: ");
      String var15 = this.mService;
      var1.append(var15);
      StringBuilder var17 = var1.append("\n      authtoken: ");
      String var18 = this.mAuthtoken;
      var1.append(var18);
      StringBuilder var20 = var1.append("\n      oauthAccessToken: ");
      String var21 = this.mOAuthAccessToken;
      var1.append(var21);
      StringBuilder var23 = var1.append("\n   captchatoken: ");
      String var24 = this.mCaptchaToken;
      var1.append(var24);
      StringBuilder var26 = var1.append("\n  captchaanswer: ");
      String var27 = this.mCaptchaAnswer;
      var1.append(var27);
      StringBuilder var29 = var1.append("\n    captchadata: ");
      String var30;
      if(this.mCaptchaData == null) {
         var30 = "null";
      } else {
         StringBuilder var32 = new StringBuilder();
         String var33 = Integer.toString(this.mCaptchaData.length);
         var30 = var32.append(var33).append(" bytes").toString();
      }

      var1.append(var30);
      return var1.toString();
   }

   public void readFromParcel(Parcel var1) {
      String var2 = var1.readString();
      this.mUsername = var2;
      String var3 = var1.readString();
      this.mEncryptedPassword = var3;
      String var4 = var1.readString();
      this.mPassword = var4;
      String var5 = var1.readString();
      this.mService = var5;
      String var6 = var1.readString();
      this.mCaptchaToken = var6;
      int var7 = var1.readInt();
      if(var7 == -1) {
         this.mCaptchaData = null;
      } else {
         byte[] var16 = new byte[var7];
         this.mCaptchaData = var16;
         byte[] var17 = this.mCaptchaData;
         var1.readByteArray(var17);
      }

      String var8 = var1.readString();
      this.mCaptchaMimeType = var8;
      String var9 = var1.readString();
      this.mCaptchaAnswer = var9;
      int var10 = var1.readInt();
      this.mFlags = var10;
      String var11 = var1.readString();
      if(var11 == null) {
         this.mStatus = null;
      } else {
         LoginData.Status var18 = LoginData.Status.valueOf(var11);
         this.mStatus = var18;
      }

      String var12 = var1.readString();
      this.mJsonString = var12;
      String var13 = var1.readString();
      this.mSid = var13;
      String var14 = var1.readString();
      this.mAuthtoken = var14;
      String var15 = var1.readString();
      this.mOAuthAccessToken = var15;
   }

   public void writeToParcel(Parcel var1, int var2) {
      String var3 = this.mUsername;
      var1.writeString(var3);
      String var4 = this.mEncryptedPassword;
      var1.writeString(var4);
      String var5 = this.mPassword;
      var1.writeString(var5);
      String var6 = this.mService;
      var1.writeString(var6);
      String var7 = this.mCaptchaToken;
      var1.writeString(var7);
      if(this.mCaptchaData == null) {
         var1.writeInt(-1);
      } else {
         int var15 = this.mCaptchaData.length;
         var1.writeInt(var15);
         byte[] var16 = this.mCaptchaData;
         var1.writeByteArray(var16);
      }

      String var8 = this.mCaptchaMimeType;
      var1.writeString(var8);
      String var9 = this.mCaptchaAnswer;
      var1.writeString(var9);
      int var10 = this.mFlags;
      var1.writeInt(var10);
      if(this.mStatus == null) {
         var1.writeString((String)null);
      } else {
         String var17 = this.mStatus.name();
         var1.writeString(var17);
      }

      String var11 = this.mJsonString;
      var1.writeString(var11);
      String var12 = this.mSid;
      var1.writeString(var12);
      String var13 = this.mAuthtoken;
      var1.writeString(var13);
      String var14 = this.mOAuthAccessToken;
      var1.writeString(var14);
   }

   static class 1 implements Creator<LoginData> {

      1() {}

      public LoginData createFromParcel(Parcel var1) {
         return new LoginData(var1, (LoginData.1)null);
      }

      public LoginData[] newArray(int var1) {
         return new LoginData[var1];
      }
   }

   public static enum Status {

      // $FF: synthetic field
      private static final LoginData.Status[] $VALUES;
      ACCOUNT_DISABLED("ACCOUNT_DISABLED", 1),
      BAD_REQUEST("BAD_REQUEST", 3),
      BAD_USERNAME("BAD_USERNAME", 2),
      CANCELLED("CANCELLED", 10),
      CAPTCHA("CAPTCHA", 9),
      DELETED_GMAIL("DELETED_GMAIL", 11),
      DMAGENT("DMAGENT", 13),
      LOGIN_FAIL("LOGIN_FAIL", 4),
      MISSING_APPS("MISSING_APPS", 6),
      NETWORK_ERROR("NETWORK_ERROR", 8),
      NO_GMAIL("NO_GMAIL", 7),
      OAUTH_MIGRATION_REQUIRED("OAUTH_MIGRATION_REQUIRED", 12),
      SERVER_ERROR("SERVER_ERROR", 5),
      SUCCESS("SUCCESS", 0);


      static {
         LoginData.Status[] var0 = new LoginData.Status[14];
         LoginData.Status var1 = SUCCESS;
         var0[0] = var1;
         LoginData.Status var2 = ACCOUNT_DISABLED;
         var0[1] = var2;
         LoginData.Status var3 = BAD_USERNAME;
         var0[2] = var3;
         LoginData.Status var4 = BAD_REQUEST;
         var0[3] = var4;
         LoginData.Status var5 = LOGIN_FAIL;
         var0[4] = var5;
         LoginData.Status var6 = SERVER_ERROR;
         var0[5] = var6;
         LoginData.Status var7 = MISSING_APPS;
         var0[6] = var7;
         LoginData.Status var8 = NO_GMAIL;
         var0[7] = var8;
         LoginData.Status var9 = NETWORK_ERROR;
         var0[8] = var9;
         LoginData.Status var10 = CAPTCHA;
         var0[9] = var10;
         LoginData.Status var11 = CANCELLED;
         var0[10] = var11;
         LoginData.Status var12 = DELETED_GMAIL;
         var0[11] = var12;
         LoginData.Status var13 = OAUTH_MIGRATION_REQUIRED;
         var0[12] = var13;
         LoginData.Status var14 = DMAGENT;
         var0[13] = var14;
         $VALUES = var0;
      }

      private Status(String var1, int var2) {}
   }
}
