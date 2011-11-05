package com.google.android.finsky.billing.carrierbilling.fragment;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.FinskyApp;

public class CarrierBillingErrorDialog extends DialogFragment implements OnClickListener {

   private static final String KEY_ARGUMENT_MESSAGE = "error_message";
   CarrierBillingErrorDialog.CarrierBillingErrorListener mListener;


   public CarrierBillingErrorDialog() {}

   public static CarrierBillingErrorDialog newInstance(String var0) {
      CarrierBillingErrorDialog var1 = new CarrierBillingErrorDialog();
      var1.setCancelable((boolean)0);
      Bundle var2 = new Bundle();
      var2.putString("error_message", var0);
      var1.setArguments(var2);
      return var1;
   }

   public void onClick(DialogInterface var1, int var2) {
      if(this.mListener != null) {
         this.mListener.onErrorDismiss();
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Builder var3 = new Builder(var2);
      String var4 = FinskyApp.get().getString(2131230928);
      var3.setTitle(var4);
      String var6 = this.getArguments().getString("error_message");
      var3.setMessage(var6);
      Builder var8 = var3.setPositiveButton(17039370, this).setCancelable((boolean)0);
      return var3.create();
   }

   public void setOnResultListener(CarrierBillingErrorDialog.CarrierBillingErrorListener var1) {
      this.mListener = var1;
   }

   public interface CarrierBillingErrorListener {

      void onErrorDismiss();
   }
}
