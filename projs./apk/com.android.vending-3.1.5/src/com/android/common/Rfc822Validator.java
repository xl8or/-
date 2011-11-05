package com.android.common;

import android.text.TextUtils;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import android.widget.AutoCompleteTextView.Validator;
import java.util.regex.Pattern;

@Deprecated
public class Rfc822Validator implements Validator {

   private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[^\\s@]+@([^\\s@\\.]+\\.)+[a-zA-z][a-zA-Z][a-zA-Z]*");
   private String mDomain;
   private boolean mRemoveInvalid = 0;


   public Rfc822Validator(String var1) {
      this.mDomain = var1;
   }

   private String removeIllegalCharacters(String var1) {
      StringBuilder var2 = new StringBuilder();
      int var3 = var1.length();

      for(int var4 = 0; var4 < var3; ++var4) {
         char var5 = var1.charAt(var4);
         if(var5 > 32 && var5 <= 126 && var5 != 40 && var5 != 41 && var5 != 60 && var5 != 62 && var5 != 64 && var5 != 44 && var5 != 59 && var5 != 58 && var5 != 92 && var5 != 34 && var5 != 91 && var5 != 93) {
            var2.append(var5);
         }
      }

      return var2.toString();
   }

   public CharSequence fixText(CharSequence var1) {
      Object var2;
      if(TextUtils.getTrimmedLength(var1) == 0) {
         var2 = "";
      } else {
         Rfc822Token[] var3 = Rfc822Tokenizer.tokenize(var1);
         var2 = new StringBuilder();
         int var4 = 0;

         while(true) {
            int var5 = var3.length;
            if(var4 >= var5) {
               break;
            }

            String var6 = var3[var4].getAddress();
            if(!this.mRemoveInvalid || this.isValid(var6)) {
               label57: {
                  int var7 = var6.indexOf(64);
                  if(var7 < 0) {
                     if(this.mDomain != null) {
                        Rfc822Token var8 = var3[var4];
                        StringBuilder var9 = new StringBuilder();
                        String var10 = this.removeIllegalCharacters(var6);
                        StringBuilder var11 = var9.append(var10).append("@");
                        String var12 = this.mDomain;
                        String var13 = var11.append(var12).toString();
                        var8.setAddress(var13);
                     }
                  } else {
                     String var19 = var6.substring(0, var7);
                     String var20 = this.removeIllegalCharacters(var19);
                     if(TextUtils.isEmpty(var20)) {
                        break label57;
                     }

                     int var21 = var7 + 1;
                     String var22 = var6.substring(var21);
                     String var23 = this.removeIllegalCharacters(var22);
                     boolean var24;
                     if(var23.length() == 0) {
                        var24 = true;
                     } else {
                        var24 = false;
                     }

                     if(!var24 || this.mDomain != null) {
                        Rfc822Token var25 = var3[var4];
                        StringBuilder var26 = (new StringBuilder()).append(var20).append("@");
                        if(var24) {
                           var23 = this.mDomain;
                        }

                        String var27 = var26.append(var23).toString();
                        var25.setAddress(var27);
                     }
                  }

                  String var14 = var3[var4].toString();
                  ((StringBuilder)var2).append(var14);
                  int var16 = var4 + 1;
                  int var17 = var3.length;
                  if(var16 < var17) {
                     StringBuilder var18 = ((StringBuilder)var2).append(", ");
                  }
               }
            }

            ++var4;
         }
      }

      return (CharSequence)var2;
   }

   public boolean isValid(CharSequence var1) {
      byte var2 = 1;
      Rfc822Token[] var3 = Rfc822Tokenizer.tokenize(var1);
      if(var3.length == var2) {
         Pattern var4 = EMAIL_ADDRESS_PATTERN;
         String var5 = var3[0].getAddress();
         if(var4.matcher(var5).matches()) {
            return (boolean)var2;
         }
      }

      var2 = 0;
      return (boolean)var2;
   }

   public void setRemoveInvalid(boolean var1) {
      this.mRemoveInvalid = var1;
   }
}
