package com.google.android.finsky.billing.carrierbilling.flow;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.carrierbilling.action.CarrierCredentialsAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierProvisioningAction;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingPasswordDialogFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierTosDialogFragment;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingCredentials;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.config.G;
import com.google.android.finsky.utils.FinskyLog;

public class CompleteCarrierBillingFlow extends BillingFlow implements CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener, CarrierTosDialogFragment.CarrierTosResultListener {

   private static final String KEY_PASSWORD_FRAGMENT = "password_fragment";
   private static final String KEY_STATE = "state";
   private static final String KEY_TOS_FRAGMENT = "tos_fragment";
   private final Analytics mAnalytics;
   private final BillingFlowContext mContext;
   private CarrierCredentialsAction mCredentialsAction;
   private boolean mCredentialsCheckPerformed;
   private CarrierBillingParameters mParams;
   private String mPassword;
   private CarrierBillingPasswordDialogFragment mPasswordFragment;
   private CarrierBillingProvisioning mProvisioning;
   private CarrierProvisioningAction mProvisioningAction;
   private String mReferrerListCookie;
   private String mReferrerUrl;
   private CompleteCarrierBillingFlow.State mState;
   private final CarrierBillingStorage mStorage;
   private CarrierTosDialogFragment mTosFragment;
   private int mTosNumber;


   public CompleteCarrierBillingFlow(BillingFlowContext var1, BillingFlowListener var2, Analytics var3, Bundle var4) {
      CarrierBillingStorage var5 = BillingLocator.getCarrierBillingStorage();
      this(var1, var2, var5, var3, var4);
      CompleteCarrierBillingFlow.State var11 = CompleteCarrierBillingFlow.State.CHECK_CARRIER_TOS_VERSION;
      this.mState = var11;
      this.mCredentialsCheckPerformed = (boolean)0;
      CarrierProvisioningAction var12 = new CarrierProvisioningAction();
      this.mProvisioningAction = var12;
      CarrierCredentialsAction var13 = new CarrierCredentialsAction();
      this.mCredentialsAction = var13;
   }

   CompleteCarrierBillingFlow(BillingFlowContext var1, BillingFlowListener var2, CarrierBillingStorage var3, Analytics var4, Bundle var5) {
      super(var1, var2, var5);
      this.mTosNumber = 0;
      this.mContext = var1;
      this.mStorage = var3;
      CarrierBillingParameters var6 = this.mStorage.getParams();
      this.mParams = var6;
      CarrierBillingProvisioning var7 = this.mStorage.getProvisioning();
      this.mProvisioning = var7;
      this.mAnalytics = var4;
      if(var5 != null) {
         String var8 = var5.getString("referrer_url");
         this.mReferrerUrl = var8;
         String var9 = var5.getString("referrer_list_cookie");
         this.mReferrerListCookie = var9;
      }
   }

   private void log(String var1) {
      Analytics var2 = this.mAnalytics;
      String var3 = this.mReferrerUrl;
      String var4 = this.mReferrerListCookie;
      var2.logPageView(var3, var4, var1);
   }

   void createAndShowPasswordFragment() {
      this.log("dcbPinEntry");
      CarrierBillingPasswordDialogFragment var1 = new CarrierBillingPasswordDialogFragment();
      this.mPasswordFragment = var1;
      this.mPasswordFragment.setOnResultListener(this);
      BillingFlowContext var2 = this.mBillingFlowContext;
      CarrierBillingPasswordDialogFragment var3 = this.mPasswordFragment;
      var2.showDialogFragment(var3, "PasswordDialog");
   }

   void createAndShowTosFragment() {
      this.log("dcbTosChanged");
      CarrierTosDialogFragment var1 = new CarrierTosDialogFragment();
      this.mTosFragment = var1;
      this.mTosFragment.setOnResultListener(this);
      BillingFlowContext var2 = this.mBillingFlowContext;
      CarrierTosDialogFragment var3 = this.mTosFragment;
      StringBuilder var4 = (new StringBuilder()).append("TosDialog");
      int var5 = this.mTosNumber;
      String var6 = var4.append(var5).toString();
      var2.showDialogFragment(var3, var6);
      int var7 = this.mTosNumber + 1;
      this.mTosNumber = var7;
   }

   boolean credentialTimeStillValid(long var1, long var3, long var5) {
      boolean var7;
      if(var1 - var3 > var5) {
         var7 = true;
      } else {
         var7 = false;
      }

      return var7;
   }

   boolean credentialsStillValid(CarrierBillingCredentials var1) {
      boolean var2;
      if(var1 != null && var1.isProvisioned() && !TextUtils.isEmpty(var1.getCredentials())) {
         long var3 = var1.getExpirationTime();
         long var5 = ((Long)G.vendingCarrierCredentialsBufferMs.get()).longValue();
         long var7 = System.currentTimeMillis();
         var2 = this.credentialTimeStillValid(var3, var5, var7);
      } else {
         var2 = false;
      }

      return var2;
   }

   CompleteCarrierBillingFlow.State getState() {
      return this.mState;
   }

   public void onCarrierBillingPasswordResult(CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var1, String var2) {
      CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var3 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.SUCCESS;
      if(!var1.equals(var3) && this.mPasswordFragment != null) {
         this.mPasswordFragment.dismiss();
         this.mPasswordFragment = null;
      }

      int[] var4 = CompleteCarrierBillingFlow.1.$SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult;
      int var5 = var1.ordinal();
      switch(var4[var5]) {
      case 1:
         this.log("dcbPinEntryConfirm");
         this.mPassword = var2;
         this.performNext();
         return;
      case 2:
         this.log("dcbPinEntryCancel");
         this.cancel();
         return;
      case 3:
         Object[] var6 = new Object[0];
         FinskyLog.d("Getting password info failed.", var6);
         String var7 = FinskyApp.get().getString(2131230818);
         this.fail(var7);
         return;
      default:
      }
   }

   public void onCarrierTosResult(CarrierTosDialogFragment.CarrierTosResultListener.TosResult var1) {
      this.mTosFragment.dismiss();
      this.mTosFragment = null;
      int[] var2 = CompleteCarrierBillingFlow.1.$SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierTosDialogFragment$CarrierTosResultListener$TosResult;
      int var3 = var1.ordinal();
      switch(var2[var3]) {
      case 1:
         this.log("dcbTosChangedConfirm");
         CarrierProvisioningAction var4 = this.mProvisioningAction;
         CompleteCarrierBillingFlow.AfterProvisioning var5 = new CompleteCarrierBillingFlow.AfterProvisioning((CompleteCarrierBillingFlow.1)null);
         CompleteCarrierBillingFlow.AfterError var6 = new CompleteCarrierBillingFlow.AfterError((CompleteCarrierBillingFlow.1)null);
         String var7 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
         var4.forceRun(var5, var6, var7);
         return;
      case 2:
         Object[] var8 = new Object[0];
         FinskyLog.d("Showing TOS to user failed.", var8);
         String var9 = FinskyApp.get().getString(2131230818);
         this.fail(var9);
         return;
      case 3:
         this.log("dcbTosChangedCancel");
         this.cancel();
         return;
      default:
      }
   }

   protected void performNext() {
      CompleteCarrierBillingFlow.State var1 = this.mState;
      CompleteCarrierBillingFlow.State var2 = CompleteCarrierBillingFlow.State.CHECK_CARRIER_TOS_VERSION;
      if(var1 == var2) {
         if(!this.mParams.showCarrierTos()) {
            CompleteCarrierBillingFlow.State var3 = CompleteCarrierBillingFlow.State.CHECK_VALID_CREDENTIALS;
            this.mState = var3;
            this.performNext();
         } else {
            String var4 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
            String var5 = this.mProvisioning.getTosVersion();
            if(var4 != null && var4.equals(var5)) {
               CompleteCarrierBillingFlow.State var6 = CompleteCarrierBillingFlow.State.CHECK_VALID_CREDENTIALS;
               this.mState = var6;
               this.performNext();
            } else {
               this.createAndShowTosFragment();
            }
         }
      } else {
         CompleteCarrierBillingFlow.State var7 = this.mState;
         CompleteCarrierBillingFlow.State var8 = CompleteCarrierBillingFlow.State.CHECK_VALID_CREDENTIALS;
         CarrierBillingCredentials var10;
         if(var7 == var8) {
            if(this.mProvisioning.isPasswordRequired()) {
               CompleteCarrierBillingFlow.State var9 = CompleteCarrierBillingFlow.State.PASSWORD_REQUEST;
               this.mState = var9;
               this.createAndShowPasswordFragment();
            } else {
               var10 = null;
               if(!this.mParams.perTransactionCredentialsRequired() || this.mCredentialsCheckPerformed) {
                  var10 = this.mStorage.getCredentials();
               }

               if(!this.credentialsStillValid(var10)) {
                  if(this.mCredentialsCheckPerformed) {
                     Object[] var11 = new Object[0];
                     FinskyLog.d("Credentials already fetched once and still not valid.", var11);
                     String var12 = FinskyApp.get().getString(2131230818);
                     this.fail(var12);
                  } else {
                     CompleteCarrierBillingFlow.State var13 = CompleteCarrierBillingFlow.State.CARRIER_CREDENTIALS_REQUEST;
                     this.mState = var13;
                     CarrierCredentialsAction var14 = this.mCredentialsAction;
                     String var15 = this.mProvisioning.getProvisioningId();
                     CompleteCarrierBillingFlow.AfterCredentials var16 = new CompleteCarrierBillingFlow.AfterCredentials((CompleteCarrierBillingFlow.1)null);
                     CompleteCarrierBillingFlow.AfterError var17 = new CompleteCarrierBillingFlow.AfterError((CompleteCarrierBillingFlow.1)null);
                     var14.run(var15, (String)null, var16, var17);
                     if(this.mPasswordFragment != null) {
                        this.mPasswordFragment.showProgressIndicator();
                     }
                  }
               } else {
                  this.finish();
               }
            }
         } else {
            CompleteCarrierBillingFlow.State var18 = this.mState;
            CompleteCarrierBillingFlow.State var19 = CompleteCarrierBillingFlow.State.CHECK_VALID_PASSWORD;
            if(var18 == var19) {
               var10 = this.mStorage.getCredentials();
               Boolean var20 = Boolean.valueOf(var10.invalidPassword());
               if(var20 != null && var20.booleanValue()) {
                  Toast.makeText(FinskyApp.get(), 2131230816, 1).show();
                  if(this.mPasswordFragment != null) {
                     this.mPasswordFragment.clearPasswordField();
                  }

                  CompleteCarrierBillingFlow.State var21 = CompleteCarrierBillingFlow.State.PASSWORD_REQUEST;
                  this.mState = var21;
               } else {
                  if(this.mPasswordFragment != null) {
                     this.mPasswordFragment.dismiss();
                  }

                  if(this.credentialsStillValid(var10)) {
                     this.finish();
                  } else {
                     Object[] var22 = new Object[0];
                     FinskyLog.d("Valid password, but invalid credentials.", var22);
                     String var23 = FinskyApp.get().getString(2131230818);
                     this.fail(var23);
                  }
               }
            } else {
               CompleteCarrierBillingFlow.State var24 = this.mState;
               CompleteCarrierBillingFlow.State var25 = CompleteCarrierBillingFlow.State.CARRIER_CREDENTIALS_REQUEST;
               if(var24 == var25) {
                  CarrierBillingCredentials var26 = this.mStorage.getCredentials();
                  CompleteCarrierBillingFlow.State var27 = CompleteCarrierBillingFlow.State.CHECK_VALID_CREDENTIALS;
                  this.mState = var27;
                  if(!var26.isProvisioned()) {
                     CarrierProvisioningAction var28 = this.mProvisioningAction;
                     CompleteCarrierBillingFlow.AfterProvisioning var29 = new CompleteCarrierBillingFlow.AfterProvisioning((CompleteCarrierBillingFlow.1)null);
                     CompleteCarrierBillingFlow.AfterError var30 = new CompleteCarrierBillingFlow.AfterError((CompleteCarrierBillingFlow.1)null);
                     String var31 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
                     var28.forceRun(var29, var30, var31);
                     if(this.mPasswordFragment != null) {
                        this.mPasswordFragment.showProgressIndicator();
                     }
                  } else {
                     this.performNext();
                  }
               } else {
                  CompleteCarrierBillingFlow.State var32 = this.mState;
                  CompleteCarrierBillingFlow.State var33 = CompleteCarrierBillingFlow.State.PASSWORD_REQUEST;
                  if(var32 == var33) {
                     CompleteCarrierBillingFlow.State var34 = CompleteCarrierBillingFlow.State.CHECK_VALID_PASSWORD;
                     this.mState = var34;
                     CarrierCredentialsAction var35 = this.mCredentialsAction;
                     String var36 = this.mProvisioning.getProvisioningId();
                     String var37 = this.mPassword;
                     CompleteCarrierBillingFlow.AfterCredentials var38 = new CompleteCarrierBillingFlow.AfterCredentials((CompleteCarrierBillingFlow.1)null);
                     CompleteCarrierBillingFlow.AfterError var39 = new CompleteCarrierBillingFlow.AfterError((CompleteCarrierBillingFlow.1)null);
                     var35.run(var36, var37, var38, var39);
                     if(this.mPasswordFragment != null) {
                        this.mPasswordFragment.showProgressIndicator();
                     }
                  } else {
                     StringBuilder var40 = (new StringBuilder()).append("Unexpected state: ");
                     CompleteCarrierBillingFlow.State var41 = this.mState;
                     String var42 = var40.append(var41).toString();
                     throw new IllegalStateException(var42);
                  }
               }
            }
         }
      }
   }

   public void resumeFromSavedState(Bundle var1) {
      CompleteCarrierBillingFlow.State var2 = this.mState;
      CompleteCarrierBillingFlow.State var3 = CompleteCarrierBillingFlow.State.CHECK_CARRIER_TOS_VERSION;
      if(var2 != var3) {
         throw new IllegalStateException();
      } else {
         CompleteCarrierBillingFlow.State var4 = CompleteCarrierBillingFlow.State.valueOf(var1.getString("state"));
         this.mState = var4;
         if(this.mParams == null || this.mProvisioning == null) {
            Object[] var5 = new Object[0];
            FinskyLog.d("Cannot run this BillingFlow since params or provisioning are null.", var5);
            String var6 = FinskyApp.get().getString(2131230818);
            this.fail(var6);
         }

         if(var1.containsKey("tos_fragment")) {
            CarrierTosDialogFragment var7 = (CarrierTosDialogFragment)this.mContext.restoreFragment(var1, "tos_fragment");
            this.mTosFragment = var7;
            this.mTosFragment.setOnResultListener(this);
         }

         if(var1.containsKey("password_fragment")) {
            CarrierBillingPasswordDialogFragment var8 = (CarrierBillingPasswordDialogFragment)this.mContext.restoreFragment(var1, "password_fragment");
            this.mPasswordFragment = var8;
            this.mPasswordFragment.setOnResultListener(this);
         }
      }
   }

   public void saveState(Bundle var1) {
      String var2 = this.mState.name();
      var1.putString("state", var2);
      if(this.mTosFragment != null) {
         BillingFlowContext var3 = this.mContext;
         CarrierTosDialogFragment var4 = this.mTosFragment;
         var3.persistFragment(var1, "tos_fragment", var4);
      }

      if(this.mPasswordFragment != null) {
         BillingFlowContext var5 = this.mContext;
         CarrierBillingPasswordDialogFragment var6 = this.mPasswordFragment;
         var5.persistFragment(var1, "password_fragment", var6);
      }
   }

   void setCredentialsAction(CarrierCredentialsAction var1) {
      this.mCredentialsAction = var1;
   }

   void setCredentialsCheckPerformed(boolean var1) {
      this.mCredentialsCheckPerformed = var1;
   }

   void setProvisioningAction(CarrierProvisioningAction var1) {
      this.mProvisioningAction = var1;
   }

   void setState(CompleteCarrierBillingFlow.State var1) {
      this.mState = var1;
   }

   public void start() {
      if(this.mParams != null && this.mProvisioning != null) {
         this.performNext();
      } else {
         Object[] var1 = new Object[0];
         FinskyLog.d("Cannot run this BillingFlow since params or provisioning are null.", var1);
         String var2 = FinskyApp.get().getString(2131230818);
         this.fail(var2);
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult;
      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierTosDialogFragment$CarrierTosResultListener$TosResult = new int[CarrierTosDialogFragment.CarrierTosResultListener.TosResult.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierTosDialogFragment$CarrierTosResultListener$TosResult;
            int var1 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.SUCCESS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var23) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierTosDialogFragment$CarrierTosResultListener$TosResult;
            int var3 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.FAILURE.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var22) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierTosDialogFragment$CarrierTosResultListener$TosResult;
            int var5 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.CANCELED.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var21) {
            ;
         }

         $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult = new int[CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.values().length];

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult;
            int var7 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.SUCCESS.ordinal();
            var6[var7] = 1;
         } catch (NoSuchFieldError var20) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult;
            int var9 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.CANCELED.ordinal();
            var8[var9] = 2;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$CarrierBillingPasswordDialogFragment$CarrierBillingPasswordResultListener$PasswordResult;
            int var11 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.FAILURE.ordinal();
            var10[var11] = 3;
         } catch (NoSuchFieldError var18) {
            ;
         }
      }
   }

   public static enum State {

      // $FF: synthetic field
      private static final CompleteCarrierBillingFlow.State[] $VALUES;
      CARRIER_CREDENTIALS_REQUEST("CARRIER_CREDENTIALS_REQUEST", 3),
      CHECK_CARRIER_TOS_VERSION("CHECK_CARRIER_TOS_VERSION", 0),
      CHECK_VALID_CREDENTIALS("CHECK_VALID_CREDENTIALS", 1),
      CHECK_VALID_PASSWORD("CHECK_VALID_PASSWORD", 2),
      PASSWORD_REQUEST("PASSWORD_REQUEST", 4);


      static {
         CompleteCarrierBillingFlow.State[] var0 = new CompleteCarrierBillingFlow.State[5];
         CompleteCarrierBillingFlow.State var1 = CHECK_CARRIER_TOS_VERSION;
         var0[0] = var1;
         CompleteCarrierBillingFlow.State var2 = CHECK_VALID_CREDENTIALS;
         var0[1] = var2;
         CompleteCarrierBillingFlow.State var3 = CHECK_VALID_PASSWORD;
         var0[2] = var3;
         CompleteCarrierBillingFlow.State var4 = CARRIER_CREDENTIALS_REQUEST;
         var0[3] = var4;
         CompleteCarrierBillingFlow.State var5 = PASSWORD_REQUEST;
         var0[4] = var5;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }

   private class AfterProvisioning implements Runnable {

      private AfterProvisioning() {}

      // $FF: synthetic method
      AfterProvisioning(CompleteCarrierBillingFlow.1 var2) {
         this();
      }

      public void run() {
         if(CompleteCarrierBillingFlow.this.mPasswordFragment != null) {
            CompleteCarrierBillingFlow.this.mPasswordFragment.hideProgressIndicator();
         }

         CompleteCarrierBillingFlow var1 = CompleteCarrierBillingFlow.this;
         CarrierBillingProvisioning var2 = CompleteCarrierBillingFlow.this.mStorage.getProvisioning();
         var1.mProvisioning = var2;
         CompleteCarrierBillingFlow.this.performNext();
      }
   }

   private class AfterCredentials implements Runnable {

      private AfterCredentials() {}

      // $FF: synthetic method
      AfterCredentials(CompleteCarrierBillingFlow.1 var2) {
         this();
      }

      public void run() {
         if(CompleteCarrierBillingFlow.this.mPasswordFragment != null) {
            CompleteCarrierBillingFlow.this.mPasswordFragment.hideProgressIndicator();
         }

         boolean var1 = (boolean)(CompleteCarrierBillingFlow.this.mCredentialsCheckPerformed = (boolean)1);
         CompleteCarrierBillingFlow.this.performNext();
      }
   }

   private class AfterError implements Runnable {

      private AfterError() {}

      // $FF: synthetic method
      AfterError(CompleteCarrierBillingFlow.1 var2) {
         this();
      }

      public void run() {
         if(CompleteCarrierBillingFlow.this.mPasswordFragment != null) {
            CompleteCarrierBillingFlow.this.mPasswordFragment.dismiss();
         }

         Object[] var1 = new Object[0];
         FinskyLog.d("Fetching info from carrier failed", var1);
         CompleteCarrierBillingFlow var2 = CompleteCarrierBillingFlow.this;
         String var3 = FinskyApp.get().getString(2131230818);
         var2.fail(var3);
      }
   }
}
