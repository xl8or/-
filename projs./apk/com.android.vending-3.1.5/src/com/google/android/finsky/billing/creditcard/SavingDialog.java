package com.google.android.finsky.billing.creditcard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class SavingDialog extends DialogFragment {

   public SavingDialog() {
      this.setCancelable((boolean)0);
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      ProgressDialog var3 = new ProgressDialog(var2);
      var3.setProgressStyle(0);
      CharSequence var4 = this.getActivity().getText(2131230799);
      var3.setMessage(var4);
      var3.setCancelable((boolean)0);
      var3.setIndeterminate((boolean)1);
      return var3;
   }
}
