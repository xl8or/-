package com.htc.android.mail;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IContentProvider;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;
import com.htc.android.mail.Mail;
import com.htc.android.mail.MailProvider;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.widget.HtcAlertDialog.Builder;

public class DownloadAttachmentSDFullDialog extends ListActivity {

   private static final boolean DEBUG = Mail.EAS_DEBUG;
   private static final String TAG = "DownloadAttachmentSDFullDialog";
   private long mAccountId;
   private Context mContext;


   public DownloadAttachmentSDFullDialog() {}

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      if(DEBUG) {
         EASLog.d("DownloadAttachmentSDFullDialog", "- onCreate()");
      }

      this.mContext = this;
      long var2 = this.getIntent().getLongExtra("accountId", 65535L);
      this.mAccountId = var2;
      this.showDialog(0);
   }

   protected Dialog onCreateDialog(int var1) {
      if(DEBUG) {
         String var2 = "onCreateDialog: " + var1;
         EASLog.d("DownloadAttachmentSDFullDialog", var2);
      }

      Builder var3 = new Builder(this);
      CharSequence var4 = this.getText(2131362510);
      Builder var5 = var3.setTitle(var4).setMessage(2131362552);
      DownloadAttachmentSDFullDialog.3 var6 = new DownloadAttachmentSDFullDialog.3();
      Builder var7 = var5.setPositiveButton(2131362432, var6);
      DownloadAttachmentSDFullDialog.2 var8 = new DownloadAttachmentSDFullDialog.2();
      Builder var9 = var7.setNegativeButton(2131361931, var8);
      DownloadAttachmentSDFullDialog.1 var10 = new DownloadAttachmentSDFullDialog.1();
      return var9.setOnKeyListener(var10).setCancelable((boolean)1).create();
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
         if(DownloadAttachmentSDFullDialog.DEBUG) {
            EASLog.d("DownloadAttachmentSDFullDialog", "press positivie button");
         }

         DownloadAttachmentSDFullDialog.3.1 var3 = new DownloadAttachmentSDFullDialog.3.1();
         (new Thread(var3)).start();
         DownloadAttachmentSDFullDialog.this.finish();
      }

      class 1 implements Runnable {

         1() {}

         public void run() {
            try {
               ContentValues var1 = new ContentValues();
               Object[] var2 = new Object[1];
               Long var3 = Long.valueOf(DownloadAttachmentSDFullDialog.this.mAccountId);
               var2[0] = var3;
               String var4 = String.format("_id = \'%d\'", var2);
               var1.put("_enableSDsave", "0");
               IContentProvider var5 = MailProvider.instance();
               Uri var6 = MailProvider.sAccountsURI;
               var5.update(var6, var1, var4, (String[])null);
            } catch (RemoteException var8) {
               var8.printStackTrace();
            }
         }
      }
   }

   class 1 implements OnKeyListener {

      1() {}

      public boolean onKey(DialogInterface var1, int var2, KeyEvent var3) {
         switch(var2) {
         case 3:
         case 4:
            DownloadAttachmentSDFullDialog.this.finish();
         default:
            return false;
         }
      }
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         if(DownloadAttachmentSDFullDialog.DEBUG) {
            EASLog.d("DownloadAttachmentSDFullDialog", "press negative button");
         }

         DownloadAttachmentSDFullDialog.this.finish();
      }
   }
}
