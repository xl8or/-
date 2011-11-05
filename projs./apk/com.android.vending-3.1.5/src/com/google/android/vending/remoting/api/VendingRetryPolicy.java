package com.google.android.vending.remoting.api;

import com.android.volley.AuthFailureException;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.google.android.vending.remoting.api.VendingApiContext;

public class VendingRetryPolicy extends DefaultRetryPolicy {

   private static final float VENDING_BACKOFF_MULT = 0.0F;
   private static final int VENDING_MAX_RETRIES = 1;
   private static final int VENDING_TIMEOUT_MS = 20000;
   private boolean mHadAuthException;
   private boolean mUseSecureToken;
   private final VendingApiContext mVendingApiContext;


   public VendingRetryPolicy(int var1, int var2, float var3, VendingApiContext var4, boolean var5) {
      super(var1, var2, var3);
      this.mVendingApiContext = var4;
      this.mUseSecureToken = var5;
   }

   public VendingRetryPolicy(VendingApiContext var1, boolean var2) {
      super(20000, 1, 0.0F);
      this.mVendingApiContext = var1;
      this.mUseSecureToken = var2;
   }

   public void retry(NetworkError var1) throws NetworkError {
      if(var1 instanceof AuthFailureException) {
         if(this.mHadAuthException) {
            throw var1;
         }

         this.mHadAuthException = (boolean)1;
         VendingApiContext var2 = this.mVendingApiContext;
         boolean var3 = this.mUseSecureToken;
         var2.invalidateAuthToken(var3);
      }

      super.retry(var1);
   }
}
