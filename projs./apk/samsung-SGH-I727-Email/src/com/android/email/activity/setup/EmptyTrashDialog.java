package com.android.email.activity.setup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.widget.Toast;
import com.android.email.Controller;
import com.android.email.provider.EmailContent;

public class EmptyTrashDialog extends DialogPreference {

   private long mAccountId;
   private Context mContext;
   private Controller mController;
   private Dialog mDialog;


   public EmptyTrashDialog(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      this.mController = null;
      this.mAccountId = 65535L;
      this.mDialog = null;
   }

   protected void onDialogClosed(boolean var1) {
      if(var1) {
         if(this.mAccountId >= 0L) {
            Context var2 = this.mContext;
            long var3 = this.mAccountId;
            EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(var2, var3);
            if(var5 != null && (var5.mFlags & 32) != 0 && var5.mSecuritySyncKey == null) {
               Context var6 = this.mContext;
               Context var7 = this.mContext;
               Object[] var8 = new Object[1];
               String var9 = var5.getDisplayName();
               var8[0] = var9;
               String var10 = var7.getString(2131167073, var8);
               Toast.makeText(var6, var10, 0).show();
            } else {
               Controller var11 = this.mController;
               long var12 = this.mAccountId;
               var11.emptyTrash(var12);
               if(this.mDialog != null) {
                  this.mDialog.show();
               }
            }
         } else {
            Context var14 = this.mContext;
            Builder var15 = new Builder(var14);
            Builder var16 = var15.setTitle(2131166672);
            EmptyTrashDialog.1 var17 = new EmptyTrashDialog.1();
            Builder var18 = var16.setPositiveButton("Ok", var17).setCancelable((boolean)1);
            AlertDialog var19 = var15.create();
            if(var19 != null) {
               String var20 = this.mContext.getString(2131166678);
               var19.setMessage(var20);
               var19.show();
            }
         }
      }
   }

   void setAccountId(long var1) {
      this.mAccountId = var1;
   }

   void setController(Controller var1) {
      this.mController = var1;
   }

   void setPositiveResultDialog(Dialog var1) {
      this.mDialog = var1;
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {}
   }
}
