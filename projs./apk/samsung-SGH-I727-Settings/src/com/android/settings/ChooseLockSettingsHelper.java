package com.android.settings;

import android.app.Activity;
import android.content.Intent;
import com.android.internal.widget.LockPatternUtils;

public class ChooseLockSettingsHelper {

   private Activity mActivity;
   private LockPatternUtils mLockPatternUtils;


   public ChooseLockSettingsHelper(Activity var1) {
      this.mActivity = var1;
      LockPatternUtils var2 = new LockPatternUtils(var1);
      this.mLockPatternUtils = var2;
   }

   private boolean confirmPassword(int var1) {
      boolean var2;
      if(!this.mLockPatternUtils.isLockPasswordEnabled()) {
         var2 = false;
      } else {
         Intent var3 = new Intent();
         Intent var4 = var3.setClassName("com.android.settings", "com.android.settings.ConfirmLockPassword");
         this.mActivity.startActivityForResult(var3, var1);
         var2 = true;
      }

      return var2;
   }

   private boolean confirmPattern(int var1, CharSequence var2, CharSequence var3) {
      boolean var4;
      if(this.mLockPatternUtils.isLockPatternEnabled() && this.mLockPatternUtils.savedPatternExists()) {
         Intent var5 = new Intent();
         var5.putExtra("com.android.settings.ConfirmLockPattern.header", var2);
         var5.putExtra("com.android.settings.ConfirmLockPattern.footer", var3);
         Intent var8 = var5.setClassName("com.android.settings", "com.android.settings.ConfirmLockPattern");
         this.mActivity.startActivityForResult(var5, var1);
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   protected boolean launchConfirmationActivity(int var1, CharSequence var2, CharSequence var3) {
      byte var4 = 0;
      switch(this.mLockPatternUtils.getKeyguardStoredPasswordQuality()) {
      case 65536:
         var4 = this.confirmPattern(var1, var2, var3);
         break;
      case 131072:
      case 262144:
      case 327680:
         var4 = this.confirmPassword(var1);
      }

      return (boolean)var4;
   }

   public LockPatternUtils utils() {
      return this.mLockPatternUtils;
   }
}
