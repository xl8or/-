package com.android.email.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Toast;
import com.android.email.activity.FindLocationActivity;
import com.digc.seven.SevenSyncProvider;

public class AddLocationActivity extends Activity {

   private static final int ACTIVITY_REQUEST_GPS_SETTING = 2;
   private static final int ACTIVITY_REQUEST_PICK_LOCATION = 1;
   private static final int DIALOG_GPS_ACCESS = 2;
   private static final int DIALOG_LOCATION_SELECT = 1;


   public AddLocationActivity() {}

   private boolean isEnabledGPS() {
      return ((LocationManager)this.getSystemService("location")).isProviderEnabled("gps");
   }

   protected void onActivityResult(int var1, int var2, Intent var3) {
      super.onActivityResult(var1, var2, var3);
      if(var1 == 1 && var2 == -1) {
         CharSequence var4 = var3.getCharSequenceExtra("location-char");
         String var5 = var3.getStringExtra("location-string");
         if(TextUtils.isEmpty(var5) && TextUtils.isEmpty(var4)) {
            Toast.makeText(this.getBaseContext(), 2131166600, 1).show();
         } else {
            Intent var6 = new Intent();
            Object var7;
            if(var4 == null) {
               var7 = "";
            } else {
               var7 = var4;
            }

            var6.putExtra("location-char", (CharSequence)var7);
            String var9;
            if(var5 == null) {
               var9 = "";
            } else {
               var9 = var5;
            }

            var6.putExtra("location-string", var9);
            this.setResult(-1, var6);
         }
      } else if(var1 == 2) {
         this.showDialog(1);
         return;
      }

      this.finish();
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.getWindow().requestFeature(1);
      this.setContentView(2130903100);
      this.showDialog(1);
   }

   protected Dialog onCreateDialog(int var1, Bundle var2) {
      Object var3;
      switch(var1) {
      case 1:
         Builder var4 = new Builder(this);
         Builder var5 = var4.setTitle(2131166589);
         String[] var6 = new String[2];
         String var7 = this.getString(2131166590);
         var6[0] = var7;
         String var8 = this.getString(2131166591);
         var6[1] = var8;
         AddLocationActivity.1 var9 = new AddLocationActivity.1();
         var4.setItems(var6, var9);
         AddLocationActivity.2 var11 = new AddLocationActivity.2();
         var4.setOnKeyListener(var11);
         var3 = var4.create();
         break;
      case 2:
         Builder var13 = new Builder(this);
         Builder var14 = var13.setTitle(2131166592);
         Builder var15 = var13.setMessage(2131166593);
         String var16 = this.getString(2131166594);
         AddLocationActivity.3 var17 = new AddLocationActivity.3();
         var13.setPositiveButton(var16, var17);
         String var19 = this.getString(2131166595);
         AddLocationActivity.4 var20 = new AddLocationActivity.4();
         var13.setNegativeButton(var19, var20);
         AddLocationActivity.5 var22 = new AddLocationActivity.5();
         var13.setOnKeyListener(var22);
         var3 = var13.create();
         break;
      default:
         var3 = super.onCreateDialog(var1, var2);
      }

      return (Dialog)var3;
   }

   protected void onRestoreInstanceState(Bundle var1) {
      super.onRestoreInstanceState(var1);
      if(SevenSyncProvider.checkSevenApkVer(this)) {
         ;
      }
   }

   class 5 implements OnKeyListener {

      5() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         if(var2 == 4 && var3.getAction() == 1) {
            AddLocationActivity.this.showDialog(1);
         }

         return false;
      }
   }

   class 4 implements OnClickListener {

      4() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
         AddLocationActivity.this.showDialog(1);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         Intent var3 = new Intent();
         Intent var4 = var3.setClassName("com.android.settings", "com.android.settings.SecuritySettings");
         AddLocationActivity.this.startActivityForResult(var3, 2);
      }
   }

   class 2 implements OnKeyListener {

      2() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         boolean var4;
         if(var2 == 4 && var3.getAction() == 1) {
            AddLocationActivity.this.finish();
            var4 = true;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         Intent var3 = new Intent();
         Context var4 = AddLocationActivity.this.getBaseContext();
         var3.setClass(var4, FindLocationActivity.class);
         if(var2 == 0) {
            if(AddLocationActivity.this.isEnabledGPS()) {
               Intent var6 = var3.putExtra("com.android.email.intent.extra.is_from_map", (boolean)0);
               AddLocationActivity.this.startActivityForResult(var3, 1);
               var1.dismiss();
            } else {
               AddLocationActivity.this.showDialog(2);
            }
         } else if(var2 == 1) {
            Intent var7 = var3.putExtra("com.android.email.intent.extra.is_from_map", (boolean)1);
            AddLocationActivity.this.startActivityForResult(var3, 1);
            var1.dismiss();
         }
      }
   }
}
