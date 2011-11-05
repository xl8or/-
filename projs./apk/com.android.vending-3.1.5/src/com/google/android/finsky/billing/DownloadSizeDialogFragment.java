package com.google.android.finsky.billing;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.google.android.finsky.utils.FinskyLog;

public class DownloadSizeDialogFragment extends DialogFragment {

   public DownloadSizeDialogFragment() {}

   public static DownloadSizeDialogFragment newInstance(Fragment var0) {
      if(!(var0 instanceof DownloadSizeDialogFragment.DownloadSizeDialogListener)) {
         throw new IllegalArgumentException("targetFragment must implement DownloadSizeDialogListener");
      } else {
         DownloadSizeDialogFragment var1 = new DownloadSizeDialogFragment();
         var1.setTargetFragment(var0, -1);
         return var1;
      }
   }

   public void onCancel(DialogInterface var1) {
      super.onCancel(var1);
      if(this.getTargetFragment() instanceof DownloadSizeDialogFragment.DownloadSizeDialogListener) {
         ((DownloadSizeDialogFragment.DownloadSizeDialogListener)this.getTargetFragment()).onDownloadCancel();
      } else {
         Object[] var2 = new Object[0];
         FinskyLog.wtf("Target fragment does not implement DownloadSizeDialogListener.", var2);
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Builder var3 = (new Builder(var2)).setTitle(2131231042).setMessage(2131231043);
      DownloadSizeDialogFragment.2 var4 = new DownloadSizeDialogFragment.2();
      Builder var5 = var3.setPositiveButton(2131231030, var4);
      DownloadSizeDialogFragment.1 var6 = new DownloadSizeDialogFragment.1();
      return var5.setNegativeButton(2131230813, var6).create();
   }

   public void onDismiss(DialogInterface var1) {
      super.onDismiss(var1);
      if(this.getTargetFragment() instanceof DownloadSizeDialogFragment.DownloadSizeDialogListener) {
         ((DownloadSizeDialogFragment.DownloadSizeDialogListener)this.getTargetFragment()).onDownloadCancel();
      } else {
         Object[] var2 = new Object[0];
         FinskyLog.wtf("Target fragment does not implement DownloadSizeDialogListener.", var2);
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         ((DownloadSizeDialogFragment.DownloadSizeDialogListener)DownloadSizeDialogFragment.this.getTargetFragment()).onDownloadCancel();
      }
   }

   public interface DownloadSizeDialogListener {

      void onDownloadCancel();

      void onDownloadOk();
   }

   class 2 implements OnClickListener {

      2() {}

      public void onClick(DialogInterface var1, int var2) {
         ((DownloadSizeDialogFragment.DownloadSizeDialogListener)DownloadSizeDialogFragment.this.getTargetFragment()).onDownloadOk();
      }
   }
}
