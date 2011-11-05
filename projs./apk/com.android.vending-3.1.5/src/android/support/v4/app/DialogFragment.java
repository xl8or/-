package android.support.v4.app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

public class DialogFragment extends Fragment implements OnCancelListener, OnDismissListener {

   private static final String SAVED_BACK_STACK_ID = "android:backStackId";
   private static final String SAVED_CANCELABLE = "android:cancelable";
   private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
   private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
   private static final String SAVED_STYLE = "android:style";
   private static final String SAVED_THEME = "android:theme";
   public static final int STYLE_NORMAL = 0;
   public static final int STYLE_NO_FRAME = 2;
   public static final int STYLE_NO_INPUT = 3;
   public static final int STYLE_NO_TITLE = 1;
   int mBackStackId = -1;
   boolean mCancelable = 1;
   boolean mDestroyed;
   Dialog mDialog;
   boolean mRemoved;
   boolean mShowsDialog = 1;
   int mStyle = 0;
   int mTheme = 0;


   public DialogFragment() {}

   public void dismiss() {
      this.dismissInternal((boolean)0);
   }

   void dismissInternal(boolean var1) {
      if(this.mDialog != null) {
         this.mDialog.dismiss();
         this.mDialog = null;
      }

      this.mRemoved = (boolean)1;
      if(this.mBackStackId >= 0) {
         FragmentManager var2 = this.getFragmentManager();
         int var3 = this.mBackStackId;
         var2.popBackStack(var3, 1);
         this.mBackStackId = -1;
      } else {
         FragmentTransaction var4 = this.getFragmentManager().beginTransaction();
         var4.remove(this);
         if(var1) {
            int var6 = var4.commitAllowingStateLoss();
         } else {
            int var7 = var4.commit();
         }
      }
   }

   public Dialog getDialog() {
      return this.mDialog;
   }

   public LayoutInflater getLayoutInflater(Bundle var1) {
      LayoutInflater var2;
      if(!this.mShowsDialog) {
         var2 = super.getLayoutInflater(var1);
      } else {
         Dialog var3 = this.onCreateDialog(var1);
         this.mDialog = var3;
         this.mDestroyed = (boolean)0;
         switch(this.mStyle) {
         case 3:
            this.mDialog.getWindow().addFlags(24);
         case 1:
         case 2:
            boolean var4 = this.mDialog.requestWindowFeature(1);
         default:
            var2 = (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
         }
      }

      return var2;
   }

   public boolean getShowsDialog() {
      return this.mShowsDialog;
   }

   public int getTheme() {
      return this.mTheme;
   }

   public boolean isCancelable() {
      return this.mCancelable;
   }

   public void onActivityCreated(Bundle var1) {
      super.onActivityCreated(var1);
      if(this.mShowsDialog) {
         View var2 = this.getView();
         if(var2 != null) {
            if(var2.getParent() != null) {
               throw new IllegalStateException("DialogFragment can not be attached to a container view");
            }

            this.mDialog.setContentView(var2);
         }

         Dialog var3 = this.mDialog;
         FragmentActivity var4 = this.getActivity();
         var3.setOwnerActivity(var4);
         Dialog var5 = this.mDialog;
         boolean var6 = this.mCancelable;
         var5.setCancelable(var6);
         this.mDialog.setOnCancelListener(this);
         this.mDialog.setOnDismissListener(this);
         if(var1 != null) {
            Bundle var7 = var1.getBundle("android:savedDialogState");
            if(var7 != null) {
               this.mDialog.onRestoreInstanceState(var7);
            }
         }
      }
   }

   public void onCancel(DialogInterface var1) {}

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      byte var2;
      if(this.mContainerId == 0) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      this.mShowsDialog = (boolean)var2;
      if(var1 != null) {
         int var3 = var1.getInt("android:style", 0);
         this.mStyle = var3;
         int var4 = var1.getInt("android:theme", 0);
         this.mTheme = var4;
         boolean var5 = var1.getBoolean("android:cancelable", (boolean)1);
         this.mCancelable = var5;
         boolean var6 = this.mShowsDialog;
         boolean var7 = var1.getBoolean("android:showsDialog", var6);
         this.mShowsDialog = var7;
         int var8 = var1.getInt("android:backStackId", -1);
         this.mBackStackId = var8;
      }
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      int var3 = this.getTheme();
      return new Dialog(var2, var3);
   }

   public void onDestroyView() {
      super.onDestroyView();
      this.mDestroyed = (boolean)1;
      if(this.mDialog != null) {
         this.mRemoved = (boolean)1;
         this.mDialog.dismiss();
         this.mDialog = null;
      }
   }

   public void onDismiss(DialogInterface var1) {
      if(!this.mRemoved) {
         this.dismissInternal((boolean)1);
      }
   }

   public void onSaveInstanceState(Bundle var1) {
      super.onSaveInstanceState(var1);
      if(this.mDialog != null) {
         Bundle var2 = this.mDialog.onSaveInstanceState();
         if(var2 != null) {
            var1.putBundle("android:savedDialogState", var2);
         }
      }

      if(this.mStyle != 0) {
         int var3 = this.mStyle;
         var1.putInt("android:style", var3);
      }

      if(this.mTheme != 0) {
         int var4 = this.mTheme;
         var1.putInt("android:theme", var4);
      }

      if(!this.mCancelable) {
         boolean var5 = this.mCancelable;
         var1.putBoolean("android:cancelable", var5);
      }

      if(!this.mShowsDialog) {
         boolean var6 = this.mShowsDialog;
         var1.putBoolean("android:showsDialog", var6);
      }

      if(this.mBackStackId != -1) {
         int var7 = this.mBackStackId;
         var1.putInt("android:backStackId", var7);
      }
   }

   public void onStart() {
      super.onStart();
      if(this.mDialog != null) {
         this.mRemoved = (boolean)0;
         this.mDialog.show();
      }
   }

   public void onStop() {
      super.onStop();
      if(this.mDialog != null) {
         this.mDialog.hide();
      }
   }

   public void setCancelable(boolean var1) {
      this.mCancelable = var1;
      if(this.mDialog != null) {
         this.mDialog.setCancelable(var1);
      }
   }

   public void setShowsDialog(boolean var1) {
      this.mShowsDialog = var1;
   }

   public void setStyle(int var1, int var2) {
      this.mStyle = var1;
      if(this.mStyle == 2 || this.mStyle == 3) {
         this.mTheme = 16973913;
      }

      if(var2 != 0) {
         this.mTheme = var2;
      }
   }

   public int show(FragmentTransaction var1, String var2) {
      var1.add(this, var2);
      this.mRemoved = (boolean)0;
      int var4 = var1.commit();
      this.mBackStackId = var4;
      return this.mBackStackId;
   }

   public void show(FragmentManager var1, String var2) {
      FragmentTransaction var3 = var1.beginTransaction();
      var3.add(this, var2);
      int var5 = var3.commit();
   }
}
