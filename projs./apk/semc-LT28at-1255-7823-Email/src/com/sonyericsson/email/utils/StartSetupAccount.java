package com.sonyericsson.email.utils;

import android.app.Activity;
import android.content.Context;
import com.android.email.activity.setup.AccountSetupBasics;
import com.sonyericsson.email.ui.BrandedAccountsList;

public class StartSetupAccount {

   public StartSetupAccount() {}

   public static void startSettingUpAccountFlow(Context var0, boolean var1) {
      if(var0 != null) {
         if(BrandedAccountsList.checkBrandAccountSettings(var0) == 1) {
            BrandedAccountsList.actionSetupAccount((Activity)var0, var1);
         } else {
            AccountSetupBasics.actionNewAccount((Activity)var0, var1);
         }
      }
   }
}
