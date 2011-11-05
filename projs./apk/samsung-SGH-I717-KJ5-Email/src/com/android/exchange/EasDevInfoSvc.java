package com.android.exchange;

import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.email.mail.MessagingException;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.OoOCommandParser;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.adapter.SettingsCommandAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;

public class EasDevInfoSvc extends EasSyncService {

   private static final int COMMAND_TIMEOUT = 20000;
   private static final int IRM_PROGRESS_END = 100;
   private static final int IRM_PROGRESS_START = 0;
   private static final String TAG = "EasDevInfoSvc";
   private boolean isIrmtemplateRefreshNeeded = 0;
   private int mEnableOutboundSMS;
   private String mFriendlyName;
   private String mImei;
   private String mMobileOperator;
   private String mModel;
   private String mOS;
   private String mOSLanguage;
   private String mPhoneNumber;
   private TelephonyManager mTeleohonyManager;
   private String mUserAgent;


   public EasDevInfoSvc(Context var1, EmailContent.Account var2) {
      super(var1, var2);
      String var3 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var3;
      Double var4 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var4;
      TelephonyManager var5 = (TelephonyManager)var1.getSystemService("phone");
      this.mTeleohonyManager = var5;
   }

   public EasDevInfoSvc(Context var1, EmailContent.Account var2, boolean var3) {
      super(var1, var2);
      this.isIrmtemplateRefreshNeeded = var3;
   }

   private void collectDeviceInformation() {
      String var1 = Build.MODEL;
      this.mModel = var1;
      String var2 = this.mTeleohonyManager.getDeviceId();
      this.mImei = var2;
      String var3 = Build.DEVICE;
      this.mFriendlyName = var3;
      this.mOS = "Android-Linux";
      String var4 = Locale.getDefault().getDisplayLanguage();
      this.mOSLanguage = var4;
      String var5 = this.mTeleohonyManager.getLine1Number();
      this.mPhoneNumber = var5;
      StringBuilder var6 = (new StringBuilder()).append("mModel=");
      String var7 = this.mModel;
      StringBuilder var8 = var6.append(var7).append("; mImei=");
      String var9 = this.mImei;
      StringBuilder var10 = var8.append(var9).append("; mFriendlyName=");
      String var11 = this.mFriendlyName;
      StringBuilder var12 = var10.append(var11).append("; mPhoneNumber=");
      String var13 = this.mPhoneNumber;
      StringBuilder var14 = var12.append(var13).append("; mOSLanguage");
      String var15 = this.mOSLanguage;
      String var16 = var14.append(var15).toString();
      int var17 = Log.d("EasDevInfoSvc", var16);
      if(this.mProtocolVersionDouble.doubleValue() >= 14.0D) {
         this.mUserAgent = "Android/0.3";
         this.mEnableOutboundSMS = 1;
         String var18 = this.mTeleohonyManager.getNetworkOperatorName();
         this.mMobileOperator = var18;
         StringBuilder var19 = (new StringBuilder()).append("mMobileOperator=");
         String var20 = this.mMobileOperator;
         String var21 = var19.append(var20).toString();
         int var22 = Log.d("EasDevInfoSvc", var21);
      }
   }

   private void deviceInfoCb(long var1, int var3, int var4) {
      try {
         SyncManager.callback().setDeviceInfoStatus(var1, var3, var4);
      } catch (RemoteException var6) {
         ;
      }
   }

   private void refreshIRMTemplatesCb(long var1, int var3, int var4) {
      try {
         SyncManager.callback().refreshIRMTemplatesStatus(var1, var3, var4);
      } catch (RemoteException var6) {
         ;
      }
   }

   private int setDeviceInformation() throws IOException, MessagingException {
      long var1 = this.mAccount.mId;
      this.deviceInfoCb(var1, 1, 0);
      byte var3 = 0;
      Serializer var4 = new Serializer();
      this.collectDeviceInformation();
      Serializer var5 = var4.start(1157).start(1174).start(1160).start(1175);
      String var6 = this.mModel;
      Serializer var7 = var5.text(var6).end().start(1176);
      String var8 = this.mImei;
      Serializer var9 = var7.text(var8).end().start(1177);
      String var10 = this.mFriendlyName;
      Serializer var11 = var9.text(var10).end().start(1178);
      String var12 = this.mOS;
      Serializer var13 = var11.text(var12).end().start(1179);
      String var14 = this.mOSLanguage;
      Serializer var15 = var13.text(var14).end().start(1180);
      String var16 = this.mPhoneNumber;
      Serializer var17 = var15.text(var16).end();
      if(this.mProtocolVersion.equals("14.0")) {
         Serializer var18 = var17.start(1184);
         String var19 = this.mUserAgent;
         Serializer var20 = var18.text(var19).end().start(1185);
         String var21 = Integer.toString(this.mEnableOutboundSMS);
         Serializer var22 = var20.text(var21).end().start(1186);
         String var23 = this.mMobileOperator;
         var17 = var22.text(var23).end();
      }

      Serializer var24 = var17.end().end().end();
      var24.done();
      byte[] var25 = var24.toByteArray();
      ByteArrayEntity var26 = new ByteArrayEntity(var25);
      HttpResponse var27 = this.sendHttpClientPost("Settings", var26, 20000);
      int var28 = var27.getStatusLine().getStatusCode();
      if(var28 == 200) {
         HttpEntity var29 = var27.getEntity();
         if((int)var29.getContentLength() != 0) {
            InputStream var30 = var29.getContent();
            EmailContent.Mailbox var31 = this.mMailbox;
            SettingsCommandAdapter var32 = new SettingsCommandAdapter(var31, this);
            if((new OoOCommandParser(var30, var32)).parse()) {
               long var33 = this.mAccount.mId;
               this.deviceInfoCb(var33, 0, 100);
            } else {
               if(this.isProvisionError(var28)) {
                  var3 = 23;
               } else {
                  var3 = -1;
               }

               long var35 = this.mAccount.mId;
               this.deviceInfoCb(var35, var3, 100);
            }
         } else {
            long var37 = this.mAccount.mId;
            this.deviceInfoCb(var37, 0, 100);
         }
      } else {
         var3 = 21;
         if(this.isProvisionError(var28)) {
            var3 = 23;
         } else if(this.isAuthError(var28)) {
            var3 = 22;
         }

         long var39 = this.mAccount.mId;
         this.deviceInfoCb(var39, var3, 100);
      }

      return var3;
   }

   public void run() {
      // $FF: Couldn't be decompiled
   }
}
