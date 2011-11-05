package com.facebook.katana.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import com.facebook.katana.ComposerActivity;
import com.facebook.katana.util.Utils;

public class ComposerEditText extends EditText {

   protected static final String TAG = Utils.getClassName(ComposerEditText.class);


   public ComposerEditText(Context var1) {
      super(var1);
   }

   public ComposerEditText(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public void extendSelection(int var1) {
      int var2 = ComposerActivity.getEndIndex(this.getText());
      int var3 = Math.min(var1, var2);
      super.extendSelection(var3);
   }

   protected void onSelectionChanged(int var1, int var2) {
      int var3 = ComposerActivity.getEndIndex(this.getText());
      if(var2 <= var3 && var1 <= var3) {
         if(var1 == -1 || var2 == -1) {
            this.setSelection(0, 0);
         }
      } else {
         this.setSelection(var1, var2);
      }
   }

   public void setSelection(int var1) {
      int var2 = ComposerActivity.getEndIndex(this.getText());
      int var3 = Math.min(var1, var2);
      super.setSelection(var3);
   }

   public void setSelection(int var1, int var2) {
      int var3 = ComposerActivity.getEndIndex(this.getText());
      int var4 = Math.min(var1, var3);
      int var5 = Math.min(var2, var3);
      super.setSelection(var4, var5);
   }
}
