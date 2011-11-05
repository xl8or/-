package com.google.android.finsky.billing.carrierbilling.flow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.android.volley.AuthFailureException;
import com.android.volley.NetworkError;
import com.android.volley.Response;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.Analytics;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.BillingFlow;
import com.google.android.finsky.billing.BillingFlowContext;
import com.google.android.finsky.billing.BillingFlowListener;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.carrierbilling.PhoneCarrierBillingUtils;
import com.google.android.finsky.billing.carrierbilling.action.CarrierBillingAction;
import com.google.android.finsky.billing.carrierbilling.action.CarrierProvisioningAction;
import com.google.android.finsky.billing.carrierbilling.fragment.AddCarrierBillingFragment;
import com.google.android.finsky.billing.carrierbilling.fragment.CarrierBillingErrorDialog;
import com.google.android.finsky.billing.carrierbilling.fragment.EditCarrierBillingFragment;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingStorage;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.remoting.protos.Address;
import com.google.android.finsky.remoting.protos.BuyInstruments;
import com.google.android.finsky.remoting.protos.EncryptedSubscriberInfo;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateCarrierBillingFlow extends BillingFlow implements AddCarrierBillingFragment.AddCarrierBillingResultListener, EditCarrierBillingFragment.EditCarrierBillingResultListener, Response.ErrorListener, CarrierBillingErrorDialog.CarrierBillingErrorListener {

   private static final String KEY_ADD_FRAGMENT = "add_fragment";
   private static final String KEY_EDIT_FRAGMENT = "edit_fragment";
   private static final String KEY_ERROR_FRAGMENT = "error_dialog";
   private static final String KEY_STATE = "state";
   private static final String LOG_ERROR_INVALID_INPUT = "INVALID_INPUT";
   private static final String LOG_ERROR_UNKNOWN = "UNKNOWN";
   private static final String LOG_NETWORK = "NETWORK";
   private AddCarrierBillingFragment mAddFragment;
   private AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult mAddResult;
   private final Analytics mAnalytics;
   private final AndroidAuthenticator mAuthenticator;
   private final BillingFlowContext mContext;
   private final DfeApi mDfeApi;
   private EditCarrierBillingFragment mEditFragment;
   private EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult mEditResult;
   private CarrierBillingErrorDialog mErrorFragment;
   final Runnable mOnError;
   final Runnable mOnSuccess;
   private String mReferrer;
   private String mReferrerListCookie;
   private CreateCarrierBillingFlow.State mState;
   private final CarrierBillingStorage mStorage;
   private BuyInstruments.UpdateInstrumentResponse mUpdateInstrumentResponse;
   private SubscriberInfo mUserProvidedAddress;


   public CreateCarrierBillingFlow(BillingFlowContext var1, BillingFlowListener var2, AndroidAuthenticator var3, DfeApi var4, Analytics var5, Bundle var6) {
      CarrierBillingStorage var7 = BillingLocator.getCarrierBillingStorage();
      this(var1, var2, var3, var7, var4, var5, var6);
   }

   CreateCarrierBillingFlow(BillingFlowContext var1, BillingFlowListener var2, AndroidAuthenticator var3, CarrierBillingStorage var4, DfeApi var5, Analytics var6, Bundle var7) {
      super(var1, var2, var7);
      CreateCarrierBillingFlow.State var8 = CreateCarrierBillingFlow.State.INIT;
      this.mState = var8;
      this.mAddResult = null;
      this.mEditResult = null;
      CreateCarrierBillingFlow.3 var9 = new CreateCarrierBillingFlow.3();
      this.mOnSuccess = var9;
      CreateCarrierBillingFlow.4 var10 = new CreateCarrierBillingFlow.4();
      this.mOnError = var10;
      this.mContext = var1;
      this.mStorage = var4;
      this.mAuthenticator = var3;
      this.mDfeApi = var5;
      this.mAnalytics = var6;
      if(var7 != null) {
         String var11 = var7.getString("referrer_url");
         this.mReferrer = var11;
         String var12 = var7.getString("referrer_list_cookie");
         this.mReferrerListCookie = var12;
      }
   }

   private void continueResume(Bundle var1) {
      CreateCarrierBillingFlow.State var2 = this.mState;
      CreateCarrierBillingFlow.State var3 = CreateCarrierBillingFlow.State.INIT;
      if(var2 != var3) {
         throw new IllegalStateException();
      } else {
         CreateCarrierBillingFlow.State var4 = CreateCarrierBillingFlow.State.valueOf(var1.getString("state"));
         this.mState = var4;
         if(var1.containsKey("error_dialog")) {
            CarrierBillingErrorDialog var5 = (CarrierBillingErrorDialog)this.mContext.restoreFragment(var1, "error_dialog");
            this.mErrorFragment = var5;
            this.mErrorFragment.setOnResultListener(this);
         }

         int[] var6 = CreateCarrierBillingFlow.8.$SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
         int var7 = this.mState.ordinal();
         switch(var6[var7]) {
         case 1:
         case 2:
         case 3:
            if(!var1.containsKey("add_fragment")) {
               return;
            }

            AddCarrierBillingFragment var8 = (AddCarrierBillingFragment)this.mContext.restoreFragment(var1, "add_fragment");
            this.mAddFragment = var8;
            this.mAddFragment.setOnResultListener(this);
            return;
         case 4:
            if(!var1.containsKey("edit_fragment")) {
               return;
            }

            EditCarrierBillingFragment var9 = (EditCarrierBillingFragment)this.mContext.restoreFragment(var1, "edit_fragment");
            this.mEditFragment = var9;
            this.mEditFragment.setOnResultListener(this);
            return;
         default:
            if(this.mErrorFragment != null) {
               this.cancel();
            } else {
               this.finish();
            }
         }
      }
   }

   private BuyInstruments.Instrument createCarrierBillingInstrument() {
      BuyInstruments.Instrument var1 = new BuyInstruments.Instrument();
      BuyInstruments.CarrierBillingInstrument var2 = new BuyInstruments.CarrierBillingInstrument();
      String var3 = this.mStorage.getCurrentSimIdentifier();
      var2.setInstrumentKey(var3);
      String var5 = this.mStorage.getProvisioning().getAccountType();
      var2.setAccountType(var5);
      String var7 = this.mStorage.getProvisioning().getSubscriberCurrency();
      var2.setCurrencyCode(var7);
      long var9 = this.mStorage.getProvisioning().getTransactionLimit();
      var2.setTransactionLimit(var9);
      if(this.mStorage.getProvisioning().getSubscriberInfo() != null) {
         String var12 = this.mStorage.getProvisioning().getSubscriberInfo().getIdentifier();
         var2.setSubscriberIdentifier(var12);
      }

      BuyInstruments.CarrierBillingCredentials var14 = new BuyInstruments.CarrierBillingCredentials();
      if(this.mStorage.getCredentials() != null) {
         String var15 = this.mStorage.getCredentials().getCredentials();
         var14.setValue(var15);
         long var17 = this.mStorage.getCredentials().getExpirationTime();
         var14.setExpiration(var17);
      }

      var2.setCredentials(var14);
      if(this.mUserProvidedAddress != null) {
         Address var21 = PhoneCarrierBillingUtils.subscriberInfoToAddress(this.mUserProvidedAddress);
         var1.setBillingAddress(var21);
      } else if(this.mStorage.getProvisioning().getSubscriberInfo() != null) {
         Address var24 = PhoneCarrierBillingUtils.subscriberInfoToAddress(this.mStorage.getProvisioning().getSubscriberInfo());
         var1.setBillingAddress(var24);
      } else if(this.mStorage.getProvisioning().getEncryptedSubscriberInfo() != null) {
         EncryptedSubscriberInfo var26 = this.mStorage.getProvisioning().getEncryptedSubscriberInfo().toProto();
         var2.setEncryptedSubscriberInfo(var26);
      }

      var1.setCarrierBilling(var2);
      return var1;
   }

   private ArrayList<Integer> getInvalidEntries(List<BuyInstruments.InputValidationError> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         BuyInstruments.InputValidationError var4 = (BuyInstruments.InputValidationError)var3.next();
         int var5 = var4.getInputField();
         switch(var5) {
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 13:
            Integer var9 = Integer.valueOf(var5);
            var2.add(var9);
            break;
         case 10:
         case 11:
         case 12:
         default:
            Object[] var6 = new Object[2];
            Integer var7 = Integer.valueOf(var4.getInputField());
            var6[0] = var7;
            String var8 = var4.getErrorMessage();
            var6[1] = var8;
            FinskyLog.d("InputValidationError that can\'t be edited: type=%d, message=%s", var6);
         }
      }

      return var2;
   }

   private void getProvisioning(Runnable var1, Runnable var2) {
      CarrierProvisioningAction var3 = new CarrierProvisioningAction();
      String var4 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
      var3.forceRun(var1, var2, var4);
   }

   private void getSecondProvisioning() {
      Runnable var1 = this.mOnSuccess;
      Runnable var2 = this.mOnError;
      this.getProvisioning(var1, var2);
   }

   private void hideEditFragment() {
      if(this.mEditFragment != null) {
         BillingFlowContext var1 = this.mContext;
         EditCarrierBillingFragment var2 = this.mEditFragment;
         var1.hideFragment(var2, (boolean)0);
         this.mEditFragment = null;
      }
   }

   private void hideTosFragment() {
      if(this.mAddFragment != null) {
         BillingFlowContext var1 = this.mContext;
         AddCarrierBillingFragment var2 = this.mAddFragment;
         var1.hideFragment(var2, (boolean)0);
         this.mAddFragment = null;
      }
   }

   private boolean isAddressInfoPresent() {
      boolean var1 = true;
      if(this.mUserProvidedAddress == null) {
         CarrierBillingParameters var2 = this.mStorage.getParams();
         CarrierBillingProvisioning var3 = this.mStorage.getProvisioning();
         if(var2 != null && var3 != null) {
            int var4 = var2.getCarrierApiVersion();
            if(var4 <= 1) {
               if(var3.getSubscriberInfo() == null || TextUtils.isEmpty(var3.getSubscriberInfo().getAddress1()) || TextUtils.isEmpty(var3.getSubscriberInfo().getName()) || TextUtils.isEmpty(var3.getSubscriberInfo().getCity())) {
                  var1 = false;
               }
            } else if(var4 == 2) {
               if(TextUtils.isEmpty(var3.getAddressSnippet())) {
                  var1 = false;
               }
            } else {
               String var5 = "Invalid carrier billing api number " + var4;
               Object[] var6 = new Object[0];
               FinskyLog.wtf(var5, var6);
               var1 = false;
            }
         } else {
            var1 = false;
         }
      }

      return var1;
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

   private void log(String var1) {
      Analytics var2 = this.mAnalytics;
      String var3 = this.mReferrer;
      String var4 = this.mReferrerListCookie;
      var2.logPageView(var3, var4, var1);
   }

   private void logEditAddress(boolean var1) {
      String var2 = "addDcbEdit?address=" + var1;
      this.log(var2);
   }

   private void logError(String var1) {
      String var2 = "addDcbError?error=" + var1;
      this.log(var2);
   }

   private void logTosAndAddress(boolean var1, boolean var2, boolean var3) {
      String var4;
      if(var3) {
         var4 = "FULL";
      } else if(var2) {
         var4 = "SNIPPET";
      } else {
         var4 = "NONE";
      }

      String var5 = "addDcbTos?tos=" + var1 + "&address=" + var4;
      this.log(var5);
   }

   private void performInitDcb1(CarrierBillingParameters var1, CarrierBillingProvisioning var2) {
      if(var2.getSubscriberInfo() != null && !TextUtils.isEmpty(var2.getSubscriberInfo().getAddress1()) && !TextUtils.isEmpty(var2.getSubscriberInfo().getName()) && !TextUtils.isEmpty(var2.getSubscriberInfo().getCity())) {
         CreateCarrierBillingFlow.State var3 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS;
         this.mState = var3;
         if(var1.showCarrierTos()) {
            AddCarrierBillingFragment.Type var4 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS;
            this.showAddFragment(var4);
         } else {
            AddCarrierBillingFragment.Type var5 = AddCarrierBillingFragment.Type.FULL_ADDRESS;
            this.showAddFragment(var5);
         }
      } else {
         CreateCarrierBillingFlow.State var6 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
         this.mState = var6;
         this.showEditFragment((SubscriberInfo)null);
      }
   }

   private void performInitDcb2(CarrierBillingParameters var1, CarrierBillingProvisioning var2) {
      String var3 = var2.getAddressSnippet();
      if(!TextUtils.isEmpty(var3) && !"null".equals(var3)) {
         CreateCarrierBillingFlow.State var4 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS_SNIPPET;
         this.mState = var4;
         if(var1.showCarrierTos()) {
            AddCarrierBillingFragment.Type var5 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET_AND_TOS;
            this.showAddFragment(var5);
         } else {
            AddCarrierBillingFragment.Type var6 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET;
            this.showAddFragment(var6);
         }
      } else if(var1.showCarrierTos()) {
         CreateCarrierBillingFlow.State var7 = CreateCarrierBillingFlow.State.SHOWING_TOS;
         this.mState = var7;
         AddCarrierBillingFragment.Type var8 = AddCarrierBillingFragment.Type.TOS;
         this.showAddFragment(var8);
      } else {
         CreateCarrierBillingFlow.State var9 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
         this.mState = var9;
         this.showEditFragment((SubscriberInfo)null);
      }
   }

   private void performNextInit() {
      CarrierBillingParameters var1 = this.mStorage.getParams();
      CarrierBillingProvisioning var2 = this.mStorage.getProvisioning();
      if(var1 != null && var2 != null) {
         if(var1.getCarrierApiVersion() <= 1) {
            this.performInitDcb1(var1, var2);
         } else if(var1.getCarrierApiVersion() == 2) {
            this.performInitDcb2(var1, var2);
         } else {
            StringBuilder var5 = (new StringBuilder()).append("Invalid Dcb api version ");
            int var6 = var1.getCarrierApiVersion();
            String var7 = var5.append(var6).toString();
            Object[] var8 = new Object[0];
            FinskyLog.w(var7, var8);
            String var9 = FinskyApp.get().getString(2131230817);
            this.fail(var9);
         }
      } else {
         Object[] var3 = new Object[0];
         FinskyLog.w("Invalid carrier billing provisioning parameters.", var3);
         String var4 = FinskyApp.get().getString(2131230817);
         this.fail(var4);
      }
   }

   private void queueCarrierBillingUpdateRequest(String var1) {
      BuyInstruments.UpdateInstrumentRequest var2 = new BuyInstruments.UpdateInstrumentRequest();
      BuyInstruments.Instrument var3 = this.createCarrierBillingInstrument();
      var2.setInstrument(var3);
      DfeApi var5 = this.mDfeApi;
      CreateCarrierBillingFlow.6 var6 = new CreateCarrierBillingFlow.6();
      CreateCarrierBillingFlow.7 var7 = new CreateCarrierBillingFlow.7();
      var5.updateInstrument(var2, var1, var6, var7);
   }

   private void showEditFragment(SubscriberInfo var1) {
      this.showEditFragment(var1, (ArrayList)null);
   }

   private void showEditFragment(SubscriberInfo var1, ArrayList<Integer> var2) {
      this.hideTosFragment();
      byte var3;
      if(var1 != null) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      this.logEditAddress((boolean)var3);
      EditCarrierBillingFragment var4 = EditCarrierBillingFragment.newInstance(var1, var2);
      this.mEditFragment = var4;
      this.mEditFragment.setOnResultListener(this);
      BillingFlowContext var5 = this.mContext;
      EditCarrierBillingFragment var6 = this.mEditFragment;
      var5.showFragment(var6, -1, (boolean)0);
   }

   private void showError() {
      FinskyApp var1 = FinskyApp.get();
      Object[] var2 = new Object[1];
      String var3 = BillingLocator.getCarrierBillingStorage().getParams().getName();
      var2[0] = var3;
      String var4 = var1.getString(2131230821, var2);
      this.showError(var4);
   }

   private void showError(String var1) {
      CarrierBillingErrorDialog var2 = CarrierBillingErrorDialog.newInstance(var1);
      this.mErrorFragment = var2;
      this.mErrorFragment.setOnResultListener(this);
      BillingFlowContext var3 = this.mContext;
      CarrierBillingErrorDialog var4 = this.mErrorFragment;
      var3.showDialogFragment(var4, "error");
   }

   private void showRetryError(ArrayList<Integer> var1) {
      if(this.mUserProvidedAddress != null) {
         SubscriberInfo var2 = this.mUserProvidedAddress;
         this.showEditFragment(var2, var1);
      } else if(this.mStorage.getParams().getCarrierApiVersion() <= 1) {
         SubscriberInfo var3 = this.mStorage.getProvisioning().getSubscriberInfo();
         this.showEditFragment(var3, var1);
      } else {
         this.showEditFragment((SubscriberInfo)null);
      }
   }

   public void back() {
      CreateCarrierBillingFlow.State var1 = this.mState;
      CreateCarrierBillingFlow.State var2 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
      if(var1 == var2) {
         this.hideEditFragment();
         EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var3 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.CANCELED;
         this.mEditResult = var3;
         this.performNext();
      } else {
         if(FinskyLog.DEBUG) {
            Object[] var4 = new Object[0];
            FinskyLog.v("carrier billing tos canceled.", var4);
         }

         this.cancel();
      }
   }

   public boolean canGoBack() {
      return true;
   }

   public void cancel() {
      this.log("addDcbCancel");
      super.cancel();
   }

   protected void getCheckoutTokenAndQueueUpdateRequest() {
      AndroidAuthenticator var1 = this.mAuthenticator;
      CreateCarrierBillingFlow.5 var2 = new CreateCarrierBillingFlow.5();
      Looper var3 = Looper.getMainLooper();
      Handler var4 = new Handler(var3);
      var1.getAuthTokenAsync(var2, var4, (boolean)1);
   }

   CreateCarrierBillingFlow.State getState() {
      return this.mState;
   }

   SubscriberInfo getUserProvidedAddress() {
      return this.mUserProvidedAddress;
   }

   public void onAddCarrierBillingResult(AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var1) {
      AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var2 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.SUCCESS;
      if(var1 == var2) {
         if(this.mAddFragment != null) {
            this.mAddFragment.showLoadingIndicator();
         }

         this.mAddResult = var1;
         this.log("addDcbConfirm");
         CreateCarrierBillingFlow.AfterProvisioning var3 = new CreateCarrierBillingFlow.AfterProvisioning((CreateCarrierBillingFlow.1)null);
         CreateCarrierBillingFlow.AfterError var4 = new CreateCarrierBillingFlow.AfterError((CreateCarrierBillingFlow.1)null);
         this.getProvisioning(var3, var4);
      } else {
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var5 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.EDIT_ADDRESS;
         if(var1 == var5) {
            this.mAddResult = var1;
            this.performNext();
         } else {
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var6 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.CANCELED;
            if(var1 == var6) {
               if(FinskyLog.DEBUG) {
                  Object[] var7 = new Object[0];
                  FinskyLog.v("carrier billing tos canceled.", var7);
               }

               this.cancel();
            } else {
               AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var8 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.NETWORK_FAILURE;
               if(var1 == var8) {
                  Object[] var9 = new Object[0];
                  FinskyLog.w("Network Connection error while loading Tos.", var9);
                  this.logError("NETWORK");
                  String var10 = FinskyApp.get().getString(2131230921);
                  this.showError(var10);
               } else {
                  this.logError("UNKNOWN");
                  Object[] var11 = new Object[0];
                  FinskyLog.w("Invalid error code.", var11);
                  this.showError();
               }
            }
         }
      }
   }

   public void onEditCarrierBillingResult(EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var1, SubscriberInfo var2) {
      this.hideEditFragment();
      EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var3 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.SUCCESS;
      if(var1 == var3) {
         if(FinskyLog.DEBUG) {
            Object[] var4 = new Object[0];
            FinskyLog.v("Editing Successful.", var4);
         }

         this.mUserProvidedAddress = var2;
         this.mEditResult = var1;
         this.log("addDcbEditConfirm");
         this.performNext();
      } else {
         EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var5 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.CANCELED;
         if(var1 == var5) {
            if(FinskyLog.DEBUG) {
               Object[] var6 = new Object[0];
               FinskyLog.v("carrier billing edit address canceled.", var6);
            }

            this.log("addDcbEditCancel");
            this.mEditResult = var1;
            if(this.isAddressInfoPresent()) {
               this.performNext();
            } else {
               this.cancel();
            }
         } else {
            Object[] var7 = new Object[0];
            FinskyLog.w("Invalid error code.", var7);
            this.logError("addDcbError");
            this.showError();
         }
      }
   }

   public void onErrorDismiss() {
      CreateCarrierBillingFlow.State var1 = CreateCarrierBillingFlow.State.DONE;
      this.mState = var1;
      this.cancel();
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      Object[] var4 = new Object[]{var3, var2};
      FinskyLog.w("Error received: %s / %s", var4);
      String var5 = ErrorStrings.get(FinskyApp.get(), var1, var2);
      this.showError(var5);
      String var6 = var1.name();
      this.logError(var6);
   }

   void performNext() {
      int[] var1 = CreateCarrierBillingFlow.8.$SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
      int var2 = this.mState.ordinal();
      switch(var1[var2]) {
      case 1:
         this.getSecondProvisioning();
         return;
      case 2:
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var4 = this.mAddResult;
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var5 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.EDIT_ADDRESS;
         if(var4 == var5) {
            CreateCarrierBillingFlow.State var6 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
            this.mState = var6;
            if(this.mUserProvidedAddress == null) {
               SubscriberInfo var7 = this.mStorage.getProvisioning().getSubscriberInfo();
               this.showEditFragment(var7);
               return;
            }

            SubscriberInfo var8 = this.mUserProvidedAddress;
            this.showEditFragment(var8);
            return;
         } else {
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var9 = this.mAddResult;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var10 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.SUCCESS;
            if(var9 == var10) {
               CreateCarrierBillingFlow.State var11 = CreateCarrierBillingFlow.State.SENDING_REQUEST;
               this.mState = var11;
               this.getCheckoutTokenAndQueueUpdateRequest();
               return;
            }

            StringBuilder var12 = (new StringBuilder()).append("Invalid result code in SHOWING_TOS_AND_ADDRESS state.");
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var13 = this.mAddResult;
            String var14 = var12.append(var13).toString();
            Object[] var15 = new Object[0];
            FinskyLog.w(var14, var15);
            return;
         }
      case 3:
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var16 = this.mAddResult;
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var17 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.EDIT_ADDRESS;
         if(var16 == var17) {
            CreateCarrierBillingFlow.State var18 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
            this.mState = var18;
            this.showEditFragment((SubscriberInfo)null);
            return;
         } else {
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var19 = this.mAddResult;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var20 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.SUCCESS;
            if(var19 == var20) {
               CreateCarrierBillingFlow.State var21 = CreateCarrierBillingFlow.State.SENDING_REQUEST;
               this.mState = var21;
               this.getCheckoutTokenAndQueueUpdateRequest();
               return;
            }

            StringBuilder var22 = (new StringBuilder()).append("Invalid result code in SHOWING_TOS_AND_ADDRESS_SNIPPET state.");
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var23 = this.mAddResult;
            String var24 = var22.append(var23).toString();
            Object[] var25 = new Object[0];
            FinskyLog.w(var24, var25);
            return;
         }
      case 4:
         EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var26 = this.mEditResult;
         EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var27 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.CANCELED;
         if(var26 == var27) {
            if(this.mUserProvidedAddress == null) {
               this.performNextInit();
               return;
            } else {
               CreateCarrierBillingFlow.State var28 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS;
               this.mState = var28;
               if(this.mStorage.getParams().showCarrierTos()) {
                  AddCarrierBillingFragment.Type var29 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS;
                  SubscriberInfo var30 = this.mUserProvidedAddress;
                  this.showAddFragment(var29, var30);
                  return;
               }

               AddCarrierBillingFragment.Type var31 = AddCarrierBillingFragment.Type.FULL_ADDRESS;
               SubscriberInfo var32 = this.mUserProvidedAddress;
               this.showAddFragment(var31, var32);
               return;
            }
         } else {
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var33 = this.mEditResult;
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var34 = EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult.SUCCESS;
            if(var33 == var34) {
               if(this.mUserProvidedAddress == null) {
                  Object[] var35 = new Object[0];
                  FinskyLog.w("User entered invalid address. Retrying.", var35);
                  this.showEditFragment((SubscriberInfo)null);
                  return;
               }

               CreateCarrierBillingFlow.State var36 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS;
               this.mState = var36;
               if(this.mStorage.getParams().showCarrierTos()) {
                  AddCarrierBillingFragment.Type var37 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS;
                  SubscriberInfo var38 = this.mUserProvidedAddress;
                  this.showAddFragment(var37, var38);
                  return;
               }

               AddCarrierBillingFragment.Type var39 = AddCarrierBillingFragment.Type.FULL_ADDRESS;
               SubscriberInfo var40 = this.mUserProvidedAddress;
               this.showAddFragment(var39, var40);
               return;
            }

            StringBuilder var41 = (new StringBuilder()).append("Invalid result code in SHOWING_EDIT_ADDRESS state.");
            EditCarrierBillingFragment.EditCarrierBillingResultListener.EditResult var42 = this.mEditResult;
            String var43 = var41.append(var42).toString();
            Object[] var44 = new Object[0];
            FinskyLog.w(var43, var44);
            return;
         }
      case 5:
         if(this.mUpdateInstrumentResponse.getResult() == 0) {
            CreateCarrierBillingFlow.State var45 = CreateCarrierBillingFlow.State.DONE;
            this.mState = var45;
            this.log("addDcbSuccess");
            this.finish();
            return;
         } else {
            BuyInstruments.UpdateInstrumentResponse var46 = this.mUpdateInstrumentResponse;
            if(this.isRetryableError(var46)) {
               List var47 = this.mUpdateInstrumentResponse.getErrorInputFieldList();
               ArrayList var48 = this.getInvalidEntries(var47);
               if(var48.isEmpty()) {
                  Object[] var49 = new Object[0];
                  FinskyLog.w("Could not add carrier billing instrument.", var49);
                  this.logError("UNKNOWN");
                  this.showError();
                  return;
               }

               CreateCarrierBillingFlow.State var50 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
               this.mState = var50;
               this.logError("INVALID_INPUT");
               this.showRetryError(var48);
               return;
            }

            Object[] var51 = new Object[0];
            FinskyLog.w("Could not add carrier billing instrument.", var51);
            this.logError("UNKNOWN");
            this.showError();
            return;
         }
      case 6:
         this.performNextInit();
         return;
      default:
         Object[] var3 = new Object[0];
         FinskyLog.w("Invalid Dcb state.", var3);
         this.logError("UNKNOWN");
         this.showError();
      }
   }

   public void resumeFromSavedState(Bundle var1) {
      CarrierBillingAction var2 = new CarrierBillingAction();
      CreateCarrierBillingFlow.2 var3 = new CreateCarrierBillingFlow.2(var1);
      var2.run(var3);
   }

   public void saveState(Bundle var1) {
      String var2 = this.mState.name();
      var1.putString("state", var2);
      if(this.mErrorFragment != null) {
         BillingFlowContext var3 = this.mContext;
         CarrierBillingErrorDialog var4 = this.mErrorFragment;
         var3.persistFragment(var1, "error_dialog", var4);
      }

      int[] var5 = CreateCarrierBillingFlow.8.$SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
      int var6 = this.mState.ordinal();
      switch(var5[var6]) {
      case 1:
      case 2:
      case 3:
         BillingFlowContext var7 = this.mContext;
         AddCarrierBillingFragment var8 = this.mAddFragment;
         var7.persistFragment(var1, "add_fragment", var8);
         return;
      case 4:
         BillingFlowContext var9 = this.mContext;
         EditCarrierBillingFragment var10 = this.mEditFragment;
         var9.persistFragment(var1, "edit_fragment", var10);
         return;
      default:
      }
   }

   void setAddResult(AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var1) {
      this.mAddResult = var1;
   }

   void setState(CreateCarrierBillingFlow.State var1) {
      this.mState = var1;
   }

   protected void setUpdateResponse(BuyInstruments.UpdateInstrumentResponse var1) {
      this.mUpdateInstrumentResponse = var1;
   }

   void showAddFragment(AddCarrierBillingFragment.Type var1) {
      this.showAddFragment(var1, (SubscriberInfo)null);
   }

   void showAddFragment(AddCarrierBillingFragment.Type var1, SubscriberInfo var2) {
      byte var6;
      label32: {
         AddCarrierBillingFragment.Type var3 = AddCarrierBillingFragment.Type.TOS;
         if(var1 != var3) {
            AddCarrierBillingFragment.Type var4 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET_AND_TOS;
            if(var1 != var4) {
               AddCarrierBillingFragment.Type var5 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS;
               if(var1 != var5) {
                  var6 = 0;
                  break label32;
               }
            }
         }

         var6 = 1;
      }

      byte var9;
      label26: {
         AddCarrierBillingFragment.Type var7 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET;
         if(var1 != var7) {
            AddCarrierBillingFragment.Type var8 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET_AND_TOS;
            if(var1 != var8) {
               var9 = 0;
               break label26;
            }
         }

         var9 = 1;
      }

      byte var12;
      label21: {
         AddCarrierBillingFragment.Type var10 = AddCarrierBillingFragment.Type.FULL_ADDRESS;
         if(var1 != var10) {
            AddCarrierBillingFragment.Type var11 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS;
            if(var1 != var11) {
               var12 = 0;
               break label21;
            }
         }

         var12 = 1;
      }

      this.logTosAndAddress((boolean)var6, (boolean)var9, (boolean)var12);
      AddCarrierBillingFragment var13 = AddCarrierBillingFragment.newInstance(var1, var2);
      this.mAddFragment = var13;
      this.mAddFragment.setOnResultListener(this);
      BillingFlowContext var14 = this.mContext;
      AddCarrierBillingFragment var15 = this.mAddFragment;
      var14.showFragment(var15, -1, (boolean)0);
   }

   public void start() {
      this.log("addDcb");
      CarrierBillingAction var1 = new CarrierBillingAction();
      CreateCarrierBillingFlow.1 var2 = new CreateCarrierBillingFlow.1();
      var1.run(var2);
   }

   private class AfterProvisioning implements Runnable {

      private AfterProvisioning() {}

      // $FF: synthetic method
      AfterProvisioning(CreateCarrierBillingFlow.1 var2) {
         this();
      }

      public void run() {
         CreateCarrierBillingFlow.this.performNext();
      }
   }

   class 7 implements Response.ErrorListener {

      7() {}

      public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
         Object[] var4 = new Object[]{var1, var2};
         FinskyLog.w("Error received: %s / %s", var4);
         CreateCarrierBillingFlow var5 = CreateCarrierBillingFlow.this;
         String var6 = ErrorStrings.get(FinskyApp.get(), var1, var2);
         var5.showError(var6);
      }
   }

   // $FF: synthetic class
   static class 8 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State = new int[CreateCarrierBillingFlow.State.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var1 = CreateCarrierBillingFlow.State.SHOWING_TOS.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var23) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var3 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var22) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var5 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS_SNIPPET.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var21) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var7 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var20) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var9 = CreateCarrierBillingFlow.State.SENDING_REQUEST.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$flow$CreateCarrierBillingFlow$State;
            int var11 = CreateCarrierBillingFlow.State.INIT.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var18) {
            ;
         }
      }
   }

   class 5 implements AndroidAuthenticator.AuthTokenListener {

      5() {}

      public void onAuthTokenReceived(String var1) {
         CreateCarrierBillingFlow.this.queueCarrierBillingUpdateRequest(var1);
      }

      public void onErrorReceived(AuthFailureException var1) {
         Object[] var2 = new Object[0];
         FinskyLog.w("Could not get Auth token", var2);
         CreateCarrierBillingFlow.this.showError();
      }
   }

   class 6 implements Response.Listener<BuyInstruments.UpdateInstrumentResponse> {

      6() {}

      public void onResponse(BuyInstruments.UpdateInstrumentResponse var1) {
         CreateCarrierBillingFlow.this.mUpdateInstrumentResponse = var1;
         CreateCarrierBillingFlow.this.performNext();
      }
   }

   class 3 implements Runnable {

      3() {}

      public void run() {
         CarrierBillingProvisioning var1 = CreateCarrierBillingFlow.this.mStorage.getProvisioning();
         if(!TextUtils.isEmpty(var1.getAddressSnippet())) {
            CreateCarrierBillingFlow var2 = CreateCarrierBillingFlow.this;
            CreateCarrierBillingFlow.State var3 = CreateCarrierBillingFlow.State.SHOWING_TOS_AND_ADDRESS_SNIPPET;
            var2.mState = var3;
            CreateCarrierBillingFlow.this.hideTosFragment();
            String var5 = (String)BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION.get();
            String var6 = var1.getTosVersion();
            if(var5 != null && var5.equals(var6)) {
               CreateCarrierBillingFlow var7 = CreateCarrierBillingFlow.this;
               AddCarrierBillingFragment.Type var8 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET;
               var7.showAddFragment(var8);
            } else {
               CreateCarrierBillingFlow var9 = CreateCarrierBillingFlow.this;
               AddCarrierBillingFragment.Type var10 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET_AND_TOS;
               var9.showAddFragment(var10);
            }
         } else {
            CreateCarrierBillingFlow var11 = CreateCarrierBillingFlow.this;
            CreateCarrierBillingFlow.State var12 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
            var11.mState = var12;
            CreateCarrierBillingFlow.this.showEditFragment((SubscriberInfo)null);
         }
      }
   }

   class 4 implements Runnable {

      4() {}

      public void run() {
         CreateCarrierBillingFlow var1 = CreateCarrierBillingFlow.this;
         CreateCarrierBillingFlow.State var2 = CreateCarrierBillingFlow.State.SHOWING_EDIT_ADDRESS;
         var1.mState = var2;
         CreateCarrierBillingFlow.this.showEditFragment((SubscriberInfo)null);
      }
   }

   class 1 implements Runnable {

      1() {}

      public void run() {
         CreateCarrierBillingFlow.this.performNext();
      }
   }

   class 2 implements Runnable {

      // $FF: synthetic field
      final Bundle val$bundle;


      2(Bundle var2) {
         this.val$bundle = var2;
      }

      public void run() {
         CreateCarrierBillingFlow var1 = CreateCarrierBillingFlow.this;
         Bundle var2 = this.val$bundle;
         var1.continueResume(var2);
      }
   }

   static enum State {

      // $FF: synthetic field
      private static final CreateCarrierBillingFlow.State[] $VALUES;
      DONE("DONE", 6),
      INIT("INIT", 0),
      SENDING_REQUEST("SENDING_REQUEST", 5),
      SHOWING_EDIT_ADDRESS("SHOWING_EDIT_ADDRESS", 4),
      SHOWING_TOS("SHOWING_TOS", 1),
      SHOWING_TOS_AND_ADDRESS("SHOWING_TOS_AND_ADDRESS", 2),
      SHOWING_TOS_AND_ADDRESS_SNIPPET("SHOWING_TOS_AND_ADDRESS_SNIPPET", 3);


      static {
         CreateCarrierBillingFlow.State[] var0 = new CreateCarrierBillingFlow.State[7];
         CreateCarrierBillingFlow.State var1 = INIT;
         var0[0] = var1;
         CreateCarrierBillingFlow.State var2 = SHOWING_TOS;
         var0[1] = var2;
         CreateCarrierBillingFlow.State var3 = SHOWING_TOS_AND_ADDRESS;
         var0[2] = var3;
         CreateCarrierBillingFlow.State var4 = SHOWING_TOS_AND_ADDRESS_SNIPPET;
         var0[3] = var4;
         CreateCarrierBillingFlow.State var5 = SHOWING_EDIT_ADDRESS;
         var0[4] = var5;
         CreateCarrierBillingFlow.State var6 = SENDING_REQUEST;
         var0[5] = var6;
         CreateCarrierBillingFlow.State var7 = DONE;
         var0[6] = var7;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }

   private class AfterError implements Runnable {

      private AfterError() {}

      // $FF: synthetic method
      AfterError(CreateCarrierBillingFlow.1 var2) {
         this();
      }

      public void run() {
         Object[] var1 = new Object[0];
         FinskyLog.d("Fetching provisioning from carrier failed", var1);
         CreateCarrierBillingFlow var2 = CreateCarrierBillingFlow.this;
         String var3 = FinskyApp.get().getString(2131230817);
         var2.showError(var3);
      }
   }
}
