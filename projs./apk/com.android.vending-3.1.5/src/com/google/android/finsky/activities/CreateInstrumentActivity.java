package com.google.android.finsky.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.ViewGroup;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.FopFactory;
import com.google.android.finsky.billing.carrierbilling.CarrierBillingInstrument;
import com.google.android.finsky.billing.creditcard.CreditCardInstrument;
import com.google.android.finsky.utils.FinskyLog;

public abstract class CreateInstrumentActivity extends FragmentActivity implements BillingFlowContext, BillingFlowListener {

   public static final String ACCOUNT_NAME_EXTRA = "authAccount";
   public static final String BACKEND_ID_EXTRA = "backend_id";
   public static final String BILLING_FLOW_CANCELED_RESULT_EXTRA = "billing_flow_canceled";
   public static final String BILLING_FLOW_ERROR_MESSAGE_RESULT_EXTRA = "billing_flow_error_message";
   public static final String BILLING_FLOW_ERROR_RESULT_EXTRA = "billing_flow_error";
   public static final String INSTRUMENT_FAMILY_EXTRA = "billing_flow";
   protected static final String KEY_SAVED_FLOW_STATE = "flow_state";
   protected Bundle mBillingFlowParameters;
   private FopFactory mFopFactory;
   protected ViewGroup mFragmentContainer;
   private BillingFlow mRunningFlow;
   private boolean mSaveInstanceStateCalled;
   protected Bundle mSavedFlowState;


   public CreateInstrumentActivity() {
      Bundle var1 = new Bundle();
      this.mBillingFlowParameters = var1;
   }

   private void startOrResumeFlow(Bundle var1) {
      int var2 = this.getIntent().getIntExtra("billing_flow", 0);
      FopFactory var3 = this.mFopFactory;
      Bundle var4 = this.mBillingFlowParameters;
      BillingFlow var5 = var3.create(var2, this, this, var4);
      this.mRunningFlow = var5;
      if(this.mRunningFlow == null) {
         Object[] var6 = new Object[1];
         Integer var7 = Integer.valueOf(var2);
         var6[0] = var7;
         FinskyLog.w("Couldn\'t instantiate BillingFlow for FOP type %d", var6);
         this.finish();
      } else {
         this.mFragmentContainer.setVisibility(0);
         this.findViewById(2131755243).setVisibility(8);
         if(var1 == null) {
            this.mRunningFlow.start();
         } else {
            this.mRunningFlow.resumeFromSavedState(var1);
         }
      }
   }

   private void stopFlow() {
      this.mFragmentContainer.setVisibility(8);
      this.mRunningFlow = null;
   }

   public void hideFragment(Fragment var1, boolean var2) {
      if(!this.mSaveInstanceStateCalled) {
         FragmentTransaction var3 = this.getSupportFragmentManager().beginTransaction();
         var3.remove(var1);
         if(var2) {
            FragmentTransaction var5 = var3.addToBackStack((String)null);
         }

         int var6 = var3.commit();
      }
   }

   public void onBackPressed() {
      if(this.mRunningFlow != null) {
         if(this.mRunningFlow.canGoBack()) {
            this.mRunningFlow.back();
         } else {
            Object[] var1 = new Object[0];
            FinskyLog.d("Cannot interrupt the current flow.", var1);
         }
      } else {
         super.onBackPressed();
      }
   }

   protected void onCreate(Bundle var1) {
      this.setContentView(2130968586);
      super.onCreate(var1);
      ViewGroup var2 = (ViewGroup)this.findViewById(2131755130);
      this.mFragmentContainer = var2;
      String var3 = this.getIntent().getStringExtra("authAccount");
      if(!AccountHandler.hasAccount(var3, this)) {
         throw new IllegalArgumentException("Invalid account supplied in the intent.");
      } else {
         this.mBillingFlowParameters.putString("authAccount", var3);
         Bundle var4 = this.mBillingFlowParameters;
         String var5 = this.getIntent().getStringExtra("referrer_url");
         var4.putString("referrer_url", var5);
         Bundle var6 = this.mBillingFlowParameters;
         String var7 = this.getIntent().getStringExtra("referrer_list_cookie");
         var6.putString("referrer_list_cookie", var7);
         if(var1 != null) {
            Bundle var8 = var1.getBundle("flow_state");
            this.mSavedFlowState = var8;
         }

         Looper var9 = Looper.getMainLooper();
         Handler var10 = new Handler(var9);
         CreateInstrumentActivity.1 var11 = new CreateInstrumentActivity.1();
         var10.post(var11);
      }
   }

   protected void onDestroy() {
      super.onDestroy();
      this.stopFlow();
   }

   public void onError(BillingFlow var1, String var2) {
      Intent var3 = new Intent();
      Intent var4 = var3.putExtra("billing_flow_error", (boolean)1);
      var3.putExtra("billing_flow_error_message", var2);
      this.setResult(-1, var3);
      this.finish();
   }

   public void onFinished(BillingFlow var1, boolean var2) {
      Intent var3 = new Intent();
      Intent var4 = var3.putExtra("billing_flow_error", (boolean)0);
      var3.putExtra("billing_flow_canceled", var2);
      this.setResult(-1, var3);
      this.finish();
   }

   public boolean onOptionsItemSelected(MenuItem var1) {
      boolean var2;
      switch(var1.getItemId()) {
      case 16908332:
         this.onBackPressed();
         var2 = true;
         break;
      default:
         var2 = super.onOptionsItemSelected(var1);
      }

      return var2;
   }

   protected void onSaveInstanceState(Bundle var1) {
      this.mSaveInstanceStateCalled = (boolean)1;
      super.onSaveInstanceState(var1);
      if(this.mRunningFlow != null) {
         Bundle var2 = new Bundle();
         this.mRunningFlow.saveState(var2);
         var1.putBundle("flow_state", var2);
      }
   }

   public void persistFragment(Bundle var1, String var2, Fragment var3) {
      this.getSupportFragmentManager().putFragment(var1, var2, var3);
   }

   public void popFragmentStack() {
      if(!this.mSaveInstanceStateCalled) {
         this.getSupportFragmentManager().popBackStack();
      }
   }

   public Fragment restoreFragment(Bundle var1, String var2) {
      return this.getSupportFragmentManager().getFragment(var1, var2);
   }

   protected abstract void setTitle(String var1);

   public void showDialogFragment(DialogFragment var1, String var2) {
      if(!this.mSaveInstanceStateCalled) {
         FragmentTransaction var3 = this.getSupportFragmentManager().beginTransaction();
         Fragment var4 = this.getSupportFragmentManager().findFragmentByTag(var2);
         if(var4 != null) {
            var3.remove(var4);
         }

         FragmentTransaction var6 = var3.addToBackStack((String)null);
         FragmentManager var7 = this.getSupportFragmentManager();
         var1.show(var7, var2);
      }
   }

   public void showFragment(Fragment var1, int var2, boolean var3) {
      if(!this.mSaveInstanceStateCalled) {
         if(var2 != -1) {
            String var4 = this.getString(var2);
            this.setTitle(var4);
         } else {
            this.setTitle((String)null);
         }

         FragmentTransaction var5 = this.getSupportFragmentManager().beginTransaction();
         var5.add(2131755130, var1);
         if(var3) {
            FragmentTransaction var7 = var5.addToBackStack((String)null);
         }

         int var8 = var5.commitAllowingStateLoss();
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         CreateInstrumentActivity var1 = CreateInstrumentActivity.this;
         FopFactory var2 = new FopFactory();
         var1.mFopFactory = var2;
         CreditCardInstrument.registerWithFactory(CreateInstrumentActivity.this.mFopFactory);
         CarrierBillingInstrument.registerWithFactory(CreateInstrumentActivity.this.mFopFactory);
         CreateInstrumentActivity var4 = CreateInstrumentActivity.this;
         Bundle var5 = CreateInstrumentActivity.this.mSavedFlowState;
         var4.startOrResumeFlow(var5);
      }
   }
}
