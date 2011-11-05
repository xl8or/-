package com.htc.android.mail.easclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.htc.android.mail.Mail;
import com.htc.android.mail.eassvc.util.EASLog;
import com.htc.app.HtcProgressDialog;

public class EASProgressDialog extends HtcProgressDialog {

   public static final String ACTION_DISMISS_DIALOG = "action.easprogressdialog.dismiss";
   private static final boolean DEBUG = Mail.EAS_DEBUG;
   public static final String EXTRA_DIALOG_ACTION = "extra.easprogressdialog.dialogaction";
   EASProgressDialog.DialogAction mAction;
   private boolean mAutoDismiss = 1;
   EASProgressDialog.DialogCallback mCallback;
   Context mContext;
   private BroadcastReceiver mReceiver;


   public EASProgressDialog(Context var1) {
      super(var1);
      EASProgressDialog.DialogAction var2 = EASProgressDialog.DialogAction.NONE;
      this.mAction = var2;
      this.mCallback = null;
      this.mContext = null;
      EASProgressDialog.1 var3 = new EASProgressDialog.1();
      this.mReceiver = var3;
      this.mContext = var1;
   }

   public void onStart() {
      if(DEBUG) {
         EASLog.d("EASProgressDialog", "onStart()");
      }

      super.onStart();
      IntentFilter var1 = new IntentFilter();
      var1.addAction("intent.eas.progress.message");
      var1.addAction("action.easprogressdialog.dismiss");
      Context var2 = this.mContext;
      BroadcastReceiver var3 = this.mReceiver;
      var2.registerReceiver(var3, var1);
   }

   protected void onStop() {
      if(DEBUG) {
         EASLog.d("EASProgressDialog", "onStop()");
      }

      super.onStop();
      Context var1 = this.mContext;
      BroadcastReceiver var2 = this.mReceiver;
      var1.unregisterReceiver(var2);
      Intent var3 = new Intent("action.easprogressdialog.dismiss");
      EASProgressDialog.DialogAction var4 = this.mAction;
      var3.putExtra("extra.easprogressdialog.dialogaction", var4);
      this.mContext.sendBroadcast(var3);
      if(this.mCallback != null) {
         this.mCallback.onStop();
      }
   }

   public void setAutoDismiss(boolean var1) {
      this.mAutoDismiss = var1;
   }

   public void setCallback(EASProgressDialog.DialogCallback var1) {
      this.mCallback = var1;
   }

   public void setDialog(EASProgressDialog.DialogAction var1) {
      this.mAction = var1;
      int[] var2 = EASProgressDialog.2.$SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction;
      int var3 = this.mAction.ordinal();
      switch(var2[var3]) {
      case 1:
         CharSequence var4 = this.mContext.getText(2131362428);
         this.setMessage(var4);
         this.setIndeterminate((boolean)1);
         this.setCancelable((boolean)0);
         return;
      case 2:
         CharSequence var5 = this.mContext.getText(2131361892);
         this.setMessage(var5);
         this.setIndeterminate((boolean)1);
         this.setCancelable((boolean)0);
         return;
      case 3:
         CharSequence var6 = this.mContext.getText(2131362428);
         this.setMessage(var6);
         this.setIndeterminate((boolean)1);
         this.setCancelable((boolean)1);
         return;
      case 4:
         CharSequence var7 = this.mContext.getText(2131362427);
         this.setMessage(var7);
         this.setIndeterminate((boolean)1);
         this.setCancelable((boolean)1);
         return;
      default:
      }
   }

   interface DialogCallback {

      void onStop();
   }

   class 1 extends BroadcastReceiver {

      1() {}

      public void onReceive(Context var1, Intent var2) {
         String var3 = var2.getAction();
         if(var3.equals("intent.eas.progress.message")) {
            if(var2.getIntExtra("extra.sync_result", -1) == 1) {
               if(EASProgressDialog.this.isShowing()) {
                  if(EASProgressDialog.this.mAutoDismiss) {
                     EASProgressDialog.this.dismiss();
                  }
               }
            } else {
               EASProgressDialog.DialogAction var4 = EASProgressDialog.this.mAction;
               EASProgressDialog.DialogAction var5 = EASProgressDialog.DialogAction.DELETE_ACCOUNT;
               if(var4 == var5) {
                  String var6 = var2.getStringExtra("extra.eas.progress.message");
                  if(!TextUtils.isEmpty(var6)) {
                     EASProgressDialog.this.setMessage(var6);
                  }
               }
            }
         } else if(var3.equals("action.easprogressdialog.dismiss")) {
            if(EASProgressDialog.DEBUG) {
               EASLog.d("EASProgressDialog", "ACTION_DISMISS_DIALOG");
            }

            EASProgressDialog var7 = EASProgressDialog.this;
            EASProgressDialog.DialogAction var8 = (EASProgressDialog.DialogAction)var2.getSerializableExtra("extra.easprogressdialog.dialogaction");
            var7.mAction = var8;
            if(EASProgressDialog.this.isShowing()) {
               EASProgressDialog.this.dismiss();
            }
         }
      }
   }

   public static enum DialogAction {

      // $FF: synthetic field
      private static final EASProgressDialog.DialogAction[] $VALUES;
      AUTO_DISCOVER("AUTO_DISCOVER", 3),
      CREATE_ACCOUNT("CREATE_ACCOUNT", 1),
      DELETE_ACCOUNT("DELETE_ACCOUNT", 2),
      NONE("NONE", 0),
      TEST_SERVER("TEST_SERVER", 4);


      static {
         EASProgressDialog.DialogAction[] var0 = new EASProgressDialog.DialogAction[5];
         EASProgressDialog.DialogAction var1 = NONE;
         var0[0] = var1;
         EASProgressDialog.DialogAction var2 = CREATE_ACCOUNT;
         var0[1] = var2;
         EASProgressDialog.DialogAction var3 = DELETE_ACCOUNT;
         var0[2] = var3;
         EASProgressDialog.DialogAction var4 = AUTO_DISCOVER;
         var0[3] = var4;
         EASProgressDialog.DialogAction var5 = TEST_SERVER;
         var0[4] = var5;
         $VALUES = var0;
      }

      private DialogAction(String var1, int var2) {}
   }

   // $FF: synthetic class
   static class 2 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction = new int[EASProgressDialog.DialogAction.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction;
            int var1 = EASProgressDialog.DialogAction.CREATE_ACCOUNT.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction;
            int var3 = EASProgressDialog.DialogAction.DELETE_ACCOUNT.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction;
            int var5 = EASProgressDialog.DialogAction.AUTO_DISCOVER.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$htc$android$mail$easclient$EASProgressDialog$DialogAction;
            int var7 = EASProgressDialog.DialogAction.TEST_SERVER.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var12) {
            ;
         }
      }
   }
}
