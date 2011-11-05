package com.google.android.finsky.billing.carrierbilling.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressWidget;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.billing.carrierbilling.model.SubscriberInfo;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.FinskyLog;
import java.util.List;

public class AddCarrierBillingFragment extends Fragment implements OnClickListener {

   private static final String EDITED_ADDRESS_KEY = "edited_address";
   private static final String TYPE_KEY = "type";
   private Button mAcceptButton;
   private View mCarrierTosMainView;
   AddCarrierBillingFragment.AddCarrierBillingResultListener mListener;
   private View mProgressIndicator;
   private String mTosUrl;
   private String mTosVersion;
   private AddCarrierBillingFragment.CarrierTosWebViewClient mTosWebViewclient;


   public AddCarrierBillingFragment() {}

   private void forceFinishLoadingTos(View var1) {
      var1.findViewById(2131755079).setVisibility(0);
      this.mAcceptButton.setEnabled((boolean)1);
      var1.findViewById(2131755093).setVisibility(8);
   }

   public static AddCarrierBillingFragment newInstance(AddCarrierBillingFragment.Type var0, SubscriberInfo var1) {
      AddCarrierBillingFragment var2 = new AddCarrierBillingFragment();
      Bundle var3 = new Bundle();
      String var4 = var0.name();
      var3.putString("type", var4);
      var3.putParcelable("edited_address", var1);
      var2.setArguments(var3);
      return var2;
   }

   private void setAddressToFull(View var1, SubscriberInfo var2) {
      String var3 = BillingLocator.getCarrierBillingStorage().getParams().getName();
      Object[] var4 = new Object[]{var3};
      String var5 = this.getString(2131230806, var4);
      ((TextView)var1.findViewById(2131755086)).setText(var5);
      SubscriberInfo var6;
      if(var2 != null) {
         var6 = var2;
      } else {
         var6 = BillingLocator.getCarrierBillingStorage().getProvisioning().getSubscriberInfo();
      }

      AddressData.Builder var7 = new AddressData.Builder();
      String var8 = var6.getName();
      AddressData.Builder var9 = var7.setRecipient(var8);
      String var10 = var6.getAddress1();
      AddressData.Builder var11 = var9.setAddressLine1(var10);
      String var12 = var6.getAddress2();
      AddressData.Builder var13 = var11.setAddressLine2(var12);
      String var14 = var6.getCity();
      AddressData.Builder var15 = var13.setLocality(var14);
      String var16 = var6.getState();
      AddressData.Builder var17 = var15.setAdminArea(var16);
      String var18 = var6.getPostalCode();
      AddressData.Builder var19 = var17.setPostalCode(var18);
      String var20 = var6.getCountry();
      AddressData var21 = var19.setCountry(var20).build();
      TextView var22 = (TextView)var1.findViewById(2131755089);
      if(TextUtils.isEmpty(var21.getRecipient()) && TextUtils.isEmpty(var21.getAddressLine1()) && TextUtils.isEmpty(var21.getAddressLine2()) && TextUtils.isEmpty(var21.getLocality()) && TextUtils.isEmpty(var21.getAdministrativeArea()) && TextUtils.isEmpty(var21.getPostalCode()) && TextUtils.isEmpty(var21.getPostalCountry())) {
         var22.setVisibility(8);
      } else {
         var22.setVisibility(0);
         Context var23 = this.getActivity().getBaseContext();
         List var24 = AddressWidget.getFullEnvelopeAddress(var21, var23);
         String var25 = TextUtils.join("\n", var24);
         var22.setText(var25);
      }

      String var26 = var6.getIdentifier();
      if(BillingUtils.isEmptyOrSpaces(var26)) {
         var26 = PhoneNumberUtils.formatNumber(BillingLocator.getLine1NumberFromTelephony());
      }

      TextView var27 = (TextView)var1.findViewById(2131755090);
      this.showPhoneNumber(var27, var26);
      ((TextView)var1.findViewById(2131755091)).setVisibility(8);
   }

   private void setAddressToSnippet(View var1) {
      String var2 = BillingLocator.getCarrierBillingStorage().getProvisioning().getAddressSnippet();
      String var3 = BillingLocator.getCarrierBillingStorage().getProvisioning().getCountry();
      String var4 = BillingLocator.getCarrierBillingStorage().getParams().getName();
      Object[] var5 = new Object[]{var4};
      String var6 = this.getString(2131230805, var5);
      ((TextView)var1.findViewById(2131755086)).setText(var6);
      ((TextView)var1.findViewById(2131755089)).setText(var2);
      ((TextView)var1.findViewById(2131755091)).setVisibility(0);
      ((TextView)var1.findViewById(2131755090)).setVisibility(8);
   }

   private void setUpTos() {
      CarrierBillingProvisioning var1 = BillingLocator.getCarrierBillingStorage().getProvisioning();
      String var2 = var1.getTosVersion();
      this.mTosVersion = var2;
      String var3 = var1.getTosUrl();
      String var4 = this.getString(2131230804);
      if(!TextUtils.isEmpty(var4)) {
         var3 = var3.replace("%locale%", var4);
      }

      String var5 = BillingUtils.replaceLocale(var3);
      this.mTosUrl = var5;
   }

   private void setUpViewForType(View var1, AddCarrierBillingFragment.Type var2, SubscriberInfo var3) {
      int[] var4 = AddCarrierBillingFragment.1.$SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
      int var5 = var2.ordinal();
      switch(var4[var5]) {
      case 1:
         this.showTosSection(var1, (boolean)0);
         this.forceFinishLoadingTos(var1);
         this.showAddressSection(var1, (boolean)1);
         this.setAddressToSnippet(var1);
         return;
      case 2:
         var1.findViewById(2131755084).findViewById(2131755092).setVisibility(0);
         this.showTosSection(var1, (boolean)1);
         this.showAddressSection(var1, (boolean)1);
         this.setAddressToSnippet(var1);
         return;
      case 3:
         this.showTosSection(var1, (boolean)0);
         this.forceFinishLoadingTos(var1);
         this.showAddressSection(var1, (boolean)1);
         this.setAddressToFull(var1, var3);
         return;
      case 4:
         var1.findViewById(2131755084).findViewById(2131755092).setVisibility(0);
         this.showTosSection(var1, (boolean)1);
         this.showAddressSection(var1, (boolean)1);
         this.setAddressToFull(var1, var3);
         return;
      case 5:
         this.showTosSection(var1, (boolean)1);
         this.showAddressSection(var1, (boolean)0);
         return;
      default:
         String var6 = "Unexpected type " + var2;
         Object[] var7 = new Object[0];
         FinskyLog.d(var6, var7);
      }
   }

   private void showAddressSection(View var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      View var4 = var1.findViewById(2131755084);
      var4.findViewById(2131755086).setVisibility(var3);
      var4.findViewById(2131755087).setVisibility(var3);
      var4.findViewById(2131755088).setVisibility(var3);
      var4.findViewById(2131755089).setVisibility(var3);
      var4.findViewById(2131755090).setVisibility(var3);
      var4.findViewById(2131755091).setVisibility(var3);
   }

   private void showPhoneNumber(TextView var1, String var2) {
      if(!BillingUtils.isEmptyOrSpaces(var2)) {
         var1.setVisibility(0);
         var1.setText(var2);
      } else {
         var1.setVisibility(8);
      }
   }

   private void showTosSection(View var1, boolean var2) {
      byte var3;
      if(var2) {
         var3 = 0;
      } else {
         var3 = 8;
      }

      View var4 = var1.findViewById(2131755083);
      if(var2) {
         WebView var5 = (WebView)var4.findViewById(2131755081);
         View var6 = var4.findViewById(2131755093);
         View var7 = var4.findViewById(2131755079);
         AddCarrierBillingFragment.CarrierTosWebViewClient var8 = this.getCarrierTosWebViewClient(var6, var7);
         this.mTosWebViewclient = var8;
         AddCarrierBillingFragment.CarrierTosWebViewClient var9 = this.mTosWebViewclient;
         var5.setWebViewClient(var9);
         String var10 = this.mTosUrl;
         var5.loadUrl(var10);
         var5.getSettings().setSupportZoom((boolean)0);
         this.mAcceptButton.setEnabled((boolean)0);
      }

      var4.findViewById(2131755081).setVisibility(var3);
   }

   AddCarrierBillingFragment.CarrierTosWebViewClient getCarrierTosWebViewClient(View var1, View var2) {
      return new AddCarrierBillingFragment.CarrierTosWebViewClient(var1, var2);
   }

   public void hideLoadingIndicator() {
      if(this.mCarrierTosMainView != null) {
         if(this.mProgressIndicator != null) {
            this.mCarrierTosMainView.setVisibility(0);
            this.mProgressIndicator.setVisibility(8);
         }
      }
   }

   void onClick(int var1) {
      switch(var1) {
      case 2131755087:
         AddCarrierBillingFragment.AddCarrierBillingResultListener var9 = this.mListener;
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var10 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.EDIT_ADDRESS;
         var9.onAddCarrierBillingResult(var10);
         return;
      case 2131755094:
         PreferenceFile.SharedPreference var3 = BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION;
         String var4 = this.mTosVersion;
         var3.put(var4);
         AddCarrierBillingFragment.AddCarrierBillingResultListener var5 = this.mListener;
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var6 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.SUCCESS;
         var5.onAddCarrierBillingResult(var6);
         return;
      case 2131755095:
         AddCarrierBillingFragment.AddCarrierBillingResultListener var7 = this.mListener;
         AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var8 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.CANCELED;
         var7.onAddCarrierBillingResult(var8);
         return;
      default:
         Object[] var2 = new Object[0];
         FinskyLog.d("Unexpected button press. do nothing.", var2);
      }
   }

   public void onClick(View var1) {
      int var2 = var1.getId();
      this.onClick(var2);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      this.setUpTos();
      View var4 = var1.inflate(2130968597, var2, (boolean)0);
      View var5 = var4.findViewById(2131755079);
      this.mCarrierTosMainView = var5;
      View var6 = var4.findViewById(2131755093);
      this.mProgressIndicator = var6;
      this.hideLoadingIndicator();
      Button var7 = (Button)var4.findViewById(2131755094);
      this.mAcceptButton = var7;
      this.mAcceptButton.setOnClickListener(this);
      ((Button)var4.findViewById(2131755095)).setOnClickListener(this);
      ((ImageButton)var4.findViewById(2131755087)).setOnClickListener(this);
      Bundle var8 = this.getArguments();
      AddCarrierBillingFragment.Type var9 = AddCarrierBillingFragment.Type.valueOf(var8.getString("type"));
      SubscriberInfo var10 = (SubscriberInfo)var8.getParcelable("edited_address");
      View var11 = var4.findViewById(2131755083);
      this.setUpViewForType(var11, var9, var10);
      return var4;
   }

   public void setOnResultListener(AddCarrierBillingFragment.AddCarrierBillingResultListener var1) {
      this.mListener = var1;
   }

   public void showLoadingIndicator() {
      if(this.mCarrierTosMainView != null) {
         if(this.mProgressIndicator != null) {
            this.mCarrierTosMainView.setVisibility(8);
            this.mProgressIndicator.setVisibility(0);
         }
      }
   }

   public interface AddCarrierBillingResultListener {

      void onAddCarrierBillingResult(AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var1);

      public static enum AddResult {

         // $FF: synthetic field
         private static final AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult[] $VALUES;
         CANCELED("CANCELED", 2),
         EDIT_ADDRESS("EDIT_ADDRESS", 3),
         NETWORK_FAILURE("NETWORK_FAILURE", 1),
         SUCCESS("SUCCESS", 0);


         static {
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult[] var0 = new AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult[4];
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var1 = SUCCESS;
            var0[0] = var1;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var2 = NETWORK_FAILURE;
            var0[1] = var2;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var3 = CANCELED;
            var0[2] = var3;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var4 = EDIT_ADDRESS;
            var0[3] = var4;
            $VALUES = var0;
         }

         private AddResult(String var1, int var2) {}
      }
   }

   public static enum Type {

      // $FF: synthetic field
      private static final AddCarrierBillingFragment.Type[] $VALUES;
      ADDRESS_SNIPPET("ADDRESS_SNIPPET", 0),
      ADDRESS_SNIPPET_AND_TOS("ADDRESS_SNIPPET_AND_TOS", 1),
      FULL_ADDRESS("FULL_ADDRESS", 2),
      FULL_ADDRESS_AND_TOS("FULL_ADDRESS_AND_TOS", 3),
      TOS("TOS", 4);


      static {
         AddCarrierBillingFragment.Type[] var0 = new AddCarrierBillingFragment.Type[5];
         AddCarrierBillingFragment.Type var1 = ADDRESS_SNIPPET;
         var0[0] = var1;
         AddCarrierBillingFragment.Type var2 = ADDRESS_SNIPPET_AND_TOS;
         var0[1] = var2;
         AddCarrierBillingFragment.Type var3 = FULL_ADDRESS;
         var0[2] = var3;
         AddCarrierBillingFragment.Type var4 = FULL_ADDRESS_AND_TOS;
         var0[3] = var4;
         AddCarrierBillingFragment.Type var5 = TOS;
         var0[4] = var5;
         $VALUES = var0;
      }

      private Type(String var1, int var2) {}
   }

   private class CarrierTosWebViewClient extends WebViewClient {

      private final View mProgress;
      private boolean mReceivedError;
      private final View mTosDisplayView;


      public CarrierTosWebViewClient(View var2, View var3) {
         this.mProgress = var2;
         this.mTosDisplayView = var3;
         this.mReceivedError = (boolean)0;
         this.mProgress.setVisibility(0);
      }

      public void onPageFinished(WebView var1, String var2) {
         var1.setVisibility(0);
         this.mTosDisplayView.setVisibility(0);
         if(!this.mReceivedError) {
            AddCarrierBillingFragment.this.mAcceptButton.setEnabled((boolean)1);
         }

         this.mProgress.setVisibility(8);
      }

      public void onReceivedError(WebView var1, int var2, String var3, String var4) {
         String var5 = "Web error: (" + var4 + ") " + var3;
         Object[] var6 = new Object[0];
         FinskyLog.w(var5, var6);
         this.mReceivedError = (boolean)1;
         if(AddCarrierBillingFragment.this.mListener != null) {
            AddCarrierBillingFragment.AddCarrierBillingResultListener var7 = AddCarrierBillingFragment.this.mListener;
            AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult var8 = AddCarrierBillingFragment.AddCarrierBillingResultListener.AddResult.NETWORK_FAILURE;
            var7.onAddCarrierBillingResult(var8);
         }
      }
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type = new int[AddCarrierBillingFragment.Type.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
            int var1 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var19) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
            int var3 = AddCarrierBillingFragment.Type.ADDRESS_SNIPPET_AND_TOS.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var18) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
            int var5 = AddCarrierBillingFragment.Type.FULL_ADDRESS.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var17) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
            int var7 = AddCarrierBillingFragment.Type.FULL_ADDRESS_AND_TOS.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var16) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$google$android$finsky$billing$carrierbilling$fragment$AddCarrierBillingFragment$Type;
            int var9 = AddCarrierBillingFragment.Type.TOS.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var15) {
            ;
         }
      }
   }
}
