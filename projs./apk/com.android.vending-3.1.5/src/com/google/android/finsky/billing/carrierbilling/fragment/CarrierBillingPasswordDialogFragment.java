package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.finsky.billing.BillingLocator;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingParameters;
import com.google.android.finsky.billing.carrierbilling.model.CarrierBillingProvisioning;

public class CarrierBillingPasswordDialogFragment extends DialogFragment implements OnClickListener {

   private Button mBuyButton;
   private Button mCancelButton;
   private CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener mListener;
   private View mMainPasswordView;
   private EditText mPasswordEditText;
   private View mProgressIndicator;


   public CarrierBillingPasswordDialogFragment() {}

   private String createPasswordForgotHtml(String var1) {
      StringBuilder var2 = (new StringBuilder()).append("<a href=\"").append(var1).append("\">");
      String var3 = this.getString(2131230814);
      return var2.append(var3).append("</a>").toString();
   }

   public void clearPasswordField() {
      this.mPasswordEditText.setText("");
   }

   public void hideProgressIndicator() {
      this.mMainPasswordView.setVisibility(0);
      this.mProgressIndicator.setVisibility(8);
   }

   public void onCancel(DialogInterface var1) {
      super.onCancel(var1);
      CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener var2 = this.mListener;
      CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var3 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.CANCELED;
      var2.onCarrierBillingPasswordResult(var3, (String)null);
   }

   public void onClick(View var1) {
      Button var2 = this.mBuyButton;
      if(var1 == var2) {
         String var3 = this.mPasswordEditText.getText().toString();
         if(TextUtils.isEmpty(var3)) {
            Toast.makeText(this.getActivity(), 2131230815, 0).show();
         } else {
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener var4 = this.mListener;
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var5 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.SUCCESS;
            var4.onCarrierBillingPasswordResult(var5, var3);
         }
      } else {
         Button var6 = this.mCancelButton;
         if(var1 == var6) {
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener var7 = this.mListener;
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var8 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.CANCELED;
            var7.onCarrierBillingPasswordResult(var8, (String)null);
         }
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      View var3 = View.inflate(new ContextThemeWrapper(var2, 16973829), 2130968595, (ViewGroup)null);
      View var4 = var3.findViewById(2131755072);
      this.mMainPasswordView = var4;
      View var5 = var3.findViewById(2131755150);
      this.mProgressIndicator = var5;
      this.hideProgressIndicator();
      TextView var6 = (TextView)var3.findViewById(2131755074);
      TextView var7 = (TextView)var3.findViewById(2131755076);
      EditText var8 = (EditText)var3.findViewById(2131755075);
      this.mPasswordEditText = var8;
      Button var9 = (Button)var3.findViewById(2131755077);
      this.mBuyButton = var9;
      Button var10 = (Button)var3.findViewById(2131755054);
      this.mCancelButton = var10;
      this.mBuyButton.setOnClickListener(this);
      this.mCancelButton.setOnClickListener(this);
      CarrierBillingParameters var11 = BillingLocator.getCarrierBillingStorage().getParams();
      CarrierBillingProvisioning var12 = BillingLocator.getCarrierBillingStorage().getProvisioning();
      if(var11 == null || var12 == null) {
         CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener var13 = this.mListener;
         CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var14 = CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult.FAILURE;
         var13.onCarrierBillingPasswordResult(var14, (String)null);
      }

      String var15 = var12.getPasswordPrompt();
      var6.setText(var15);
      if(TextUtils.isEmpty(var12.getPasswordForgotUrl())) {
         var7.setVisibility(8);
      } else {
         String var20 = var12.getPasswordForgotUrl();
         Spanned var21 = Html.fromHtml(this.createPasswordForgotHtml(var20));
         var7.setText(var21);
         MovementMethod var22 = LinkMovementMethod.getInstance();
         var7.setMovementMethod(var22);
      }

      FragmentActivity var16 = this.getActivity();
      AlertDialog var17 = (new Builder(var16)).setTitle(2131230812).create();
      byte var18 = 0;
      byte var19 = 0;
      var17.setView(var3, 0, 5, var18, var19);
      return var17;
   }

   public void setOnResultListener(CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener var1) {
      this.mListener = var1;
   }

   public void showProgressIndicator() {
      this.mMainPasswordView.setVisibility(4);
      this.mProgressIndicator.setVisibility(0);
   }

   public interface CarrierBillingPasswordResultListener {

      void onCarrierBillingPasswordResult(CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var1, String var2);

      public static enum PasswordResult {

         // $FF: synthetic field
         private static final CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult[] $VALUES;
         CANCELED("CANCELED", 2),
         FAILURE("FAILURE", 1),
         SUCCESS("SUCCESS", 0);


         static {
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult[] var0 = new CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult[3];
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var1 = SUCCESS;
            var0[0] = var1;
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var2 = FAILURE;
            var0[1] = var2;
            CarrierBillingPasswordDialogFragment.CarrierBillingPasswordResultListener.PasswordResult var3 = CANCELED;
            var0[2] = var3;
            $VALUES = var0;
         }

         private PasswordResult(String var1, int var2) {}
      }
   }
}
