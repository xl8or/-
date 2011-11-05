package com.htc.android.mail.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.AsyncTask;
import android.os.Handler;
import com.htc.android.mail.ll;
import com.htc.app.HtcProgressDialog;
import java.lang.ref.WeakReference;

public class DelayedProgressDialogTask<Result extends Object, MyActivity extends Activity, MyHandler extends Handler> extends AsyncTask<Void, Void, Result> {

   private static final String TAG = "DelayedProgressDialogTask";
   private HtcProgressDialog mDialog;
   protected boolean mIsCanceled;
   private Boolean mIsNotified;
   private Runnable mShowDialogRunnable;
   protected final WeakReference<MyActivity> mTarget;
   private WeakReference<MyHandler> mWeakHandler;


   public DelayedProgressDialogTask(MyActivity var1, MyHandler var2) {
      Boolean var3 = Boolean.valueOf((boolean)0);
      this.mIsNotified = var3;
      this.mIsCanceled = (boolean)0;
      WeakReference var4 = new WeakReference(var1);
      this.mTarget = var4;
      WeakReference var5 = new WeakReference(var2);
      this.mWeakHandler = var5;
      HtcProgressDialog var6 = new HtcProgressDialog(var1);
      this.mDialog = var6;
      HtcProgressDialog var7 = this.mDialog;
      CharSequence var8 = var1.getText(2131362428);
      var7.setMessage(var8);
      this.mDialog.setCancelable((boolean)1);
      HtcProgressDialog var9 = this.mDialog;
      DelayedProgressDialogTask.1 var10 = new DelayedProgressDialogTask.1();
      var9.setOnDismissListener(var10);
      DelayedProgressDialogTask.2 var11 = new DelayedProgressDialogTask.2();
      this.mShowDialogRunnable = var11;
   }

   protected Result doHeavyTask() {
      return null;
   }

   protected Result doInBackground(Void ... param1) {
      // $FF: Couldn't be decompiled
   }

   protected void doPostTask(Result var1) {}

   protected void onPostExecute(Result var1) {
      Activity var2 = (Activity)this.mTarget.get();
      if(var2 != null) {
         Handler var3 = (Handler)this.mWeakHandler.get();
         if(var3 != null) {
            Runnable var4 = this.mShowDialogRunnable;
            var3.removeCallbacks(var4);
            if(!var2.isFinishing()) {
               this.mDialog.dismiss();
               this.doPostTask(var1);
            }
         }
      }
   }

   protected void onPreExecute() {
      if((Activity)this.mTarget.get() != null) {
         Handler var1 = (Handler)this.mWeakHandler.get();
         if(var1 != null) {
            Runnable var2 = this.mShowDialogRunnable;
            var1.postDelayed(var2, 1000L);
         }
      }
   }

   protected void onProgressUpdate(Void ... param1) {
      // $FF: Couldn't be decompiled
   }

   class 2 implements Runnable {

      2() {}

      public void run() {
         if(!((Activity)DelayedProgressDialogTask.this.mTarget.get()).isFinishing()) {
            DelayedProgressDialogTask.this.mDialog.show();
         } else {
            ll.i("DelayedProgressDialogTask", "target is going finished");
         }
      }
   }

   class 1 implements OnDismissListener {

      1() {}

      public void onDismiss(DialogInterface var1) {
         DelayedProgressDialogTask.this.mIsCanceled = (boolean)1;
      }
   }
}
