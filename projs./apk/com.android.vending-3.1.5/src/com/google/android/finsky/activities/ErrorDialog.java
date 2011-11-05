package com.google.android.finsky.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.utils.FinskyLog;

public class ErrorDialog extends DialogFragment {

   private static final String KEY_ARGUMENT_GO_BACK = "go_back";
   private static final String KEY_ARGUMENT_HTML_MESSAGE = "html_message";
   private static final String KEY_ARGUMENT_TITLE = "title";
   private static final String TAG_ERROR_DIALOG = "error_dialog";


   public ErrorDialog() {
      this.setCancelable((boolean)1);
   }

   private static ErrorDialog newInstance(String var0, String var1, boolean var2) {
      Bundle var3 = new Bundle();
      if(var0 == null) {
         var0 = FinskyApp.get().getString(2131230928);
      }

      var3.putString("title", var0);
      var3.putString("html_message", var1);
      var3.putBoolean("go_back", var2);
      ErrorDialog var4 = new ErrorDialog();
      var4.setArguments(var3);
      return var4;
   }

   public static ErrorDialog show(FragmentManager var0, String var1, String var2, boolean var3) {
      Fragment var4 = var0.findFragmentByTag("error_dialog");
      if(var4 != null) {
         FragmentTransaction var5 = var0.beginTransaction();

         try {
            int var6 = var5.remove(var4).addToBackStack((String)null).commit();
         } catch (IllegalStateException var11) {
            String var9 = "Double remove of error dialog fragment: " + var4;
            Object[] var10 = new Object[0];
            FinskyLog.w(var9, var10);
         }
      }

      ErrorDialog var7 = newInstance(var1, var2, var3);
      var7.show(var0, "error_dialog");
      return var7;
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Builder var3 = new Builder(var2);
      String var4 = this.getArguments().getString("title");
      AlertDialog var5 = var3.setTitle(var4).setPositiveButton(17039370, (OnClickListener)null).setCancelable((boolean)1).create();
      View var6 = var5.getLayoutInflater().inflate(2130968647, (ViewGroup)null);
      TextView var7 = (TextView)var6.findViewById(2131755190);
      Spanned var8 = Html.fromHtml(this.getArguments().getString("html_message"));
      var7.setText(var8);
      MovementMethod var9 = LinkMovementMethod.getInstance();
      var7.setMovementMethod(var9);
      var5.setView(var6);
      return var5;
   }

   public void onDismiss(DialogInterface var1) {
      if(this.getActivity() != null && this.getArguments().getBoolean("go_back")) {
         if(this.getActivity() instanceof PageFragmentHost) {
            ((PageFragmentHost)this.getActivity()).goBack();
         } else {
            Object[] var2 = new Object[0];
            FinskyLog.wtf("Dialog not hosted by PageFragmentHost. Cannot navigate back.", var2);
         }
      }

      super.onDismiss(var1);
   }
}
