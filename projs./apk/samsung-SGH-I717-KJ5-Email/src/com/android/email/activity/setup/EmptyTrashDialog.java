package com.android.email.activity.setup;

import android.app.Dialog;
import android.content.Context;
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
         Context var2 = this.mContext;
         long var3 = this.mAccountId;
         EmailContent.Account var5 = EmailContent.Account.restoreAccountWithId(var2, var3);
         if(var5 != null && (var5.mFlags & 32) != 0 && var5.mSecuritySyncKey == null) {
            Context var6 = this.mContext;
            Context var7 = this.mContext;
            Object[] var8 = new Object[1];
            String var9 = var5.getDisplayName();
            var8[0] = var9;
            String var10 = var7.getString(2131167082, var8);
            Toast.makeText(var6, var10, 0).show();
         } else {
            Controller var11 = this.mController;
            long var12 = this.mAccountId;
            var11.emptyTrash(var12);
            if(this.mDialog != null) {
               this.mDialog.show();
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
}
