package com.google.android.finsky.billing.creditcard;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.finsky.activities.CreateInstrumentActivity;
import com.google.android.finsky.billing.creditcard.CreateCreditCardFlow;
import com.google.android.finsky.layout.CustomActionBarFactory;
import com.google.android.finsky.utils.FinskyLog;

public class AddCreditCardActivity extends CreateInstrumentActivity {

   private static final String KEY_CARDHOLDER_NAME = "cardholder_name";


   public AddCreditCardActivity() {}

   private void removeActionBar() {
      CustomActionBarFactory.getInstance(this).hide();
   }

   protected void onCreate(Bundle var1) {
      Intent var2 = this.getIntent();
      Intent var3 = var2.putExtra("billing_flow", 0);
      super.onCreate(var1);
      this.removeActionBar();
      if(this.mBillingFlowParameters.getString("referrer_url") == null) {
         Bundle var4 = this.mBillingFlowParameters;
         StringBuilder var5 = (new StringBuilder()).append("externalPackage?pkg=");
         String var6 = this.getCallingPackage();
         String var7 = var5.append(var6).toString();
         var4.putString("referrer_url", var7);
      }

      Bundle var8 = this.mBillingFlowParameters;
      String var9 = var2.getStringExtra("cardholder_name");
      var8.putString("cardholder_name", var9);
      Bundle var10 = this.mBillingFlowParameters;
      String var11 = CreateCreditCardFlow.PARAM_VALUE_MODE_EXTERNAL;
      var10.putString("mode", var11);
   }

   protected void setTitle(String var1) {
      Object[] var2 = new Object[]{var1};
      FinskyLog.d("Swallowing title: %s", var2);
   }
}
