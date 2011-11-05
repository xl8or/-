package com.htc.android.mail.eassvc.provision;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.common.EASSyncCommon;
import com.htc.android.mail.eassvc.provision.EASDeviceAdmin;
import com.htc.android.mail.eassvc.provision.EASProvision;
import com.htc.android.mail.eassvc.provision.EASProvisionDoc;
import com.htc.android.mail.eassvc.util.AccountUtil;
import com.htc.android.mail.eassvc.util.EASLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ProvisionUtil {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "ProvisionFileUtil";


   public ProvisionUtil() {}

   public static EASProvisionDoc aggregatePolicy(ArrayList<EASProvisionDoc> var0) {
      EASProvisionDoc var1 = new EASProvisionDoc();
      var1.AllowSimpleDevicePassword = "1";
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         EASProvisionDoc var3 = (EASProvisionDoc)var2.next();
         if(isEqual(var3.DevicePasswordEnabled, "1")) {
            String var4 = var1.DevicePasswordEnabled;
            String var5 = var3.DevicePasswordEnabled;
            String var6 = maxInt(var4, var5);
            var1.DevicePasswordEnabled = var6;
            String var7 = var1.DevicePasswordHistory;
            String var8 = var3.DevicePasswordHistory;
            String var9 = maxInt(var7, var8);
            var1.DevicePasswordHistory = var9;
            String var10 = var1.AlphanumericDevicePasswordRequired;
            String var11 = var3.AlphanumericDevicePasswordRequired;
            String var12 = maxInt(var10, var11);
            var1.AlphanumericDevicePasswordRequired = var12;
            String var13 = var1.AllowSimpleDevicePassword;
            String var14 = var3.AllowSimpleDevicePassword;
            intCompare(var13, var14);
            if(var3.AllowSimpleDevicePassword == null) {
               String var16 = var3.AllowSimpleDevicePassword;
               var1.AllowSimpleDevicePassword = var16;
            } else {
               String var53 = var1.AllowSimpleDevicePassword;
               String var54 = var3.AllowSimpleDevicePassword;
               String var55 = minInt(var53, var54);
               var1.AllowSimpleDevicePassword = var55;
            }

            String var17 = var1.MinDevicePasswordComplexCharacters;
            String var18 = var3.MinDevicePasswordComplexCharacters;
            String var19 = maxInt(var17, var18);
            var1.MinDevicePasswordComplexCharacters = var19;
            String var20 = var1.MinDevicePasswordLength;
            String var21 = var3.MinDevicePasswordLength;
            String var22 = maxInt(var20, var21);
            var1.MinDevicePasswordLength = var22;
            String var23 = var1.MaxInactivityTimeDeviceLock;
            String var24 = var3.MaxInactivityTimeDeviceLock;
            String var25 = minInt(var23, var24);
            var1.MaxInactivityTimeDeviceLock = var25;
            String var26 = var1.DevicePasswordExpiration;
            String var27 = var3.DevicePasswordExpiration;
            String var28 = minInt(var26, var27);
            var1.DevicePasswordExpiration = var28;
            String var29 = var1.MaxDevicePasswordFailedAttempts;
            String var30 = var3.MaxDevicePasswordFailedAttempts;
            String var31 = minInt(var29, var30);
            var1.MaxDevicePasswordFailedAttempts = var31;
            String var32 = var1.AllowWiFi;
            String var33 = var3.AllowWiFi;
            String var34 = minInt(var32, var33);
            var1.AllowWiFi = var34;
            String var35 = var1.AllowBluetooth;
            String var36 = var3.AllowBluetooth;
            String var37 = minInt(var35, var36);
            var1.AllowBluetooth = var37;
            String var38 = var1.AllowInternetSharing;
            String var39 = var3.AllowInternetSharing;
            String var40 = minInt(var38, var39);
            var1.AllowInternetSharing = var40;
            String var41 = var1.AllowCamera;
            String var42 = var3.AllowCamera;
            String var43 = minInt(var41, var42);
            var1.AllowCamera = var43;
            String var44 = var1.AllowBrowser;
            String var45 = var3.AllowBrowser;
            String var46 = minInt(var44, var45);
            var1.AllowBrowser = var46;
            String var47 = var1.AllowTextMessaging;
            String var48 = var3.AllowTextMessaging;
            String var49 = minInt(var47, var48);
            var1.AllowTextMessaging = var49;
            String var50 = var1.AllowStorageCard;
            String var51 = var3.AllowStorageCard;
            String var52 = minInt(var50, var51);
            var1.AllowStorageCard = var52;
         }
      }

      return var1;
   }

   public static boolean checkPasswordValid(DevicePolicyManager var0, EASProvisionDoc var1) {
      boolean var2 = var0.isActivePasswordSufficient();
      if(!TextUtils.isEmpty(var1.DevicePasswordExpiration)) {
         int var3 = Integer.valueOf(var1.DevicePasswordExpiration).intValue();
      }

      return var2;
   }

   public static boolean deleteProvisionData(Context var0, long var1) {
      File var3 = AccountUtil.getAccountConfigPath(var0, var1);
      File var4 = new File(var3, "eas_provision.prefs");
      boolean var6;
      if(var4.exists()) {
         boolean var5 = var4.delete();
         var6 = true;
      } else {
         var6 = false;
      }

      return var6;
   }

   public static void enforceLimitationPolicyToAdmin(Context var0, EASProvisionDoc var1) {
      DevicePolicyManager var2 = (DevicePolicyManager)var0.getSystemService("device_policy");
      ComponentName var3 = new ComponentName(var0, EASDeviceAdmin.class);
      if(var1.AllowWiFi != null) {
         int var4 = Integer.valueOf(var1.AllowWiFi).intValue();
         var2.setAllowWifi(var3, var4);
      }

      if(var1.AllowBluetooth != null) {
         String var5 = var1.AllowBluetooth;
         byte var6;
         if("0".equals(var5)) {
            var6 = 0;
         } else {
            String var12 = var1.AllowBluetooth;
            if("1".equals(var12)) {
               var6 = 2;
            } else {
               var6 = 1;
            }
         }

         var2.setAllowBT(var3, var6);
      }

      if(var1.AllowInternetSharing != null) {
         int var7 = Integer.valueOf(var1.AllowInternetSharing).intValue();
         var2.setAllowInternetSharing(var3, var7);
      }

      if(var1.AllowTextMessaging != null) {
         int var8 = Integer.valueOf(var1.AllowInternetSharing).intValue();
         var2.setAllowTextMessaging(var3, var8);
      }

      if(var1.AllowCamera != null) {
         int var9 = Integer.valueOf(var1.AllowCamera).intValue();
         var2.setAllowCamera(var3, var9);
      }

      if(var1.AllowBrowser != null) {
         int var10 = Integer.valueOf(var1.AllowBrowser).intValue();
         var2.setAllowBrowser(var3, var10);
      }

      if(var1.AllowStorageCard != null) {
         int var11 = Integer.valueOf(var1.AllowStorageCard).intValue();
         var2.setAllowStorageCard(var3, var11);
      }
   }

   public static void enforcePolicyToAdmin(Context var0, EASProvisionDoc var1) {
      DevicePolicyManager var2 = (DevicePolicyManager)var0.getSystemService("device_policy");
      ComponentName var3 = new ComponentName(var0, EASDeviceAdmin.class);
      String var4 = var1.DevicePasswordEnabled;
      if("1".equals(var4)) {
         if(var1.MaxDevicePasswordFailedAttempts != null) {
            int var5 = Integer.valueOf(var1.MaxDevicePasswordFailedAttempts).intValue();
            var2.setMaximumFailedPasswordsForWipe(var3, var5);
         }

         if(var1.MinDevicePasswordLength != null) {
            int var6 = Integer.valueOf(var1.MinDevicePasswordLength).intValue();
            var2.setPasswordMinimumLength(var3, var6);
         }

         int var7 = getPasswordQuality(var1);
         var2.setPasswordQuality(var3, var7);
         if(var1.MinDevicePasswordComplexCharacters != null) {
            int var8 = Integer.valueOf(var1.MinDevicePasswordComplexCharacters).intValue();
            var2.setPasswordComplexity(var3, var8);
         }

         if(var1.DevicePasswordHistory != null) {
            int var9 = Integer.valueOf(var1.DevicePasswordHistory).intValue();
            var2.setPasswordHistory(var3, var9);
         }

         if(var1.DevicePasswordExpiration != null) {
            int var10 = Integer.valueOf(var1.DevicePasswordExpiration).intValue();
            var2.setPasswordExpiration(var3, var10);
         }
      }

      if(var1.AllowWiFi != null) {
         int var11 = Integer.valueOf(var1.AllowWiFi).intValue();
         var2.setAllowWifi(var3, var11);
      }

      if(var1.AllowBluetooth != null) {
         int var12 = Integer.valueOf(var1.AllowBluetooth).intValue();
         var2.setAllowBT(var3, var12);
      }

      if(var1.AllowInternetSharing != null) {
         int var13 = Integer.valueOf(var1.AllowInternetSharing).intValue();
         var2.setAllowInternetSharing(var3, var13);
      }

      if(var1.AllowTextMessaging != null) {
         int var14 = Integer.valueOf(var1.AllowInternetSharing).intValue();
         var2.setAllowTextMessaging(var3, var14);
      }

      if(var1.AllowCamera != null) {
         int var15 = Integer.valueOf(var1.AllowCamera).intValue();
         var2.setAllowCamera(var3, var15);
      }

      if(var1.AllowBrowser != null) {
         int var16 = Integer.valueOf(var1.AllowBrowser).intValue();
         var2.setAllowBrowser(var3, var16);
      }

      if(var1.AllowStorageCard != null) {
         int var17 = Integer.valueOf(var1.AllowStorageCard).intValue();
         var2.setAllowStorageCard(var3, var17);
      }
   }

   public static ArrayList<String> findDifferent(EASProvisionDoc param0, EASProvisionDoc param1) {
      // $FF: Couldn't be decompiled
   }

   public static EASProvisionDoc getDeviceProvision(Context param0) {
      // $FF: Couldn't be decompiled
   }

   public static int getPasswordQuality(EASProvisionDoc var0) {
      int var1;
      if(var0.AllowSimpleDevicePassword != null && Integer.valueOf(var0.AllowSimpleDevicePassword).intValue() > 0) {
         var1 = 131072;
      } else if(var0.AlphanumericDevicePasswordRequired != null && Integer.valueOf(var0.AlphanumericDevicePasswordRequired).intValue() > 0) {
         var1 = 327680;
      } else {
         var1 = 131072;
      }

      return var1;
   }

   public static ArrayList<String> getUnsupportList(EASProvisionDoc var0) {
      ArrayList var1 = new ArrayList();
      if(var0.DeviceEncryptionEnabled != null && !var0.DeviceEncryptionEnabled.equals("0")) {
         String var2 = EASProvision.DeviceEncryptionEnabled;
         var1.add(var2);
      }

      if(var0.RequireDeviceEncryption != null && !var0.RequireDeviceEncryption.equals("0")) {
         String var4 = EASProvision.RequireDeviceEncryption;
         var1.add(var4);
      }

      if(var0.RequireSignedSMIMEMessages != null && !var0.RequireSignedSMIMEMessages.equals("0")) {
         String var6 = EASProvision.RequireSignedSMIMEMessages;
         var1.add(var6);
      }

      if(var0.RequireEncryptedSMIMEMessages != null && !var0.RequireEncryptedSMIMEMessages.equals("0")) {
         String var8 = EASProvision.RequireEncryptedSMIMEMessages;
         var1.add(var8);
      }

      if(var0.RequireSignedSMIMEAlgorithm != null && !var0.RequireSignedSMIMEAlgorithm.equals("0")) {
         String var10 = EASProvision.RequireSignedSMIMEAlgorithm;
         var1.add(var10);
      }

      if(var0.RequireEncryptionSMIMEAlgorithm != null && !var0.RequireEncryptionSMIMEAlgorithm.equals("0")) {
         String var12 = EASProvision.RequireEncryptionSMIMEAlgorithm;
         var1.add(var12);
      }

      if(var0.UnapprovedInROMApplicationList != null && var0.UnapprovedInROMApplicationList.length > 0) {
         String var14 = EASProvision.UnapprovedInROMApplicationList;
         var1.add(var14);
      }

      if(var0.ApprovedApplicationList != null && var0.ApprovedApplicationList.length > 0) {
         String var16 = EASProvision.ApprovedApplicationList;
         var1.add(var16);
      }

      if(var0.AllowSMIMEEncryptionAlgorithmNegotiation != null && var0.AllowSMIMEEncryptionAlgorithmNegotiation.equals("0")) {
         String var18 = EASProvision.AllowSMIMEEncryptionAlgorithmNegotiation;
         var1.add(var18);
      }

      if(var0.AllowUnsignedApplications != null && var0.AllowUnsignedApplications.equals("0")) {
         String var20 = EASProvision.AllowUnsignedApplications;
         var1.add(var20);
      }

      if(var0.AllowUnsignedInstallationPackages != null && var0.AllowUnsignedInstallationPackages.equals("0")) {
         String var22 = EASProvision.AllowUnsignedInstallationPackages;
         var1.add(var22);
      }

      if(var0.AllowPOPIMAPEmail != null && var0.AllowPOPIMAPEmail.equals("0")) {
         String var24 = EASProvision.AllowPOPIMAPEmail;
         var1.add(var24);
      }

      if(var0.AllowIrDA != null && var0.AllowIrDA.equals("0")) {
         String var26 = EASProvision.AllowIrDA;
         var1.add(var26);
      }

      if(var0.AllowDesktopSync != null && var0.AllowDesktopSync.equals("0")) {
         String var28 = EASProvision.AllowDesktopSync;
         var1.add(var28);
      }

      if(var0.AllowSMIMESoftCerts != null && var0.AllowSMIMESoftCerts.equals("0")) {
         String var30 = EASProvision.AllowSMIMESoftCerts;
         var1.add(var30);
      }

      if(var0.AllowConsumerEmail != null && var0.AllowConsumerEmail.equals("0")) {
         String var32 = EASProvision.AllowConsumerEmail;
         var1.add(var32);
      }

      if(var0.AllowRemoteDesktop != null && var0.AllowRemoteDesktop.equals("0")) {
         String var34 = EASProvision.AllowRemoteDesktop;
         var1.add(var34);
      }

      return var1;
   }

   private static int intCompare(String var0, String var1) {
      byte var2;
      if(TextUtils.isEmpty(var0)) {
         var2 = -1;
      } else if(TextUtils.isEmpty(var1)) {
         var2 = 1;
      } else {
         int var3 = Integer.valueOf(var0).intValue();
         int var4 = Integer.valueOf(var1).intValue();
         if(var3 == var4) {
            var2 = 0;
         } else if(var3 > var4) {
            var2 = 1;
         } else {
            var2 = -1;
         }
      }

      return var2;
   }

   public static boolean isEqual(EASProvisionDoc param0, EASProvisionDoc param1) {
      // $FF: Couldn't be decompiled
   }

   public static boolean isEqual(String var0, String var1) {
      byte var2;
      if(var0 == null) {
         if(var1 == null) {
            var2 = 1;
         } else {
            var2 = 0;
         }
      } else {
         var2 = var0.equals(var1);
      }

      return (boolean)var2;
   }

   public static boolean isFullySupport(EASProvisionDoc var0) {
      boolean var1;
      if(var0.DevicePasswordEnabled != null && var0.DevicePasswordEnabled.equals("1")) {
         if(var0.DeviceEncryptionEnabled != null && !var0.DeviceEncryptionEnabled.equals("0")) {
            var1 = false;
            return var1;
         }

         if(var0.RequireDeviceEncryption != null && !var0.RequireDeviceEncryption.equals("0")) {
            var1 = false;
            return var1;
         }
      }

      if(var0.RequireSignedSMIMEMessages != null && !var0.RequireSignedSMIMEMessages.equals("0")) {
         var1 = false;
      } else if(var0.RequireEncryptedSMIMEMessages != null && !var0.RequireEncryptedSMIMEMessages.equals("0")) {
         var1 = false;
      } else if(var0.RequireSignedSMIMEAlgorithm != null && !var0.RequireSignedSMIMEAlgorithm.equals("0")) {
         var1 = false;
      } else if(var0.RequireEncryptionSMIMEAlgorithm != null && !var0.RequireEncryptionSMIMEAlgorithm.equals("0")) {
         var1 = false;
      } else if(var0.UnapprovedInROMApplicationList != null && var0.UnapprovedInROMApplicationList.length > 0) {
         var1 = false;
      } else if(var0.ApprovedApplicationList != null && var0.ApprovedApplicationList.length > 0) {
         var1 = false;
      } else if(var0.AllowSMIMEEncryptionAlgorithmNegotiation != null && var0.AllowSMIMEEncryptionAlgorithmNegotiation.equals("0")) {
         var1 = false;
      } else if(var0.AllowUnsignedApplications != null && var0.AllowUnsignedApplications.equals("0")) {
         var1 = false;
      } else if(var0.AllowUnsignedInstallationPackages != null && var0.AllowUnsignedInstallationPackages.equals("0")) {
         var1 = false;
      } else if(var0.AllowPOPIMAPEmail != null && var0.AllowPOPIMAPEmail.equals("0")) {
         var1 = false;
      } else if(var0.AllowIrDA != null && var0.AllowIrDA.equals("0")) {
         var1 = false;
      } else if(var0.AllowDesktopSync != null && var0.AllowDesktopSync.equals("0")) {
         var1 = false;
      } else if(var0.AllowSMIMEEncryptionAlgorithmNegotiation != null && var0.AllowSMIMEEncryptionAlgorithmNegotiation.equals("0")) {
         var1 = false;
      } else if(var0.AllowSMIMESoftCerts != null && var0.AllowSMIMESoftCerts.equals("0")) {
         var1 = false;
      } else if(var0.AllowConsumerEmail != null && var0.AllowConsumerEmail.equals("0")) {
         var1 = false;
      } else if(var0.AllowRemoteDesktop != null && var0.AllowRemoteDesktop.equals("0")) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static boolean isPasswordSufficient(Context var0, EASProvisionDoc var1) {
      boolean var2;
      if(var1 == null) {
         var2 = true;
      } else {
         DevicePolicyManager var3 = (DevicePolicyManager)var0.getSystemService("device_policy");
         ComponentName var4 = new ComponentName(var0, EASDeviceAdmin.class);
         if(!isEqual(var1.DevicePasswordEnabled, "1")) {
            var2 = true;
         } else if(!var3.isAdminActive(var4)) {
            var2 = false;
         } else {
            boolean var5 = var3.isActivePasswordSufficient();
            String var6 = var1.DevicePasswordExpiration;
            int var7 = -1;
            if(!TextUtils.isEmpty(var6)) {
               var7 = Integer.valueOf(var6).intValue();
            }

            if(var5 && (var7 <= 0 || !EASDeviceAdmin.isPasswordExpire(var0, var7))) {
               var2 = true;
            } else {
               var2 = false;
            }
         }
      }

      return var2;
   }

   private static String maxInt(String var0, String var1) {
      String var2;
      if(TextUtils.isEmpty(var0)) {
         var2 = var1;
      } else if(TextUtils.isEmpty(var1)) {
         var2 = var0;
      } else {
         int var3 = Integer.valueOf(var0).intValue();
         int var4 = Integer.valueOf(var1).intValue();
         if(var3 >= var4) {
            var2 = var0;
         } else {
            var2 = var1;
         }
      }

      return var2;
   }

   private static String minInt(String var0, String var1) {
      String var2;
      if(TextUtils.isEmpty(var0)) {
         var2 = var1;
      } else if(TextUtils.isEmpty(var1)) {
         var2 = var0;
      } else {
         int var3 = Integer.valueOf(var0).intValue();
         int var4 = Integer.valueOf(var1).intValue();
         if(var3 >= var4) {
            var2 = var1;
         } else {
            var2 = var0;
         }
      }

      return var2;
   }

   public static boolean needAdmin(EASProvisionDoc var0) {
      String var1 = var0.AllowWiFi;
      boolean var2;
      if(!"1".equals(var1)) {
         var2 = true;
      } else {
         String var3 = var0.AllowBluetooth;
         if(!"2".equals(var3)) {
            var2 = true;
         } else {
            String var4 = var0.AllowInternetSharing;
            if(!"1".equals(var4)) {
               var2 = true;
            } else {
               String var5 = var0.AllowCamera;
               if(!"1".equals(var5)) {
                  var2 = true;
               } else {
                  String var6 = var0.AllowBrowser;
                  if(!"1".equals(var6)) {
                     var2 = true;
                  } else {
                     String var7 = var0.AllowTextMessaging;
                     if(!"1".equals(var7)) {
                        var2 = true;
                     } else {
                        String var8 = var0.AllowStorageCard;
                        if(!"1".equals(var8)) {
                           var2 = true;
                        } else {
                           var2 = false;
                        }
                     }
                  }
               }
            }
         }
      }

      return var2;
   }

   public static void printProvisionData121(long param0, EASProvisionDoc param2) {
      // $FF: Couldn't be decompiled
   }

   public static Bundle provisionDocToBundle(EASProvisionDoc var0) {
      Bundle var1 = new Bundle();
      String[] var2 = EASSyncCommon.EAS_PROVISION_SUPPORTED;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];

         try {
            String var6 = (String)var0.getClass().getDeclaredField(var5).get(var0);
            if(var6 != null) {
               var1.putString(var5, var6);
            }
         } catch (Exception var9) {
            if(DEBUG) {
               String var8 = "[Provision] Error: " + var5 + "not support.";
               EASLog.v("ProvisionFileUtil", var8);
            }
         }
      }

      return var1;
   }

   public static EASProvisionDoc readProvisionData(Context param0, long param1) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public static void writeProvisionData(Context param0, long param1, EASProvisionDoc param3) throws Exception {
      // $FF: Couldn't be decompiled
   }
}
