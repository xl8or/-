package com.google.android.apps.analytics;

import com.google.android.apps.analytics.CustomVariable;

class CustomVariableBuffer {

   private CustomVariable[] customVariables;


   public CustomVariableBuffer() {
      CustomVariable[] var1 = new CustomVariable[5];
      this.customVariables = var1;
   }

   private void throwOnInvalidIndex(int var1) {
      if(var1 < 1 || var1 > 5) {
         throw new IllegalArgumentException("Index must be between 1 and 5 inclusive.");
      }
   }

   public CustomVariable[] getCustomVariableArray() {
      return (CustomVariable[])this.customVariables.clone();
   }

   public CustomVariable getCustomVariableAt(int var1) {
      this.throwOnInvalidIndex(var1);
      CustomVariable[] var2 = this.customVariables;
      int var3 = var1 + -1;
      return var2[var3];
   }

   public boolean hasCustomVariables() {
      byte var1 = 0;
      int var2 = var1;

      while(true) {
         int var3 = this.customVariables.length;
         if(var2 >= var3) {
            break;
         }

         if(this.customVariables[var2] != false) {
            var1 = 1;
            break;
         }

         ++var2;
      }

      return (boolean)var1;
   }

   public boolean isIndexAvailable(int var1) {
      this.throwOnInvalidIndex(var1);
      CustomVariable[] var2 = this.customVariables;
      int var3 = var1 + -1;
      boolean var4;
      if(var2[var3] == false) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public void setCustomVariable(CustomVariable var1) {
      int var2 = var1.getIndex();
      this.throwOnInvalidIndex(var2);
      CustomVariable[] var3 = this.customVariables;
      int var4 = var1.getIndex() + -1;
      var3[var4] = var1;
   }
}
