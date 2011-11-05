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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.finsky.activities.FlagContentFragment;

public class FlagContentDialog extends DialogFragment {

   private static final String ARG_ID_PREVIOUS_MESSAGE = "previous_message";
   private static final String ARG_ID_PROMPT_STRING_RES_ID = "prompt_string_res_id";
   private FlagContentFragment.FlagType mFlagType;
   private String mMessage;


   public FlagContentDialog() {}

   private FlagContentDialog.Listener getListener() {
      Fragment var1 = this.getTargetFragment();
      FlagContentDialog.Listener var3;
      if(var1 instanceof FlagContentDialog.Listener) {
         var3 = (FlagContentDialog.Listener)var1;
      } else {
         FragmentActivity var2 = this.getActivity();
         if(var2 instanceof FlagContentDialog.Listener) {
            var3 = (FlagContentDialog.Listener)var2;
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   public static FlagContentDialog newInstance(FlagContentFragment.FlagType var0) {
      FlagContentDialog var1 = new FlagContentDialog();
      Bundle var2 = new Bundle();
      int var3 = var0.flagPromptStringResId();
      var2.putInt("prompt_string_res_id", var3);
      var1.setArguments(var2);
      return var1;
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Bundle var3 = this.getArguments();
      int var4 = var3.getInt("prompt_string_res_id");
      Bundle var5;
      if(var1 != null) {
         var5 = var1;
      } else {
         var5 = var3;
      }

      String var6 = var5.getString("previous_message");
      this.mMessage = var6;
      View var7 = LayoutInflater.from(this.getActivity()).inflate(2130968652, (ViewGroup)null, (boolean)0);
      TextView var8 = (TextView)var7.findViewById(2131755205);
      String var9 = this.mMessage;
      var8.setText(var9);
      Builder var10 = new Builder(var2);
      var10.setTitle(var4);
      var10.setView(var7);
      Builder var13 = var10.setCancelable((boolean)1);
      FlagContentDialog.1 var14 = new FlagContentDialog.1(var8);
      var10.setPositiveButton(17039370, var14);
      Builder var16 = var10.setNegativeButton(17039360, (OnClickListener)null);
      FlagContentDialog.2 var17 = new FlagContentDialog.2();
      var8.addTextChangedListener(var17);
      return var10.create();
   }

   public void onSaveInstanceState(Bundle var1) {
      if(!TextUtils.isEmpty(this.mMessage)) {
         String var2 = this.mMessage;
         var1.putString("previous_message", var2);
      }
   }

   public void onStart() {
      super.onStart();
      Button var1 = ((AlertDialog)this.getDialog()).getButton(-1);
      byte var2;
      if(!TextUtils.isEmpty(this.mMessage)) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var1.setEnabled((boolean)var2);
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final TextView val$commentBox;


      1(TextView var2) {
         this.val$commentBox = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         FlagContentDialog.Listener var3 = FlagContentDialog.this.getListener();
         if(var3 != null) {
            String var4 = this.val$commentBox.getText().toString();
            var3.onPositiveClick(var4);
         }
      }
   }

   class 2 implements TextWatcher {

      2() {}

      public void afterTextChanged(Editable var1) {
         FlagContentDialog var2 = FlagContentDialog.this;
         String var3;
         if(var1 == null) {
            var3 = null;
         } else {
            var3 = var1.toString();
         }

         var2.mMessage = var3;
         Button var5 = ((AlertDialog)FlagContentDialog.this.getDialog()).getButton(-1);
         byte var6;
         if(!TextUtils.isEmpty(FlagContentDialog.this.mMessage)) {
            var6 = 1;
         } else {
            var6 = 0;
         }

         var5.setEnabled((boolean)var6);
      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   public interface Listener {

      void onPositiveClick(String var1);
   }
}
