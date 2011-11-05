package com.android.settings;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import com.android.settings.ChooseLockPassword;
import com.android.settings.ChooseLockPattern;
import com.android.settings.ChooseLockPatternTutorial;
import com.android.settings.ChooseLockSettingsHelper;
import com.android.settings.de.DeviceEncryption;

public class ChooseLockGeneric extends PreferenceActivity {

   static final String CONFIRM_CREDENTIALS = "confirm_credentials";
   private static final int CONFIRM_EXISTING_REQUEST = 100;
   private static final String KEY_UNLOCK_SET_NONE = "unlock_set_none";
   private static final String KEY_UNLOCK_SET_PASSWORD = "unlock_set_password";
   private static final String KEY_UNLOCK_SET_PATTERN = "unlock_set_pattern";
   private static final String KEY_UNLOCK_SET_PIN = "unlock_set_pin";
   private static final int MIN_PASSWORD_LENGTH = 4;
   private static final String PASSWORD_CONFIRMED = "password_confirmed";
   private ChooseLockSettingsHelper mChooseLockSettingsHelper;
   private DevicePolicyManager mDPM;
   private boolean mPasswordConfirmed = 0;


   public ChooseLockGeneric() {}

   private void disableUnusablePreferenceByDeviceEncryption(Preference var1, String var2) {
      if(DeviceEncryption.isB2CDeviceEncryptionFeatured()) {
         if(DeviceEncryption.isDeviceEncrypted()) {
            if("unlock_set_none".equals(var2) || "unlock_set_pattern".equals(var2)) {
               var1.setSummary(2131231319);
               var1.setEnabled((boolean)0);
            }
         }
      }
   }

   private void disableUnusablePreferences(int var1) {
      PreferenceCategory var2 = (PreferenceCategory)this.getPreferenceScreen().findPreference("security_picker_category");
      int var3 = var2.getPreferenceCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         Preference var5 = var2.getPreference(var4);
         if(var5 instanceof PreferenceScreen) {
            String var6 = ((PreferenceScreen)var5).getKey();
            boolean var7 = true;
            if("unlock_set_none".equals(var6)) {
               if(var1 <= 0) {
                  var7 = true;
               } else {
                  var7 = false;
               }
            } else if("unlock_set_pattern".equals(var6)) {
               String var8 = this.getIntent().getStringExtra("email_device_policy");
               if(var8 != null) {
                  if(var8.equals("email")) {
                     if(var1 < 65536) {
                        var7 = true;
                     } else {
                        var7 = false;
                     }
                  } else if(var1 <= 65536) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }
               } else if(var1 <= 65536) {
                  var7 = true;
               } else {
                  var7 = false;
               }
            } else if("unlock_set_pin".equals(var6)) {
               if(var1 <= 131072) {
                  var7 = true;
               } else {
                  var7 = false;
               }
            } else if("unlock_set_password".equals(var6)) {
               if(var1 <= 327680) {
                  var7 = true;
               } else {
                  var7 = false;
               }
            }

            if(!var7) {
               var5.setSummary(2131230974);
               var5.setEnabled((boolean)0);
            }

            if(var7) {
               this.disableUnusablePreferenceByDeviceEncryption(var5, var6);
            }
         }
      }

   }

   private void updatePreferencesOrFinish() {
      int var1 = this.getIntent().getIntExtra("lockscreen.password_type", -1);
      if(var1 == -1) {
         int var2 = this.mChooseLockSettingsHelper.utils().getKeyguardStoredPasswordQuality();
         PreferenceScreen var3 = this.getPreferenceScreen();
         if(var3 != null) {
            var3.removeAll();
         }

         this.addPreferencesFromResource(2130968610);
         int var4 = this.mDPM.getPasswordQuality((ComponentName)null);
         this.disableUnusablePreferences(var4);
      } else {
         this.updateUnlockMethodAndFinish(var1);
      }
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var1 == 100 && var2 == -1) {
         this.mPasswordConfirmed = (boolean)1;
         this.updatePreferencesOrFinish();
      } else {
         this.setResult(0);
         this.finish();
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      DevicePolicyManager var2 = (DevicePolicyManager)this.getSystemService("device_policy");
      this.mDPM = var2;
      ChooseLockSettingsHelper var3 = new ChooseLockSettingsHelper(this);
      this.mChooseLockSettingsHelper = var3;
      if(var1 != null) {
         boolean var4 = var1.getBoolean("password_confirmed");
         this.mPasswordConfirmed = var4;
      }

      if(!this.mPasswordConfirmed) {
         if(!(new ChooseLockSettingsHelper(this)).launchConfirmationActivity(100, (CharSequence)null, (CharSequence)null)) {
            this.mPasswordConfirmed = (boolean)1;
            this.updatePreferencesOrFinish();
         }
      } else {
         this.updatePreferencesOrFinish();
      }
   }

   public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2) {
      String var3 = var2.getKey();
      boolean var4 = true;
      if("unlock_set_none".equals(var3)) {
         this.updateUnlockMethodAndFinish(0);
      } else if("unlock_set_pattern".equals(var3)) {
         this.updateUnlockMethodAndFinish(65536);
      } else if("unlock_set_pin".equals(var3)) {
         this.updateUnlockMethodAndFinish(131072);
      } else if("unlock_set_password".equals(var3)) {
         this.updateUnlockMethodAndFinish(262144);
      } else {
         var4 = false;
      }

      return var4;
   }

   protected void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      boolean var2 = this.mPasswordConfirmed;
      var1.putBoolean("password_confirmed", var2);
   }

   void updateUnlockMethodAndFinish(int var1) {
      if(!this.mPasswordConfirmed) {
         throw new IllegalStateException("Tried to update password without confirming first");
      } else {
         int var2 = this.mDPM.getPasswordQuality((ComponentName)null);
         if(var1 < var2) {
            var1 = var2;
         }

         if(var1 >= 131072) {
            int var3 = this.mDPM.getPasswordMinimumLength((ComponentName)null);
            if(var3 < 4) {
               var3 = 4;
            }

            int var4 = this.mDPM.getPasswordMaximumLength(var1);
            Intent var5 = (new Intent()).setClass(this, ChooseLockPassword.class);
            var5.putExtra("lockscreen.password_type", var1);
            var5.putExtra("lockscreen.password_min", var3);
            var5.putExtra("lockscreen.password_max", var4);
            Intent var9 = var5.putExtra("confirm_credentials", (boolean)0);
            Intent var10 = var5.addFlags(33554432);
            this.startActivity(var5);
         } else if(var1 == 65536) {
            boolean var11;
            if(!this.mChooseLockSettingsHelper.utils().isPatternEverChosen()) {
               var11 = true;
            } else {
               var11 = false;
            }

            Intent var12 = new Intent();
            Class var13;
            if(var11) {
               var13 = ChooseLockPatternTutorial.class;
            } else {
               var13 = ChooseLockPattern.class;
            }

            var12.setClass(this, var13);
            Intent var15 = var12.addFlags(33554432);
            Intent var16 = var12.putExtra("key_lock_method", "pattern");
            Intent var17 = var12.putExtra("confirm_credentials", (boolean)0);
            this.startActivity(var12);
         } else if(var1 == 0) {
            this.mChooseLockSettingsHelper.utils().clearLock();
            this.setResult(-1);
         }

         this.finish();
      }
   }
}
