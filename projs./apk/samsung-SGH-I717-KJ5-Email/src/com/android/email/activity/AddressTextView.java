package com.android.email.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;
import android.widget.AutoCompleteTextView.Validator;

class AddressTextView extends MultiAutoCompleteTextView {

   private AddressTextView.ForwardValidator mInternalValidator;
   private boolean mIsValid = 1;


   public AddressTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
      AddressTextView.ForwardValidator var3 = new AddressTextView.ForwardValidator((AddressTextView.1)null);
      this.mInternalValidator = var3;
      AddressTextView.ForwardValidator var4 = this.mInternalValidator;
      super.setValidator(var4);
   }

   private void markError(boolean var1) {
      if(var1) {
         String var2 = this.getContext().getString(2131166322);
         this.setError(var2);
      } else {
         this.setError((CharSequence)null);
      }
   }

   public void performValidation() {
      this.mIsValid = (boolean)1;
      super.performValidation();
   }

   protected void replaceText(CharSequence var1) {
      String var2 = var1.toString();
      String var3 = this.getResources().getString(2131166829);
      if(!var2.equals(var3)) {
         super.replaceText(var1);
      }
   }

   public void setValidator(Validator var1) {
      this.mInternalValidator.setValidator(var1);
   }

   private class ForwardValidator implements Validator {

      private Validator mValidator;


      private ForwardValidator() {
         this.mValidator = null;
      }

      // $FF: synthetic method
      ForwardValidator(AddressTextView.1 var2) {
         this();
      }

      public CharSequence fixText(CharSequence var1) {
         boolean var2 = (boolean)(AddressTextView.this.mIsValid = (boolean)0);
         return var1;
      }

      public boolean isValid(CharSequence var1) {
         boolean var2;
         if(this.mValidator != null) {
            var2 = this.mValidator.isValid(var1);
         } else {
            var2 = true;
         }

         return var2;
      }

      public void setValidator(Validator var1) {
         this.mValidator = var1;
      }
   }

   // $FF: synthetic class
   static class 1 {
   }
}
