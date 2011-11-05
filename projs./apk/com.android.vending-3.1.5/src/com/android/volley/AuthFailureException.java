package com.android.volley;

import android.content.Intent;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;

public class AuthFailureException extends NetworkError {

   private Intent mResolutionIntent;


   public AuthFailureException() {}

   public AuthFailureException(Intent var1) {
      this.mResolutionIntent = var1;
   }

   public AuthFailureException(NetworkResponse var1) {
      super(var1);
   }

   public AuthFailureException(String var1) {
      super(var1);
   }

   public AuthFailureException(String var1, Exception var2) {
      super(var1, var2);
   }

   public String getMessage() {
      String var1;
      if(this.mResolutionIntent != null) {
         var1 = "User needs to (re)enter credentials.";
      } else {
         var1 = super.getMessage();
      }

      return var1;
   }

   public Intent getResolutionIntent() {
      return this.mResolutionIntent;
   }
}
