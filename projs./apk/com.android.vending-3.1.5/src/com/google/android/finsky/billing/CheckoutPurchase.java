package com.google.android.finsky.billing;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.android.volley.AuthFailureException;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.AndroidAuthenticator;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.billing.FopFactory;
import com.google.android.finsky.billing.IabParameters;
import com.google.android.finsky.billing.Instrument;
import com.google.android.finsky.remoting.protos.Buy;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import com.google.android.finsky.utils.Maps;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckoutPurchase implements Response.Listener<Buy.BuyResponse>, Response.ErrorListener, AndroidAuthenticator.AuthTokenListener {

   private static final String KEY_BUY_RESPONSE = "buy_response";
   private static final String KEY_CHECKOUT_TOKEN = "checkout_token";
   private static final String KEY_COMPLETING_INSTRUMENT_ID = "instrument_id";
   private static final String KEY_COMPLETING_RISK_HEADER = "risk_header";
   private static final String KEY_ERROR = "error";
   private static final String KEY_IAB_PARAMETERS = "iab_parameters";
   private static final String KEY_NUM_AUTH_RETRIES = "num_auth_retries";
   private static final String KEY_STATE = "state";
   private static final int MAX_AUTH_RETRIES = 3;
   private static Map<CheckoutPurchase.State, List<CheckoutPurchase.State>> sValidTransitions = Maps.newHashMap();
   private Buy.BuyResponse mBuyResponse;
   private Buy.BuyResponse.CheckoutInfo mCheckoutInfo;
   private String mCheckoutToken;
   private final AndroidAuthenticator mCheckoutTokenAuthenticator;
   private boolean mCompletingHasAcceptedTos;
   private Instrument mCompletingInstrument;
   private String mCompletingRiskHeader;
   private Instrument mDefaultInstrument;
   private final DfeApi mDfeApi;
   private final String mDocId;
   private List<Integer> mEligibleInstrumentFamilies;
   private CheckoutPurchase.Error mError;
   private final FopFactory mFopFactory;
   private List<Instrument> mInstruments;
   private boolean mIsCheckoutTokenRetry;
   private CheckoutPurchase.Listener mListener;
   private int mNumAuthRetries;
   private final int mOfferType;
   private IabParameters mPreparingCompletingIabParameters;
   private Request<?> mPreparingRequest;
   private String mPurchaseStatusUrl;
   private CheckoutPurchase.State mState;
   private List<CheckoutPurchase.Tos> mTosList;


   static {
      Map var0 = sValidTransitions;
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARING;
      CheckoutPurchase.State[] var2 = new CheckoutPurchase.State[4];
      CheckoutPurchase.State var3 = CheckoutPurchase.State.INIT;
      var2[0] = var3;
      CheckoutPurchase.State var4 = CheckoutPurchase.State.PREPARED;
      var2[1] = var4;
      CheckoutPurchase.State var5 = CheckoutPurchase.State.PREPARING;
      var2[2] = var5;
      CheckoutPurchase.State var6 = CheckoutPurchase.State.ERROR;
      var2[3] = var6;
      ArrayList var7 = Lists.newArrayList(var2);
      var0.put(var1, var7);
      Map var9 = sValidTransitions;
      CheckoutPurchase.State var10 = CheckoutPurchase.State.PREPARED;
      CheckoutPurchase.State[] var11 = new CheckoutPurchase.State[2];
      CheckoutPurchase.State var12 = CheckoutPurchase.State.PREPARING;
      var11[0] = var12;
      CheckoutPurchase.State var13 = CheckoutPurchase.State.COMPLETING;
      var11[1] = var13;
      ArrayList var14 = Lists.newArrayList(var11);
      var9.put(var10, var14);
      Map var16 = sValidTransitions;
      CheckoutPurchase.State var17 = CheckoutPurchase.State.COMPLETING;
      CheckoutPurchase.State[] var18 = new CheckoutPurchase.State[2];
      CheckoutPurchase.State var19 = CheckoutPurchase.State.PREPARED;
      var18[0] = var19;
      CheckoutPurchase.State var20 = CheckoutPurchase.State.COMPLETING;
      var18[1] = var20;
      ArrayList var21 = Lists.newArrayList(var18);
      var16.put(var17, var21);
      Map var23 = sValidTransitions;
      CheckoutPurchase.State var24 = CheckoutPurchase.State.COMPLETED;
      CheckoutPurchase.State[] var25 = new CheckoutPurchase.State[1];
      CheckoutPurchase.State var26 = CheckoutPurchase.State.COMPLETING;
      var25[0] = var26;
      ArrayList var27 = Lists.newArrayList(var25);
      var23.put(var24, var27);
      Map var29 = sValidTransitions;
      CheckoutPurchase.State var30 = CheckoutPurchase.State.ERROR;
      ArrayList var31 = Lists.newArrayList(CheckoutPurchase.State.values());
      var29.put(var30, var31);
   }

   public CheckoutPurchase(DfeApi var1, AndroidAuthenticator var2, FopFactory var3, String var4, int var5) {
      ArrayList var6 = Lists.newArrayList();
      this.mInstruments = var6;
      ArrayList var7 = Lists.newArrayList();
      this.mEligibleInstrumentFamilies = var7;
      this.mDfeApi = var1;
      this.mFopFactory = var3;
      this.mDocId = var4;
      this.mOfferType = var5;
      this.mCheckoutTokenAuthenticator = var2;
      CheckoutPurchase.State var8 = CheckoutPurchase.State.INIT;
      this.mState = var8;
   }

   private void cancelCurrentAction() {
      CheckoutPurchase.State var1 = this.mState;
      CheckoutPurchase.State var2 = CheckoutPurchase.State.PREPARING;
      if(var1 == var2) {
         if(this.mPreparingRequest != null) {
            this.mPreparingRequest.cancel();
         }
      }
   }

   private void checkInState(CheckoutPurchase.State var1) {
      if(this.mState != var1) {
         StringBuilder var2 = (new StringBuilder()).append("This operation is only valid in state ").append(var1).append(". Current state = ");
         CheckoutPurchase.State var3 = this.mState;
         String var4 = var2.append(var3).append(")").toString();
         throw new IllegalStateException(var4);
      }
   }

   private void checkStateTransition(CheckoutPurchase.State var1, CheckoutPurchase.State var2) {
      if(!((List)sValidTransitions.get(var2)).contains(var1)) {
         String var3 = "Cannot transition from state " + var1 + " to " + var2;
         throw new IllegalStateException(var3);
      }
   }

   private void checkTosAcceptance() {
      if(!this.areAllTossesAccepted()) {
         Object[] var1 = new Object[0];
         FinskyLog.wtf("TOSes to accept present, not all were accepted!", var1);
      }
   }

   private void getAuthTokenAsyncAndPerformRequest() {
      byte var1;
      if(!this.mIsCheckoutTokenRetry && this.mCheckoutToken != null) {
         var1 = 0;
      } else {
         var1 = 1;
      }

      AndroidAuthenticator var2 = this.mCheckoutTokenAuthenticator;
      Looper var3 = Looper.getMainLooper();
      Handler var4 = new Handler(var3);
      var2.getAuthTokenAsync(this, var4, (boolean)var1);
   }

   private void populateFieldsFromBuyResponse() {
      Buy.BuyResponse.CheckoutInfo var1 = this.mBuyResponse.getCheckoutInfo();
      this.mCheckoutInfo = var1;
      List var2 = this.mCheckoutInfo.getCheckoutOptionList();
      this.mInstruments.clear();
      Iterator var3 = var2.iterator();

      while(var3.hasNext()) {
         Buy.BuyResponse.CheckoutInfo.CheckoutOption var4 = (Buy.BuyResponse.CheckoutInfo.CheckoutOption)var3.next();
         FopFactory var5 = this.mFopFactory;
         int var6 = var4.getInstrumentFamily();
         if(!var5.isRegistered(var6)) {
            Object[] var7 = new Object[3];
            String var8 = var4.getFormOfPayment();
            var7[0] = var8;
            String var9 = var4.getInstrumentId();
            var7[1] = var9;
            Integer var10 = Integer.valueOf(var4.getInstrumentFamily());
            var7[2] = var10;
            FinskyLog.w("Ignoring instrument [%s,id=%s]. Instrument %d family not supported.", var7);
         } else {
            FopFactory var11 = this.mFopFactory;
            int var12 = var4.getInstrumentFamily();
            Drawable var13 = var11.getAddIcon(var12);
            FopFactory var14 = this.mFopFactory;
            int var15 = var4.getInstrumentFamily();
            Instrument var16 = var14.get(var15, var4, var13);
            if(var4.getSelectedInstrument()) {
               this.mDefaultInstrument = var16;
            }

            this.mInstruments.add(var16);
         }
      }

      this.mEligibleInstrumentFamilies.clear();
      Iterator var18 = this.mCheckoutInfo.getEligibleInstrumentFamilyList().iterator();

      while(var18.hasNext()) {
         int var19 = ((Integer)var18.next()).intValue();
         if(!this.mFopFactory.isRegistered(var19)) {
            Object[] var20 = new Object[1];
            Integer var21 = Integer.valueOf(var19);
            var20[0] = var21;
            FinskyLog.w("Ignoring eligible instrument family %d. Not supported.", var20);
         } else {
            List var22 = this.mEligibleInstrumentFamilies;
            Integer var23 = Integer.valueOf(var19);
            var22.add(var23);
         }
      }

      this.mPreparingRequest = null;
      Buy.BuyResponse.CheckoutInfo var25 = this.mBuyResponse.getCheckoutInfo();
      this.mCheckoutInfo = var25;
      ArrayList var26 = Lists.newArrayList();
      Iterator var27 = this.mBuyResponse.getTosCheckboxHtmlList().iterator();

      while(var27.hasNext()) {
         String var28 = (String)var27.next();
         CheckoutPurchase.Tos var29 = new CheckoutPurchase.Tos(var28);
         var26.add(var29);
      }

      this.mTosList = var26;
   }

   private void queueCompletingRequest(Instrument var1) {
      String var2;
      if(this.mPreparingCompletingIabParameters == null) {
         var2 = null;
      } else {
         var2 = this.mPreparingCompletingIabParameters.developerPayload;
      }

      DfeApi var3 = this.mDfeApi;
      String var4 = this.mDocId;
      String var5 = var1.getCheckoutOption().getEncodedAdjustedCart();
      String var6 = this.mCheckoutToken;
      Map var7 = var1.getCompleteParams();
      boolean var8 = this.mCompletingHasAcceptedTos;
      String var9 = this.mCompletingRiskHeader;
      var3.completePurchase(var4, var5, var6, var7, var8, var9, var2, this, this);
   }

   private void queuePreparingRequest() {
      if(this.mPreparingCompletingIabParameters != null) {
         DfeApi var1 = this.mDfeApi;
         String var2 = this.mDocId;
         int var3 = this.mOfferType;
         String var4 = this.mCheckoutToken;
         String var5 = this.mPreparingCompletingIabParameters.packageName;
         String var6 = this.mPreparingCompletingIabParameters.packageSignatureHash;
         int var7 = this.mPreparingCompletingIabParameters.packageVersionCode;
         Map var8 = this.mFopFactory.getAllPrepareParameters();
         Request var11 = var1.makePurchase(var2, var3, var4, var5, var6, var7, var8, this, this);
         this.mPreparingRequest = var11;
      } else {
         DfeApi var12 = this.mDfeApi;
         String var13 = this.mDocId;
         int var14 = this.mOfferType;
         String var15 = this.mCheckoutToken;
         Map var16 = this.mFopFactory.getAllPrepareParameters();
         Request var19 = var12.makePurchase(var13, var14, var15, var16, this, this);
         this.mPreparingRequest = var19;
      }
   }

   private void retryLastRequest() {
      CheckoutPurchase.State var1 = this.mState;
      CheckoutPurchase.State var2 = CheckoutPurchase.State.PREPARING;
      if(var1 != var2) {
         CheckoutPurchase.State var3 = this.mState;
         CheckoutPurchase.State var4 = CheckoutPurchase.State.COMPLETING;
         if(var3 != var4) {
            throw new IllegalStateException("Must be in state PREPARING or COMPLETING for retry.");
         }
      }

      CheckoutPurchase.State var5 = this.mState;
      this.transitionToState(var5);
   }

   private void setError(CheckoutPurchase.Error var1) {
      this.mError = var1;
      Object[] var2 = new Object[3];
      String var3 = var1.type.name();
      var2[0] = var3;
      Integer var4 = Integer.valueOf(var1.code);
      var2[1] = var4;
      String var5 = var1.message;
      var2[2] = var5;
      FinskyLog.e("type=%s, code=%d, message=%s", var2);
      CheckoutPurchase.State var6 = CheckoutPurchase.State.ERROR;
      this.transitionToState(var6);
   }

   private void transitionToState(CheckoutPurchase.State var1) {
      CheckoutPurchase.State var2 = this.mState;
      this.checkStateTransition(var2, var1);
      this.cancelCurrentAction();
      CheckoutPurchase.State var3 = this.mState;
      this.mState = var1;
      if(this.mListener != null) {
         this.mListener.onStateChange(this, var3, var1);
      }

      CheckoutPurchase.State var4 = CheckoutPurchase.State.PREPARING;
      if(var1 == var4) {
         this.mError = null;
         this.getAuthTokenAsyncAndPerformRequest();
      } else {
         CheckoutPurchase.State var5 = CheckoutPurchase.State.PREPARED;
         if(var1 != var5) {
            CheckoutPurchase.State var6 = CheckoutPurchase.State.COMPLETING;
            if(var1 == var6) {
               this.mError = null;
               this.getAuthTokenAsyncAndPerformRequest();
            } else {
               CheckoutPurchase.State var7 = CheckoutPurchase.State.COMPLETED;
               if(var1 == var7) {
                  ;
               }
            }
         }
      }
   }

   public boolean areAllTossesAccepted() {
      Iterator var1 = this.mTosList.iterator();

      boolean var2;
      while(true) {
         if(var1.hasNext()) {
            if(((CheckoutPurchase.Tos)var1.next()).mIsAccepted) {
               continue;
            }

            var2 = false;
            break;
         }

         var2 = true;
         break;
      }

      return var2;
   }

   public void attach(CheckoutPurchase.Listener var1) {
      this.mListener = var1;
      CheckoutPurchase.State var2 = this.getState();
      CheckoutPurchase.State var3 = this.getState();
      var1.onStateChange(this, var2, var3);
   }

   public void complete(Instrument var1, String var2) {
      CheckoutPurchase.State var3 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var3);
      this.checkTosAcceptance();
      this.mCompletingInstrument = var1;
      byte var4;
      if(this.mTosList.size() > 0 && this.areAllTossesAccepted()) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      this.mCompletingHasAcceptedTos = (boolean)var4;
      this.mCompletingRiskHeader = var2;
      CheckoutPurchase.State var5 = CheckoutPurchase.State.COMPLETING;
      this.transitionToState(var5);
   }

   public void detach() {
      this.mListener = null;
   }

   public Buy.BuyResponse.CheckoutInfo getCheckoutInfo() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var1);
      return this.mCheckoutInfo;
   }

   public Instrument getDefaultInstrument() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var1);
      return this.mDefaultInstrument;
   }

   public List<Integer> getEligibleInstrumentFamilies() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var1);
      return this.mEligibleInstrumentFamilies;
   }

   public CheckoutPurchase.Error getError() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.ERROR;
      this.checkInState(var1);
      return this.mError;
   }

   public Instrument getInstrument(String var1) {
      Iterator var2 = this.mInstruments.iterator();

      Instrument var3;
      do {
         if(!var2.hasNext()) {
            var3 = null;
            break;
         }

         var3 = (Instrument)var2.next();
      } while(!var3.getInstrumentId().equals(var1));

      return var3;
   }

   public List<Instrument> getInstruments() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var1);
      return this.mInstruments;
   }

   public CheckoutPurchase.State getState() {
      return this.mState;
   }

   public String getStatusUrl() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.COMPLETED;
      this.checkInState(var1);
      return this.mPurchaseStatusUrl;
   }

   public List<CheckoutPurchase.Tos> getToses() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARED;
      this.checkInState(var1);
      return this.mTosList;
   }

   public void onAuthTokenReceived(String var1) {
      this.mCheckoutToken = var1;
      int[] var2 = CheckoutPurchase.1.$SwitchMap$com$google$android$finsky$billing$CheckoutPurchase$State;
      int var3 = this.getState().ordinal();
      switch(var2[var3]) {
      case 1:
         this.queuePreparingRequest();
         return;
      case 2:
         Instrument var7 = this.mCompletingInstrument;
         this.queueCompletingRequest(var7);
         return;
      default:
         StringBuilder var4 = (new StringBuilder()).append("Don\'t know which request to send for state: ");
         CheckoutPurchase.State var5 = this.getState();
         String var6 = var4.append(var5).toString();
         throw new IllegalStateException(var6);
      }
   }

   public void onErrorReceived(AuthFailureException var1) {
      StringBuilder var2 = (new StringBuilder()).append("Could not retrieve Checkout auth token: ");
      String var3 = var1.getMessage();
      String var4 = var2.append(var3).toString();
      Object[] var5 = new Object[0];
      FinskyLog.d(var4, var5);
      CheckoutPurchase.Error var6 = new CheckoutPurchase.Error;
      CheckoutPurchase.ErrorType var7 = CheckoutPurchase.ErrorType.UNKNOWN;
      CheckoutPurchase.State var8 = this.mState;
      CheckoutPurchase.State var9 = CheckoutPurchase.State.PREPARING;
      int var10;
      if(var8 == var9) {
         var10 = 2131230817;
      } else {
         var10 = 2131230818;
      }

      var6.<init>(var7, -1, var10);
      this.setError(var6);
   }

   public void onErrorResponse(Response.ErrorCode var1, String var2, NetworkError var3) {
      Response.ErrorCode var4 = Response.ErrorCode.AUTH;
      if(var1 == var4 && this.mNumAuthRetries < 3) {
         Object[] var5 = new Object[2];
         Integer var6 = Integer.valueOf(this.mNumAuthRetries);
         var5[0] = var6;
         Integer var7 = Integer.valueOf(3);
         var5[1] = var7;
         FinskyLog.d("Auth failure. Retry [n=%d,max=%d]", var5);
         int var8 = this.mNumAuthRetries + 1;
         this.mNumAuthRetries = var8;
         this.retryLastRequest();
      } else {
         CheckoutPurchase.ErrorType var11;
         label18: {
            String var9 = var1 + ": " + var2;
            Object[] var10 = new Object[0];
            FinskyLog.w(var9, var10);
            var11 = CheckoutPurchase.ErrorType.UNKNOWN;
            Response.ErrorCode var12 = Response.ErrorCode.SERVER;
            if(var1 != var12) {
               Response.ErrorCode var13 = Response.ErrorCode.NETWORK;
               if(var1 != var13) {
                  Response.ErrorCode var14 = Response.ErrorCode.TIMEOUT;
                  if(var1 != var14) {
                     break label18;
                  }
               }
            }

            var11 = CheckoutPurchase.ErrorType.NETWORK_OR_SERVER;
         }

         String var15 = ErrorStrings.get(FinskyApp.get(), var1, var2);
         CheckoutPurchase.Error var16 = new CheckoutPurchase.Error(var11, -1, var15);
         this.setError(var16);
      }
   }

   public void onResponse(Buy.BuyResponse var1) {
      this.mNumAuthRetries = 0;
      if(var1.getCheckoutTokenRequired()) {
         if(this.mIsCheckoutTokenRetry) {
            Object[] var2 = new Object[0];
            FinskyLog.w("Checkout token still invalid after having sent a fresh one.", var2);
            CheckoutPurchase.ErrorType var3 = CheckoutPurchase.ErrorType.NETWORK_OR_SERVER;
            CheckoutPurchase.Error var4 = new CheckoutPurchase.Error(var3, -1, 2131230817);
            this.setError(var4);
         } else {
            Object[] var5 = new Object[0];
            FinskyLog.d("Checkout token invalid, invalidating and retrying request.", var5);
            this.mIsCheckoutTokenRetry = (boolean)1;
            this.retryLastRequest();
         }
      } else {
         this.mIsCheckoutTokenRetry = (boolean)0;
         CheckoutPurchase.State var6 = this.mState;
         CheckoutPurchase.State var7 = CheckoutPurchase.State.PREPARING;
         if(var6 == var7) {
            this.mBuyResponse = var1;
            if(this.mBuyResponse != null && this.mBuyResponse.hasIabPermissionError()) {
               CheckoutPurchase.ErrorType var8 = CheckoutPurchase.ErrorType.IAB_PERMISSION_ERROR;
               int var9 = this.mBuyResponse.getIabPermissionError();
               CheckoutPurchase.Error var10 = new CheckoutPurchase.Error(var8, var9, (String)null);
               this.setError(var10);
            } else if(this.mBuyResponse != null && this.mBuyResponse.hasCheckoutInfo()) {
               this.populateFieldsFromBuyResponse();
               CheckoutPurchase.State var11 = CheckoutPurchase.State.PREPARED;
               this.transitionToState(var11);
            } else {
               CheckoutPurchase.ErrorType var12 = CheckoutPurchase.ErrorType.NETWORK_OR_SERVER;
               CheckoutPurchase.Error var13 = new CheckoutPurchase.Error(var12, -1, 2131230817);
               this.setError(var13);
            }
         } else {
            CheckoutPurchase.State var14 = this.mState;
            CheckoutPurchase.State var15 = CheckoutPurchase.State.COMPLETING;
            if(var14 == var15) {
               if(var1.hasPurchaseStatusUrl()) {
                  String var16 = var1.getPurchaseStatusUrl();
                  this.mPurchaseStatusUrl = var16;
                  this.mCompletingInstrument = null;
                  CheckoutPurchase.State var17 = CheckoutPurchase.State.COMPLETED;
                  this.transitionToState(var17);
               } else {
                  Object[] var18 = new Object[0];
                  FinskyLog.w("BuyResponse without purchaseStatusUrl.", var18);
                  CheckoutPurchase.ErrorType var19 = CheckoutPurchase.ErrorType.NETWORK_OR_SERVER;
                  CheckoutPurchase.Error var20 = new CheckoutPurchase.Error(var19, -1, 2131230817);
                  this.setError(var20);
               }
            } else {
               StringBuilder var21 = (new StringBuilder()).append("Received network response while in state: ");
               CheckoutPurchase.State var22 = this.getState();
               String var23 = var21.append(var22).toString();
               throw new IllegalStateException(var23);
            }
         }
      }
   }

   public void prepare() {
      CheckoutPurchase.State var1 = CheckoutPurchase.State.PREPARING;
      this.transitionToState(var1);
   }

   public void resumeFromSavedState(Bundle var1) {
      CheckoutPurchase.State var2 = CheckoutPurchase.State.valueOf(var1.getString("state"));
      CheckoutPurchase.Error var3 = (CheckoutPurchase.Error)var1.getParcelable("error");
      this.mError = var3;
      IabParameters var4 = (IabParameters)var1.getParcelable("iab_parameters");
      this.mPreparingCompletingIabParameters = var4;
      CheckoutPurchase.State var5 = CheckoutPurchase.State.PREPARING;
      if(var2 == var5) {
         CheckoutPurchase.State var6 = CheckoutPurchase.State.INIT;
         this.mState = var6;
         this.prepare();
      } else {
         CheckoutPurchase.State var7 = CheckoutPurchase.State.COMPLETING;
         if(var2 == var7) {
            CheckoutPurchase.ErrorType var8 = CheckoutPurchase.ErrorType.UNKNOWN;
            String var9 = FinskyApp.get().getString(2131230819);
            CheckoutPurchase.Error var10 = new CheckoutPurchase.Error(var8, -1, var9);
            this.setError(var10);
         } else {
            if(var1.containsKey("buy_response")) {
               Buy.BuyResponse var11 = (Buy.BuyResponse)((ParcelableProto)var1.getParcelable("buy_response")).getPayload();
               this.mBuyResponse = var11;
               this.populateFieldsFromBuyResponse();
            }

            if(var1.containsKey("instrument_id")) {
               String var12 = var1.getString("instrument_id");
               Instrument var13 = this.getInstrument(var12);
               this.mCompletingInstrument = var13;
               if(this.mCompletingInstrument == null) {
                  String var14 = "Could not find instrument with persisted ID " + var12;
                  throw new IllegalStateException(var14);
               }
            }

            if(var1.containsKey("risk_header")) {
               String var15 = var1.getString("risk_header");
               this.mCompletingRiskHeader = var15;
            }

            this.mState = var2;
         }
      }
   }

   public void saveState(Bundle var1) {
      String var2 = this.mState.name();
      var1.putString("state", var2);
      CheckoutPurchase.Error var3 = this.mError;
      var1.putParcelable("error", var3);
      int var4 = this.mNumAuthRetries;
      var1.putInt("num_auth_retries", var4);
      String var5 = this.mCheckoutToken;
      var1.putString("checkout_token", var5);
      if(this.mPreparingCompletingIabParameters != null) {
         IabParameters var6 = this.mPreparingCompletingIabParameters;
         var1.putParcelable("iab_parameters", var6);
      }

      if(this.mCheckoutInfo != null) {
         ParcelableProto var7 = ParcelableProto.forProto(this.mBuyResponse);
         var1.putParcelable("buy_response", var7);
      }

      if(this.mCompletingInstrument != null) {
         String var8 = this.mCompletingInstrument.getInstrumentId();
         var1.putString("instrument_id", var8);
      }

      if(this.mCompletingRiskHeader != null) {
         String var9 = this.mCompletingRiskHeader;
         var1.putString("risk_header", var9);
      }
   }

   public void setIabParameters(IabParameters var1) {
      this.mPreparingCompletingIabParameters = var1;
   }

   public interface Listener {

      void onStateChange(CheckoutPurchase var1, CheckoutPurchase.State var2, CheckoutPurchase.State var3);
   }

   public static class Error implements Parcelable {

      public static final Creator<CheckoutPurchase.Error> CREATOR = new CheckoutPurchase.Error.1();
      public final int code;
      public final String message;
      public final CheckoutPurchase.ErrorType type;


      public Error(CheckoutPurchase.ErrorType var1, int var2, int var3) {
         this.type = var1;
         this.code = var2;
         String var4 = FinskyApp.get().getString(var3);
         this.message = var4;
      }

      public Error(CheckoutPurchase.ErrorType var1, int var2, String var3) {
         this.type = var1;
         this.code = var2;
         this.message = var3;
      }

      public int describeContents() {
         return 0;
      }

      public boolean equals(Object var1) {
         boolean var2 = false;
         if(var1 != null && var1 instanceof CheckoutPurchase.Error) {
            CheckoutPurchase.Error var3 = (CheckoutPurchase.Error)var1;
            CheckoutPurchase.ErrorType var4 = this.type;
            CheckoutPurchase.ErrorType var5 = var3.type;
            if(var4.equals(var5)) {
               int var6 = this.code;
               int var7 = var3.code;
               if(var6 == var7) {
                  String var8 = this.message;
                  String var9 = var3.message;
                  if(var8 != var9) {
                     if(this.message == null) {
                        return var2;
                     }

                     String var10 = this.message;
                     String var11 = var3.message;
                     if(!var10.equals(var11)) {
                        return var2;
                     }
                  }

                  var2 = true;
               }
            }
         }

         return var2;
      }

      public String toString() {
         Object[] var1 = new Object[3];
         String var2 = this.type.name();
         var1[0] = var2;
         Integer var3 = Integer.valueOf(this.code);
         var1[1] = var3;
         String var4 = this.message;
         var1[2] = var4;
         return String.format("Error[type=%s,code=%d,message=%s", var1);
      }

      public void writeToParcel(Parcel var1, int var2) {
         String var3 = this.type.name();
         var1.writeString(var3);
         int var4 = this.code;
         var1.writeInt(var4);
         String var5 = this.message;
         var1.writeString(var5);
      }

      static class 1 implements Creator<CheckoutPurchase.Error> {

         1() {}

         public CheckoutPurchase.Error createFromParcel(Parcel var1) {
            String var2 = var1.readString();
            int var3 = var1.readInt();
            String var4 = var1.readString();
            CheckoutPurchase.ErrorType var5 = CheckoutPurchase.ErrorType.valueOf(var2);
            return new CheckoutPurchase.Error(var5, var3, var4);
         }

         public CheckoutPurchase.Error[] newArray(int var1) {
            return new CheckoutPurchase.Error[var1];
         }
      }
   }

   public class Tos {

      protected boolean mIsAccepted;
      protected String mTosLink;


      protected Tos(String var2) {
         this.mTosLink = var2;
         this.mIsAccepted = (boolean)0;
      }

      public String getShorthand() {
         return this.mTosLink;
      }

      public void setAcceptance(boolean var1) {
         this.mIsAccepted = var1;
      }
   }

   public static enum ErrorType {

      // $FF: synthetic field
      private static final CheckoutPurchase.ErrorType[] $VALUES;
      IAB_PERMISSION_ERROR("IAB_PERMISSION_ERROR", 1),
      NETWORK_OR_SERVER("NETWORK_OR_SERVER", 2),
      UNKNOWN("UNKNOWN", 0);


      static {
         CheckoutPurchase.ErrorType[] var0 = new CheckoutPurchase.ErrorType[3];
         CheckoutPurchase.ErrorType var1 = UNKNOWN;
         var0[0] = var1;
         CheckoutPurchase.ErrorType var2 = IAB_PERMISSION_ERROR;
         var0[1] = var2;
         CheckoutPurchase.ErrorType var3 = NETWORK_OR_SERVER;
         var0[2] = var3;
         $VALUES = var0;
      }

      private ErrorType(String var1, int var2) {}
   }

   public static enum State {

      // $FF: synthetic field
      private static final CheckoutPurchase.State[] $VALUES;
      COMPLETED("COMPLETED", 4),
      COMPLETING("COMPLETING", 3),
      ERROR("ERROR", 5),
      INIT("INIT", 0),
      PREPARED("PREPARED", 2),
      PREPARING("PREPARING", 1);


      static {
         CheckoutPurchase.State[] var0 = new CheckoutPurchase.State[6];
         CheckoutPurchase.State var1 = INIT;
         var0[0] = var1;
         CheckoutPurchase.State var2 = PREPARING;
         var0[1] = var2;
         CheckoutPurchase.State var3 = PREPARED;
         var0[2] = var3;
         CheckoutPurchase.State var4 = COMPLETING;
         var0[3] = var4;
         CheckoutPurchase.State var5 = COMPLETED;
         var0[4] = var5;
         CheckoutPurchase.State var6 = ERROR;
         var0[5] = var6;
         $VALUES = var0;
      }

      private State(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$CheckoutPurchase$State = new int[CheckoutPurchase.State.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$billing$CheckoutPurchase$State;
            int var1 = CheckoutPurchase.State.PREPARING.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$billing$CheckoutPurchase$State;
            int var3 = CheckoutPurchase.State.COMPLETING.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var6) {
            ;
         }
      }
   }
}
