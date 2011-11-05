package com.google.android.finsky.api;

import com.android.volley.AuthFailureException;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.google.android.finsky.api.DfeApiContext;

public class DfeRetryPolicy extends DefaultRetryPolicy {

   private final DfeApiContext mDfeApiContext;
   private boolean mHadAuthException;


   public DfeRetryPolicy(int var1, int var2, float var3, DfeApiContext var4) {
      super(var1, var2, var3);
      this.mDfeApiContext = var4;
   }

   public DfeRetryPolicy(DfeApiContext var1) {
      this.mDfeApiContext = var1;
   }

   public void retry(NetworkError var1) throws NetworkError {
      if(var1 instanceof AuthFailureException) {
         if(this.mHadAuthException) {
            throw var1;
         }

         this.mHadAuthException = (boolean)1;
         this.mDfeApiContext.invalidateAuthToken();
      }

      super.retry(var1);
   }
}
