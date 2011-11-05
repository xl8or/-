package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.BillingPreferences;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;
import com.google.android.finsky.config.PreferenceFile;
import com.google.android.finsky.utils.FinskyLog;

public class CarrierTosDialogFragment extends DialogFragment implements OnClickListener {

   private Button mAcceptButton;
   private CarrierTosDialogFragment.CarrierTosResultListener mListener;
   private String mTosUrl;
   private String mTosVersion;
   private CarrierTosDialogFragment.CarrierTosWebViewClient mTosWebViewclient;


   public CarrierTosDialogFragment() {}

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

   CarrierTosDialogFragment.CarrierTosWebViewClient getCarrierTosWebViewClient(View var1, View var2) {
      return new CarrierTosDialogFragment.CarrierTosWebViewClient(var1, var2);
   }

   public void onCancel(DialogInterface var1) {
      super.onCancel(var1);
      CarrierTosDialogFragment.CarrierTosResultListener var2 = this.mListener;
      CarrierTosDialogFragment.CarrierTosResultListener.TosResult var3 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.CANCELED;
      var2.onCarrierTosResult(var3);
   }

   public void onClick(View var1) {
      switch(var1.getId()) {
      case 2131755094:
         PreferenceFile.SharedPreference var3 = BillingPreferences.ACCEPTED_CARRIER_TOS_VERSION;
         String var4 = this.mTosVersion;
         var3.put(var4);
         CarrierTosDialogFragment.CarrierTosResultListener var5 = this.mListener;
         CarrierTosDialogFragment.CarrierTosResultListener.TosResult var6 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.SUCCESS;
         var5.onCarrierTosResult(var6);
         return;
      case 2131755095:
         CarrierTosDialogFragment.CarrierTosResultListener var7 = this.mListener;
         CarrierTosDialogFragment.CarrierTosResultListener.TosResult var8 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.CANCELED;
         var7.onCarrierTosResult(var8);
         return;
      default:
         Object[] var2 = new Object[0];
         FinskyLog.d("Unexpected button press.", var2);
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      View var3 = View.inflate(new ContextThemeWrapper(var2, 16973829), 2130968596, (ViewGroup)null);
      this.setUpTos();
      Button var4 = (Button)var3.findViewById(2131755094);
      this.mAcceptButton = var4;
      this.mAcceptButton.setOnClickListener(this);
      ((Button)var3.findViewById(2131755095)).setOnClickListener(this);
      WebView var5 = (WebView)var3.findViewById(2131755081);
      View var6 = var3.findViewById(2131755082);
      View var7 = var3.findViewById(2131755079);
      CarrierTosDialogFragment.CarrierTosWebViewClient var8 = this.getCarrierTosWebViewClient(var6, var7);
      this.mTosWebViewclient = var8;
      CarrierTosDialogFragment.CarrierTosWebViewClient var9 = this.mTosWebViewclient;
      var5.setWebViewClient(var9);
      String var10 = this.mTosUrl;
      var5.loadUrl(var10);
      var5.getSettings().setSupportZoom((boolean)0);
      this.mAcceptButton.setEnabled((boolean)0);
      String var11 = BillingLocator.getCarrierBillingStorage().getParams().getName();
      FragmentActivity var12 = this.getActivity();
      Builder var13 = new Builder(var12);
      Object[] var14 = new Object[]{var11};
      String var15 = this.getString(2131230803, var14);
      AlertDialog var16 = var13.setTitle(var15).create();
      byte var17 = 0;
      byte var18 = 0;
      var16.setView(var3, 0, 5, var17, var18);
      return var16;
   }

   public void setOnResultListener(CarrierTosDialogFragment.CarrierTosResultListener var1) {
      this.mListener = var1;
   }

   private class CarrierTosWebViewClient extends WebViewClient {

      private final View mProgress;
      private boolean mReceivedError;
      private final View mTosDisplayView;


      public CarrierTosWebViewClient(View var2, View var3) {
         this.mProgress = var2;
         this.mTosDisplayView = var3;
         this.mReceivedError = (boolean)0;
      }

      public void onPageFinished(WebView var1, String var2) {
         var1.setVisibility(0);
         this.mTosDisplayView.setVisibility(0);
         if(!this.mReceivedError) {
            CarrierTosDialogFragment.this.mAcceptButton.setEnabled((boolean)1);
         }

         this.mProgress.setVisibility(8);
      }

      public void onReceivedError(WebView var1, int var2, String var3, String var4) {
         String var5 = "Web error: (" + var4 + ") " + var3;
         Object[] var6 = new Object[0];
         FinskyLog.w(var5, var6);
         this.mReceivedError = (boolean)1;
         CarrierTosDialogFragment.CarrierTosResultListener var7 = CarrierTosDialogFragment.this.mListener;
         CarrierTosDialogFragment.CarrierTosResultListener.TosResult var8 = CarrierTosDialogFragment.CarrierTosResultListener.TosResult.FAILURE;
         var7.onCarrierTosResult(var8);
      }
   }

   public interface CarrierTosResultListener {

      void onCarrierTosResult(CarrierTosDialogFragment.CarrierTosResultListener.TosResult var1);

      public static enum TosResult {

         // $FF: synthetic field
         private static final CarrierTosDialogFragment.CarrierTosResultListener.TosResult[] $VALUES;
         CANCELED("CANCELED", 2),
         FAILURE("FAILURE", 1),
         SUCCESS("SUCCESS", 0);


         static {
            CarrierTosDialogFragment.CarrierTosResultListener.TosResult[] var0 = new CarrierTosDialogFragment.CarrierTosResultListener.TosResult[3];
            CarrierTosDialogFragment.CarrierTosResultListener.TosResult var1 = SUCCESS;
            var0[0] = var1;
            CarrierTosDialogFragment.CarrierTosResultListener.TosResult var2 = FAILURE;
            var0[1] = var2;
            CarrierTosDialogFragment.CarrierTosResultListener.TosResult var3 = CANCELED;
            var0[2] = var3;
            $VALUES = var0;
         }

         private TosResult(String var1, int var2) {}
      }
   }
}
