package com.android.settings.de;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.deviceencryption.DeviceEncryptionManager;
import android.os.Environment;
import android.os.RecoverySystem;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.IMountService;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.os.storage.IMountService.Stub;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.SecuritySettings;
import java.io.IOException;

public class DeviceEncryption {

   public static final String ACTION_DEVICE_POLICY_MANAGER_STATE_CHANGED = "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED";
   private Activity mContext;
   private LockPatternUtils mLockPatternUtils;
   private Preference mPhone;
   private BroadcastReceiver mPolicyChagnedListener;
   private Preference mSDcard;
   private StorageEventListener mStorageListener;
   private StorageManager mStorageManager = null;


   public DeviceEncryption(Activity var1) {
      DeviceEncryption.1 var2 = new DeviceEncryption.1();
      this.mPolicyChagnedListener = var2;
      DeviceEncryption.2 var3 = new DeviceEncryption.2();
      this.mStorageListener = var3;
      this.mContext = var1;
      if(this.mStorageManager == null) {
         StorageManager var4 = (StorageManager)this.mContext.getSystemService("storage");
         this.mStorageManager = var4;
         StorageManager var5 = this.mStorageManager;
         StorageEventListener var6 = this.mStorageListener;
         var5.registerListener(var6);
      }

      IntentFilter var7 = new IntentFilter();
      var7.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
      Activity var8 = this.mContext;
      BroadcastReceiver var9 = this.mPolicyChagnedListener;
      var8.registerReceiver(var9, var7);
      Activity var11 = this.mContext;
      LockPatternUtils var12 = new LockPatternUtils(var11);
      this.mLockPatternUtils = var12;
   }

   private void decryptPhone() {
      boolean var1 = isSDcardEncrypted();
      this.setPolicyProperty((boolean)0, var1);
      this.setEncryptionStatusOfMountService("internal", (boolean)0);
      boolean var3 = isSDcardEncrypted();
      this.setEncryptionStatusOfMountService("external", var3);
      this.rebootForPhoneEncryption((boolean)0);
   }

   private void decryptSDcard() {
      boolean var1 = isPhoneEncrypted();
      this.setPolicyProperty(var1, (boolean)0);
      boolean var3 = isPhoneEncrypted();
      this.setEncryptionStatusOfMountService("internal", var3);
      this.setEncryptionStatusOfMountService("external", (boolean)0);
      boolean var4 = this.remountSDcard((boolean)0);
   }

   private void encryptPhone() {
      boolean var1 = isSDcardEncrypted();
      this.setPolicyProperty((boolean)1, var1);
      this.setEncryptionStatusOfMountService("internal", (boolean)1);
      boolean var3 = isSDcardEncrypted();
      this.setEncryptionStatusOfMountService("external", var3);
      this.rebootForPhoneEncryption((boolean)1);
   }

   private void encryptSDcard() {
      boolean var1 = isPhoneEncrypted();
      this.setPolicyProperty(var1, (boolean)1);
      boolean var3 = isPhoneEncrypted();
      this.setEncryptionStatusOfMountService("internal", var3);
      this.setEncryptionStatusOfMountService("external", (boolean)1);
      boolean var4 = this.remountSDcard((boolean)1);
   }

   public static boolean isB2CDeviceEncryptionFeatured() {
      boolean var0;
      if(!DeviceEncryptionManager.isDeviceEncryptionEnabled()) {
         var0 = false;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isDeviceEncrypted() {
      boolean var0;
      if(!isPhoneEncrypted() && !isSDcardEncrypted()) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   private boolean isNumericPasswordEnabled() {
      boolean var1;
      if(this.mLockPatternUtils == null) {
         var1 = false;
      } else {
         switch(this.mLockPatternUtils.getKeyguardStoredPasswordQuality()) {
         case 131072:
         case 262144:
         case 327680:
            var1 = true;
            break;
         default:
            var1 = false;
         }
      }

      return var1;
   }

   private static boolean isPhoneEncrypted() {
      return DeviceEncryptionManager.getInternalStorageStatus();
   }

   private boolean isPhonePolicySet() {
      DevicePolicyManager var1 = (DevicePolicyManager)this.mContext.getSystemService("device_policy");
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.getRequireDeviceEncryption((ComponentName)null);
      }

      return (boolean)var2;
   }

   private static boolean isSDcardEncrypted() {
      return DeviceEncryptionManager.getExternalStorageStatus();
   }

   private boolean isSDcardPolicySet() {
      DevicePolicyManager var1 = (DevicePolicyManager)this.mContext.getSystemService("device_policy");
      byte var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.getRequireStorageCardEncryption((ComponentName)null);
      }

      return (boolean)var2;
   }

   private boolean remountSDcard(boolean var1) {
      boolean var2 = true;
      IMountService var3 = Stub.asInterface(ServiceManager.getService("mount"));
      boolean var4;
      if(var3 == null) {
         var4 = false;
      } else {
         label19: {
            try {
               String var5 = Environment.getExternalStorageDirectorySd().getPath();
               if(var3.getVolumeState(var5).equals("mounted")) {
                  var3.unmountVolume(var5, (boolean)1);
                  int var6 = android.os.storage.IEncryptService.Stub.asInterface(ServiceManager.getService("encrypt")).mountVolume((String)null);
                  break label19;
               }
            } catch (Exception var8) {
               break label19;
            }

            var2 = false;
         }

         var4 = var2;
      }

      return var4;
   }

   private void resetPreference() {
      if(this.mPhone != null) {
         Preference var1 = this.mPhone;
         this.setPhonePref(var1);
      }

      if(this.mSDcard != null) {
         Preference var2 = this.mSDcard;
         this.setSDcardPref(var2);
      }
   }

   private void setEncryptionStatusOfMountService(String var1, boolean var2) {
      IMountService var3 = Stub.asInterface(ServiceManager.getService("mount"));
      if(var3 != null) {
         try {
            if(var1.equals("internal")) {
               String var4 = Environment.getExternalStorageDirectory().getPath();
               var3.setEncryptionEnabled(var4, var2);
               return;
            }
         } catch (Exception var9) {
            return;
         }

         if(var1.equals("external")) {
            String var7 = Environment.getExternalStorageDirectorySd().getPath();
            var3.setEncryptionEnabled(var7, var2);
         }
      }
   }

   private void setPhonePref(Preference var1) {
      var1.setKey("DeviceEncryptPhone");
      var1.setTitle(2131231318);
      if(!this.isNumericPasswordEnabled()) {
         var1.setSummary(2131231317);
         var1.setEnabled((boolean)0);
      } else if(isPhoneEncrypted()) {
         var1.setSummary(2131231307);
      } else {
         var1.setSummary(2131231306);
      }

      if(this.isPhonePolicySet()) {
         var1.setEnabled((boolean)0);
         var1.setSummary(2131230974);
      }

      DeviceEncryption.3 var2 = new DeviceEncryption.3();
      var1.setOnPreferenceClickListener(var2);
   }

   private boolean setPolicyProperty(boolean var1, boolean var2) {
      String var3 = DeviceEncryptionManager.getFileCryptProperty("policy");
      String var4 = "";
      if(var1 && var2) {
         var4 = "internal:external";
      } else if(var1) {
         var4 = "internal";
      } else if(var2) {
         var4 = "external";
      }

      boolean var5;
      if(var3 != null && var3.equals(var4)) {
         var5 = false;
      } else {
         int var6 = DeviceEncryptionManager.setFileCryptProperty("policy", var4);
         var5 = true;
      }

      return var5;
   }

   private void setSDcardPref(Preference var1) {
      var1.setKey("DeviceEncryptSDcard");
      var1.setTitle(2131231310);
      if(!this.isNumericPasswordEnabled()) {
         var1.setSummary(2131231317);
         var1.setEnabled((boolean)0);
      } else if(isSDcardEncrypted()) {
         var1.setSummary(2131231312);
      } else {
         var1.setSummary(2131231311);
      }

      if(this.isSDcardPolicySet()) {
         var1.setEnabled((boolean)0);
         var1.setSummary(2131230974);
      }

      DeviceEncryption.4 var2 = new DeviceEncryption.4();
      var1.setOnPreferenceClickListener(var2);
   }

   public void destroy() {
      StorageManager var1 = this.mStorageManager;
      StorageEventListener var2 = this.mStorageListener;
      var1.unregisterListener(var2);
      Activity var3 = this.mContext;
      BroadcastReceiver var4 = this.mPolicyChagnedListener;
      var3.unregisterReceiver(var4);
   }

   public Preference getPreferenceDeviceEncryptionPhone() {
      Activity var1 = this.mContext;
      Preference var2 = new Preference(var1);
      this.mPhone = var2;
      Preference var3 = this.mPhone;
      this.setPhonePref(var3);
      return this.mPhone;
   }

   public Preference getPreferenceDeviceEncryptionSDcard() {
      Activity var1 = this.mContext;
      Preference var2 = new Preference(var1);
      this.mSDcard = var2;
      Preference var3 = this.mSDcard;
      this.setSDcardPref(var3);
      return this.mSDcard;
   }

   protected void rebootForPhoneEncryption(boolean var1) {
      try {
         String var2 = SystemProperties.get("ro.privatedatapartion");
         byte var3;
         if(var1) {
            var3 = 0;
         } else {
            var3 = 1;
         }

         if(var2 != null && var2.equals("true")) {
            RecoverySystem.rebootEncryptPartition(this.mContext, var3, 5);
         } else {
            RecoverySystem.rebootEncryptPartition(this.mContext, var3, 3);
         }
      } catch (IOException var5) {
         ;
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         if(var2.getAction().equals("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED")) {
            DeviceEncryption.this.resetPreference();
         }
      }
   }

   class 2 extends StorageEventListener {

      2() {}

      public void onStorageStateChanged(String var1, String var2, String var3) {
         DeviceEncryption.this.resetPreference();
      }
   }

   class 3 implements OnPreferenceClickListener {

      3() {}

      public boolean onPreferenceClick(Preference var1) {
         if(!DeviceEncryptionManager.getInternalStorageStatus()) {
            Activity var2 = DeviceEncryption.this.mContext;
            Builder var3 = (new Builder(var2)).setMessage(2131231309).setTitle(2131231318).setIcon(17301659);
            DeviceEncryption.3.1 var4 = new DeviceEncryption.3.1();
            AlertDialog var5 = var3.setPositiveButton(17039370, var4).setNegativeButton(17039360, (OnClickListener)null).show();
         } else {
            Activity var6 = DeviceEncryption.this.mContext;
            Builder var7 = (new Builder(var6)).setMessage(2131231308).setTitle(2131231318).setIcon(17301659);
            DeviceEncryption.3.2 var8 = new DeviceEncryption.3.2();
            AlertDialog var9 = var7.setPositiveButton(17039370, var8).setNegativeButton(17039360, (OnClickListener)null).show();
         }

         return false;
      }

      class 1 implements OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            DeviceEncryption.this.encryptPhone();
         }
      }

      class 2 implements OnClickListener {

         2() {}

         public void onClick(DialogInterface var1, int var2) {
            DeviceEncryption.this.decryptPhone();
         }
      }
   }

   class 4 implements OnPreferenceClickListener {

      4() {}

      public boolean onPreferenceClick(Preference var1) {
         if(!DeviceEncryptionManager.getExternalStorageStatus()) {
            Activity var2 = DeviceEncryption.this.mContext;
            Builder var3 = (new Builder(var2)).setMessage(2131231314).setTitle(2131231310).setIcon(17301659);
            DeviceEncryption.4.1 var4 = new DeviceEncryption.4.1();
            AlertDialog var5 = var3.setPositiveButton(17039370, var4).setNegativeButton(17039360, (OnClickListener)null).show();
         } else {
            Activity var6 = DeviceEncryption.this.mContext;
            Builder var7 = (new Builder(var6)).setMessage(2131231313).setTitle(2131231310).setIcon(17301659);
            DeviceEncryption.4.2 var8 = new DeviceEncryption.4.2();
            AlertDialog var9 = var7.setPositiveButton(17039370, var8).setNegativeButton(17039360, (OnClickListener)null).show();
         }

         return false;
      }

      class 2 implements OnClickListener {

         2() {}

         public void onClick(DialogInterface var1, int var2) {
            DeviceEncryption.this.decryptSDcard();
            DeviceEncryption.this.resetPreference();
            ((SecuritySettings)DeviceEncryption.this.mContext).setDisableADBCMenu();
         }
      }

      class 1 implements OnClickListener {

         1() {}

         public void onClick(DialogInterface var1, int var2) {
            DeviceEncryption.this.encryptSDcard();
            DeviceEncryption.this.resetPreference();
            ((SecuritySettings)DeviceEncryption.this.mContext).setDisableADBCMenu();
         }
      }
   }
}
