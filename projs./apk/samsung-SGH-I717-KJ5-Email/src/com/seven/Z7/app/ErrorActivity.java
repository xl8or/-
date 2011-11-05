package com.seven.Z7.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.seven.Z7.shared.ANSharedCommon;
import java.util.Iterator;

public class ErrorActivity extends Activity implements OnDismissListener {

   public static final String TAG = "ErrorActivity";
   private Bundle mData;


   public ErrorActivity() {}

   private Dialog getErrorDialog(String var1, String var2, int var3) {
      View var4 = LayoutInflater.from(this).inflate(2130903184, (ViewGroup)null);
      TextView var5 = (TextView)var4.findViewById(2131361955);
      if(var1 == null || var1.length() == 0) {
         var1 = this.getString(2131165652);
      }

      if(var2 == null || var2.length() == 0) {
         var2 = this.getString(2131165674);
      }

      var5.setText(var2);
      Builder var6 = (new Builder(this)).setIcon(17301543).setTitle(var1).setView(var4).setCancelable((boolean)1);
      String var7 = this.getString(2131165467);
      ErrorActivity.1 var8 = new ErrorActivity.1(var3);
      AlertDialog var9 = var6.setPositiveButton(var7, var8).create();
      var9.setOnDismissListener(this);
      return var9;
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      Bundle var2 = this.getIntent().getExtras();
      this.mData = var2;
      if(this.mData == null) {
         int var3 = Log.e("ErrorActivity", "Bundle contains no data.");
      } else {
         String var8;
         int var9;
         for(Iterator var4 = this.mData.keySet().iterator(); var4.hasNext(); var9 = Log.v("ErrorActivity", var8)) {
            String var5 = (String)var4.next();
            StringBuilder var6 = (new StringBuilder()).append("ErrorBundle:");
            Object var7 = this.mData.get(var5);
            var8 = var6.append(var7).toString();
         }

         this.showDialog(1);
      }
   }

   protected Dialog onCreateDialog(int var1) {
      Dialog var2;
      switch(var1) {
      case 1:
         String var3 = this.mData.getString("subject");
         String var4 = this.mData.getString("message");
         int var5 = this.mData.getInt("action");
         var2 = this.getErrorDialog(var3, var4, var5);
         break;
      default:
         var2 = null;
      }

      return var2;
   }

   public void onDismiss(DialogInterface var1) {
      this.removeDialog(1);
      this.finish();
   }

   class 1 implements OnClickListener {

      // $FF: synthetic field
      final int val$action;


      1(int var2) {
         this.val$action = var2;
      }

      public void onClick(DialogInterface var1, int var2) {
         if(this.val$action == 60) {
            ANSharedCommon.forceExit();
         } else {
            ErrorActivity.this.finish();
         }
      }
   }
}
