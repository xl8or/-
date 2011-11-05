package com.google.android.finsky.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.utils.Utils;

public class BackgroundDataDialog extends DialogFragment {

   private static final String SYNC_SETTINGS_ACTION = "android.settings.SYNC_SETTINGS";
   private static final String SYNC_SETTINGS_CATEGORY = "android.intent.category.DEFAULT";
   private static final String TAG_BACKGROUND_DATA_DIALOG = "bg_data_dialog";


   public BackgroundDataDialog() {}

   public static void dismissExisting(FragmentManager var0) {
      Fragment var1 = var0.findFragmentByTag("bg_data_dialog");
      if(var1 != null) {
         ((DialogFragment)var1).dismiss();
      }
   }

   public static void show(FragmentManager var0, Activity var1) {
      if(var0.findFragmentByTag("bg_data_dialog") == null) {
         (new BackgroundDataDialog()).show(var0, "bg_data_dialog");
      }
   }

   public void onCancel(DialogInterface var1) {
      super.onCancel(var1);
      FragmentActivity var2 = this.getActivity();
      if(var2 != null) {
         if(!Utils.isBackgroundDataEnabled(var2)) {
            ((BackgroundDataDialog.BackgroundDataSettingListener)var2).onBackgroundDataNotEnabled();
         }
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Builder var3 = (new Builder(var2)).setTitle(2131230923);
      BackgroundDataDialog.2 var4 = new BackgroundDataDialog.2();
      Builder var5 = var3.setPositiveButton(2131230925, var4);
      BackgroundDataDialog.1 var6 = new BackgroundDataDialog.1();
      AlertDialog var7 = var5.setNegativeButton(2131230926, var6).create();
      View var8 = var7.getLayoutInflater().inflate(2130968647, (ViewGroup)null);
      TextView var9 = (TextView)var8.findViewById(2131755190);
      String var10 = this.getString(2131230924);
      var9.setText(var10);
      var7.setView(var8);
      var7.setCancelable((boolean)1);
      return var7;
   }

   public interface BackgroundDataSettingListener {

      void onBackgroundDataNotEnabled();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         ((BackgroundDataDialog.BackgroundDataSettingListener)BackgroundDataDialog.this.getActivity()).onBackgroundDataNotEnabled();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         Intent var3 = new Intent("android.settings.SYNC_SETTINGS");
         Intent var4 = var3.addCategory("android.intent.category.DEFAULT");
         Intent var5 = var3.setFlags(524288);
         BackgroundDataDialog.this.startActivity(var3);
      }
   }
}
