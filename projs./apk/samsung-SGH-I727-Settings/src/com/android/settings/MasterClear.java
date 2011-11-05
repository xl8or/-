package com.android.settings;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.ChooseLockSettingsHelper;
import com.android.settings.Password;
import com.android.settings.Utils;

public class MasterClear extends Activity {

   private static final int CONFIRM_PHONE_PASSWORD = 101;
   private static final int KEYGUARD_REQUEST = 55;
   private static final String LOG_TAG = "MasterClear";
   private static final int REQUEST_CHANGE_FIRST_TIME_PHONE_PASSWORD = 100;
   private static final String SYSTEM_PHONE_PASSWORD = "SYSTEM_PHONE_PASSWORD";
   private static TelephonyManager mTelMan;
   private CheckBox mExternalStorage;
   private View mExternalStorageContainer;
   private Button mFinalButton;
   private OnClickListener mFinalClickListener;
   private View mFinalView;
   private LayoutInflater mInflater;
   private View mInitialView;
   private Button mInitiateButton;
   private OnClickListener mInitiateListener;
   private LockPatternUtils mLockUtils;
   private String mNewPassword;
   private String mOldPassword;
   private MasterClear.PWState mPWState;


   public MasterClear() {
      MasterClear.PWState var1 = MasterClear.PWState.CURRENT;
      this.mPWState = var1;
      MasterClear.1 var2 = new MasterClear.1();
      this.mFinalClickListener = var2;
      MasterClear.2 var3 = new MasterClear.2();
      this.mInitiateListener = var3;
   }

   private void DoMasterReset() {
      this.mInitiateButton.setEnabled((boolean)0);
      this.mFinalButton.setEnabled((boolean)0);
      if(!Utils.isMonkeyRunning()) {
         if(this.mExternalStorage.isChecked()) {
            this.factoryResetAndClean((boolean)1);
         } else {
            this.factoryResetAndClean((boolean)0);
         }
      }
   }

   private void SetFirstTimePhonepassword(Intent var1) {
      int[] var2 = MasterClear.4.$SwitchMap$com$android$settings$MasterClear$PWState;
      int var3 = this.mPWState.ordinal();
      switch(var2[var3]) {
      case 1:
         String var4 = var1.getStringExtra(".password");
         this.mNewPassword = var4;
         StringBuilder var5 = (new StringBuilder()).append("1old phone password = ");
         String var6 = this.mOldPassword;
         String var7 = var5.append(var6).toString();
         int var8 = Log.e("MasterClear", var7);
         StringBuilder var9 = (new StringBuilder()).append("1new phone password = ");
         String var10 = this.mNewPassword;
         String var11 = var9.append(var10).toString();
         int var12 = Log.e("MasterClear", var11);
         MasterClear.PWState var13 = MasterClear.PWState.CONFIRM;
         this.mPWState = var13;
         String var14 = this.getString(2131232430);
         String var15 = this.getString(2131232434);
         String var16 = this.mNewPassword;
         this.queryPhonepassword(var14, var15, var16, 100);
         return;
      case 2:
         MasterClear.PWState var17 = MasterClear.PWState.CURRENT;
         this.mPWState = var17;
         StringBuilder var18 = (new StringBuilder()).append("2old phone password = ");
         String var19 = this.mOldPassword;
         String var20 = var18.append(var19).toString();
         int var21 = Log.e("MasterClear", var20);
         StringBuilder var22 = (new StringBuilder()).append("2new phone password = ");
         String var23 = this.mNewPassword;
         String var24 = var22.append(var23).toString();
         int var25 = Log.e("MasterClear", var24);
         ContentResolver var26 = this.getContentResolver();
         String var27 = this.mNewPassword;
         Secure.putString(var26, "SYSTEM_PHONE_PASSWORD", var27);
         this.DoMasterReset();
         return;
      default:
      }
   }

   private void StartPassword() {
      String var1 = Secure.getString(this.getContentResolver(), "SYSTEM_PHONE_PASSWORD");
      if(var1 == null) {
         MasterClear.PWState var2 = MasterClear.PWState.NEW;
         this.mPWState = var2;
         String var3 = this.getString(2131232430);
         String var4 = this.getString(2131232431);
         String var5 = this.getString(2131232432);
         this.queryPhonepassword(var3, var4, var5, 100);
      } else {
         String var6 = this.getString(2131232430);
         String var7 = this.getString(2131232434);
         this.queryPhonepassword(var6, var7, var1, 101);
      }
   }

   private void establishFinalConfirmationState() {
      if(this.mFinalView == null) {
         View var1 = this.mInflater.inflate(2130903091, (ViewGroup)null);
         this.mFinalView = var1;
         Button var2 = (Button)this.mFinalView.findViewById(2131427493);
         this.mFinalButton = var2;
         Button var3 = this.mFinalButton;
         OnClickListener var4 = this.mFinalClickListener;
         var3.setOnClickListener(var4);
      }

      View var5 = this.mFinalView;
      this.setContentView(var5);
      boolean var6 = this.mFinalButton.requestFocus();
   }

   private void establishInitialState() {
      if(this.mInitialView == null) {
         View var1 = this.mInflater.inflate(2130903092, (ViewGroup)null);
         this.mInitialView = var1;
         Button var2 = (Button)this.mInitialView.findViewById(2131427496);
         this.mInitiateButton = var2;
         Button var3 = this.mInitiateButton;
         OnClickListener var4 = this.mInitiateListener;
         var3.setOnClickListener(var4);
         View var5 = this.mInitialView.findViewById(2131427494);
         this.mExternalStorageContainer = var5;
         CheckBox var6 = (CheckBox)this.mInitialView.findViewById(2131427495);
         this.mExternalStorage = var6;
         View var7 = this.mExternalStorageContainer;
         MasterClear.3 var8 = new MasterClear.3();
         var7.setOnClickListener(var8);
      }

      View var9 = this.mInitialView;
      this.setContentView(var9);
   }

   private void factoryResetAndClean(boolean var1) {
      int var2 = Log.i("MasterClear", "Ready to factory reset");
      Intent var3 = new Intent("android.intent.action.MAIN");
      Intent var4 = var3.addCategory("android.intent.category.LAUNCHER");
      ComponentName var5 = new ComponentName("com.android.settings", "com.android.settings.SecFactoryReset");
      var3.setComponent(var5);
      var3.putExtra("FACTORY", var1);
      this.startActivity(var3);
   }

   public static boolean isSimEnabled() {
      mTelMan = TelephonyManager.getDefault();
      boolean var0;
      if(mTelMan.getSimState() != 0 && mTelMan.getSimState() != 1) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   private boolean runKeyguardConfirmation(int var1) {
      ChooseLockSettingsHelper var2 = new ChooseLockSettingsHelper(this);
      CharSequence var3 = this.getText(2131231461);
      CharSequence var4 = this.getText(2131231462);
      return var2.launchConfirmationActivity(var1, var3, var4);
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      switch(var1) {
      case 55:
         if(var2 == -1) {
            this.establishFinalConfirmationState();
            return;
         } else {
            if(var2 == 0) {
               this.finish();
               return;
            }

            this.establishInitialState();
            return;
         }
      case 100:
         if(var2 != -1) {
            return;
         }

         this.SetFirstTimePhonepassword(var3);
         return;
      case 101:
         if(var2 != -1) {
            return;
         }

         this.DoMasterReset();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.mInitialView = null;
      this.mFinalView = null;
      LayoutInflater var2 = LayoutInflater.from(this);
      this.mInflater = var2;
      LockPatternUtils var3 = new LockPatternUtils(this);
      this.mLockUtils = var3;
      this.establishInitialState();
   }

   public void onPause() {
      super.onPause();
      if(!this.isFinishing()) {
         this.establishInitialState();
      }
   }

   protected void queryPhonepassword(String var1, String var2, String var3, int var4) {
      Intent var5 = new Intent(this, Password.class);
      var5.putExtra(".title", var1);
      var5.putExtra(".subject", var2);
      var5.putExtra(".password", var3);
      this.startActivityForResult(var5, var4);
   }

   private static enum PWState {

      // $FF: synthetic field
      private static final MasterClear.PWState[] $VALUES;
      CONFIRM("CONFIRM", 2),
      CURRENT("CURRENT", 0),
      NEW("NEW", 1);


      static {
         MasterClear.PWState[] var0 = new MasterClear.PWState[3];
         MasterClear.PWState var1 = CURRENT;
         var0[0] = var1;
         MasterClear.PWState var2 = NEW;
         var0[1] = var2;
         MasterClear.PWState var3 = CONFIRM;
         var0[2] = var3;
         $VALUES = var0;
      }

      private PWState(String var1, int var2) {}
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(View var1) {
         MasterClear.this.mExternalStorage.toggle();
      }
   }

   // $FF: synthetic class
   static class 4 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$settings$MasterClear$PWState = new int[MasterClear.PWState.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$settings$MasterClear$PWState;
            int var1 = MasterClear.PWState.NEW.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$settings$MasterClear$PWState;
            int var3 = MasterClear.PWState.CONFIRM.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(View var1) {
         if(MasterClear.isSimEnabled()) {
            MasterClear.this.DoMasterReset();
         } else {
            MasterClear.this.StartPassword();
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         if(AccountManager.get(MasterClear.this).getAccountsByType("com.osp.app.signin").length != 0) {
            Toast.makeText(MasterClear.this, 2131232235, 1).show();
         } else if(!MasterClear.this.runKeyguardConfirmation(55)) {
            MasterClear.this.establishFinalConfirmationState();
         }
      }
   }
}
