package com.facebook.katana;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.katana.service.FacebookService;
import com.facebook.katana.util.Toaster;

public class WidgetActivity extends Activity {

   public static final String ACTION_WIDGET_PUBLISH_RESULT = "com.facebook.katana.widget.publish.result";
   public static final String EXTRA_ERROR_CODE = "extra_error_code";
   private static final int PROGRESS_DIALOG = 2;
   private static final int SHARE_DIALOG = 1;
   private boolean mFirstTime = 1;
   private BroadcastReceiver mReceiver;
   private String mText;


   public WidgetActivity() {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      WidgetActivity.1 var2 = new WidgetActivity.1();
      this.mReceiver = var2;
      IntentFilter var3 = new IntentFilter();
      var3.addAction("com.facebook.katana.widget.publish.result");
      BroadcastReceiver var4 = this.mReceiver;
      this.registerReceiver(var4, var3);
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 1:
         Builder var3 = new Builder(this);
         View var4 = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2130903155, (ViewGroup)null);
         var3.setView(var4);
         Builder var6 = var3.setTitle(2131362256);
         TextView var7 = (TextView)var4.findViewById(2131624145);
         if(this.mText != null) {
            String var8 = this.mText;
            var7.setText(var8);
         }

         String var9 = this.getString(2131362255);
         WidgetActivity.2 var10 = new WidgetActivity.2(var7);
         var3.setPositiveButton(var9, var10);
         String var12 = this.getString(2131361826);
         WidgetActivity.3 var13 = new WidgetActivity.3();
         var3.setNegativeButton(var12, var13);
         WidgetActivity.4 var15 = new WidgetActivity.4();
         var3.setOnCancelListener(var15);
         var2 = var3.create();
         break;
      case 2:
         ProgressDialog var17 = new ProgressDialog(this);
         var17.setProgressStyle(0);
         CharSequence var18 = this.getText(2131362245);
         var17.setMessage(var18);
         var17.setIndeterminate((boolean)1);
         var17.setCancelable((boolean)0);
         var2 = var17;
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   protected void onDestroy() {
      super.onDestroy();
      if(this.mReceiver != null) {
         BroadcastReceiver var1 = this.mReceiver;
         this.unregisterReceiver(var1);
      }
   }

   public void onResume() {
      super.onResume();
      if(this.mFirstTime) {
         this.mFirstTime = (boolean)0;
         this.showDialog(1);
      }
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         WidgetActivity.this.finish();
      }
   }

   class 4 implements OnCancelListener {

      4() {}

      public void onCancel(DialogInterface var1) {
         WidgetActivity.this.finish();
      }
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final TextView val$textInput;


      2(TextView var2) {
         this.val$textInput = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         String var3 = this.val$textInput.getText().toString().trim();
         if(var3.length() == 0) {
            WidgetActivity.this.finish();
         } else {
            WidgetActivity.this.mText = var3;

            try {
               WidgetActivity var5 = WidgetActivity.this;
               Intent var6 = new Intent(var5, FacebookService.class);
               Intent var7 = var6.putExtra("type", 92);
               var6.putExtra("status", var3);
               PendingIntent.getService(WidgetActivity.this, 0, var6, 268435456).send();
               WidgetActivity.this.showDialog(2);
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      }
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         WidgetActivity.this.removeDialog(2);
         if(var2.getIntExtra("extra_error_code", 0) == 200) {
            Toaster.toast(var1, 2131362354);
            WidgetActivity.this.finish();
         } else {
            WidgetActivity.this.showDialog(1);
            Toaster.toast(var1, 2131362244);
         }
      }
   }
}
