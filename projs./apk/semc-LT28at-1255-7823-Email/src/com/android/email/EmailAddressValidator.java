package com.android.email;

import android.widget.AutoCompleteTextView.Validator;
import com.android.email.mail.Address;

public class EmailAddressValidator implements Validator {

   public EmailAddressValidator() {}

   public CharSequence fixText(CharSequence var1) {
      return "";
   }

   public boolean isValid(CharSequence var1) {
      String var2 = var1.toString();
      boolean[] var3 = new boolean[0];
      boolean var4;
      if(Address.parse(var2, var3).length > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }
}
