package com.digc.seven;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import com.android.email.Email;
import com.android.email.activity.setup.AccountSetupBasics;
import com.android.email.activity.setup.AccountSetupBasicsPremium;
import com.android.email.activity.setup.AccountSetupCustomer;
import com.digc.seven.SevenSyncProvider;
import com.seven.Z7.app.Z7AppBaseActivity;
import com.seven.Z7.common.Z7ServiceCallback;
import com.seven.Z7.common.provisioning.Z7ProvisioningResponse;
import com.seven.Z7.shared.Z7ServiceConstants;
import com.seven.Z7.util.Z7ErrorCode2;
import com.seven.util.IntArrayMap;
import com.seven.util.Z7Result;
import java.util.Iterator;

public class CheckPremiumUser extends Z7AppBaseActivity {

   private static final boolean EMAIL_COMBINDED = true;
   private static final boolean EMAIL_NORMAL = false;
   private static final String EXTRA_NEXT_ACTIVITY = "extra_next_activity";
   public static final String EXTRA_SELECTED_PROVISION_NAME = "extra_selected_provision_name";
   private Class<?> cls;
   private boolean isActionForAddAccount;
   private CheckPremiumUser.MyListener mListener;
   private String selectedProvisionName;


   public CheckPremiumUser() {}

   public static void actionCheckPremiumUser(Intent var0, Context var1, Class<?> var2) {
      var0.setClass(var1, CheckPremiumUser.class);
      Intent var5 = var0.putExtra("extra_next_activity", var2);
      var1.startActivity(var0);
   }

   private void callGACForSevenEngine() {
      this.showProgressDialogForProvision();
      CheckPremiumUser.MyListener var1 = this.mListener;
      this.initializeSevenEngineAndCallGAC(var1);
      this.startConnectionTimeOut();
   }

   private void resultForIsPremiumUser(boolean var1) {
      this.dismissProgressDialog();
      Intent var2 = this.getIntent();
      if(this.isActionForAddAccount) {
         if(!var1) {
            this.showDialog(10002);
            return;
         }

         var2.setClass(this, AccountSetupBasicsPremium.class);
         String var4 = this.selectedProvisionName;
         var2.putExtra("extra_selected_provision_name", var4);
      } else {
         Class var6 = this.cls;
         var2.setClass(this, var6);
      }

      this.startActivity(var2);
      this.finish();
   }

   private void showProgressDialogForProvision() {
      CheckPremiumUser.1 var1 = new CheckPremiumUser.1();
      this.showProgressDialog(var1);
   }

   protected void negativeActionForDialog(int var1) {
      switch(var1) {
      case 10002:
         this.finish();
         return;
      default:
      }
   }

   protected void onCreate(Bundle var1) {
      boolean var2 = this.requestWindowFeature(1);
      super.onCreate(var1);
      if(!AccountSetupCustomer.getInstance().getEmailType()) {
         AccountSetupBasics.actionNewAccountSetType(this, 0);
         this.finish();
      } else if(!SevenSyncProvider.checkSevenApkVer(this)) {
         Intent var3 = this.getIntent();
         if(var3.getAction().equals("intent.seven.action.ADD_ACCOUNT")) {
            this.isActionForAddAccount = (boolean)1;
         } else {
            Class var6 = (Class)var3.getSerializableExtra("extra_next_activity");
            this.cls = var6;
         }

         this.setContentView(2130903076);
         Handler var4 = this.handler;
         CheckPremiumUser.MyListener var5 = new CheckPremiumUser.MyListener(var4);
         this.mListener = var5;
         AccountSetupBasics.actionNewAccountSetType(this, 1);
         this.finish();
      }
   }

   protected void onDestroy() {
      this.stopConnectionTimeOut();
      super.onDestroy();
   }

   protected void onPause() {
      super.onPause();
   }

   protected void onResume() {
      super.onResume();
   }

   private class MyListener extends Email.Z7ConnectionListener {

      public MyListener(Handler var2) {
         super(var2);
      }

      private String getSelectedBrandId() {
         String var1 = CheckPremiumUser.this.getIntent().getStringExtra("accountType");
         String var2;
         if(var1.equals("com.seven.Z7.work")) {
            var2 = "owa";
         } else if(var1.equals("com.seven.Z7.gmail")) {
            var2 = "gmail";
         } else if(var1.equals("com.seven.Z7.msn")) {
            var2 = "msn";
         } else if(var1.equals("com.seven.Z7.yahoo")) {
            var2 = "yahoo";
         } else {
            var2 = "";
         }

         return var2;
      }

      public void onCallback(Z7ServiceCallback var1) {
         Z7ServiceConstants.SystemCallbackType var2 = var1.getSystemCallbackType();
         if(var2 != null) {
            int[] var3 = CheckPremiumUser.2.$SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var4 = var2.ordinal();
            switch(var3[var4]) {
            case 1:
               int var5 = var1.getResultCode();
               Z7Result var6 = new Z7Result(var5);
               CheckPremiumUser.this.stopConnectionTimeOut();
               if(!Z7Result.Z7_SUCCEEDED(var6)) {
                  int var16 = var1.getErrorCode();
                  int var17 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SUBSCRIPTION_EXPIRED.getValue();
                  if(var16 != var17) {
                     int var18 = var1.getErrorCode();
                     int var19 = Z7ErrorCode2.Z7ErrorCode.Z7_ERR_SERVICE_SUBSCRIPTION_REQUIRED.getValue();
                     if(var18 != var19) {
                        return;
                     }
                  }

                  CheckPremiumUser.this.resultForIsPremiumUser((boolean)0);
                  return;
               } else {
                  IntArrayMap var7 = (IntArrayMap)var1.getObject();
                  Z7ProvisioningResponse var8 = new Z7ProvisioningResponse(var7);
                  if(var8.isImScope()) {
                     return;
                  }

                  String var9 = this.getSelectedBrandId();
                  Iterator var10 = var8.getConnectors().iterator();

                  while(var10.hasNext()) {
                     IntArrayMap var11 = (IntArrayMap)var10.next();
                     String var12 = var11.getString(49);
                     if(var12 != null && var12.equals(var9)) {
                        CheckPremiumUser var13 = CheckPremiumUser.this;
                        String var14 = var11.getString(11);
                        var13.selectedProvisionName = var14;
                        break;
                     }
                  }

                  CheckPremiumUser.this.resultForIsPremiumUser((boolean)1);
                  CheckPremiumUser.this.changePrefToPremiumConnector();
                  return;
               }
            default:
            }
         }
      }
   }

   class 1 implements OnKeyListener {

      1() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var4;
         if(var2 == 4) {
            CheckPremiumUser.this.dismissProgressDialog();
            CheckPremiumUser.this.finish();
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType = new int[Z7ServiceConstants.SystemCallbackType.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$seven$Z7$shared$Z7ServiceConstants$SystemCallbackType;
            int var1 = Z7ServiceConstants.SystemCallbackType.Z7_CALLBACK_PROVISIONING_CONNECTORS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }
      }
   }
}
