package com.android.exchange.security.ode;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.deviceencryption.DeviceEncryptionManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.email.SecurityPolicy;
import com.android.email.provider.EmailContent;
import com.android.exchange.security.ode.ODEService;
import com.android.exchange.security.ode.ODESyncService;
import com.android.exchange.security.ode.ODEUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ODEHandler extends Handler {

   private static final int HND_BASE = 0;
   public static final int HND_QUIT = 2;
   public static final int HND_START = 1;
   private static final String REQUIRE_DEVICE_ENCRYPTION = "RequireDeviceEncryption";
   private static final String REQUIRE_STORAGE_ENCRYPTION = "RequireStorageCardEncryption";
   private ComponentName mAdminName;
   private Context mContext = null;


   public ODEHandler(Looper var1, Context var2) {
      super(var1);
      this.mContext = var2;
      ComponentName var3 = new ComponentName(var2, SecurityPolicy.PolicyAdmin.class);
      this.mAdminName = var3;
   }

   private void checkEncryptionHoldRequired(EmailContent.Account param1) {
      // $FF: Couldn't be decompiled
   }

   private boolean doProvisioningForAccount(EmailContent.Account var1) {
      boolean var2 = false;
      Context var3 = this.mContext;
      ODESyncService var4 = new ODESyncService(var3, var1);
      if(var4.setupAdhocServiceBasedonAccount()) {
         try {
            if(!var4.doProvisioning()) {
               return var2;
            }

            StringBuilder var5 = (new StringBuilder()).append("Completed provisioning for account: ");
            String var6 = var1.mEmailAddress;
            ODEService.log(var5.append(var6).toString());
         } catch (IOException var9) {
            var9.printStackTrace();
            StringBuilder var7 = (new StringBuilder()).append("Unable to complete provisioning for account: ");
            String var8 = var1.mEmailAddress;
            ODEService.log(var7.append(var8).toString());
            return var2;
         }

         var2 = true;
      }

      return var2;
   }

   private void handleStart(Message var1) {
      String var2 = null;
      ArrayList var3 = new ArrayList();
      ODEUtils.getExchangeAccounts(this.mContext, var3);
      ODEService.clearODEError(this.mContext);
      if(var1.obj instanceof Integer) {
         int var4 = ((Integer)var1.obj).intValue();
         ODEService.log("Started handler with : " + var4);
         switch(var4) {
         case 1:
         case 2:
         case 4:
         case 5:
            DevicePolicyManager var5 = ODEUtils.getDPM(this.mContext);
            ComponentName var6 = this.mAdminName;
            if(var5.isAdminActive(var6)) {
               ComponentName var7 = SecurityPolicy.getInstance(this.mContext).getAdminComponent();
               var2 = var5.generateRecoveryPassword(var7);
            }

            if(var2 != null && var2.length() > 0) {
               Iterator var8 = var3.iterator();

               while(var8.hasNext()) {
                  EmailContent.Account var9 = (EmailContent.Account)var8.next();
                  this.checkEncryptionHoldRequired(var9);
                  if(this.isAccountOnSecurityHold(var9) && var4 != 5 && var4 != 4) {
                     if(this.doProvisioningForAccount(var9)) {
                        ODEUtils.clearAccountHoldFlags(this.mContext, var9, 32);
                        if(!this.sendRecoveryPassword(var9, var2)) {
                           ODEService.setODEError(this.mContext);
                        }
                     } else {
                        ODEService.setODEError(this.mContext);
                     }
                  } else if(!this.sendRecoveryPassword(var9, var2)) {
                     ODEService.setODEError(this.mContext);
                  }
               }
            }

            this.interactWithODE();
            break;
         case 3:
            Context var10 = this.mContext;
            (new DeviceEncryptionManager(var10)).setEncryptPolicy(2, 0);
            break;
         case 6:
            Context var11 = this.mContext;
            ComponentName var12 = this.mAdminName;
            if(!ODEUtils.checkDeviceEncryptionStatus(var11, var12)) {
               ODEService.setODEError(this.mContext);
            }
            break;
         default:
            ODEService.log("invalid start message");
         }
      } else {
         ODEService.log("Unknown message passed to ODEhandler");
      }

      this.quit();
   }

   private void interactWithODE() {
      Context var1 = this.mContext;
      DeviceEncryptionManager var2 = new DeviceEncryptionManager(var1);
      DevicePolicyManager var3 = ODEUtils.getDPM(this.mContext);
      Context var4 = this.mContext;
      ComponentName var5 = this.mAdminName;
      boolean var6 = ODEUtils.pendingInternalEncryption(var4, var5);
      Context var7 = this.mContext;
      ComponentName var8 = this.mAdminName;
      boolean var9 = ODEUtils.pendingExternalEncryption(var7, var8);
      if(var6 || var9) {
         label35: {
            ComponentName var10 = this.mAdminName;
            if(var3.getRequireDeviceEncryption(var10)) {
               ComponentName var11 = this.mAdminName;
               if(var3.getRequireStorageCardEncryption(var11)) {
                  ODEService.log("Encrypting both internal and external storage");
                  var2.setEncryptionPolicy(1, 1);
                  break label35;
               }
            }

            ComponentName var15 = this.mAdminName;
            if(var3.getRequireDeviceEncryption(var15)) {
               ComponentName var16 = this.mAdminName;
               if(!var3.getRequireStorageCardEncryption(var16)) {
                  ODEService.log("Encrypting internal storage and de-crypting external storage");
                  var2.setEncryptionPolicy(1, 0);
                  break label35;
               }
            }

            ComponentName var17 = this.mAdminName;
            if(!var3.getRequireDeviceEncryption(var17)) {
               ComponentName var18 = this.mAdminName;
               if(var3.getRequireStorageCardEncryption(var18)) {
                  ODEService.log("De-crypting internal storage and encrypting external storage");
                  var2.setEncryptionPolicy(0, 1);
                  break label35;
               }
            }

            ODEService.log("De-crypting internal and external storage");
            var2.setEncryptionPolicy(0, 0);
         }
      }

      Context var12 = this.mContext;
      ComponentName var13 = this.mAdminName;
      ODEUtils.checkDeviceEncryptionStatus(var12, var13);
   }

   private boolean isAccountOnSecurityHold(EmailContent.Account var1) {
      boolean var2;
      if((var1.mFlags & 32) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void quit() {
      boolean var1 = this.sendEmptyMessage(2);
   }

   private boolean sendRecoveryPassword(EmailContent.Account param1, String param2) {
      // $FF: Couldn't be decompiled
   }

   public void handleMessage(Message var1) {
      switch(var1.what) {
      case 1:
         ODEService.log("Starting the ODE handler");
         this.handleStart(var1);
         return;
      case 2:
         this.getLooper().quit();
         ODEService.log("Stoping the ODE handler");
         return;
      default:
         super.handleMessage(var1);
      }
   }
}
