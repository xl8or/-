package com.android.settings.wifi;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import com.android.settings.wifi.WifiApSettings;

public class WifiApSettings_Help extends Activity {

   public static final String HELP_NOT_SHOW_AGAIN = "ap_help_not_show_again";
   private static final String TAG = "WifiAp Settings Help";
   private ContentResolver mContentResolver;


   public WifiApSettings_Help() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903140);
      CheckBox var2 = (CheckBox)this.findViewById(2131427662);
      Button var3 = (Button)this.findViewById(2131427663);
      ContentResolver var4 = this.getContentResolver();
      this.mContentResolver = var4;
      WifiApSettings_Help.1 var5 = new WifiApSettings_Help.1(var2);
      var2.setOnClickListener(var5);
      WifiApSettings_Help.2 var6 = new WifiApSettings_Help.2();
      var3.setOnClickListener(var6);
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(View var1) {
         WifiApSettings_Help var2 = WifiApSettings_Help.this;
         Intent var3 = new Intent(var2, WifiApSettings.class);
         WifiApSettings_Help.this.startActivity(var3);
         WifiApSettings_Help.this.finish();
      }
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final CheckBox val$not_show_again;


      1(CheckBox var2) {
         this.val$not_show_again = var2;
      }

      public void onClick(View var1) {
         ContentResolver var2 = WifiApSettings_Help.this.mContentResolver;
         byte var3;
         if(this.val$not_show_again.isChecked()) {
            var3 = 1;
         } else {
            var3 = 0;
         }

         System.putInt(var2, "ap_help_not_show_again", var3);
      }
   }
}
