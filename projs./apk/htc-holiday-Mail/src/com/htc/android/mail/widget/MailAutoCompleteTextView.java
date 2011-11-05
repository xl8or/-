package com.htc.android.mail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class MailAutoCompleteTextView extends AutoCompleteTextView {

   private MailAutoCompleteTextView.OnDisMissDropDownListener mDisMissDropDownListener;


   public MailAutoCompleteTextView(Context var1) {
      super(var1);
   }

   public MailAutoCompleteTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public MailAutoCompleteTextView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public void dismissDropDown() {
      super.dismissDropDown();
      if(this.mDisMissDropDownListener != null) {
         this.mDisMissDropDownListener.onDisMiss();
      }
   }

   public void setDisMissDropDownListener(MailAutoCompleteTextView.OnDisMissDropDownListener var1) {
      this.mDisMissDropDownListener = var1;
   }

   public interface OnDisMissDropDownListener {

      void onDisMiss();
   }
}
