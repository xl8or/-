package com.android.email;

import android.widget.AutoCompleteTextView.Validator;
import com.android.email.mail.Address;

public class EmailAddressValidator implements Validator {

   public EmailAddressValidator() {}

   public CharSequence fixText(CharSequence var1) {
      return "";
   }

   public boolean isValid(CharSequence var1) {
      boolean var2;
      if(Address.parse(var1.toString()).length > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
}
