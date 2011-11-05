package com.htc.android.mail.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.htc.widget.HtcAlertDialog.Builder;

public class StorageFullDialog extends Activity {

   private static final String TAG = "StorageFullDialog";
   private final int DIALOG_STORAGE_FULL = 0;


   public StorageFullDialog() {}

   public final void onCreate(Bundle var1) {
      super.onCreate(var1);
      boolean var2 = this.requestWindowFeature(1);
      this.showDialog(0);
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 0:
         String var3 = this.getString(2131362511);
         Object[] var4 = new Object[1];
         Integer var5 = Integer.valueOf(Math.abs(1024));
         var4[0] = var5;
         String var6 = String.format(var3, var4);
         Builder var7 = (new Builder(this)).setTitle(2131362510).setMessage(var6);
         StorageFullDialog.2 var8 = new StorageFullDialog.2();
         Builder var9 = var7.setPositiveButton(2131362145, var8);
         StorageFullDialog.1 var10 = new StorageFullDialog.1();
         var2 = var9.setNegativeButton(2131361931, var10).setCancelable((boolean)1).create();
         break;
      default:
         var2 = super.onCreateDialog(var1);
      }

      return (Dialog)var2;
   }

   protected final void onDestroy() {
      super.onDestroy();
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         StorageFullDialog.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         try {
            Intent var3 = new Intent("android.intent.action.MANAGE_PACKAGE_STORAGE");
            StorageFullDialog.this.startActivity(var3);
            StorageFullDialog.this.finish();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }
   }
}
