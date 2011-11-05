package com.android.settings;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

class EditPinPreference extends EditTextPreference {

   private EditPinPreference.OnPinEnteredListener mPinListener;


   public EditPinPreference(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   public EditPinPreference(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
   }

   public boolean isDialogOpen() {
      Dialog var1 = this.getDialog();
      boolean var2;
      if(var1 != null && var1.isShowing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected void onBindDialogView(View var1) {
      super.onBindDialogView(var1);
      EditText var2 = (EditText)var1.findViewById(16908291);
      if(var2 != null) {
         var2.setSingleLine((boolean)1);
         PasswordTransformationMethod var3 = PasswordTransformationMethod.getInstance();
         var2.setTransformationMethod(var3);
         DigitsKeyListener var4 = DigitsKeyListener.getInstance();
         var2.setKeyListener(var4);
         int var5 = var2.getInputType() | 128;
         var2.setInputType(var5);
      }
   }

   protected void onDialogClosed(boolean var1) {
      super.onDialogClosed(var1);
      if(this.mPinListener != null) {
         this.mPinListener.onPinEntered(this, var1);
      }
   }

   public void setOnPinEnteredListener(EditPinPreference.OnPinEnteredListener var1) {
      this.mPinListener = var1;
   }

   public void showPinDialog() {
      Dialog var1 = this.getDialog();
      if(var1 == null || !var1.isShowing()) {
         this.showDialog((Bundle)null);
      }
   }

   interface OnPinEnteredListener {

      void onPinEntered(EditPinPreference var1, boolean var2);
   }
}
