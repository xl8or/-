package com.android.exchange;

import android.content.Context;
import android.util.Log;
import com.android.email.SecurityPolicy;
import com.android.email.mail.DeviceAccessException;
import com.android.email.provider.EmailContent;
import com.android.exchange.Eas;
import com.android.exchange.EasException;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.LogAdapter;
import com.android.exchange.adapter.Serializer;
import com.android.exchange.adapter.SettingsParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public class PasswordRecoveryService extends EasSyncService {

   String TAG;
   String mPassword;


   public PasswordRecoveryService(Context var1, EmailContent.Account var2, String var3) {
      EmailContent.Mailbox var4 = getMailboxForAccount(var1, var2);
      super(var1, var4);
      String var5 = PasswordRecoveryService.class.getSimpleName();
      this.TAG = var5;
      String var6 = this.TAG;
      String var7 = this.TAG;
      Log.d(var6, var7);

      try {
         String var9 = SyncManager.getDeviceId();
         this.mDeviceId = var9;
      } catch (IOException var18) {
         String var13 = this.TAG;
         StringBuilder var14 = (new StringBuilder()).append("Exception caught while getting device id: ");
         String var15 = var18.getMessage();
         String var16 = var14.append(var15).toString();
         Log.e(var13, var16);
      }

      this.mPassword = var3;
      String var10 = this.mAccount.mProtocolVersion;
      this.mProtocolVersion = var10;
      Double var11 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
      this.mProtocolVersionDouble = var11;
   }

   private static EmailContent.Mailbox getMailboxForAccount(Context var0, EmailContent.Account var1) {
      EmailContent.Mailbox var2 = null;
      if(var1 != null) {
         long var3 = var1.mId;
         var2 = EmailContent.Mailbox.restoreMailboxOfType(var0, var3, 68);
      }

      return var2;
   }

   private void logResponse(HttpResponse var1) throws DeviceAccessException {
      int var2 = Log.d(this.TAG, "logResponse");
      if(var1 != null) {
         int var3 = var1.getStatusLine().getStatusCode();
         String var4 = this.TAG;
         String var5 = "statusCode=" + var3;
         Log.d(var4, var5);
         if(var3 == 200) {
            SecurityPolicy.getInstance(this.mContext).setRecoverPasswordState((boolean)1);
            int var7 = Log.d("NPG", "Recovery Password State saved");
            HttpEntity var8 = var1.getEntity();
            if(var8 != null) {
               InputStream var9 = null;

               label42: {
                  InputStream var10;
                  try {
                     var10 = var8.getContent();
                  } catch (IllegalStateException var18) {
                     var18.printStackTrace();
                     break label42;
                  } catch (IOException var19) {
                     var19.printStackTrace();
                     break label42;
                  }

                  var9 = var10;
               }

               int var11 = Log.d(this.TAG, "creating parser");
               SettingsParser var12 = null;

               label37: {
                  SettingsParser var13;
                  try {
                     var13 = new SettingsParser(var9);
                  } catch (IOException var17) {
                     var17.printStackTrace();
                     break label37;
                  }

                  var12 = var13;
               }

               if(var12 != null) {
                  try {
                     boolean var14 = var12.parse();
                  } catch (IOException var15) {
                     var15.printStackTrace();
                  } catch (EasException var16) {
                     var16.printStackTrace();
                  }
               }
            }
         }
      }
   }

   public void run() {
      int var1 = Log.d(this.TAG, "run");
      boolean var2 = this.setupService();
      Serializer var3 = new Serializer();

      try {
         Serializer var4 = var3.start(1157);
         Serializer var5 = var3.start(1172);
         Serializer var6 = var3.start(1160);
         Serializer var7 = var3.start(1173);
         String var8 = this.mPassword;
         Serializer var9 = var7.text(var8).end();
         Serializer var10 = var3.end();
         Serializer var11 = var3.end();
         var3.end().done();
      } catch (IOException var40) {
         var40.printStackTrace();
      }

      String var12 = this.TAG;
      StringBuilder var13 = (new StringBuilder()).append("sending http post with recovery password, mUsername:");
      String var14 = this.mUserName;
      String var15 = var13.append(var14).toString();
      Log.d(var12, var15);

      try {
         if(Eas.PARSER_LOG) {
            String[] var17 = new String[]{"PasswordRecoverySerice:run(): Wbxml:"};
            this.userLog(var17);
            byte[] var18 = var3.toByteArray();
            ByteArrayInputStream var19 = new ByteArrayInputStream(var18);
            EmailContent.Mailbox var20 = this.mMailbox;
            boolean var21 = (new LogAdapter(var20, this)).parse(var19);
         }

         byte[] var22 = var3.toByteArray();
         HttpResponse var23 = this.sendHttpClientPost("Settings", var22);
         this.logResponse(var23);
      } catch (DeviceAccessException var38) {
         StringBuilder var25 = (new StringBuilder()).append("Caught Exceptoin, Device is blocked or quarantined ");
         String var26 = var38.toString();
         String var27 = var25.append(var26).toString();
         int var28 = Log.w("DeviceAccessPermission", var27);
         this.mExitStatus = 6;
         long var29 = this.mAccount.mId;
         int var31 = EmailContent.Account.DEVICE_IS_BLOCKED;
         SyncManager.blockDevice(var29, var31);
      } catch (IOException var39) {
         String var33 = this.TAG;
         StringBuilder var34 = (new StringBuilder()).append("Caught IO Exception: message :");
         String var35 = var39.getMessage();
         String var36 = var34.append(var35).toString();
         Log.e(var33, var36);
         var39.printStackTrace();
      }
   }
}
