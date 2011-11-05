package com.android.common;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

public class Rfc822InputFilter implements InputFilter {

   public Rfc822InputFilter() {}

   public CharSequence filter(CharSequence var1, int var2, int var3, Spanned var4, int var5, int var6) {
      Object var7 = null;
      if(var3 - var2 == 1 && var1.charAt(var2) == 32) {
         int var8 = var5;
         boolean var9 = false;

         while(var8 > 0) {
            var8 += -1;
            switch(var4.charAt(var8)) {
            case 44:
               return (CharSequence)var7;
            case 46:
               var9 = true;
               break;
            case 64:
               if(var9) {
                  if(var1 instanceof Spanned) {
                     var7 = new SpannableStringBuilder(",");
                     ((SpannableStringBuilder)var7).append(var1);
                  } else {
                     var7 = ", ";
                  }

                  return (CharSequence)var7;
               }

               return (CharSequence)var7;
            }
         }
      }

      return (CharSequence)var7;
   }
}
