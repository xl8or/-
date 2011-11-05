package com.google.android.gsf;

import android.util.AndroidException;
import com.google.android.gsf.GoogleLoginServiceConstants;

public class GoogleLoginServiceNotFoundException extends AndroidException {

   private int mErrorCode;


   public GoogleLoginServiceNotFoundException(int var1) {
      String var2 = GoogleLoginServiceConstants.getErrorCodeMessage(var1);
      super(var2);
      this.mErrorCode = var1;
   }

   int getErrorCode() {
      return this.mErrorCode;
   }
}
