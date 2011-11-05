package com.android.email;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.widget.TextView;

public class ExtendedProgressDialog extends ProgressDialog {

   private static final int MAX_FILENAME_SIZE = 300;
   Context mContext;
   private TextView mView = null;


   public ExtendedProgressDialog(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   public ExtendedProgressDialog(Context var1, int var2) {
      super(var1, var2);
   }

   private TextView getTextViewForTitle() {
      return (TextView)this.findViewById(16908299);
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      TextView var2 = this.getTextViewForTitle();
      this.mView = var2;
   }

   public boolean onSearchRequested() {
      return false;
   }

   public void setMessage(CharSequence var1) {
      if(this.mView != null) {
         TextView var2 = this.mView;
         TruncateAt var3 = TruncateAt.END;
         var2.setEllipsize(var3);
         if(this.mContext.getApplicationContext().getResources().getConfiguration().orientation != 2) {
            this.mView.setMaxWidth(300);
         }

         this.mView.setSingleLine((boolean)1);
      }

      super.setMessage(var1);
   }
}
