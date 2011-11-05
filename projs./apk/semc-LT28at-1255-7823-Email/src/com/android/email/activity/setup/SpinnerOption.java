package com.android.email.activity.setup;

import android.widget.Spinner;

public class SpinnerOption {

   public final String label;
   public final Object value;


   public SpinnerOption(Object var1, String var2) {
      this.value = var1;
      this.label = var2;
   }

   public static void setSpinnerOptionValue(Spinner var0, Object var1) {
      int var2 = 0;

      for(int var3 = var0.getCount(); var2 < var3; ++var2) {
         if(((SpinnerOption)var0.getItemAtPosition(var2)).value.equals(var1)) {
            var0.setSelection(var2, (boolean)1);
            return;
         }
      }

   }

   public String toString() {
      return this.label;
   }
}
