package com.google.android.finsky.activities;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class SimpleAlertDialog extends DialogFragment {

   private static final String ARG_EXTRA_ARGUMENTS = "extra_arguments";
   private static final String ARG_MESSAGE_ID = "message_id";
   private static final String ARG_NEGATIVE_ID = "negative_id";
   private static final String ARG_POSITIVE_ID = "positive_id";
   private static final String ARG_TARGET_REQUEST_CODE = "target_request_code";
   private static final String ARG_TITLE_ID = "title_id";


   public SimpleAlertDialog() {}

   private SimpleAlertDialog.Listener getListener() {
      Fragment var1 = this.getTargetFragment();
      SimpleAlertDialog.Listener var3;
      if(var1 instanceof SimpleAlertDialog.Listener) {
         var3 = (SimpleAlertDialog.Listener)var1;
      } else {
         FragmentActivity var2 = this.getActivity();
         if(var2 instanceof SimpleAlertDialog.Listener) {
            var3 = (SimpleAlertDialog.Listener)var2;
         } else {
            var3 = null;
         }
      }

      return var3;
   }

   public static SimpleAlertDialog newInstance(int var0, int var1, int var2, int var3) {
      SimpleAlertDialog var4 = new SimpleAlertDialog();
      Bundle var5 = new Bundle();
      var5.putInt("title_id", var0);
      var5.putInt("message_id", var1);
      var5.putInt("positive_id", var2);
      var5.putInt("negative_id", var3);
      var4.setArguments(var5);
      return var4;
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Bundle var3 = this.getArguments();
      int var4 = var3.getInt("title_id");
      int var5 = var3.getInt("message_id");
      int var6 = var3.getInt("positive_id");
      int var7 = var3.getInt("negative_id");
      Bundle var8 = var3.getBundle("extra_arguments");
      int var9 = var3.getInt("target_request_code");
      Builder var10 = new Builder(var2);
      if(var4 != -1) {
         var10.setTitle(var4);
      }

      if(var5 != -1) {
         var10.setMessage(var5);
      }

      if(var6 != -1) {
         SimpleAlertDialog.1 var13 = new SimpleAlertDialog.1(var9, var8);
         var10.setPositiveButton(var6, var13);
      }

      if(var7 != -1) {
         SimpleAlertDialog.2 var15 = new SimpleAlertDialog.2(var9, var8);
         var10.setNegativeButton(var7, var15);
      }

      return var10.create();
   }

   public SimpleAlertDialog setCallback(Fragment var1, int var2, Bundle var3) {
      this.setTargetFragment(var1, 0);
      if(var3 != null || var2 != 0) {
         Bundle var4 = this.getArguments();
         var4.putBundle("extra_arguments", var3);
         var4.putInt("target_request_code", var2);
         this.setArguments(var4);
      }

      return this;
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final Bundle val$extraArguments;
      // $FF: synthetic field
      final int val$requestCode;


      1(int var2, Bundle var3) {
         this.val$requestCode = var2;
         this.val$extraArguments = var3;
      }

      public void onClick(DialogInterface var1, int var2) {
         SimpleAlertDialog.this.dismiss();
         SimpleAlertDialog.Listener var3 = SimpleAlertDialog.this.getListener();
         if(var3 != null) {
            int var4 = this.val$requestCode;
            Bundle var5 = this.val$extraArguments;
            var3.onPositiveClick(var4, var5);
         }
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final Bundle val$extraArguments;
      // $FF: synthetic field
      final int val$requestCode;


      2(int var2, Bundle var3) {
         this.val$requestCode = var2;
         this.val$extraArguments = var3;
      }

      public void onClick(DialogInterface var1, int var2) {
         SimpleAlertDialog.this.dismiss();
         SimpleAlertDialog.Listener var3 = SimpleAlertDialog.this.getListener();
         if(var3 != null) {
            int var4 = this.val$requestCode;
            Bundle var5 = this.val$extraArguments;
            var3.onNegativeClick(var4, var5);
         }
      }
   }

   public interface Listener {

      void onNegativeClick(int var1, Bundle var2);

      void onPositiveClick(int var1, Bundle var2);
   }
}
