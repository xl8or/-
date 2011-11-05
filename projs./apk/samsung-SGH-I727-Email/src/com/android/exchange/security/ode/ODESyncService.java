package com.android.exchange.security.ode;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;
import com.android.email.SecurityPolicy;
import com.android.email.provider.EmailContent;
import com.android.exchange.EasSyncService;
import com.android.exchange.SyncManager;
import com.android.exchange.adapter.ProvisionParser;
import java.io.IOException;

public class ODESyncService extends EasSyncService {

   public ODESyncService(Context var1, EmailContent.Account var2) {
      super(var1, var2);
   }

   public boolean doProvisioning() throws IOException {
      ProvisionParser var1 = this.canProvision();
      boolean var18;
      if(var1 != null) {
         SecurityPolicy var2 = SecurityPolicy.getInstance(this.mContext);
         SecurityPolicy.PolicySet var3 = var1.getPolicySet();
         if(var3 != null) {
            EmailContent.Account var4 = this.mAccount;
            Context var5 = this.mContext;
            if(var3.writeAccount(var4, (String)null, (boolean)1, var5)) {
               long var6 = this.mAccount.mId;
               var2.updatePolicies(var6);
            }
         }

         if(var1.getRemoteWipe()) {
            StringBuilder var8 = new StringBuilder();
            String var9 = this.TAG;
            StringBuilder var10 = var8.append(var9).append("<");
            long var11 = Thread.currentThread().getId();
            int var13 = Log.e(var10.append(var11).append(">").toString(), "Remote Wipe got from Provision Parser");

            try {
               String var14 = var1.getPolicyKey();
               this.acknowledgeRemoteWipe(var14);
            } catch (Exception var34) {
               int var20 = Log.e(this.TAG, "acknowledgeRemoteWipe exception");
            }

            if(!var2.isActiveAdmin()) {
               int var15 = Log.e(this.TAG, "Email is not admin. Sending broadcast for MASTER_CLEAR");
               Context var16 = this.mContext;
               Intent var17 = new Intent("android.intent.action.MASTER_CLEAR");
               var16.sendBroadcast(var17);
            } else {
               var2.remoteWipe();
            }

            var18 = false;
            return var18;
         }

         if(var2.isActive(var3)) {
            String var21 = var1.getPolicyKey();
            String var22 = this.acknowledgeProvision(var21, "1");
            if(var22 != null) {
               if(var3 != null) {
                  EmailContent.Account var23 = this.mAccount;
                  Context var24 = this.mContext;
                  var3.writeAccount(var23, var22, (boolean)1, var24);
               }

               var18 = true;
               return var18;
            }
         } else {
            long var26 = this.mAccount.mId;
            var2.policiesRequired(var26);
         }
      } else {
         StringBuilder var28 = new StringBuilder();
         String var29 = this.TAG;
         StringBuilder var30 = var28.append(var29).append("<");
         long var31 = Thread.currentThread().getId();
         int var33 = Log.e(var30.append(var31).append(">").toString(), "pp is null. CANNOT PROVISION");
      }

      var18 = false;
      return var18;
   }

   public boolean sendRecoveryPassword(String param1) {
      // $FF: Couldn't be decompiled
   }

   public boolean setupAdhocServiceBasedonAccount() {
      boolean var1;
      if(this.mAccount == null) {
         var1 = false;
      } else {
         Context var2 = this.mContext;
         long var3 = this.mAccount.mId;
         EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(var2, var3);
         this.mAccount = var5;
         if(this.mAccount == null) {
            var1 = false;
         } else {
            Context var6 = this.mContext;
            long var7 = this.mAccount.mHostAuthKeyRecv;
            EmailContent.HostAuth var9 = EmailContent.HostAuth.restoreHostAuthWithId(var6, var7);
            if(var9 == null) {
               int var10 = Log.e(this.TAG, "ha is null for account");
               var1 = false;
            } else {
               String var11 = var9.mAddress;
               this.mHostAddress = var11;
               String var12 = var9.mLogin;
               this.mUserName = var12;
               String var13 = var9.mPassword;
               this.mPassword = var13;
               String var14 = this.mAccount.mProtocolVersion;
               this.mProtocolVersion = var14;
               if(this.mProtocolVersion == null) {
                  this.mProtocolVersion = "2.5";
               }

               Double var15 = Double.valueOf(Double.parseDouble(this.mProtocolVersion));
               this.mProtocolVersionDouble = var15;
               Thread var16 = Thread.currentThread();
               this.mThread = var16;
               Process.setThreadPriority(10);
               String var17 = this.mThread.getName();
               this.TAG = var17;

               try {
                  String var18 = SyncManager.getDeviceId();
                  this.mDeviceId = var18;
               } catch (IOException var19) {
                  var19.printStackTrace();
               }

               var1 = true;
            }
         }
      }

      return var1;
   }
}
