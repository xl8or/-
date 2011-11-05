package com.htc.android.mail.easclient;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import com.htc.android.mail.Mail;
import com.htc.android.mail.easclient.ExchangeSvrSetting;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.widget.HtcAlertDialog.Builder;

public class EASChangePwdDialog extends ListActivity {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "EASChangePwdDialog";
   private long mAccountId;
   private Context mContext;


   public EASChangePwdDialog() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         EASLog.d("EASChangePwdDialog", "- onCreate()");
      }

      this.mContext = this;
      long var2 = this.getIntent().getLongExtra("accountId", 65535L);
      this.mAccountId = var2;
      this.showDialog(0);
   }

   protected Dialog onCreateDialog(int var1) {
      if(DEBUG) {
         String var2 = "onCreateDialog: " + var1;
         EASLog.d("EASChangePwdDialog", var2);
      }

      Builder var3 = (new Builder(this)).setTitle(2131362458).setMessage(2131362459);
      EASChangePwdDialog.3 var4 = new EASChangePwdDialog.3();
      Builder var5 = var3.setPositiveButton(2131362145, var4);
      EASChangePwdDialog.2 var6 = new EASChangePwdDialog.2();
      Builder var7 = var5.setNegativeButton(2131361931, var6);
      EASChangePwdDialog.1 var8 = new EASChangePwdDialog.1();
      return var7.setOnKeyListener(var8).setCancelable((boolean)1).create();
   }

   protected void onDestroy() {
      super.onDestroy();
   }

   public void onResume() {
      super.onResume();
   }

   class 3 implements OnClickListener {

      3() {}

      public void onClick(DialogInterface var1, int var2) {
         if(EASChangePwdDialog.DEBUG) {
            EASLog.d("EASChangePwdDialog", "press positivie button");
         }

         StringBuilder var3 = (new StringBuilder()).append("content://mail/accounts/");
         long var4 = EASChangePwdDialog.this.mAccountId;
         Uri var6 = Uri.parse(var3.append(var4).toString());
         Context var7 = EASChangePwdDialog.this.mContext;
         Intent var8 = new Intent("android.intent.action.MAIN", var6, var7, ExchangeSvrSetting.class);
         Intent var9 = var8.addFlags(268435456);
         Intent var10 = var8.putExtra("intent.eas.mode.wizard", (boolean)0);
         EASChangePwdDialog.this.startActivity(var8);
         EASChangePwdDialog.this.finish();
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         if(EASChangePwdDialog.DEBUG) {
            EASLog.d("EASChangePwdDialog", "press negative button");
         }

         EASChangePwdDialog.this.finish();
      }
   }

   class 1 implements OnKeyListener {

      1() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         switch(var2) {
         case 3:
         case 4:
            EASChangePwdDialog.this.finish();
         default:
            return false;
         }
      }
   }
}
