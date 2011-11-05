package com.android.volley;

import com.android.volley.NetworkError;
import com.android.volley.RetryPolicy;

public class DefaultRetryPolicy implements RetryPolicy {

   private static final float DEFAULT_BACKOFF_MULT = 0.5F;
   private static final int DEFAULT_MAX_RETRIES = 1;
   private static final int DEFAULT_TIMEOUT_MS = 2000;
   private final float mBackoffMultiplier;
   private int mCurrentRetryCount;
   private int mCurrentTimeoutMs;
   private final int mMaxNumRetries;


   public DefaultRetryPolicy() {
      this(2000, 1, 0.5F);
   }

   public DefaultRetryPolicy(int var1, int var2, float var3) {
      this.mCurrentTimeoutMs = var1;
      this.mMaxNumRetries = var2;
      this.mBackoffMultiplier = var3;
   }

   public int getCurrentRetryCount() {
      return this.mCurrentRetryCount;
   }

   public int getCurrentTimeout() {
      return this.mCurrentTimeoutMs;
   }

   protected boolean hasAttemptRemaining() {
      int var1 = this.mCurrentRetryCount;
      int var2 = this.mMaxNumRetries;
      boolean var3;
      if(var1 <= var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void retry(NetworkError var1) throws NetworkError {
      int var2 = this.mCurrentRetryCount + 1;
      this.mCurrentRetryCount = var2;
      float var3 = (float)this.mCurrentTimeoutMs;
      float var4 = (float)this.mCurrentTimeoutMs;
      float var5 = this.mBackoffMultiplier;
      float var6 = var4 * var5;
      int var7 = (int)(var3 + var6);
      this.mCurrentTimeoutMs = var7;
      if(!this.hasAttemptRemaining()) {
         throw var1;
      }
   }
}
