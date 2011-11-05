package com.google.android.finsky.billing.creditcard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.android.volley.AuthFailureException;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.ErrorDialog;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.creditcard.AddCreditCardFragment;
import com.google.android.finsky.billing.creditcard.EscrowRequest;
import com.google.android.finsky.billing.creditcard.SavingDialog;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.remoting.protos.BuyInstruments;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import java.util.List;
import java.util.Random;

public class CreateCreditCardFlow extends BillingFlow implements AddCreditCardFragment.AddCreditCardResultListener, Response.Listener<BuyInstruments.UpdateInstrumentResponse>, Response.ErrorListener {

   private static final String KEY_FRAGMENT = "fragment";
   private static final String KEY_PROGRESS_DIALOG = "progress_dialog";
   private static final String KEY_STATE = "state";
   private static final String LOG_ERROR_ESCROW_PREFIX = "ESCROW.";
   private static final String LOG_ERROR_INVALID_INPUT = "INVALID_INPUT";
   private static final String LOG_ERROR_UNKNOWN = "UNKNOWN";
   public static final String PARAM_KEY_CARDHOLDER_NAME = "cardholder_name";
   public static final String PARAM_KEY_MODE = "mode";
   public static final String PARAM_VALUE_MODE_EXTERNAL = AddCreditCardFragment.UiMode.EXTERNAL.name();
   private AddCreditCardFragment mAddCreditCardFragment;
   private BuyInstruments.UpdateInstrumentResponse mAddCreditCardResponse;
   private final Analytics mAnalytics;
   private final AndroidAuthenticator mAuthenticator;
   private final BillingFlowContext mContext;
   private String mCreditCardNumber;
   private String mCvc;
   private final DfeApi mDfeApi;
   private BuyInstruments.Instrument mInstrument;
   private AddCreditCardFragment.UiMode mMode;
   private SavingDialog mProgressDialog;
   private String mReferrerListCookie;
   private String mReferrerUrl;
   private CreateCreditCardFlow.State mState;


   public CreateCreditCardFlow(BillingFlowContext var1, BillingFlowListener var2, AndroidAuthenticator var3, DfeApi var4, Analytics var5, Bundle var6) {
      super(var1, var2, var6);
      CreateCreditCardFlow.State var7 = CreateCreditCardFlow.State.INIT;
      this.mState = var7;
      AddCreditCardFragment.UiMode var8 = AddCreditCardFragment.UiMode.INTERNAL;
      this.mMode = var8;
      this.mContext = var1;
      this.mAuthenticator = var3;
      this.mDfeApi = var4;
      this.mAnalytics = var5;
      if(var6 != null) {
         if(var6.containsKey("mode")) {
            AddCreditCardFragment.UiMode var9 = AddCreditCardFragment.UiMode.valueOf(var6.getString("mode"));
            this.mMode = var9;
         }

         String var10 = var6.getString("referrer_url");
         this.mReferrerUrl = var10;
         String var11 = var6.getString("referrer_list_cookie");
         this.mReferrerListCookie = var11;
      }
   }

   private void getCheckoutTokenAndQueueUpdateRequest() {
      AndroidAuthenticator var1 = this.mAuthenticator;
      CreateCreditCardFlow.1 var2 = new CreateCreditCardFlow.1();
      Looper var3 = Looper.getMainLooper();
      Handler var4 = new Handler(var3);
      var1.getAuthTokenAsync(var2, var4, (boolean)1);
   }

   private void hideProgress() {
      if(this.mProgressDialog != null && this.mProgressDialog.isAdded()) {
         this.mProgressDialog.dismiss();
         this.mProgressDialog = null;
      } else {
         Object[] var1 = new Object[0];
         FinskyLog.d("hideProgress called when progress dialog was not shown.", var1);
      }
   }

   private boolean isRetryableError(BuyInstruments.UpdateInstrumentResponse var1) {
      boolean var2;
      if(var1.getResult() == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private boolean isSuccess(BuyInstruments.UpdateInstrumentResponse var1) {
      boolean var2;
      if(var1.getResult() == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private void log(String var1) {
      Analytics var2 = this.mAnalytics;
      String var3 = this.mReferrerUrl;
      String var4 = this.mReferrerListCookie;
      var2.logPageView(var3, var4, var1);
   }

   private void logError(String var1) {
      String var2 = "addCreditCardError?error=" + var1;
      this.log(var2);
   }

   private void queueEscrowCredentialsRequest() {
      long var1 = System.currentTimeMillis();
      long var3 = ((Long)G.androidId.get()).longValue();
      long var5 = var1 ^ var3;
      int var7 = Math.abs((new Random(var5)).nextInt());
      String var8 = this.mCreditCardNumber;
      String var9 = this.mCvc;
      CreateCreditCardFlow.EscrowResponseListener var10 = new CreateCreditCardFlow.EscrowResponseListener((CreateCreditCardFlow.1)null);
      CreateCreditCardFlow.EscrowErrorListener var11 = new CreateCreditCardFlow.EscrowErrorListener((CreateCreditCardFlow.1)null);
      EscrowRequest var12 = new EscrowRequest(var7, var8, var9, var10, var11);
      Request var13 = FinskyApp.get().getRequestQueue().add(var12);
      this.mCreditCardNumber = null;
      this.mCvc = null;
   }

   private void queueUpdateCreditCardRequest(String var1) {
      BuyInstruments.UpdateInstrumentRequest var2 = new BuyInstruments.UpdateInstrumentRequest();
      BuyInstruments.Instrument var3 = this.mInstrument;
      var2.setInstrument(var3);
      this.mDfeApi.updateInstrument(var2, var1, this, this);
   }

   private void showError(String var1) {
      CreateCreditCardFlow.State var2 = CreateCreditCardFlow.State.SHOWING_FORM;
      this.mState = var2;
      this.hideProgress();
      if(this.mAddCreditCardFragment.getFragmentManager() == null) {
         Object[] var3 = new Object[]{var1};
         FinskyLog.w("No fragment manager, swallowing error: %s", var3);
      } else {
         ErrorDialog var4 = ErrorDialog.show(this.mAddCreditCardFragment.getFragmentManager(), (String)null, var1, (boolean)0);
      }
   }

   private void showFormErrors(BuyInstruments.UpdateInstrumentResponse var1) {
      AddCreditCardFragment var2 = this.mAddCreditCardFragment;
      List var3 = var1.getErrorInputFieldList();
      var2.displayErrors(var3);
   }

   private void showProgress() {
      SavingDialog var1 = new SavingDialog();
      this.mProgressDialog = var1;
      BillingFlowContext var2 = this.mContext;
      SavingDialog var3 = this.mProgressDialog;
      var2.showDialogFragment(var3, "progress_dialog");
   }

   public void back() {
      CreateCreditCardFlow.State var1 = this.mState;
      CreateCreditCardFlow.State var2 = CreateCreditCardFlow.State.SHOWING_FORM;
      if(var1 != var2) {
         throw new IllegalStateException();
      } else {
         this.cancel();
      }
   }

   public boolean canGoBack() {
      CreateCreditCardFlow.State var1 = this.mState;
      CreateCreditCardFlow.State var2 = CreateCreditCardFlow.State.SHOWING_FORM;
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void cancel() {
      PreferenceFile.SharedPreference var1 = BillingPreferences.getLastAddCreditcardCanceledMillis(this.mDfeApi.getCurrentAccountName());
      Long var2 = Long.valueOf(System.currentTimeMillis());
      var1.put(var2);
      this.log("addCreditCardCancel");
      super.cancel();
   }

   public int getDisplayTitleResourceId() {
      return 2131230782;
   }

   public void onAddCreditCardResult(AddCreditCardFragment.AddCreditCardResultListener.Result var1, String var2, String var3, BuyInstruments.Instrument var4) {
      AddCreditCardFragment.AddCreditCardResultListener.Result var5 = AddCreditCardFragment.AddCreditCardResultListener.Result.SUCCESS;
      if(var1 == var5) {
         this.mCreditCardNumber = var2;
         this.mCvc = var3;
         this.mInstrument = var4;
         this.log("addCreditCardConfirm");
         this.performNext();
      } else {
         AddCreditCardFragment.AddCreditCardResultListener.Result var6 = AddCreditCardFragment.AddCreditCardResultListener.Result.CANCELED;
         if(var1 == var6) {
            if(FinskyLog.DEBUG) {
               Object[] var7 = new Object[0];
               FinskyLog.v("Add credit card canceled.", var7);
            }

            this.cancel();
         } else {
            AddCreditCardFragment.AddCreditCardResultListener.Result var8 = AddCreditCardFragment.AddCreditCardResultListener.Result.FAILURE;
            if(var1 == var8) {
               this.logError("UNKNOWN");
               String var9 = FinskyApp.get().getString(2131230820);
               this.showError(var9);
            }
         }
      }
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      Object[] var4 = new Object[]{var1, var2};
      FinskyLog.w("Error received: %s / %s", var4);
      String var5 = var1.name();
      this.logError(var5);
      String var6 = ErrorStrings.get(FinskyApp.get(), var1, var2);
      this.showError(var6);
   }

   public void onResponse(BuyInstruments.UpdateInstrumentResponse var1) {
      this.mAddCreditCardResponse = var1;
      this.performNext();
   }

   protected void performNext() {
      CreateCreditCardFlow.State var1 = this.mState;
      CreateCreditCardFlow.State var2 = CreateCreditCardFlow.State.INIT;
      if(var1 == var2) {
         CreateCreditCardFlow.State var3 = CreateCreditCardFlow.State.SHOWING_FORM;
         this.mState = var3;
         String var4 = this.mParameters.getString("cardholder_name");
         AddCreditCardFragment.UiMode var5 = this.mMode;
         String var6 = this.mDfeApi.getCurrentAccountName();
         AddCreditCardFragment var7 = AddCreditCardFragment.newInstance(var5, var6, var4);
         this.mAddCreditCardFragment = var7;
         this.mAddCreditCardFragment.setOnResultListener(this);
         BillingFlowContext var8 = this.mContext;
         AddCreditCardFragment var9 = this.mAddCreditCardFragment;
         var8.showFragment(var9, 2131230783, (boolean)1);
      } else {
         CreateCreditCardFlow.State var10 = this.mState;
         CreateCreditCardFlow.State var11 = CreateCreditCardFlow.State.SHOWING_FORM;
         if(var10 == var11) {
            CreateCreditCardFlow.State var12 = CreateCreditCardFlow.State.ESCROWING_CREDENTIALS;
            this.mState = var12;
            this.showProgress();
            this.queueEscrowCredentialsRequest();
         } else {
            CreateCreditCardFlow.State var13 = this.mState;
            CreateCreditCardFlow.State var14 = CreateCreditCardFlow.State.ESCROWING_CREDENTIALS;
            if(var13 == var14) {
               CreateCreditCardFlow.State var15 = CreateCreditCardFlow.State.SENDING_REQUEST;
               this.mState = var15;
               this.getCheckoutTokenAndQueueUpdateRequest();
            } else {
               CreateCreditCardFlow.State var16 = this.mState;
               CreateCreditCardFlow.State var17 = CreateCreditCardFlow.State.SENDING_REQUEST;
               if(var16 == var17) {
                  this.hideProgress();
                  if(this.mAddCreditCardResponse == null) {
                     Object[] var18 = new Object[0];
                     FinskyLog.e("AddCreditCard Response is null.", var18);
                     this.logError("UNKNOWN");
                     String var19 = FinskyApp.get().getString(2131230820);
                     this.showError(var19);
                  } else {
                     BuyInstruments.UpdateInstrumentResponse var20 = this.mAddCreditCardResponse;
                     if(this.isSuccess(var20)) {
                        this.log("addCreditCardSuccess");
                        CreateCreditCardFlow.State var21 = CreateCreditCardFlow.State.DONE;
                        this.mState = var21;
                        this.finish();
                     } else {
                        BuyInstruments.UpdateInstrumentResponse var22 = this.mAddCreditCardResponse;
                        if(this.isRetryableError(var22)) {
                           this.logError("INVALID_INPUT");
                           CreateCreditCardFlow.State var23 = CreateCreditCardFlow.State.SHOWING_FORM;
                           this.mState = var23;
                           BuyInstruments.UpdateInstrumentResponse var24 = this.mAddCreditCardResponse;
                           this.showFormErrors(var24);
                        } else {
                           this.logError("UNKNOWN");
                           if(this.mAddCreditCardResponse.hasUserMessageHtml()) {
                              String var25 = this.mAddCreditCardResponse.getUserMessageHtml();
                              this.showError(var25);
                           } else {
                              String var26 = FinskyApp.get().getString(2131230820);
                              this.showError(var26);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void resumeFromSavedState(Bundle var1) {
      CreateCreditCardFlow.State var2 = this.mState;
      CreateCreditCardFlow.State var3 = CreateCreditCardFlow.State.INIT;
      if(var2 != var3) {
         throw new IllegalStateException();
      } else {
         CreateCreditCardFlow.State var4 = CreateCreditCardFlow.State.valueOf(var1.getString("state"));
         this.mState = var4;
         if(var1.containsKey("progress_dialog")) {
            SavingDialog var5 = (SavingDialog)this.mContext.restoreFragment(var1, "progress_dialog");
            this.mProgressDialog = var5;
         }

         CreateCreditCardFlow.State var6 = this.mState;
         CreateCreditCardFlow.State var7 = CreateCreditCardFlow.State.SHOWING_FORM;
         if(var6 != var7) {
            this.hideProgress();
            this.finish();
         }

         if(var1.containsKey("fragment")) {
            AddCreditCardFragment var8 = (AddCreditCardFragment)this.mContext.restoreFragment(var1, "fragment");
            this.mAddCreditCardFragment = var8;
            this.mAddCreditCardFragment.setOnResultListener(this);
         }
      }
   }

   public void saveState(Bundle var1) {
      String var2 = this.mState.name();
      var1.putString("state", var2);
      if(this.mAddCreditCardFragment != null) {
         BillingFlowContext var3 = this.mContext;
         AddCreditCardFragment var4 = this.mAddCreditCardFragment;
         var3.persistFragment(var1, "fragment", var4);
      }

      if(this.mProgressDialog != null) {
         BillingFlowContext var5 = this.mContext;
         SavingDialog var6 = this.mProgressDialog;
         var5.persistFragment(var1, "progress_dialog", var6);
      }
   }

   public void start() {
      this.log("addCreditCard");
      this.performNext();
   }

   private class EscrowErrorListener implements Response.ErrorListener {

      private EscrowErrorListener() {}

      // $FF: synthetic method
      EscrowErrorListener(CreateCreditCardFlow.1 var2) {
         this();
      }

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[]{var1, var2};
         FinskyLog.w("Error received: %s / %s", var4);
         CreateCreditCardFlow var5 = CreateCreditCardFlow.this;
         StringBuilder var6 = (new StringBuilder()).append("ESCROW.");
         String var7 = var1.name();
         String var8 = var6.append(var7).toString();
         var5.logError(var8);
         CreateCreditCardFlow var9 = CreateCreditCardFlow.this;
         String var10 = ErrorStrings.get(FinskyApp.get(), var1, var2);
         var9.showError(var10);
      }
   }

   class 1 implements AndroidAuthenticator.AuthTokenListener {

      1() {}

      public void onAuthTokenReceived(String var1) {
         CreateCreditCardFlow.this.queueUpdateCreditCardRequest(var1);
      }

      public void onErrorReceived(AuthFailureException var1) {
         CreateCreditCardFlow var2 = CreateCreditCardFlow.this;
         String var3 = FinskyApp.get().getString(2131230820);
         var2.showError(var3);
      }
   }

   private static enum State {

      // $FF: synthetic field
      private static final CreateCreditCardFlow.State[] $VALUES;
      DONE("DONE", 4),
      ESCROWING_CREDENTIALS("ESCROWING_CREDENTIALS", 2),
      INIT("INIT", 0),
      SENDING_REQUEST("SENDING_REQUEST", 3),
      SHOWING_FORM("SHOWING_FORM", 1);


      static {
         CreateCreditCardFlow.State[] var0 = new CreateCreditCardFlow.State[5];
         CreateCreditCardFlow.State var1 = INIT;
         var0[0] = var1;
         CreateCreditCardFlow.State var2 = SHOWING_FORM;
         var0[1] = var2;
         CreateCreditCardFlow.State var3 = ESCROWING_CREDENTIALS;
         var0[2] = var3;
         CreateCreditCardFlow.State var4 = SENDING_REQUEST;
         var0[3] = var4;
         CreateCreditCardFlow.State var5 = DONE;
         var0[4] = var5;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }

   private class EscrowResponseListener implements Response.Listener<String> {

      private EscrowResponseListener() {}

      // $FF: synthetic method
      EscrowResponseListener(CreateCreditCardFlow.1 var2) {
         this();
      }

      public void onResponse(String var1) {
         BuyInstruments.CreditCardInstrument var2 = CreateCreditCardFlow.this.mInstrument.getCreditCard().setEscrowHandle(var1);
         CreateCreditCardFlow.this.performNext();
      }
   }
}
